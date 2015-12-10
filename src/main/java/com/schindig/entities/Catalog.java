package com.schindig.entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Agronis on 12/9/15.
 */
@Entity
public class Catalog {

    @GeneratedValue
    @Id
    Integer id;

    @Column(nullable = false)
    String name;

    Integer useCount = 0;

    Catalog(){}

    public Catalog(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }
    public Integer getUseCount() {

        return useCount;
    }

    public Integer getId() {

        return id;
    }
}
