package com.smartpos.backend.repository;

import com.smartpos.backend.model.Outlet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OutletRepository extends MongoRepository<Outlet, String> {
    Optional<Outlet> findByEmail(String email);
}
