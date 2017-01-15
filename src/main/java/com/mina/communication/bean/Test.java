package com.mina.communication.bean;

import java.io.Serializable;

/**
 * Created by apple on 16/9/17.
 */
public class Test implements Serializable {
    private String title;
    private String message;

    public String getTitle() {
        return title;
    }

    public Test setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Test setMessage(String message) {
        this.message = message;
        return this;
    }
}
