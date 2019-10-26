package com;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import enums.DataResponseType;
import models.CompetitionMetadata;
import models.HTMLToCompetitionMetaDataConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static service.LoginService.autoLogin;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String sayHello() {
        return "Hello from Backend";
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


    private String getDataSource(String url, DataResponseType dataResponseType) throws IOException {
        String password = "swanseabay";
        WebClient webClient = autoLogin(url, password);
        HtmlPage page = webClient.getPage(url);
        return dataResponseType == DataResponseType.HTML ? page.getWebResponse().getContentAsString() : page.asText();
    }


}
