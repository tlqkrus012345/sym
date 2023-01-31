package com.sym.member.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Address {

    private String country;
    private String city;
    private String zipcode;

    protected Address() {
    }

    public Address(String country, String city, String zipcode) {
        this.country = country;
        this.city = city;
        this.zipcode =zipcode;
    }
}
