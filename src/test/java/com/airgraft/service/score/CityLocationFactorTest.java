package com.airgraft.service.score;

import com.airgraft.entity.City;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CityLocationFactorTest {
    private static final City LONDON = new City("London", "London, ON, Canada", 42.98339, -81.23304);
    private static final City MONTREAL = new City("Montreal", "Montreal, QC, Canada", 39.88645, -83.44825);
    private static final City LONDONTOWNEE = new City("Londontownee", "Londontownee, MD, USA", 28.93345, 76.54941);
    private static final List<City> CITIES = Arrays.asList(LONDON, MONTREAL, LONDONTOWNEE);

    @Test(expected = IllegalArgumentException.class)
    public void buildCityLocationFactor_emptyLatitude_expectedIllegalArgumentException() {
        new CityLocationFactor(null, 42.95, CITIES);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildCityLocationFactor_emptyLongitude_expectedIllegalArgumentException() {
        new CityLocationFactor(42.95, null, CITIES);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildCityLocationFactor_negativeWeight_expectedIllegalArgumentException() {
        new CityLocationFactor(42.95, -80.25, CITIES, -1);
    }

    @Test
    public void buildCityLocationFactor_withoutWeight_expectedDefaultWeight() {
        CityLocationFactor factor = new CityLocationFactor(42.95, -80.25, CITIES);
        assertEquals(1, factor.getWeight());
    }

    @Test
    public void getScores_expectedCorrectScoreForCity() {
        CityLocationFactor factor = new CityLocationFactor(42.98339, -81.23304, CITIES);
        Map<City, Double> scores = factor.getScores();
        double delta = 0.01;
        assertEquals(1d, scores.get(LONDON), delta);
        assertEquals(0.855, scores.get(MONTREAL), delta);
        assertEquals(0d, scores.get(LONDONTOWNEE), delta);
    }
}
