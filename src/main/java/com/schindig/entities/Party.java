package com.schindig.entities;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created by Agronis on 12/9/15.
 */
@Entity
public class Party {

    @GeneratedValue
    @Id
    public Integer partyID;

    @OneToOne
    public User host;

//    @Column(nullable = false)
    public String partyName;

    public String partyType;

    public String description;

    public String subType;

//    @Column(nullable = false)
    public LocalDateTime createDate = LocalDateTime.now();

//    @Column(nullable = false)
    public String partyDate;

    public String local;

    public String stretchName;

    public Integer stretchGoal;

    public Integer stretchStatus = 0;

    public Integer wizPosition = 1;

    public Boolean byob = false;

    public Boolean themeCheck = false;

    public String theme;

    public String parking;

    public String rsvpStatus;

    public Party(){}

    public Party(User host, String partyName, String partyType, String description, String subType, LocalDateTime createDate, String partyDate, String local) {

        this.host = host;
        this.partyName = partyName;
        this.partyType = partyType;
        this.description = description;
        this.subType = subType;
        this.createDate = createDate;
        this.partyDate = partyDate;
        this.local = local;
    }
    public Party(User host, String partyName, String partyType, String description, String subType, LocalDateTime createDate, String partyDate, String local, String stretchName, Integer stretchGoal, Integer stretchStatus, Boolean byob, Boolean themeCheck, String theme, String parking) {

        this.host = host;
        this.partyName = partyName;
        this.partyType = partyType;
        this.description = description;
        this.subType = subType;
        this.createDate = createDate;
        this.partyDate = partyDate;
        this.local = local;
        this.stretchName = stretchName;
        this.stretchGoal = stretchGoal;
        this.stretchStatus = stretchStatus;
        this.byob = byob;
        this.themeCheck = themeCheck;
        this.theme = theme;
        this.parking = parking;
    }




    public String getDescription() {

        return description;
    }
    public Integer getPartyID() {

        return partyID;
    }
    public String getPartyName() {

        return partyName;
    }
    public String getPartyType() {

        return partyType;
    }
    public String getSubType() {

        return subType;
    }
    public LocalDateTime getCreateDate() {

        return createDate;
    }
    public String getPartyDate() {

        return partyDate;
    }
    public String getLocal() {

        return local;
    }

    public String getStretchName() {

        return stretchName;
    }
    public Integer getStretchGoal() {

        return stretchGoal;
    }
    public Integer getStretchStatus() {

        return stretchStatus;
    }
    public Integer getWizPosition() {

        return wizPosition;
    }
    public Boolean getByob() {

        return byob;
    }
    public String getTheme() {

        return theme;
    }
    public String getParking() {

        return parking;
    }
    public User getHost() {

        return host;
    }
    public Boolean getThemeCheck() {

        return themeCheck;
    }
    public void setPartyID(Integer partyID) {

        this.partyID = partyID;
    }
    public void setHost(User host) {

        this.host = host;
    }
    public void setPartyName(String partyName) {

        this.partyName = partyName;
    }
    public void setPartyType(String partyType) {

        this.partyType = partyType;
    }
    public void setDescription(String description) {

        this.description = description;
    }
    public void setSubType(String subType) {

        this.subType = subType;
    }
    public void setCreateDate(LocalDateTime createDate) {

        this.createDate = createDate;
    }
    public void setPartyDate(String partyDate) {

        this.partyDate = partyDate;
    }
    public void setLocal(String local) {

        this.local = local;
    }
    public void setStretchName(String stretchName) {

        this.stretchName = stretchName;
    }
    public void setStretchGoal(Integer stretchGoal) {

        this.stretchGoal = stretchGoal;
    }
    public void setStretchStatus(Integer stretchStatus) {

        this.stretchStatus = stretchStatus;
    }
    public void setWizPosition(Integer wizPosition) {

        this.wizPosition = wizPosition;
    }
    public void setByob(Boolean byob) {

        this.byob = byob;
    }
    public void setThemeCheck(Boolean themeCheck) {

        this.themeCheck = themeCheck;
    }
    public void setTheme(String theme) {

        this.theme = theme;
    }
    public void setParking(String parking) {

        this.parking = parking;
    }
    public String getRsvpStatus() {

        return rsvpStatus;
    }
    public void setRsvpStatus(String rsvpStatus) {

        this.rsvpStatus = rsvpStatus;
    }
}
