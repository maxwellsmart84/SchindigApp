package com.schindig.entities;
import javax.persistence.*;


/**
 * Created by Agronis on 12/19/15.
 */
@Entity
public class Auth {

    @GeneratedValue
    @Id
    public Integer id;

    @ManyToOne
    public User user;

    public String device;

    public String key;

    public String token;

    public Auth() {

    }
    public Auth(User user, String device, String key, String token) {

        this.user = user;
        this.device = device;
        this.key = key;
        this.token = token;
    }
    public String getKey() {

        return key;
    }
    public void setKey(String key) {

        this.key = key;
    }
    public String getToken() {

        return token;
    }
    public void setToken(String token) {

        this.token = token;
    }
    public User getUser() {

        return user;
    }
    public void setUser(User user) {

        this.user = user;
    }
    public Integer getId() {

        return id;
    }
    public void setId(Integer id) {

        this.id = id;
    }
    public String getDevice() {

        return device;
    }
    public void setDevice(String device) {

        this.device = device;
    }
}
