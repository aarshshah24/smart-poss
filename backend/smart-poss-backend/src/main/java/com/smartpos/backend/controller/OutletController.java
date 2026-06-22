package com.smartpos.backend.controller;

import com.smartpos.backend.dto.LoginRequest;
import com.smartpos.backend.dto.OutletRegisterRequest;
import com.smartpos.backend.dto.OutletResponse;
import com.smartpos.backend.model.Outlet;
import com.smartpos.backend.repository.OutletRepository;
import com.smartpos.backend.service.OutletService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class OutletController {

    private final OutletService outletService;
    private final OutletRepository outletRepository;
    public OutletController(OutletService outletService , OutletRepository outletRepository) {
        this.outletService = outletService;
        this.outletRepository = outletRepository;
    }

    @PostMapping("/outlets/register")
    public OutletResponse registerOutlet(@RequestBody OutletRegisterRequest request){
        return outletService.registerOutlet(request);
    }

    @GetMapping("/outlets/{id}")
    public OutletResponse getOutletById(@PathVariable String id) {
        return outletService.getOutletById(id);
    }

    @PostMapping("/outlet/login")
    public Map<String,Object> login(@RequestBody LoginRequest request){
        Map<String,Object> response = new HashMap<>();
        try{
            OutletResponse outlet = outletService.login(request);
            response.put("success", true);
            response.put("data", outlet);
        }catch(Exception e){
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }

    @PutMapping("/logout/{id}")
    public void logout(@PathVariable String id){
        outletService.logout(id);
    }

    @GetMapping("/admin/dashboard/summary")
    public Map<String,Long> getDashboardSummary(){
        return outletService.getDashboardSummary();
    }

    @GetMapping("/admin/outlets/pending")
    public List<OutletResponse> getPendingOutlets(){
        return outletService.getPendingOutlets();
    }

    @GetMapping("/admin/outlets/verified")
    public List<OutletResponse> getVerifiedOutlets() {
        return outletService.getVerifiedOutlets();
    }

    @PutMapping("/admin/outlets/{outletId}/approve")
    public String approveOutlet(@PathVariable String outletId){
        outletService.approveOutlet(outletId);
        return "Outlet approved successfully";
    }

    @PutMapping("/admin/outlets/{outletId}/reject")
    public String rejectOutlet(@PathVariable String outletId){
        outletService.rejectOutlet(outletId);
        return "Outlet rejected successfully";
    }
    // Inside OutletController.java
    @PutMapping("/heartbeat/{id}")
    public void poke(@PathVariable String id) {
        Outlet outlet = outletRepository.findById(id).orElseThrow();
        outlet.setLastActive(LocalDateTime.now());
        outletRepository.save(outlet);
    }
}