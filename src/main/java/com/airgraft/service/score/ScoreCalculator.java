package com.airgraft.service.score;

import com.airgraft.entity.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Calculate the final score for each city according to all the score factors.
 */
public class ScoreCalculator {
    private List<ScoreFactor> factors;

    private ScoreCalculator() {
        this.factors = new ArrayList<>();
    }

    public static ScoreCalculator of(ScoreFactor factor) {
        ScoreCalculator calculator = new ScoreCalculator();
        return calculator.addFactor(factor);
    }

    /**
     * Simply add new score factor, return the same object, then we can call this method like a chain.
     * ex: calculator.addFactor(factor1).addFactor(factor2)...
     * @param factor
     * @return the same object
     */
    public ScoreCalculator addFactor(ScoreFactor factor) {
        if(factors == null) factors = new ArrayList<>();
        factors.add(factor);
        return this;
    }

    public Map<City, Double> calculate(List<City> cities) {
        Map<City, Double> map = new HashMap<>();
        if(cities == null || cities.isEmpty()) return map;
        for(City city : cities) {
            map.put(city, calculate(city));
        }
        return map;
    }

    private double calculate(City city) {
        if(factors == null || factors.isEmpty()) return 0d;
        int totalWeight = 0;
        double totalScore = 0d;
        for(ScoreFactor factor : factors) {
            totalWeight += factor.getWeight();
            totalScore += factor.getScores().getOrDefault(city, 0d) * factor.getWeight();
        }
        return (double) Math.round(totalScore / totalWeight * 100) / 100;
    }
}
