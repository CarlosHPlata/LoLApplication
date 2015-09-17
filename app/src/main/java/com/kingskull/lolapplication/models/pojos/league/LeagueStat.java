package com.kingskull.lolapplication.models.pojos.league;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 07/09/2015.
 */
public class LeagueStat implements java.io.Serializable{
    private List<Entry> entries = new ArrayList<Entry>();
    private String name = "";
    private String participantId = "";
    private String queue = "";
    private String tier = "";

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    /**
     *
     * @return
     * The queue
     */
    public String getQueue() {
        return queue;
    }

    /**
     *
     * @param queue
     * The queue
     */
    public void setQueue(String queue) {
        this.queue = queue;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The entries
     */
    public List<Entry> getEntries() {
        return entries;
    }

    /**
     *
     * @param entries
     * The entries
     */
    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    /**
     *
     * @return
     * The tier
     */
    public String getTier() {
        return tier;
    }

    /**
     *
     * @param tier
     * The tier
     */
    public void setTier(String tier) {
        this.tier = tier;
    }

}
