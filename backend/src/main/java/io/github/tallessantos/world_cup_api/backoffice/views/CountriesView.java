package io.github.tallessantos.world_cup_api.backoffice.views;

import io.github.tallessantos.world_cup_api.backoffice.utils.AuditUtils;
import io.github.tallessantos.world_cup_api.backoffice.utils.ToastMessageUtil;
import io.github.tallessantos.world_cup_api.core.domain.CountryConfederationType;
import io.github.tallessantos.world_cup_api.core.domain.CountryEntity;
import io.github.tallessantos.world_cup_api.core.domain.MediaEntity;
import io.github.tallessantos.world_cup_api.backoffice.services.CountryBackofficeService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named("countriesBean")
@ViewScoped
public class CountriesView implements Serializable {

    @Inject
    private CountryBackofficeService service;

    @Inject
    private ToastMessageUtil toastMessageUtil;

    @Getter
    private List<CountryEntity> countries;

    @Getter
    @Setter
    private CountryEntity selectedCountry =
            new CountryEntity();

    @Getter
    @Setter
    private CountryEntity pendingSave;

    /*
     * FILTERS
     */

    @Getter
    @Setter
    private String filterCountryName;

    @Getter
    @Setter
    private String filterFifaCode;

    @Getter
    @Setter
    private CountryConfederationType filterCountryConfederationType;

    @Getter
    @Setter
    private Boolean filterFinished;

    /*
     * SORT
     */

    @Getter
    @Setter
    private String sortField =
            "name";

    @Getter
    @Setter
    private String sortDirection =
            "asc";

    /*
     * PAGINATION
     */

    @Getter
    @Setter
    private int pageSize = 5;

    @Getter
    private int currentPage = 0;

    @Getter
    private boolean hasNextPage;

    @Getter
    private long totalRecords;

    @Getter
    private int totalPages;

    /*
     * IMAGES
     */

    @Getter
    private Map<Long, Part> uploadedFlags =
            new HashMap<>();

    @PostConstruct
    public void init() {

        loadPage();
    }

    public CountryConfederationType[] getCountryConfederations() {
        return CountryConfederationType.values();
    }

    public void loadPage() {

        Page<CountryEntity> page =
                service.findPageFiltered(
                        currentPage,
                        pageSize,
                        filterCountryName,
                        filterFifaCode,
                        filterCountryConfederationType,
                        filterFinished,
                        sortField,
                        sortDirection
                );

        countries = page.stream().toList();

        totalRecords = page.getTotalElements();

        totalPages = page.getTotalPages();

        if (countries == null) {

            countries = Collections.emptyList();
        }

        hasNextPage = countries.size() == pageSize;
    }

    public void filter() {

        currentPage = 0;

        loadPage();
    }

    public void clearFilters() {

        filterCountryName = null;
        filterFifaCode = null;
        filterCountryConfederationType = null;
        filterFinished = null;

        sortField = "name";
        sortDirection = "asc";

        currentPage = 0;

        loadPage();
    }

    public void nextPage() {

        currentPage++;

        loadPage();
    }

    public void previousPage() {

        if (currentPage > 0) {

            currentPage--;
        }

        loadPage();
    }

    public void changePageSize() {

        currentPage = 0;

        loadPage();
    }

    public void selectCountry(
            CountryEntity country
    ) {

        selectedCountry =
                country;
    }

    public void clearSelection() {

        selectedCountry =
                new CountryEntity();
    }

    public void askSave(
            CountryEntity country
    ) {

        pendingSave = country;
    }

    public void confirmSave() {

        if (Boolean.TRUE.equals(
                pendingSave.getAudit()
                        .getFinished()
        )) {

            AuditUtils.markFinished(
                    pendingSave,
                    true,
                    "BACKOFFICE_USER"
            );
        }

        service.save(pendingSave);

        pendingSave = null;

        toastMessageUtil.addMessage(
                FacesMessage.SEVERITY_INFO,
                "Successfully saved registry data"
        );

        loadPage();
    }

    public void cancelSave() {

        pendingSave =
                null;

        loadPage();
    }

    public void uploadFlag(
            CountryEntity country
    ) {

        Part uploaded = uploadedFlags.get(country.getId());

        if (uploaded == null) {

            return;
        }

        try {

            byte[] bytes = uploaded.getInputStream().readAllBytes();

            MediaEntity media;

            if (country.getCountryFlagImage() == null) {
                media =
                        service.saveFlagImage(country, bytes);

            } else {

                media = service.updateFlagImage(country, bytes);

            }

            country.setCountryFlagImage(media);

            uploadedFlags.remove(country.getId());

            toastMessageUtil.addMessage(
                    FacesMessage.SEVERITY_INFO,
                    "Flag saved"
            );

            loadPage();

        } catch (Exception e) {

            throw new RuntimeException(
                    e
            );
        }

    }

}