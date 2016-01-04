package com.schindig.services;
import com.schindig.entities.Auth;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Agronis on 12/19/15.
 */
public interface AuthRepo extends CrudRepository<Auth, Integer> {

    Auth findByDevice(String device);

}
