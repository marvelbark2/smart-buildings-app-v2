package edu.episen.si.ing1.pds.backend.server.test.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.episen.si.ing1.pds.backend.server.orm.eloquent.Models;

public class Companies extends Models {
    private int id_companies;
    private String address;
    private String name;

    @JsonProperty("id_companies")
    public int getId_companies() {
        return id_companies;
    }

    public void setId_companies(int id_companies) {
        this.id_companies = id_companies;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
