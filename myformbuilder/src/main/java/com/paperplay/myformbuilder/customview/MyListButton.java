package com.paperplay.myformbuilder.customview;

/**
 * Created by AhmedYusuf on 7/7/17.
 * This class is used for list of menu button with same view
 * The action on button clicked is change activity
 */

public class MyListButton {
    public int icon;
    public String name;
    public Class activity;

    public MyListButton(int icon, String name, Class activity) {
        this.icon = icon;
        this.name = name;
        this.activity = activity;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }
}
