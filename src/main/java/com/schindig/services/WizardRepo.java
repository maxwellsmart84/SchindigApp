package com.schindig.services;
import com.schindig.entities.Wizard;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Agronis on 12/9/15.
 */
public interface WizardRepo extends CrudRepository<Wizard, Integer> {

    Wizard findOneByPartyType(String type);
}
