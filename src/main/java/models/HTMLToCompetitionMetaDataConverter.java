package models;

import service.StringHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HTMLToCompetitionMetaDataConverter {

    private final List<String> itemsToRemove = new ArrayList<>(Arrays.asList("<tr>", "</tr>", "</th>", "img src", "colspan"));
    private String rawData;
    private List<String> rawList;
    private List<String> concatenatedList;

    public List<CompetitionMetadata> getCompetitionMetadata() {
        return competitionMetadata;
    }

    private List<CompetitionMetadata> competitionMetadata;

    public List<String> getConcatenatedList() {
        return concatenatedList;
    }

    public HTMLToCompetitionMetaDataConverter(String rawData) {
        this.rawData = rawData;
    }

    public List<String> getRawList() {
        return rawList;
    }

    /** convertRawDataToArrayList
     * Eliminate everthing  in the rawdata file apart from the rows in the table of competitions
     * and split into an ArrayList.
     */
    public void convertRawDataToArrayList() {
        rawData = StringHelper.splitBeforeAndAfter(rawData, "Recent Competitions'>\n", "</table>");
        rawList = StringHelper.splitToListByNewLine(rawData);
    }


    /** removeUnwantedRowsFromList
     * Remove elements that contain certain tags <tr>", "</tr>", "</th>", "img src", "colspan"
     * should be left with just <td> elements
     */

    public void removeUnwantedRowsFromList() {
        for (String str : itemsToRemove) {
            rawList.removeIf(item -> item.contains(str));
        }
    }


    /** extractCompetitionData
     * Eliminate the elements that do not have either the date or the url (which contains the title)
     * e.g. convert =>   <td width=80 valign=top align=center>Sun 22 Sep 19</td>
     * to           =>    Sun 22 Sep 19
     */

    public void extractCompetitionData() {
        for (int i = 0; i < rawList.size(); i++) {
            String newListItem;
            if (rawList.get(i).contains("center")) {
                newListItem = StringHelper.splitBeforeAndAfter(rawList.get(i), "center>", "</td>");
                rawList.set(i, newListItem);
            } else if (rawList.get(i).contains("href")) {
                newListItem = StringHelper.splitBeforeAndAfter(rawList.get(i), "top>", "<br");
                rawList.set(i, newListItem);
            } else {
                throw new UnsupportedOperationException("The string does not contain either center or href");
            }
        }
    }

    /** concatenateList
     *  Merge the two required elements of data, url (which contains title) and date
     */

    public void concatenateList() {
        concatenatedList = new ArrayList<>();
        for (int i = 0; i < rawList.size(); i+=2) {
            concatenatedList.add(rawList.get(i) + rawList.get(i + 1));
        }
    }


    /** createListofCompetitionMetaData
     *  Convert string to a CompetitionMetaData object that can ultimately be passed to a view.
     */

    public void createListofCompetitionMetaData() {
        competitionMetadata = new ArrayList<>();
        for (String item: this.concatenatedList) {
            CompetitionMetadata competitionMetadata = new CompetitionMetadata(item);
            this.competitionMetadata.add(competitionMetadata);
        }
    }
}

