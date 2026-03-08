package com.smartpos.backend.controller;

import com.smartpos.backend.dto.MenuItemRequest;
import com.smartpos.backend.model.MenuItem;
import com.smartpos.backend.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/add")
    public MenuItem addMenuItem(@RequestBody MenuItemRequest request){
        return menuService.addMenuItem(request);
    }

    @GetMapping("/{outletId}")
    public List<MenuItem> getMenu(@PathVariable String outletId){
        return menuService.getMenuByOutlet(outletId);
    }

    @DeleteMapping("/{id}")
    public void deleteMenuItem(@PathVariable String id){
        menuService.deleteMenuItem(id);
    }

    @PutMapping("/{id}")
    public MenuItem updateMenuItem(@PathVariable String id, @RequestBody MenuItemRequest request){
        return menuService.updateMenuItem(id, request);
    }
}
