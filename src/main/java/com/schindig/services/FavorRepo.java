package com.schindig.services;
import com.schindig.entities.Favor;
import com.schindig.entities.Party;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

/**
 * Created by Agronis on 12/9/15.
 */
public interface FavorRepo extends CrudRepository<Favor, Integer> {

    @Query("SELECT COUNT(c) FROM Favor c")
    Integer favorSize();

    Favor findOneByFavorName(String favor);

    ArrayList<Favor> findByPartyType(String partyType);

}
