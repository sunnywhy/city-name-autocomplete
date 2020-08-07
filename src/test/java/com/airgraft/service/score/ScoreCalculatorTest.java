package com.airgraft.service.score;

import com.airgraft.entity.City;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ScoreCalculatorTest {
    private static final City LONDON = new City("London", "London, ON, Canada", 42.98339, -81.23304);
    private static final City LONDON1 = new City("London1", "London1, ON, Canada", 38.93345, -76.54941);
    private static final City LONDONTOWNEE = new City("Londontownee", "Londontownee, MD, USA", 42.98339, -81.23304);
    private static final List<City> CITIES = Arrays.asList(LONDON, LONDON1, LONDONTOWNEE);
    private static final double DELTA = 0.01;

    @Test
    public void calculate_singleFactor_expectedCorrectScore() {
        CityCodeFactor factor = new CityCodeFactor("London", CITIES);
        ScoreCalculator calculator = ScoreCalculator.of(factor);
        Map<City, Double> scores = calculator.calculate(CITIES);

        assertEquals(1d, scores.get(LONDON), DELTA);
        assertEquals(6d/7d, scores.get(LONDON1), DELTA);
        assertEquals(0.5, scores.get(LONDONTOWNEE), DELTA);
    }

    @Test
    public void calculate_multipleFactorsAndSameWeight_expectedCorrectScore() {
        CityCodeFactor factor1 = new CityCodeFactor("London", CITIES);
        CityLocationFactor factor2 = new CityLocationFactor(42.98339, -81.23304, CITIES);
        ScoreCalculator calculator = ScoreCalculator.of(factor1).addFactor(factor2);
        Map<City, Double> scores = calculator.calculate(CITIES);

        assertEquals(1d, scores.get(LONDON), DELTA);
        assertEquals((0.5 + 1)/2, scores.get(LONDONTOWNEE), DELTA);
        assertTrue(scores.get(LONDONTOWNEE) > scores.get(LONDON1));
    }

    @Test
    public void calculate_multipleFactorsAndDifferentWeight_expectedCorrectScore() {
        CityCodeFactor factor1 = new CityCodeFactor("London", CITIES, 2);
        CityLocationFactor factor2 = new CityLocationFactor(42.98339, -81.23304, CITIES, 1);
        ScoreCalculator calculator = ScoreCalculator.of(factor1).addFactor(factor2);
        Map<City, Double> scores = calculator.calculate(CITIES);

        assertEquals(1d, scores.get(LONDON), DELTA);
        assertEquals((0.5 * 2 + 1)/3, scores.get(LONDONTOWNEE), DELTA);
        assertTrue(scores.get(LONDONTOWNEE) < scores.get(LONDON1));
    }
}
