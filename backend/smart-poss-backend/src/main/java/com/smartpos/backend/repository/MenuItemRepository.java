package com.smartpos.backend.repository;

import com.smartpos.backend.model.MenuItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MenuItemRepository extends MongoRepository<MenuItem,String> {

    List<MenuItem> findByOutletId(String outletId);

}
