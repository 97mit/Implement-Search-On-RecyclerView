package com.amit.assignment;

/**
 * Created by AMIT on 6/8/2018.
 */

public class DevelopersList {

    private String title;
    private String html_url;
    private String avatar_url;

    public String getTitle() {
        return title;
    }

    public String getHtml_url() { return html_url; }

    public String getAvatar_url() {
        return avatar_url;
    }

    public DevelopersList(String title, String html_url, String avatar_url) {
        this.title = title;
        this.html_url = html_url;
        this.avatar_url = avatar_url;

    }
}
