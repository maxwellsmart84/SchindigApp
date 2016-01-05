package com.schindig.services;
import com.schindig.entities.Party;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

/**
 * Created by Agronis on 12/9/15.
 */
public interface PartyRepo extends CrudRepository<Party, Integer> {

}

