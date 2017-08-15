package com.kingskull.lolapplication.api.restfull.connections.responses;

import java.util.List;

/**
 * Created by Carlos on 07/05/2016.
 */
public class ListOfVersionsReady {

    private List<String> versions;

    public ListOfVersionsReady(List<String> versions) {
        this.versions = versions;
    }

    public List<String> getVersions() {
        return versions;
    }

    public void setVersions(List<String> versions) {
        this.versions = versions;
    }
}
