package stableford;

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

    public View5361() throws Exception {
        String beforePart = StringHelper.getBeforePart(competition.getMasterScoreboardFormat());
        String afterPart = "Number of Cards Processed";
        String activeData = StringHelper.splitBeforeAndAfter(currentDataFile, beforePart, afterPart);
        competition.addResultsToCompetition(activeData);
        competition.addGolfersToCompetition();
    }

    @Test
    public void ShouldBeStablefordCompetition() {
        assertEquals(STABLEFORD, competition.getScoringSystem());
    }

    @Test
    public void ShouldHaveCorrectNofResults() {
        assertEquals(61, competition.results.size());
    }

    @Test
    public void ShouldHaveCorrectNofGolfers() {

        assertEquals(59, competition.golfers.size());
    }

    @Test
    public void ShouldHaveCorrectData() {

        Golfer paulWilliams = competition.find("Paul Williams");
        assertEquals("Paul Williams", paulWilliams.getFullName());
        assertEquals(87, paulWilliams.getGross());
        assertEquals(72, paulWilliams.getNett());
        assertEquals(15, paulWilliams.getHandicap());
        assertEquals(36, paulWilliams.getPts());
    }

    @Test
    public void ShouldHaveCorrectSurname() {
        Golfer paulWilliams = competition.find("Paul Williams");
        assertEquals("Williams", paulWilliams.getSurname());
    }


    @Test
    public void ShouldHaveCorrectPlacingAndCountbackOne() {
        Golfer paulWilliams = competition.find("Paul Williams");
        assertEquals("Overall Runner-Up", paulWilliams.getPlacing());
        assertEquals("Last Three Holes", paulWilliams.getCountback());
    }


    @Test
    public void ShouldHaveCorrectPlacingAndCountbackTwo() {
        Golfer chrisHolwill = competition.find("Chris Holwill");
        assertEquals("", chrisHolwill.getPlacing());
        assertEquals("", chrisHolwill.getCountback());
    }
}