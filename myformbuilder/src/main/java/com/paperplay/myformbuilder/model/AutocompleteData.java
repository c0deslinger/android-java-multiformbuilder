package com.paperplay.myformbuilder.model;

/**
 * Created by Ahmed Yusuf on 22/08/19.
 */
public class AutocompleteData {
    int id;
    String secondaryId;
    String value;
    boolean hidden;

    public AutocompleteData(int id, String secondaryId, String value) {
        this.id = id;
        this.secondaryId = secondaryId;
        this.value = value;
        this.hidden = false;
    }

    public AutocompleteData(int id, String secondaryId, String value, boolean hidden) {
        this.id = id;
        this.secondaryId = secondaryId;
        this.value = value;
        this.hidden = hidden;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSecondaryId() {
        return secondaryId;
    }

    public void setSecondaryId(String secondaryId) {
        this.secondaryId = secondaryId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
