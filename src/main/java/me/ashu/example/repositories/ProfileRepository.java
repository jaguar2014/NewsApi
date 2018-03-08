package me.ashu.example.repositories;

import me.ashu.example.models.AppUser;
import me.ashu.example.models.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRepository extends CrudRepository<Profile, Long> {


    List<Profile> findAll();

    List<Profile> findByAppUsersIn(AppUser appUser);



}
