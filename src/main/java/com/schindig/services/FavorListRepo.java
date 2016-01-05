package com.schindig.services;

import com.schindig.entities.Favor;
import com.schindig.entities.FavorList;
import com.schindig.entities.Party;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.ArrayList;

/**
 * Created by Agronis on 12/9/15.
 */
public interface FavorListRepo extends CrudRepository<FavorList, Integer> {

    ArrayList<FavorList> findByParty(Party party);

    @Query("SELECT (f) FROM FavorList f WHERE favor = ?1 AND party = ?1")
    FavorList findOneByFavorAndParty(Favor favor, Party party);

    ArrayList<FavorList> findAllByParty(Party party);

    Favor findByFavor(Favor id);

}
