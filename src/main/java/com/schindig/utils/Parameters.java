package com.schindig.utils;

import com.schindig.entities.*;
import org.hibernate.mapping.Array;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;

/**
 * Created by landonkail on 12/14/15.
 */
public class Parameters {

    public Favor favor;

    public Party party;

    public User user;

    public Invite invites;

    public FavorList favorList;

    public String rsvpStatus;

    public ArrayList<Favor> favorDump;

    public ArrayList<Invite> inviteDump;

    public ArrayList<FavorList> favorListDump;

    public Integer partyID;

    public Integer userID;

    public String device;

    public Integer listID;

    public Double amount;

}
