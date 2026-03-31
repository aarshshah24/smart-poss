package com.smartpos.backend.service.impl;

import com.smartpos.backend.dto.MenuItemRequest;
import com.smartpos.backend.model.MenuItem;
import com.smartpos.backend.repository.MenuItemRepository;
import com.smartpos.backend.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuItemRepository menuItemRepository;

    public MenuServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public MenuItem addMenuItem(MenuItemRequest request) {
        // Task 3: Check for unique item name within the same outlet
        boolean exists = menuItemRepository.existsByOutletIdAndItemNameIgnoreCase(
                request.getOutletId(),
                request.getItemName()
        );

        if (exists) {
            // This message will be sent to the frontend
            throw new RuntimeException("Item '" + request.getItemName() + "' already exists in your menu.");
        }

        MenuItem item = new MenuItem();
        item.setOutletId(request.getOutletId());
        item.setItemName(request.getItemName());
        item.setPrice(request.getPrice());
        item.setCategory(request.getCategory());

        return menuItemRepository.save(item);
    }

    @Override
    public List<MenuItem> getMenuByOutlet(String outletId) {
        return menuItemRepository.findByOutletId(outletId);
    }

    @Override
    public void deleteMenuItem(String id) {
        menuItemRepository.deleteById(id);
    }

    @Override
    public MenuItem updateMenuItem(String id, MenuItemRequest request) {
        MenuItem item = menuItemRepository.findById(id).orElseThrow();

        // Optional: Add duplicate check here as well if name is changed during update
        item.setItemName(request.getItemName());
        item.setPrice(request.getPrice());
        item.setCategory(request.getCategory());

        return menuItemRepository.save(item);
    }
}