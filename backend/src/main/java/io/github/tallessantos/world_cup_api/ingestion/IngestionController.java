package io.github.tallessantos.world_cup_api.ingestion;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingestion")
public class IngestionController {

    @Autowired
    private IngestionService ingestionService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/main/run")
    public void runMainIngestion() throws Exception {
        ingestionService.run((String) null);
    }
}
