package com.schindig.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Agronis on 1/2/16.
 */
@Entity
public class Contact {

    @Id
    @GeneratedValue
    public Integer contactID;

    @ManyToOne
    public User user;

    public String name;

    public String phone;

    public String email;

    public Integer getContactID() {

        return contactID;
    }
    public User getUser() {

        return user;
    }
    public void setUser(User user) {

        this.user = user;
    }
    public String getName() {

        return name;
    }
    public void setName(String name) {

        this.name = name;
    }
    public String getPhone() {

        return phone;
    }
    public void setPhone(String phone) {

        this.phone = phone;
    }
    public String getEmail() {

        return email;
    }
    public void setEmail(String email) {

        this.email = email;
    }
    public Contact(User user, String name, String phone, String email) {

        this.user = user;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Contact(){}
}
