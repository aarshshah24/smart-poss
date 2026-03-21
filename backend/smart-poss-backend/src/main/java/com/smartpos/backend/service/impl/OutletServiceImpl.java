package com.smartpos.backend.service.impl;

import com.smartpos.backend.dto.LoginRequest;
import com.smartpos.backend.dto.OutletRegisterRequest;
import com.smartpos.backend.dto.OutletResponse;
import com.smartpos.backend.model.Outlet;
import com.smartpos.backend.repository.OutletRepository;
import com.smartpos.backend.service.EmailService;
import com.smartpos.backend.service.OutletService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OutletServiceImpl implements OutletService {

    private final OutletRepository outletRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public OutletServiceImpl(OutletRepository outletRepository, EmailService emailService) {
        this.outletRepository = outletRepository;
        this.emailService = emailService;
    }

    @Override
    public OutletResponse registerOutlet(OutletRegisterRequest request){
        Outlet outlet = new Outlet();
        outlet.setOwnerName(request.getOwnerName());
        outlet.setEmail(request.getEmail());
        outlet.setPhoneNumber(request.getPhoneNumber());
        outlet.setOutletName(request.getOutletName());
        outlet.setCity(request.getCity());
        outlet.setOutletType(request.getOutletType());
        outlet.setPassword(passwordEncoder.encode(request.getPassword()));
        outlet.setStatus("PENDING");
        outlet.setLoggedIn(false);
        outlet.setCreatedAt(LocalDateTime.now());
        outletRepository.save(outlet);
        return mapToResponse(outlet);
    }

    @Override
    public OutletResponse login(LoginRequest request){
        Outlet outlet = outletRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        if(!passwordEncoder.matches(request.getPassword(), outlet.getPassword())){
            throw new RuntimeException("Invalid email or password");
        }
        if(!outlet.getStatus().equals("APPROVED")){
            throw new RuntimeException("Outlet not approved yet");
        }
        outlet.setLoggedIn(true);
        outletRepository.save(outlet);
        return mapToResponse(outlet);
    }

    @Override
    public void logout(String outletId){
        Outlet outlet = outletRepository.findById(outletId)
                .orElseThrow(() -> new RuntimeException("Outlet not found"));
        outlet.setLoggedIn(false);
        outletRepository.save(outlet);
    }

    // Inside OutletServiceImpl.java
    @Override
    public Map<String, Long> getDashboardSummary() {
        Map<String, Long> data = new HashMap<>();

        // Define the window for what counts as "active"
        LocalDateTime activeThreshold = LocalDateTime.now().minusMinutes(2);

        data.put("totalOutlets", outletRepository.count());
        data.put("pendingOutlets", outletRepository.countByStatus("PENDING"));
        data.put("approvedOutlets", outletRepository.countByStatus("APPROVED"));

        // New logic: Count users active in the last 2 minutes
        data.put("activeUsers", outletRepository.countByLastActiveAfter(activeThreshold));

        return data;
    }

    @Override
    public List<OutletResponse> getPendingOutlets(){
        List<Outlet> outlets = outletRepository.findByStatus("PENDING");
        List<OutletResponse> responses = new ArrayList<>();
        for(Outlet outlet : outlets){
            responses.add(mapToResponse(outlet));
        }
        return responses;
    }

    @Override
    public List<OutletResponse> getVerifiedOutlets() {
        List<Outlet> outlets = outletRepository.findByStatus("APPROVED");
        List<OutletResponse> responses = new ArrayList<>();
        for (Outlet outlet : outlets) {
            responses.add(mapToResponse(outlet));
        }
        return responses;
    }

    @Override
    public void approveOutlet(String outletId){
        Outlet outlet = outletRepository.findById(outletId)
                .orElseThrow(() -> new RuntimeException("Outlet not found"));
        outlet.setStatus("APPROVED");
        outlet.setApprovedAt(LocalDateTime.now());
        outletRepository.save(outlet);
        emailService.sendApprovalMail(outlet.getEmail());
    }

    @Override
    public void rejectOutlet(String outletId){
        Outlet outlet = outletRepository.findById(outletId)
                .orElseThrow(() -> new RuntimeException("Outlet not found"));
        outlet.setStatus("REJECTED");
        outletRepository.save(outlet);
        emailService.sendRejectionMail(outlet.getEmail());
    }

    private OutletResponse mapToResponse(Outlet outlet){
        OutletResponse response = new OutletResponse();
        response.setId(outlet.getId());
        response.setOwnerName(outlet.getOwnerName());
        response.setEmail(outlet.getEmail());
        response.setPhoneNumber(outlet.getPhoneNumber());
        response.setOutletName(outlet.getOutletName());
        response.setCity(outlet.getCity());
        response.setOutletType(outlet.getOutletType());
        response.setStatus(outlet.getStatus());
        response.setApprovedAt(outlet.getApprovedAt()); // Map the timestamp to DTO
        return response;
    }
}