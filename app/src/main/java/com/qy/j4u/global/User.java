package com.qy.j4u.global;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qy.j4u.global.constants.SharePreferencesKeys;
import com.qy.j4u.model.entity.ITCategoryItem;
import com.qy.j4u.utils.SharePrefTool;
import com.qy.j4u.utils.collectionutil.CollectionKit;

import java.util.List;

/**
 * 全局用户信息类
 * Created by abc on 2016/11/18.
 * modified by qy on 2019/05/13
 */

public class User {

    private String nickname = "";
    private String phone = "";
    private int id;
    private String token = "";
    private String uuid = "";
    private List<ITCategoryItem> it_categories;

    private User() {

    }

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
        return it_categories == null ? CollectionKit.newArrayList() : it_categories;
    }

    public void setIt_categories(List<ITCategoryItem> it_categories) {
        this.it_categories = it_categories;
    }

    private static class InstanceHolder {
        private static User instance = new User();
    }

    /**
     * 初始化user
     */
    public static void init(User user) {
        InstanceHolder.instance = user == null ? new User() : user;
    }

    /**
     * 将User保存到SharePreferences中
     */
    public static void save() {
        if (User.getUser() != null) {
            User user = User.getUser();
            SharePrefTool defaultInstance = SharePrefTool.getDefaultInstance();
            defaultInstance.putString(SharePreferencesKeys.KEY_USER_NAME, user.nickname);
            defaultInstance.putString(SharePreferencesKeys.KEY_USER_PHONE, user.phone);
            defaultInstance.putString(SharePreferencesKeys.KEY_USER_TOKEN, user.token);
            defaultInstance.putString(SharePreferencesKeys.KEY_UUID, user.uuid);
            defaultInstance.putInt(SharePreferencesKeys.KEY_USER_ID, user.id);
            Gson gson = new Gson();
            String s = gson.toJson(user.getIt_categories());
            defaultInstance.putString(SharePreferencesKeys.KEY_USER_IT_CATEGORIES, s);
        }
    }

    public static void initFromLocal() {
        SharePrefTool defaultInstance = SharePrefTool.getDefaultInstance();
        User user = new User();
        user.setNickname(defaultInstance.getString(SharePreferencesKeys.KEY_USER_NAME, ""));
        user.setPhone(defaultInstance.getString(SharePreferencesKeys.KEY_USER_PHONE, ""));
        user.setToken(defaultInstance.getString(SharePreferencesKeys.KEY_USER_TOKEN, ""));
        user.setUuid(defaultInstance.getString(SharePreferencesKeys.KEY_UUID, ""));
        user.setId(defaultInstance.getInt(SharePreferencesKeys.KEY_USER_ID, -1));
        String string = defaultInstance.getString(SharePreferencesKeys.KEY_USER_IT_CATEGORIES,
                "[]");
        user.setIt_categories(new Gson().fromJson(string, new TypeToken<List<ITCategoryItem>>() {
        }.getType()));
        init(user);
    }

}
