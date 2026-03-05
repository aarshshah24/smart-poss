package com.smartpos.backend.controller;

import com.smartpos.backend.dto.OutletRegisterRequest;
import com.smartpos.backend.dto.OutletResponse;
import com.smartpos.backend.service.OutletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class OutletController {
    private final OutletService outletService;

    OutletController(OutletService outletService) {
        this.outletService = outletService;
    }

    @PostMapping("/outlets/register")
    public OutletResponse registerOutlet(@RequestBody OutletRegisterRequest request){
        return outletService.registerOutlet(request);
    }

    @PostMapping("/outlets/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {

        try {
            String email = request.get("email");
            String password = request.get("password");

            OutletResponse response = outletService.login(email, password);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", e.getMessage()));
        }
    }



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
