package com.kingskull.lolapplication.models.pojos.item;

/**
 * Created by Carlos on 06/02/2016.
 */
public class MetaDataDto {

    private boolean isRune;
    private String tier;
    private String type;

    public boolean isRune() {
        return isRune;
    }

    public void setIsRune(boolean isRune) {
        this.isRune = isRune;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
