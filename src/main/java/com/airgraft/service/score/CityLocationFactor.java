package com.airgraft.service.score;

import com.airgraft.entity.City;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Calculate the score based on the similarity between "city location" and the querying "latitude" and "longitude".
 * For each city, the score is between 0 - 1. If the city latitude/longitude are exactly same as querying values, the score will be 1.
 */
public class CityLocationFactor implements ScoreFactor {
    private int weight = 1;
    private final Double latitude;
    private final Double longitude;
    private final List<City> cities;

    private final static double MAX_ALLOW_VARIANCE = 100d;

    public CityLocationFactor(Double latitude, Double longitude, List<City> cities){
        this(latitude, longitude, cities, 1);
    }

    public CityLocationFactor(Double latitude, Double longitude, List<City> cities, int weight){
        if(latitude == null || longitude == null) throw new IllegalArgumentException("latitude/longitude cannot be empty");
        if(weight <= 0) throw new IllegalArgumentException("weight has to be a positive integer");
        this.latitude = latitude;
        this.longitude = longitude;
        this.cities = cities;
        this.weight = weight;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public Map<City, Double> getScores() {
        Map<City, Double> scores = new HashMap<>();
        for(City city : cities) {
            scores.put(city, getScore(city));
        }
        return scores;
    }

    /**
     * Calculate the score according to the distance between city and querying latitude/longitude.
     * Set a Max Allow Variance, if exceed the limit, simply consider the score is 0.
     * @param city
     * @return
     */
    private double getScore(City city) {
        double variance = (this.latitude - city.getLatitude()) * (this.latitude - city.getLatitude());
        variance += (this.longitude - city.getLongitude()) * (this.longitude - city.getLongitude());
        if(variance > MAX_ALLOW_VARIANCE) return 0;
        return (MAX_ALLOW_VARIANCE - variance) / MAX_ALLOW_VARIANCE;
    }

}

