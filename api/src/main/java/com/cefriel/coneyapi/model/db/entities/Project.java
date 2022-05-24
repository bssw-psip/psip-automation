package com.cefriel.coneyapi.model.db.entities;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

public class Project {

    @Id
    @GeneratedValue
    Long id;

    String name;

    public Project(){};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
