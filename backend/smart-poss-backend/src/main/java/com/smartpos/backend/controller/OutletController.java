package com.smartpos.backend.controller;

import com.smartpos.backend.dto.LoginRequest;
import com.smartpos.backend.dto.OutletRegisterRequest;
import com.smartpos.backend.dto.OutletResponse;
import com.smartpos.backend.service.OutletService;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class OutletController {

    private final OutletService outletService;

    public OutletController(OutletService outletService) {
        this.outletService = outletService;
    }

    @PostMapping("/outlets/register")
    public OutletResponse registerOutlet(@RequestBody OutletRegisterRequest request){
        return outletService.registerOutlet(request);
    }

    // LOGIN API
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


    // LOGOUT API
    @PutMapping("/logout/{id}")
    public void logout(@PathVariable String id){
        outletService.logout(id);
    }

    // ADMIN DASHBOARD
    @GetMapping("/admin/dashboard/summary")
    public Map<String,Long> getDashboardSummary(){
        return outletService.getDashboardSummary();
    }

    @GetMapping("/admin/outlets/pending")
    public List<OutletResponse> getPendingOutlets(){
        return outletService.getPendingOutlets();
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
}
