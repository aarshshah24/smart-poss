package com.smartpos.backend.controller;

import com.smartpos.backend.dto.MenuItemRequest;
import com.smartpos.backend.model.MenuItem;
import com.smartpos.backend.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/menu") // Base path for all menu operations
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    // URL: POST http://localhost:8080/api/menu/add
    @PostMapping("/add")
    public MenuItem addMenuItem(@RequestBody MenuItemRequest request){
        return menuService.addMenuItem(request);
    }

    // URL: GET http://localhost:8080/api/menu/{outletId}
    @GetMapping("/{outletId}")
    public List<MenuItem> getMenu(@PathVariable String outletId){
        return menuService.getMenuByOutlet(outletId);
    }

    // URL: DELETE http://localhost:8080/api/menu/{id}
    @DeleteMapping("/{id}")
    public void deleteMenuItem(@PathVariable String id){
        menuService.deleteMenuItem(id);
    }

    // URL: PUT http://localhost:8080/api/menu/{id}
    @PutMapping("/{id}")
    public MenuItem updateMenuItem(@PathVariable String id, @RequestBody MenuItemRequest request){
        return menuService.updateMenuItem(id, request);
    }
}