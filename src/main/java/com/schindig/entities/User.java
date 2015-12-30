package com.schindig.entities;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.servlet.http.Cookie;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Agronis on 12/9/15.
 */
@Entity
@Table(name = "users")
public class User {

    @GeneratedValue
    @Id
    public Integer userID;

//    @Column(nullable = false)
//    @Size(min = 4, message = "Username must be a minimum of 4 characters in length.")
    public String username;

//    @Column(nullable = false)
//    @Size(min = 5, message = "Password must be at least 5 characters in length.")
    public String password;

//    @Column(nullable = false)
//    @Size(min = 2, message = "First name must have a minimum of 2 letters.")
    public String firstName;

//    @Column(nullable = false)
//    @Size(min = 2, message = "Last name must have a minimum of 2 letters.")
    public String lastName;

//    @Column(nullable = false)
    public String email;

//    @Column(nullable = false)
//    @Size(min = 10, message = "Requires a 10-digit number.")
    public String phone;

    public Integer partyCount = 0;
    public Integer hostCount = 0;
    public Integer inviteCount = 0;
    public Integer invitedCount = 0;

    public User(){}
    public User(Integer userID, String username) {
        this.username = username;
        this.userID = userID;
    }
    public User(Integer userID, String username, String firstName, String lastName, String email, String phone) {

        this.userID = userID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
    public User(Integer userID, String username, String firstName, String lastName, String email, String phone, Integer partyCount, Integer hostCount, Integer inviteCount, Integer invitedCount) {

        this.userID = userID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.partyCount = partyCount;
        this.hostCount = hostCount;
        this.inviteCount = inviteCount;
        this.invitedCount = invitedCount;
    }
    public User(String username, String password, String firstName, String lastName, String email, String phone) {

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
    public Integer getUserID() {

        return userID;
    }
    public String getUsername() {

        return username;
    }
    public String getPassword() {

        return password;
    }
    public String getFirstName() {

        return firstName;
    }
    public String getLastName() {

        return lastName;
    }
    public String getEmail() {

        return email;
    }
    public String getPhone() {

        return phone;
    }
    public Integer getPartyCount() {

        return partyCount;
    }
    public Integer getHostCount() {

        return hostCount;
    }
    public Integer getInviteCount() {

        return inviteCount;
    }
    public Integer getInvitedCount() {

        return invitedCount;
    }
    public void setUserID(Integer userID) {

        this.userID = userID;
    }
    public void setUsername(String username) {

        this.username = username;
    }
    public void setPassword(String password) {

        this.password = password;
    }
    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }
    public void setLastName(String lastName) {

        this.lastName = lastName;
    }
    public void setEmail(String email) {

        this.email = email;
    }
    public void setPhone(String phone) {

        this.phone = phone;
    }
    public void setPartyCount(Integer partyCount) {

        this.partyCount = partyCount;
    }
    public void setHostCount(Integer hostCount) {

        this.hostCount = hostCount;
    }
    public void setInviteCount(Integer inviteCount) {

        this.inviteCount = inviteCount;
    }
    public void setInvitedCount(Integer invitedCount) {

        this.invitedCount = invitedCount;
    }
}
