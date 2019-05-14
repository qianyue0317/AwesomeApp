package com.qy.j4u.model.entity;

public class ITEssayItem {

    /**
     * category_id : 0
     * id : 1
     * keyword : 百度
     * title : 测试的文章
     * url : https://www.baidu.com
     */

    private int category_id;
    private int id;
    private String keyword;
    private String title;
    private String url;

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
