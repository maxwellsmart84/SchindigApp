package com.schindig.entities;
import org.springframework.data.annotation.Transient;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;

/**
 * Created by Agronis on 12/9/15.
 */
@Entity
public class Wizard {

    @GeneratedValue
    @Id
    public Integer wizardID;

//    @Column(nullable = false)
    public String partyType;

    @Transient
    public ArrayList<String> subType;

    public Wizard(){}
    public Wizard(String partyType, ArrayList<String> subType) {

        this.partyType = partyType;
        this.subType = subType;
    }
    public Integer getWizardID() {

        return wizardID;
    }
    public String getPartyType() {

        return partyType;
    }
    public ArrayList<String> getSubType() {

        return subType;
    }
}
