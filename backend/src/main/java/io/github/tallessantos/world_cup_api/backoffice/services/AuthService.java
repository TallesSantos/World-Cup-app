package io.github.tallessantos.world_cup_api.backoffice.services;

import io.github.tallessantos.world_cup_api.core.domain.BackofficeUserEntity;
import io.github.tallessantos.world_cup_api.infra.repository.BackofficeUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements CommandLineRunner {

    @Autowired
    private BackofficeUserRepository repository;

    public Boolean login(String username, String password){
        Optional<BackofficeUserEntity> user = repository.findByUsernameAndPassword(username, password);
        return user.isPresent();
    }

    @Override
    public void run(String... args) throws Exception {
        BackofficeUserEntity entity = new BackofficeUserEntity();
        entity.setUsername("admin");
        entity.setPassword("123");
        entity.setNickname("Admin_user");
        repository.save(entity);
    }
}
