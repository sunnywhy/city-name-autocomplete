package com.airgraft.repository;

import com.airgraft.entity.City;

import java.util.List;

public interface CityRepository {
    /**
     *  Find all the cities that code start with the given {prefix}.
     *  The function is case-sensitive, if there is a city code "London", with prefix "Lon", it will include in the return result.
     *  However, with prefix "lon", the city will not be incuded in the return result.
     * @param prefix must not be {@literal null}
     * @return never {@literal null}.
     */
    List<City> getCitiesByPrefix(String prefix);
}
