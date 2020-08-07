package com.airgraft.vo;

public class CityVo {
    private final String name;
    private final Double latitude;
    private final Double longitude;
    private final Double score;

    //needed for springboot to convert pojo to json
    private CityVo() {
        this(null, null, null, null);
    }

    public CityVo(String name, Double latitude, Double longitude, Double score) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.score = score;
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

    public Double getScore() {
        return score;
    }
}
