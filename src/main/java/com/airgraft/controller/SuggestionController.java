package com.airgraft.controller;

import com.airgraft.service.CitySuggestionService;
import com.airgraft.vo.SuggestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuggestionController {

    private final CitySuggestionService citySuggestionService;

    @Autowired
    public SuggestionController(CitySuggestionService citySuggestionService) {
        this.citySuggestionService = citySuggestionService;
    }

    @GetMapping("/suggestions")
    public SuggestionVo getSuggestion(@RequestParam(name = "q") String query,
                                       @RequestParam(name = "latitude", required = false) Double latitude,
                                       @RequestParam(name = "longitude", required = false) Double longitude){
        return citySuggestionService.getSuggestion(query, latitude, longitude);
    }
}
