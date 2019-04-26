package com.qy.j4u.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

@Entity(
        indexes = {@Index(value = "text,date DESC", unique = true)}
)
public class GreenDaoTestPojo {

    @Id
    private Long id;

    @Override
    public String toString() {
        return "GreenDaoTestPojo{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }

    @NotNull
    private String text;

    private Date date;

    @Generated(hash = 1098426541)
    public GreenDaoTestPojo(Long id, @NotNull String text, Date date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    @Generated(hash = 1419091783)
    public GreenDaoTestPojo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
