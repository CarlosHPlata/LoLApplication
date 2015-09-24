package com.kingskull.lolapplication.models.pojos.match;

import java.util.List;

/**
 * Created by cherrera on 9/24/2015.
 */
public class MatchList implements java.io.Serializable{
    private int endIndex;
    private List<MatchReference> matches;
    private int startIndex;
    private int totalGames;

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public List getMatches() {
        return matches;
    }

    public void setMatches(List matches) {
        this.matches = matches;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }
}
