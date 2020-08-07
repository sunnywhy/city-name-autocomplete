package com.airgraft.service.score;


import com.airgraft.entity.City;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CityCodeFactorTest {

    private static final City LONDON = new City("London", "London, ON, Canada", 42.98339, -81.23304);
    private static final City MONTREAL = new City("Montreal", "Montreal, QC, Canada", 39.88645, -83.44825);
    private static final City LONDONTOWNEE = new City("Londontownee", "Londontownee, MD, USA", 38.93345, -76.54941);
    private static final List<City> CITIES = Arrays.asList(LONDON, MONTREAL, LONDONTOWNEE);

    @Test(expected = IllegalArgumentException.class)
    public void buildCityCodeFactor_emptyQuery_expectedIllegalArgumentException() {
        new CityCodeFactor("", CITIES);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildCityCodeFactor_negativeWeight_expectedIllegalArgumentException() {
        new CityCodeFactor("Londo", CITIES, -1);
    }

    @Test
    public void buildCityCodeFactor_withoutWeight_expectedDefaultWeight() {
        CityCodeFactor factor = new CityCodeFactor("Londo", CITIES);
        assertEquals(1, factor.getWeight());
    }

    @Test
    public void getScores_expectedCorrectScoreForCity() {
        CityCodeFactor factor = new CityCodeFactor("London", CITIES);
        Map<City, Double> scores = factor.getScores();
        double delta = 0.01;
        assertEquals(1d, scores.get(LONDON), delta);
        assertEquals(0d, scores.get(MONTREAL), delta);
        assertEquals(0.5, scores.get(LONDONTOWNEE), delta);
    }
}
