package com.pm.auth_service.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private UserRepository userRepo;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        // In a real app, hash the password
        userRepo.save(user);
        return "User registered";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> req) {
        String username = req.get("username"), password = req.get("password");
        return userRepo.findByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .map(u -> ResponseEntity.ok(Map.of("token", jwtUtil.generateToken(username))))
                .orElse(ResponseEntity.status(401).body("Invalid credentials"));
    }
}
