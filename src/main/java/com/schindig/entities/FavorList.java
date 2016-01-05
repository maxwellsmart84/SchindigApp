package com.schindig.entities;

import javax.persistence.*;

/**
 * Created by Agronis on 12/9/15.
 */
@Entity
public class FavorList {

    @GeneratedValue
    @Id
    public Integer listID;

    @OneToOne
    public Favor favor;

    @OneToOne
    public Party party;

    @OneToOne
    public User user;

    public Boolean claimed = false;

    public FavorList(Favor favor, Party party, Boolean claimed) {

        this.favor = favor;
        this.party = party;
        this.claimed = claimed;
    }
    public FavorList() {
    }
    public void setListID(Integer listID) {

        this.listID = listID;
    }
    public void setFavor(Favor favor) {

        this.favor = favor;
    }
    public Party getParty() {

        return party;
    }
    public void setParty(Party party) {

        this.party = party;
    }
    public User getUser() {

        return user;
    }
    public void setUser(User user) {

        this.user = user;
    }
    public Boolean getClaimed() {

        return claimed;
    }
    public void setClaimed(Boolean claimed) {

        this.claimed = claimed;
    }
    public Integer getListID() {
        return listID;
    }

    public Favor getFavor() {
        return favor;
    }
}
