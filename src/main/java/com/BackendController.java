package com;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import enums.DataResponseType;
import models.Competition;
import models.CompetitionMetadata;
import models.Golfer;
import models.HTMLToCompetitionMetaDataConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.StringHelper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static service.LoginService.autoLogin;

@RestController
public class BackendController {
    @RequestMapping("/health")
    public String getHealth() {
        return "Backend is running...";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/url", produces = "application/json")
    public List<CompetitionMetadata> getMasterScoreboardHomePage() throws IOException {
        String msHomePage = "http://masterscoreboard.co.uk/ListOfCompetitions.php?CWID=5142&Gender=M";
        String dataSource = getDataSource(msHomePage, DataResponseType.HTML);
        HTMLToCompetitionMetaDataConverter urlConverter = new HTMLToCompetitionMetaDataConverter(dataSource);
        urlConverter.convertRawDataToArrayList();
        urlConverter.removeUnwantedRowsFromList();
        urlConverter.extractCompetitionData();
        urlConverter.concatenateList();
        urlConverter.createListofCompetitionMetaData();
        return urlConverter.getCompetitionMetadata();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/view/{viewId}", produces = "application/json")
    public List<Golfer> getView(@PathVariable("viewId") final int viewId) throws Exception {
        String url = "http://masterscoreboard.co.uk/results/Result.php?CWID=5142&View=" + viewId;
        String dataSource = getDataSource(url, DataResponseType.TEXT);
        Competition competition = new Competition(dataSource);
        String beforePart = StringHelper.getBeforePart(competition.getMasterScoreboardFormat());
        dataSource = StringHelper.splitBeforeAndAfter(dataSource, beforePart, "Number of Cards Processed");
        competition.addResultsToCompetition(dataSource);
        competition.addGolfersToCompetition();
        Collections.sort(competition.golfers);
        competition.updateRankings();
        return competition.golfers;
    }

    private String getDataSource(String url, DataResponseType dataResponseType) throws IOException {
        String password = "swanseabay";
        WebClient webClient = autoLogin(url, password);
        HtmlPage page = webClient.getPage(url);
        return dataResponseType == DataResponseType.HTML ? page.getWebResponse().getContentAsString() : page.asText();
    }
}


