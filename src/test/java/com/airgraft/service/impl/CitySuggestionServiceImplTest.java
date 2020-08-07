package com.airgraft.service.impl;

import com.airgraft.entity.City;
import com.airgraft.repository.CityRepository;
import com.airgraft.vo.CityVo;
import com.airgraft.vo.SuggestionVo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CitySuggestionServiceImplTest {
    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CitySuggestionServiceImpl citySuggestionService;

    private static final String PREFIX = "Londo";
    private static final City CITY1 = new City("London", "London, ON, Canada", 42.98339, -81.23304);
    private static final City CITY2 = new City("London", "London, OH, USA", 39.88645, -83.44825);
    private static final City CITY3 = new City("Londontowne", "Londontowne, MD, USA", 38.93345, -76.54941);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getSuggestion_noMatchingCities_expectedEmptySuggestion() {
        when(cityRepository.getCitiesByPrefix(anyString())).thenReturn(null);
        SuggestionVo vo = citySuggestionService.getSuggestion(PREFIX, null, null);

        assertEquals(0, vo.getSuggestions().size());
        verify(cityRepository).getCitiesByPrefix(PREFIX);
    }

    @Test
    public void getSuggestion_withOnlyQuery_expectedCitiesOrderByScoresDesc() {
        when(cityRepository.getCitiesByPrefix(anyString())).thenReturn(Arrays.asList(CITY3, CITY2, CITY1));
        SuggestionVo vo = citySuggestionService.getSuggestion(PREFIX, null, null);
        List<CityVo> cityVoList = vo.getSuggestions();

        assertEquals(3, cityVoList.size());
        assertTrue(cityVoList.get(0).getScore() >= cityVoList.get(1).getScore());
        assertTrue(cityVoList.get(1).getScore() >= cityVoList.get(2).getScore());
        verify(cityRepository).getCitiesByPrefix(PREFIX);
    }

    @Test
    public void getSuggestion_withLatitudeAndLongitude_expectedCitiesOrderByScoresDesc() {
        when(cityRepository.getCitiesByPrefix(anyString())).thenReturn(Arrays.asList(CITY2, CITY3, CITY1));
        SuggestionVo vo = citySuggestionService.getSuggestion(PREFIX, 40.13, -82.55);
        List<CityVo> cityVoList = vo.getSuggestions();

        assertEquals(3, cityVoList.size());
        assertTrue(cityVoList.get(0).getScore() >= cityVoList.get(1).getScore());
        assertTrue(cityVoList.get(1).getScore() >= cityVoList.get(2).getScore());
        verify(cityRepository).getCitiesByPrefix(PREFIX);
    }
}
