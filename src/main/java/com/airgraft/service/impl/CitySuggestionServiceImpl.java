package com.airgraft.service.impl;

import com.airgraft.entity.City;
import com.airgraft.repository.CityRepository;
import com.airgraft.service.CitySuggestionService;
import com.airgraft.service.score.CityCodeFactor;
import com.airgraft.service.score.CityLocationFactor;
import com.airgraft.service.score.ScoreCalculator;
import com.airgraft.vo.CityVo;
import com.airgraft.vo.SuggestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CitySuggestionServiceImpl implements CitySuggestionService {
    private final CityRepository cityRepository;

    @Autowired
    public CitySuggestionServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public SuggestionVo getSuggestion(String query, Double latitude, Double longitude) {
        List<City> cities = cityRepository.getCitiesByPrefix(query);
        if(cities == null || cities.isEmpty()) return new SuggestionVo();

        ScoreCalculator calculator = ScoreCalculator.of(new CityCodeFactor(query, cities));

        if(latitude != null && longitude != null) calculator.addFactor(new CityLocationFactor(latitude, longitude, cities));

        Map<City, Double> scores = calculator.calculate(cities);
        List<CityVo> cityVos = new ArrayList<>();
        for(City city : cities) {
            cityVos.add(new CityVo(city.getName(), city.getLatitude(), city.getLongitude(), scores.get(city)));
        }
        cityVos.sort(Comparator.comparingDouble(CityVo::getScore).reversed());
        return new SuggestionVo(cityVos);
    }
}
