package com.qy.j4u.global;

import com.qy.j4u.model.entity.ITCategoryItem;

import java.util.List;

/**
 * 全局用户信息类
 * Created by abc on 2016/11/18.
 * modified by qy on 2019/05/13
 */

public class User {

    private String nickname;
    private String phone;
    private int id;
    private String token;
    private String uuid;
    private List<ITCategoryItem> it_categories;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", id=" + id +
                ", token='" + token + '\'' +
                ", uuid='" + uuid + '\'' +
                ", it_categories=" + it_categories +
                '}';
    }

    public static User getUser() {
        return InstanceHolder.instance;
    }

    public List<ITCategoryItem> getIt_categories() {
        return it_categories;
    }

    public void setIt_categories(List<ITCategoryItem> it_categories) {
        this.it_categories = it_categories;
    }

    private static class InstanceHolder {
        private static final User instance= new User();
    }

}
