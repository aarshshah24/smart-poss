package com.smartpos.backend.repository;

import com.smartpos.backend.model.Outlet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OutletRepository extends MongoRepository<Outlet, String> {
    Optional<Outlet> findByEmail(String email);
    List<Outlet> findByStatus(String status);
    long countByStatus(String status);
    Optional<Outlet> findByPhoneNumber(Long phoneNumber);

}
