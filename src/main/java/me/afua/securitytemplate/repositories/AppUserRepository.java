package me.afua.securitytemplate.repositories;

import me.afua.securitytemplate.models.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    AppUser findByUsername(String s);
}
