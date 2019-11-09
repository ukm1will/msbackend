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

    public View5337() throws Exception {
        beforePart = StringHelper.getBeforePart(competition.getMasterScoreboardFormat());
        afterPart = "Number of Cards Processed";
    }

    @Test
    public void CompetitionShouldBeStableford() {
        assertEquals(STABLEFORD, competition.getScoringSystem());
    }

    @Test
    public void ShouldAddResultsToCompetition() {
        String activeData = StringHelper.splitBeforeAndAfter(currentDataFile, beforePart, afterPart);
        competition.addResultsToCompetition(activeData);
        assertEquals(22, competition.results.size());
    }

    @Test
    public void ShouldAddGolfersToCompetition() throws Exception {
        String activeData = StringHelper.splitBeforeAndAfter(currentDataFile, beforePart, afterPart);
        competition.addResultsToCompetition(activeData);
        competition.addGolfersToCompetition();
        assertEquals(22, competition.golfers.size());
    }

    @Test
    public void ShouldHaveCorrectDataForGuydonCerasuolo() throws Exception {
        String activeData = StringHelper.splitBeforeAndAfter(currentDataFile, beforePart, afterPart);
        competition.addResultsToCompetition(activeData);
        competition.addGolfersToCompetition();
        Golfer guydonCerasuolo = competition.find("Guydon Cerasuolo");
        assertEquals("Guydon Cerasuolo", guydonCerasuolo.getFullName());
        assertEquals(101, guydonCerasuolo.getGross());
        assertEquals(80, guydonCerasuolo.getNett());
        assertEquals(21, guydonCerasuolo.getHandicap());
        assertEquals(28, guydonCerasuolo.getPts());
    }

    @Test
    public void ShouldHaveCorrectDataForEdnyfedMorgan() throws Exception {
        String activeData = StringHelper.splitBeforeAndAfter(currentDataFile, beforePart, afterPart);
        competition.addResultsToCompetition(activeData);
        competition.addGolfersToCompetition();
        Golfer edMorgan = competition.find("Ednyfed O. Morgan");
        assertEquals("Ednyfed O. Morgan", edMorgan.getFullName());
        assertEquals(5, edMorgan.getPosition());
        assertEquals(86, edMorgan.getGross());
        assertEquals(76, edMorgan.getNett());
        assertEquals(10, edMorgan.getHandicap());
        assertEquals(32, edMorgan.getPts());

    }

    @Test
    public void ShouldHaveCorrectSurnameForGuydonCerasuolo() throws Exception {
        String activeData = StringHelper.splitBeforeAndAfter(currentDataFile, beforePart, afterPart);
        competition.addResultsToCompetition(activeData);
        competition.addGolfersToCompetition();
        Golfer guydonCerasuolo = competition.find("Guydon Cerasuolo");
        assertEquals("Cerasuolo", guydonCerasuolo.getSurname());
    }

    @Test
    public void BMDobsonShouldHaveCorrectPlacingAndCountback() {
        String activeData = StringHelper.splitBeforeAndAfter(currentDataFile, beforePart, afterPart);
        competition.addResultsToCompetition(activeData);
        competition.addGolfersToCompetition();
        Golfer bDobson = competition.find("B. M. Dobson");
        assertEquals("Overall Winner", bDobson.getPlacing());
        assertEquals("", bDobson.getCountback());
    }

    @Test
    public void ColinHarrisShouldHaveCorrectPlacingAndCountback() {
        String activeData = StringHelper.splitBeforeAndAfter(currentDataFile, beforePart, afterPart);
        competition.addResultsToCompetition(activeData);
        competition.addGolfersToCompetition();
        Golfer colinHarris = competition.find("Colin Harris");
        assertEquals("", colinHarris.getPlacing());
        assertEquals("Last Nine Holes", colinHarris.getCountback());
    }
}