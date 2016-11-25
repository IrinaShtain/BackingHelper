package com.shtainyky.backinghelper.model;

import java.util.List;

public class ThemeItem {
    private String title;
    private String link;
    private String user;
    private int views;

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isThisThemeInSavedListItems(List<ThemeItem> themeItemList) {
        int count = themeItemList.size();

        for (int i = 0; i < themeItemList.size(); i++) {
            if (!this.getTitle().equals(themeItemList.get(i).getTitle())) {
                count = count - 1;
            }
        }
        if (count == 1) {
            return true;
        } else if (count == 0 && this.getViews() < 20) {
            return false;
        }
        return true;
    }

    public boolean isThemesUserInListUnwantedUsers(List<String> listUnwantedUsers) {
        boolean res = false;
        if (listUnwantedUsers.size() == 0)
            return false;
        for (int i = 0; i < listUnwantedUsers.size(); i++) {
            if (this.getUser().equals(listUnwantedUsers.get(i))) {
                res = true;
                break;
            }
        }
        return res;
    }
}
