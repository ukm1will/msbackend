package models;

import enums.MasterScoreboardFormat;
import enums.ScoringSystem;
import service.Split;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Competition {

    public List<Golfer> golfers = new ArrayList<>();
    public List<String> results = new ArrayList<>();
    private MasterScoreboardFormat masterScoreboardFormat;
    private ScoringSystem scoringSystem;

    public Competition(String currentDataFile) {
        this.setScoringSystem(currentDataFile);
        this.setMasterScoreboardFormat(currentDataFile);
    }

    public void setMasterScoreboardFormat(String dataSource) {
        if(dataSource.contains("Revised")) {
            this.masterScoreboardFormat = MasterScoreboardFormat.MS_OLD;
        }
        else
            this.masterScoreboardFormat = MasterScoreboardFormat.MS_NEW;
    }

    public MasterScoreboardFormat getMasterScoreboardFormat() {
        return masterScoreboardFormat;
    }

    public ScoringSystem getScoringSystem() {
        return scoringSystem;
    }

    public void setScoringSystem(String dataSource) {
        if (dataSource.contains("pts"))
            this.scoringSystem = ScoringSystem.STABLEFORD;
        else
            this.scoringSystem = ScoringSystem.MEDAL;
    }

    public void addResultsToCompetition(String str) {
        String[] nextSplit = str.split("\n");
        Collections.addAll(results, nextSplit);
    }

    public void addGolfersToCompetition() {
        for (String result : results) {
            Golfer golfer = new Golfer();
            golfer.parts = Split.rowByTab(result);
            if (firstCharOfStringIsDigit(result)) {
                golfer.assignAttributes(this.scoringSystem);
                golfers.add(golfer);
            }
        }
    }

    private boolean firstCharOfStringIsDigit(String str) {
        return str.length() > 0 && Character.isDigit(str.charAt(0));
    }

    public Golfer find(String name) {
        for (Golfer golfer : this.golfers) {
            if (golfer.getFullName().equals(name)) {
                return golfer;
            }
        }
        return null;
    }


    public MasterScoreboardFormat getFormat(String currentDataFile) {
        return this.masterScoreboardFormat;
    }
}