package com.qy.j4u.model.entity;

/**
 * IT分类的实体类
 */
public class ITCategoryItem {

    private int id;
    private String name;

    @Override
    public String toString() {
        return "ITCategoryItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
