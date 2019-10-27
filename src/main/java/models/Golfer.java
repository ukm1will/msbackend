package models;

import enums.ScoringSystem;

public class Golfer implements Comparable {

    private String[] partsOfScore;
    private String[] partsOfName;

    private int gross;
    private int nett;
    private int handicap;
    private int position;
    private int pts;
    private String forename;
    private String surname;
    private String fullName;
    private String[] parts;

    public int getPosition() {
        return position;
    }

    void setPosition(int position) {
        this.position = position;
    }

    public int getGross() {
        return gross;
    }

    @Override
    public String toString() {
        return "Golfer{" +
                "position=" + position +
                ", fullName='" + fullName + '\'' +
                ", gross=" + gross +
                ", nett=" + nett +
                ", handicap=" + handicap +
                ", pts=" + pts +
                '}';
    }

    public int getNett() {
        return nett;
    }

    public int getHandicap() {
        return handicap;
    }

    public void assignAttributes(ScoringSystem scoringSystem) {
        partsOfScore = this.parts[2].split(" ");
        handicap = calculateHandicap();
        if (scoringSystem == ScoringSystem.STABLEFORD) {
            pts = calculatePoints();
            gross = calculateGross();
        } else if (scoringSystem == ScoringSystem.MEDAL) {
            pts = -1;
            gross = Integer.parseInt(partsOfScore[0]);
        }
        else {
            throw new UnsupportedOperationException("Trouble at mill in Golfer");
        }

        nett = gross - handicap;
        partsOfName = this.parts[1].split(",");

        position = Integer.parseInt(parts[0]);
        surname = partsOfName[0].trim();
        forename = partsOfName[1].trim();

        fullName = forename + ' ' + surname;
    }


    public int calculateGross() {
        int ptsOver36 = this.pts - 36;
        int expectedGross = 72 + handicap;
        return expectedGross - ptsOver36;
    }

    private int calculatePoints() {
        return Integer.parseInt(partsOfScore[0]);
    }

    void split(String s) {
        this.parts = s.split("\t");
    }

    private int calculateHandicap() {
        partsOfScore[2] = partsOfScore[2].replaceAll("\\(", "");
        partsOfScore[2] = partsOfScore[2].replaceAll("\\)", "");
        return Integer.parseInt(partsOfScore[2]);
    }

    @Override
    public int compareTo(Object obj) {
        Golfer that = (Golfer) obj;
        if (Integer.compare(this.gross, that.gross) == 0) {
            if (this.surname.compareTo(that.surname) == 0) {
                return this.forename.compareTo(that.forename);
            } else {
                return this.surname.compareTo(that.surname);
            }
        } else {
            return Integer.compare(this.gross, that.gross);
        }
    }

    public String getFullName() {
        return fullName;
    }

    public int getPts() {
        return pts;
    }
}
