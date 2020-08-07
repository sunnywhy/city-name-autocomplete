package com.airgraft.vo;

import java.util.ArrayList;
import java.util.List;

public class SuggestionVo {
    private final List<CityVo> suggestions;

    //needed for springboot to convert pojo to json
    public SuggestionVo() {
        this(null);
    }

    public SuggestionVo(List<CityVo> suggestions) {
        if(suggestions == null) suggestions = new ArrayList<>();
        this.suggestions = suggestions;
    }

    public List<CityVo> getSuggestions() {
        return suggestions;
    }
}
