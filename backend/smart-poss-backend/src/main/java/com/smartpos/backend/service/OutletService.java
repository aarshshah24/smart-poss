package com.smartpos.backend.service;

import com.smartpos.backend.dto.OutletRegisterRequest;
import com.smartpos.backend.dto.OutletResponse;

import java.util.List;

public interface OutletService {
    OutletResponse registerOutlet(OutletRegisterRequest request);
    List<OutletResponse> getPendingOutlets();
    void approveOutlet(String outletId);
    void rejectOutlet(String outletId);
}
