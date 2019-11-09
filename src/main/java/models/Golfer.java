package models;

import enums.ScoringSystem;

public class Golfer implements Comparable {

    public String[] parts;
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
    private String countback;
    private String placing;

    public int getPosition() {
        return position;
    }

    void setPosition(int position) {
        this.position = position;
    }

    public int getGross() {
        return gross;
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
        } else {
            throw new UnsupportedOperationException("Trouble at mill in Golfer");
        }

        nett = gross - handicap;
        partsOfName = this.parts[1].split(",");

        position = Integer.parseInt(parts[0]);
        surname = partsOfName[0].trim();
        forename = partsOfName[1].trim();

        fullName = forename + ' ' + surname;

        if (parts.length == 3) {
            placing = "";
            countback = "";
        } else if (parts.length == 4) {
            placing = parts[3];
            countback = "";
        } else if (parts.length == 5) {
            if ("".equals(parts[3])) {
                placing = "";
                countback = parts[4];
            } else {
                placing = parts[3];
                countback = parts[4];
            }
        } else {
            throw new UnsupportedOperationException("Trouble at mill in Golfer");
        }
    }


    public int calculateGross() {
        int ptsOver36 = this.pts - 36;
        int expectedGross = 72 + handicap;
        return expectedGross - ptsOver36;
    }


    private int calculatePoints() {
        return Integer.parseInt(partsOfScore[0]);
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

    public String getSurname() {
        return surname;
    }

    public String getForename() {
        return forename;
    }

    public String getCountback() {
        return countback;
    }

    public String getPlacing() {
        return placing;
    }

}
