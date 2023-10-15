package net.flow9.dcjt.firebase.model;

import android.net.Uri;

public class Post {

    private String item_post_image;
    private String L_category;
    private String M_category;
    private String item_post_title;
    private String item_post_date;

    public Post(String item_post_image, String l_category, String m_category, String item_post_title, String item_post_date) {
        this.item_post_image = item_post_image;
        this.L_category = l_category;
        this.M_category = m_category;
        this.item_post_title = item_post_title;
        this.item_post_date = item_post_date;
    }

    public String getItem_post_image() {
        return item_post_image;
    }

    public void setItem_post_image(String item_post_image) {
        this.item_post_image = item_post_image;
    }

    public String getL_category() {
        return L_category;
    }

    public void setL_category(String l_category) {
        L_category = l_category;
    }

    public String getM_category() {
        return M_category;
    }

    public void setM_category(String m_category) {
        M_category = m_category;
    }

    public String getItem_post_title() {
        return item_post_title;
    }

    public void setItem_post_title(String item_post_title) {
        this.item_post_title = item_post_title;
    }

    public String getItem_post_date() {
        return item_post_date;
    }

    public void setItem_post_date(String item_post_date) {
        this.item_post_date = item_post_date;
    }
}


