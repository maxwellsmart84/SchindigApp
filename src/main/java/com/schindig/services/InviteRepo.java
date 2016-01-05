package com.schindig.services;

import com.schindig.entities.Invite;
import com.schindig.entities.Party;
import com.schindig.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agronis on 12/9/15.
 */
public interface InviteRepo extends CrudRepository<Invite, Integer> {
    @Query("SELECT COUNT(i) FROM Invite i WHERE party =?1")
    Integer findPartyInviteCount(Party party);

    @Query("SELECT party FROM Invite i WHERE user = ?1")
    List<Party> findInvite(User user);

    ArrayList<Invite> findByParty(Party party);

    @Query("SELECT i FROM Invite i WHERE party = ?1 AND user = ?1")
    Invite findByPartyAndUser(Party party, User user);

    Invite findByPartyAndEmail(Party party, String email);
}
