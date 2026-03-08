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

        item.setItemName(request.getItemName());
        item.setPrice(request.getPrice());
        item.setCategory(request.getCategory());

        return menuItemRepository.save(item);
    }
}
