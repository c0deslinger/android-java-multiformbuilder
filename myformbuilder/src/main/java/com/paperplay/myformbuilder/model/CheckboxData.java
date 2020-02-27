package com.paperplay.myformbuilder.model;

/**
 * Created by Ahmed Yusuf on 22/08/19.
 */
public class CheckboxData {
    private int id;
    private String secondaryId;
    private String value;
    private boolean checked;

    public CheckboxData(int id, String secondaryId, String value, boolean checked) {
        this.id = id;
        this.secondaryId = secondaryId;
        this.value = value;
        this.checked = checked;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
