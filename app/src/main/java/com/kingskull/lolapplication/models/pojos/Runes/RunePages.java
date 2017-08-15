package com.kingskull.lolapplication.models.pojos.Runes;

import java.util.ArrayList;

/**
 * Created by Carlos on 10/10/2015.
 */
public class RunePages {

    private long id;
    private ArrayList<RunePage> pages;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<RunePage> getPages() {
        return pages;
    }

    public void setPages(ArrayList<RunePage> pages) {
        this.pages = pages;
    }
}
