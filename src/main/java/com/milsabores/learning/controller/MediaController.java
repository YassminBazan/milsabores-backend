package com.milsabores.learning.controller; 

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/media")
@CrossOrigin(origins = "http://localhost:5173") 
public class MediaController {

    // Guardaremos las fotos en una carpeta "uploads" en la raíz del proyecto
    private final Path rootLocation = Paths.get("uploads");

    public MediaController() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el directorio uploads", e);
        }
    }

    // --- 2. SUBIR IMAGEN (POST) ---
    @PostMapping("/upload")
    public Map<String, String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Generamos nombre único para no sobrescribir fotos con el mismo nombre
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            
            // Guardamos en el disco
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename));
            
            Map<String, String> response = new HashMap<>();
            // Devolvemos la ruta para que React la guarde en la BD
            // Ejemplo: "media/foto123.jpg"
            response.put("url", "media/" + filename); 
            
            return response;
        } catch (IOException e) {
            throw new RuntimeException("Fallo al guardar archivo: " + e.getMessage());
        }
    }

    // --- 3. VER IMAGEN (GET) - ¡ESTO FALTABA EN EL TUYO! ---
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        // Esto le dice al navegador: "Es una imagen, muéstrala"
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("No se puede leer el archivo: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}