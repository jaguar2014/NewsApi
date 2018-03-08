package me.ashu.example.repositories;

import me.ashu.example.models.AppRole;
import org.springframework.data.repository.CrudRepository;

public interface AppRoleRepository extends CrudRepository<AppRole, Long> {
    AppRole findByRole(String role);
}

