package io.github.tallessantos.world_cup_api.backoffice.services;

import io.github.tallessantos.world_cup_api.core.domain.MatchEntity;
import io.github.tallessantos.world_cup_api.infra.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchBackofficeService {

    @Autowired
    private MatchRepository matchRepository;

    public List<MatchEntity> findAll() {
        return matchRepository.findAll();
    }

    public Page<MatchEntity> findFiltered(String filterStage,
                                          String filterCity,
                                          String filterHomeTeam,
                                          String filterAwayTeam,
                                          Boolean filterFinished,
                                          int page,
                                          int size,
                                          String sortField,
                                          String sortDirection) {


        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return matchRepository.findFiltered(
                filterStage,
                filterCity,
                filterHomeTeam,
                filterAwayTeam,
                filterFinished,
                pageable

        );
    }

    public void save(MatchEntity pendingSave) {
        matchRepository.save(pendingSave);
    }
}
