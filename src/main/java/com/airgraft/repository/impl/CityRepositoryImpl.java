package com.airgraft.repository.impl;

import com.airgraft.entity.City;
import com.airgraft.repository.CityRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CityRepositoryImpl implements CityRepository {

    @Override
    public List<City> getCitiesByPrefix(String prefix) {
        return getAllCities().stream().filter(x -> x.getCode().startsWith(prefix)).collect(Collectors.toList());
    }

    /**
     * User hard coded data to simulate data returned from database or other data source
     * @return all the cities.
     */
    private List<City> getAllCities() {
        List<City> cities = new ArrayList<>();
        cities.add(new City("London", "London, ON, Canada", 42.98339, -81.23304));
        cities.add(new City("London", "London, OH, USA", 39.88645, -83.44825));
        cities.add(new City("London", "London, KY, USA", 37.12898, -84.08326));
        cities.add(new City("Londontowne", "Londontowne, MD, USA", 38.93345, -76.54941));
        return cities;
    }

}
