package models;

import service.StringHelper;

import java.util.List;

public class CompetitionMetadata {

    private String dateOfCompetition;
    private String competitionTitle;
    private int viewId;


    public void setDateOfCompetition(String dateOfCompetition) {
        this.dateOfCompetition = dateOfCompetition;
    }

    public void setCompetitionTitle(String competitionTitle) {
        this.competitionTitle = competitionTitle;
    }


    public static String toString(List<CompetitionMetadata> urls) {
        StringBuilder sb = new StringBuilder();
        for (CompetitionMetadata compUrl: urls) {
            sb.append(compUrl.toString());
        }
        return sb.toString();
    }


    CompetitionMetadata(String str) {
        this.dateOfCompetition = StringHelper.removeAfter(str, "<a");
        this.competitionTitle = StringHelper.splitBeforeAndAfter(str, "'>", "</a>");
        this.viewId = Integer.parseInt(StringHelper.splitBeforeAndAfter(str, "View=", "'>"));
    }

    public CompetitionMetadata() {
    }

    public String getDateOfCompetition() {
        return dateOfCompetition;
    }

    public String getCompetitionTitle() {
        return competitionTitle;
    }


    public int getViewId() {
        return this.viewId;
    }
}

// TODO: tidy up this file, check if we need setDate etc, constructor