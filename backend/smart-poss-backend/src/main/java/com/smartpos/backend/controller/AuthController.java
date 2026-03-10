//package com.smartpos.backend.controller;
//
//import com.smartpos.backend.dto.LoginRequest;
//import com.smartpos.backend.dto.LoginResponse;
//import com.smartpos.backend.security.JwtUtil;
//import com.smartpos.backend.model.Outlet;
//import com.smartpos.backend.repository.OutletRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private OutletRepository outletRepository;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @PostMapping("/login")
//    public LoginResponse login(@RequestBody LoginRequest request) {
//
//        Optional<Outlet> outlet =
//                outletRepository.findByEmail(request.getEmail());
//
//        if (outlet.isPresent()) {
//
//            String token = jwtUtil.generateToken(request.getEmail());
//
//            return new LoginResponse(token);
//        }
//
//        throw new RuntimeException("Invalid credentials");
//    }
//}
