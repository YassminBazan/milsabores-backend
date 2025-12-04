package com.milsabores.learning.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.milsabores.learning.model.Usuario;
import com.milsabores.learning.repository.UsuarioRepository;

@RestController
@RequestMapping("/auth") 
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- 1. ENDPOINT REGISTRO ---
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().body("Error: El email ya está en uso.");
        }
        
        // Encriptamos la contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("¡Usuario registrado exitosamente!");
    }

    // --- 2. ENDPOINT LOGIN (Prueba) ---
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        Usuario user = usuarioRepository.findByEmail(email).orElse(null);

        if (user == null) {
            // Devolvemos un JSON de error
            return ResponseEntity.badRequest().body(Map.of("error", "Usuario no encontrado"));
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            // Devolvemos un JSON de error
            return ResponseEntity.badRequest().body(Map.of("error", "Contraseña incorrecta"));
        }
        user.setPassword(""); 
        return ResponseEntity.ok(user);
    }
}