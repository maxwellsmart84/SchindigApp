package com.schindig.services;
import com.schindig.entities.Contact;
import com.schindig.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

/**
 * Created by Agronis on 1/2/16.
 */
public interface ContactRepo extends CrudRepository<Contact, Integer> {

    ArrayList<Contact> findAllByUser(User user);
}
