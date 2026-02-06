package com.smartpos.backend.service.impl;

import com.smartpos.backend.dto.OutletRegisterRequest;
import com.smartpos.backend.dto.OutletResponse;
import com.smartpos.backend.exception.EmailAlreadyExistsException;
import com.smartpos.backend.exception.OutletNotFoundException;
import com.smartpos.backend.exception.PhoneNumberAlreadyExistsException;
import com.smartpos.backend.model.Outlet;
import com.smartpos.backend.repository.OutletRepository;
import com.smartpos.backend.service.EmailService;
import com.smartpos.backend.service.OutletService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

@Service
public class OutletServiceImpl implements OutletService {
    private final EmailService emailService;
    private final OutletRepository outletRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public OutletServiceImpl(OutletRepository outletRepository,BCryptPasswordEncoder passwordEncoder,EmailService emailService) {
        this.outletRepository = outletRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public OutletResponse registerOutlet(OutletRegisterRequest request){
        outletRepository.findByEmail(request.getEmail())
                .ifPresent(o ->{
                    throw new EmailAlreadyExistsException("Email already registered");
                });
        outletRepository.findByPhoneNumber(request.getPhoneNumber())
                .ifPresent(o -> {
                    throw new PhoneNumberAlreadyExistsException("Phone number already registered");
                });

        Outlet outlet = new Outlet();
        outlet.setOutletName(request.getOutletName());
        outlet.setOwnerName(request.getOwnerName());
        outlet.setEmail(request.getEmail());
        outlet.setPhoneNumber(request.getPhoneNumber());
        outlet.setCity(request.getCity());
        outlet.setOutletType(request.getOutletType());
        outlet.setPassword(passwordEncoder.encode(request.getPassword()));
        outlet.setStatus("PENDING");
        outlet.setCreatedAt(LocalDateTime.now());
        outlet.setApprovedAt(null);

        Outlet savedOutlet = outletRepository.save(outlet);
        return mapToResponse(savedOutlet);
    }
    private OutletResponse mapToResponse(Outlet outlet){
        OutletResponse response = new OutletResponse();
        response.setId(outlet.getId());
        response.setOutletName(outlet.getOutletName());
        response.setOwnerName(outlet.getOwnerName());
        response.setEmail(outlet.getEmail());
        response.setPhoneNumber(outlet.getPhoneNumber());
        response.setCity(outlet.getCity());
        response.setOutletType(outlet.getOutletType());
        response.setStatus(outlet.getStatus());
        response.setCreatedAt(outlet.getCreatedAt());
        response.setApprovedAt(outlet.getApprovedAt());
        response.setPassword(null); //don't return password
        return response;
    }

    public List<OutletResponse> getPendingOutlets(){
        return outletRepository.findByStatus("PENDING")
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Map<String,Long> getDashboardSummary(){

        Map<String,Long> data = new HashMap<>();
        data.put("totalOutlets", outletRepository.count());
        data.put("pendingOutlets",outletRepository.countByStatus("PENDING"));
        data.put("approvedOutlets",outletRepository.countByStatus("APPROVED"));
        return data;
    }

    public void approveOutlet(String outletId){
        Outlet outlet = outletRepository.findById(outletId).orElseThrow(() -> new OutletNotFoundException("Outlet not found"));
        outlet.setStatus("APPROVED");
        outlet.setApprovedAt(LocalDateTime.now());
        outletRepository.save(outlet);
        emailService.sendApprovalMail(outlet.getEmail());
    }

    public void rejectOutlet(String outletId){
        Outlet outlet = outletRepository.findById(outletId).orElseThrow(() -> new OutletNotFoundException("Outlet not found"));

        outlet.setStatus("REJECTED");
        outlet.setApprovedAt(null);
        outletRepository.save(outlet);
        emailService.sendRejectionMail(outlet.getEmail());
    }

}
