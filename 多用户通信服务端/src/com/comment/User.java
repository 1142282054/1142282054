package com.comment;

import java.io.Serializable;

/**
 * @author MinZhi
 * @version 1.0
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String pwd;

    public User(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }
}
