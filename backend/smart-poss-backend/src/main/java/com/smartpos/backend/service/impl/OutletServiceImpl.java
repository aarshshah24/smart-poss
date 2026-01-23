package com.smartpos.backend.service.impl;

import com.smartpos.backend.dto.OutletRegisterRequest;
import com.smartpos.backend.dto.OutletResponse;
import com.smartpos.backend.exception.EmailAlreadyExistsException;
import com.smartpos.backend.exception.OutletNotFoundException;
import com.smartpos.backend.model.Outlet;
import com.smartpos.backend.repository.OutletRepository;
import com.smartpos.backend.service.OutletService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OutletServiceImpl implements OutletService {
    private final OutletRepository outletRepository;

    public OutletServiceImpl(OutletRepository outletRepository) {
        this.outletRepository = outletRepository;
    }

    public OutletResponse registerOutlet(OutletRegisterRequest request){
        outletRepository.findByEmail(request.getEmail())
                .ifPresent(o ->{
                    throw new EmailAlreadyExistsException("Email already registered");
                });

        Outlet outlet = new Outlet();
        outlet.setOutletName(request.getOutletName());
        outlet.setOwnerName(request.getOwnerName());
        outlet.setEmail(request.getEmail());
        outlet.setPhoneNumber(request.getPhoneNumber());
        outlet.setCity(request.getCity());
        outlet.setOutletType(request.getOutletType());
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
        return response;
    }

    public List<OutletResponse> getPendingOutlets(){
        return outletRepository.findAll()
                .stream()
                .filter(o -> "PENDING".equals(o.getStatus()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void approveOutlet(String outletId){
        Outlet outlet = outletRepository.findById(outletId).orElseThrow(() -> new OutletNotFoundException("Outlet not found"));
        outlet.setStatus("APPROVED");
        outlet.setApprovedAt(LocalDateTime.now());
        outletRepository.save(outlet);
    }

    public void rejectOutlet(String outletId){
        Outlet outlet = outletRepository.findById(outletId).orElseThrow(() -> new OutletNotFoundException("Outlet not found"));

        outlet.setStatus("REJECTED");
        outlet.setApprovedAt(null);
        outletRepository.save(outlet);
    }

}
