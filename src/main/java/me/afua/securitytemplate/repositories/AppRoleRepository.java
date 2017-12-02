package me.afua.securitytemplate.repositories;

import me.afua.securitytemplate.models.AppRole;
import me.afua.securitytemplate.models.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppRoleRepository extends CrudRepository<AppRole, Long> {
    AppRole findByRole(String role);
}

