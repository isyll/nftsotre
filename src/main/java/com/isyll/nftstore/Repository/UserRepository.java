package com.isyll.nftstore.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.isyll.nftstore.Model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
