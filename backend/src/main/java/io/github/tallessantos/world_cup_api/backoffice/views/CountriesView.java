package io.github.tallessantos.world_cup_api.backoffice.views;

import io.github.tallessantos.world_cup_api.core.domain.CountryDetail;
import io.github.tallessantos.world_cup_api.core.service.CountryService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Named("countriesView")
@ViewScoped
public class CountriesView implements Serializable {

    @Getter
    private List<CountryDetail> countries;

    @Getter
    @Setter
    private CountryDetail selectedCountry;

    @Inject
    private CountryService service;

    @PostConstruct
    public void init() {
        countries = service.findAll();
    }

    public void selectCountry(CountryDetail country) {
        this.selectedCountry = country;
    }

    public void clearSelection() {
        this.selectedCountry = null;
    }
}