package com.schindig.entities;

import javax.persistence.*;

/**
 * Created by Agronis on 12/9/15.
 */
@Entity
public class Invite {

    @GeneratedValue
    @Id
    public Integer inviteID;

    @OneToOne
    public User user;

    @OneToOne
    public Party party;

    public String phone;

    public String email;

    public String rsvpStatus = "Undecided";

    public String name;

    public Boolean sent = false;



    public Invite(Integer inviteID, User user, Party party, String phone, String email) {
        this.inviteID = inviteID;
        this.user = user;
        this.party = party;
        this.phone = phone;
        this.email = email;
    }

    public Invite(Party party, String phone, String email, String name, Boolean sent) {

        this.party = party;
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.sent = sent;
    }
    public Invite() {
    }

    public Invite(String rsvpStatus) {

        this.rsvpStatus = rsvpStatus;
    }

    public Invite(User user, Party party, String phone, String email, String rsvpStatus, String name) {

        this.user = user;
        this.party = party;
        this.phone = phone;
        this.email = email;
        this.rsvpStatus = rsvpStatus;
        this.name = name;
    }

    public Boolean getSent() {

        return sent;
    }
    public String getRsvpStatus() {

        return rsvpStatus;
    }
    public String getName() {

        return name;
    }
    public Integer getInviteID() {
        return inviteID;
    }

    public User getUser() {
        return user;
    }

    public Party getParty() {
        return party;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

}
