package com.airgraft.service;

import com.airgraft.vo.SuggestionVo;

public interface CitySuggestionService {
    /**
     * Get city suggestions based on the given {query}, given {latitude} and given {longitude}.
     * @param query must not be null.
     * @param latitude if not null, longitude must not be null either.
     * @param longitude if not null, latitude must not be null either.
     * @return suggested cities, order by score desc.
     */
    SuggestionVo getSuggestion(String query, Double latitude, Double longitude);
}
