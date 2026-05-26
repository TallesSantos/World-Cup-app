package io.github.tallessantos.world_cup_api.api.controller;

import io.github.tallessantos.world_cup_api.api.config.ApiPathConfig;
import io.github.tallessantos.world_cup_api.api.dto.CountryDetailResponse;
import io.github.tallessantos.world_cup_api.business.core.service.CountryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.github.tallessantos.world_cup_api.api.config.ApiPathConfig.ENDPOINT_PREFIX;

@RestController
@RequestMapping(ENDPOINT_PREFIX + "/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/{id}")
    public CountryDetailResponse getById(@PathVariable String id) {
        return CountryDetailResponse.from(countryService.getCountryById(id));
    }
}
