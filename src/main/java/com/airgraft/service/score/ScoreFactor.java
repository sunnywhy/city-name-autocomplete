package com.airgraft.service.score;

import com.airgraft.entity.City;

import java.util.Map;

/**
 * The interface for score factors, every time creating a new score factor, just implements this interface
 */
public interface ScoreFactor {
    /**
     * If we want give different factors different weight, we can adjust it here
     * @return the weight of the factor, positive integer.
     */
    int getWeight();

    /**
     * Return a map for the cities need to calculate score for this factor.
     * Key is the city object, value is the score. Score is a double value between 0 to 1.
     * @return
     */
    Map<City, Double> getScores();
}
