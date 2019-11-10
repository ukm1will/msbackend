package stableford;

import data.ViewData5337;
import models.Competition;
import models.Golfer;
import org.junit.Test;
import service.StringHelper;

import static enums.ScoringSystem.STABLEFORD;
import static org.junit.Assert.assertEquals;

public class View5337 {

    private final String currentDataFile = ViewData5337.WHOLE_PAGE;
    private Competition competition = new Competition(currentDataFile);
    private String beforePart;
    private String afterPart;
    private String activeData;

    public View5337() throws Exception {
        beforePart = StringHelper.getBeforePart(competition.getMasterScoreboardFormat());
        afterPart = "Number of Cards Processed";
        activeData = StringHelper.splitBeforeAndAfter(currentDataFile, beforePart, afterPart);
        competition.addResultsToCompetition(activeData);
        competition.addGolfersToCompetition();
    }

    @Test
    public void ShouldBeStablefordCompetition() {
        assertEquals(STABLEFORD, competition.getScoringSystem());
    }

    @Test
    public void ShouldHaveCorrectNofResults() {
        assertEquals(22, competition.results.size());
    }

    @Test
    public void ShouldHaveCorrectNofGolfers() throws Exception {
        assertEquals(22, competition.golfers.size());
    }

    @Test
    public void ShouldHaveCorrectDataOne() throws Exception {
        Golfer guydonCerasuolo = competition.find("Guydon Cerasuolo");
        assertEquals("Guydon Cerasuolo", guydonCerasuolo.getFullName());
        assertEquals(101, guydonCerasuolo.getGross());
        assertEquals(80, guydonCerasuolo.getNett());
        assertEquals(21, guydonCerasuolo.getHandicap());
        assertEquals(28, guydonCerasuolo.getPts());
    }

    @Test
    public void ShouldHaveCorrectDataTwo() throws Exception {
        Golfer edMorgan = competition.find("Ednyfed O. Morgan");
        assertEquals("Ednyfed O. Morgan", edMorgan.getFullName());
        assertEquals(5, edMorgan.getPosition());
        assertEquals(86, edMorgan.getGross());
        assertEquals(76, edMorgan.getNett());
        assertEquals(10, edMorgan.getHandicap());
        assertEquals(32, edMorgan.getPts());

    }

    @Test
    public void ShouldHaveCorrectSurname() throws Exception {
        Golfer guydonCerasuolo = competition.find("Guydon Cerasuolo");
        assertEquals("Cerasuolo", guydonCerasuolo.getSurname());
    }


    @Test
    public void ShouldHaveCorrectPlacingAndCountbackOne() {
        Golfer bDobson = competition.find("B. M. Dobson");
        assertEquals("Overall Winner", bDobson.getPlacing());
        assertEquals("", bDobson.getCountback());
    }

    @Test
    public void  ShouldHaveCorrectPlacingAndCountbackTwo() {
        Golfer colinHarris = competition.find("Colin Harris");
        assertEquals("", colinHarris.getPlacing());
        assertEquals("Last Nine Holes", colinHarris.getCountback());
    }
}