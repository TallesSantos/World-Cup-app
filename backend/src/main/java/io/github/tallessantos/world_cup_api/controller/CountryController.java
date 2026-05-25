package io.github.tallessantos.world_cup_api.controller;

import io.github.tallessantos.world_cup_api.dto.CountryDetailResponse;
import io.github.tallessantos.world_cup_api.service.CountryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/countries")
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
