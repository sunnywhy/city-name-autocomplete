package com.airgraft.service.score;

import com.airgraft.entity.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Calculate the score based on the similarity between "city code" and "query".
 * For each city, the score is between 0 - 1. If the city code is exactly same as query, the score will be 1.
 */
public class CityCodeFactor implements ScoreFactor {
    //weight is used for multiple factors, we can adjust the weight for each factor
    private final int weight;
    private final String query;
    private final List<City> cities;

    public CityCodeFactor(String query, List<City> cities){
        this(query, cities, 1);
    }

    public CityCodeFactor(String query, List<City> cities, int weight){
        if(query == null || query.trim().length() == 0) throw new IllegalArgumentException("the query prefix cannot be empty");
        if(weight <= 0) throw new IllegalArgumentException("weight has to be a positive integer");
        this.query = query;
        this.weight = weight;

        if(cities == null) cities = new ArrayList<>();
        this.cities = cities;
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

    private double getScore(City city) {
        if(!city.getCode().startsWith(this.query)) return 0;
        return (double) this.query.length() / city.getCode().length();
    }
}
