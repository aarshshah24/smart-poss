package com.smartpos.backend.service;

import com.smartpos.backend.dto.MenuItemRequest;
import com.smartpos.backend.model.MenuItem;

import java.util.List;

public interface MenuService {

    MenuItem addMenuItem(MenuItemRequest request);

    List<MenuItem> getMenuByOutlet(String outletId);

    void deleteMenuItem(String id);

    MenuItem updateMenuItem(String id, MenuItemRequest request);

}
