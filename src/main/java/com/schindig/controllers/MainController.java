package com.schindig.controllers;
import com.schindig.entities.Catalog;
import com.schindig.entities.Party;
import com.schindig.entities.Wizard;
import com.schindig.services.CatalogRepo;
import com.schindig.services.WizardRepo;
import com.schindig.utils.Methods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agronis on 12/9/15.
 */
@CrossOrigin
@RestController
public class MainController {

    @Autowired
    WizardRepo wizard;

    @Autowired
    CatalogRepo catalog;

    @PostConstruct
    public void init() {

        Integer wizCheck = wizard.wizardSize();
        if (wizCheck==0) {
            String fileContent = Methods.readFile("wizard.csv");

            String[] lines = fileContent.split("\n");

            for (String line : lines) {
                String[] columns = line.split(",");
                Wizard wiz= new Wizard(columns[0], columns[1]);
                wizard.save(wiz);
            }
        }

        Integer catCheck = catalog.catalogSize();
        if (catCheck==0) {
            String fileContent = Methods.readFile("catalog.csv");

            String[] lines = fileContent.split("\n");

            for (String line : lines) {
                Catalog cat = new Catalog(line);
                catalog.save(cat);
            }
        }



    }

    @RequestMapping("/get-wizard")
    public ArrayList<Wizard> getPartyList() {
        return (ArrayList<Wizard>) wizard.findAll();
    }

    @RequestMapping("/get-catalog")
    public ArrayList<Catalog> getCatalogList() {
        return (ArrayList<Catalog>) catalog.findAll();
    }
    
}
