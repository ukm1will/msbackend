package stableford;

import data.ViewData5337;
import data.ViewData5361;
import models.Competition;
import models.Golfer;
import org.junit.Test;
import service.StringHelper;

import static enums.ScoringSystem.STABLEFORD;
import static org.junit.Assert.assertEquals;

public class View5361 {

    private final String currentDataFile = ViewData5361.WHOLE_PAGE;
    private Competition competition = new Competition(currentDataFile);
    private String beforePart;
    private String afterPart;

    public View5361() throws Exception {
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
        assertEquals(61, competition.results.size());
    }

    @Test
    public void ShouldAddGolfersToCompetition() {
        String activeData = StringHelper.splitBeforeAndAfter(currentDataFile, beforePart, afterPart);
        competition.addResultsToCompetition(activeData);
        competition.addGolfersToCompetition();
        assertEquals(59, competition.golfers.size());
    }

    @Test
    public void ShouldHaveCorrectDataForPaulWilliams() {
        String activeData = StringHelper.splitBeforeAndAfter(currentDataFile, beforePart, afterPart);
        competition.addResultsToCompetition(activeData);
        competition.addGolfersToCompetition();
        Golfer paulWilliams = competition.find("Paul Williams");
        assertEquals("Paul Williams", paulWilliams.getFullName());
        assertEquals(87, paulWilliams.getGross());
        assertEquals(72, paulWilliams.getNett());
        assertEquals(15, paulWilliams.getHandicap());
        assertEquals(36, paulWilliams.getPts());
    }

    @Test
    public void ShouldHaveCorrectSurnameForPaulWilliams()  {
        String activeData = StringHelper.splitBeforeAndAfter(currentDataFile, beforePart, afterPart);
        competition.addResultsToCompetition(activeData);
        competition.addGolfersToCompetition();
        Golfer paulWilliams = competition.find("Paul Williams");
        assertEquals("Williams", paulWilliams.getSurname());
    }


    @Test
    public void PaulWilliamsShouldHaveCorrectPlacingAndCountback() {
        String activeData = StringHelper.splitBeforeAndAfter(currentDataFile, beforePart, afterPart);
        competition.addResultsToCompetition(activeData);
        competition.addGolfersToCompetition();
        Golfer paulWilliams = competition.find("Paul Williams");
        assertEquals("Overall Runner-Up", paulWilliams.getPlacing());
        assertEquals("Last Three Holes", paulWilliams.getCountback());
    }


    @Test
    public void ChrisHolwillShouldHaveCorrectPlacingAndCountback() {
        String activeData = StringHelper.splitBeforeAndAfter(currentDataFile, beforePart, afterPart);
        competition.addResultsToCompetition(activeData);
        competition.addGolfersToCompetition();
        Golfer chrisHolwill = competition.find("Chris Holwill");
        assertEquals("", chrisHolwill.getPlacing());
        assertEquals("", chrisHolwill.getCountback());
    }
}