package com.airgraft.entity;

import java.util.Objects;

/**
 * City entity, this should include all the fields from the data source
 * Implemented equals and hashcode, since we want use City object as a key in HashMap
 */
public class City {
    //searching the code field
    private final String code;
    //unique, used to disambiguate between similarly named locations
    private final String name;
    private final Double latitude;
    private final Double longitude;

    public City(String code, String name, Double latitude, Double longitude) {
        this.code = code;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return code.equals(city.code) &&
                name.equals(city.name) &&
                latitude.equals(city.latitude) &&
                longitude.equals(city.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, latitude, longitude);
    }
}
