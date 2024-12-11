package com.example.backend.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/posts/Images")
public class FileController {

    private static final Path uploadDir = Paths.get("uploads"); // Path to the 'uploads' folder

    // API to upload an image to the 'uploads' folder
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("poster") MultipartFile poster) {
        try {
            if (poster == null || poster.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No image file selected.");
            }

            // Generate a unique file name to prevent overwriting
            String fileName = UUID.randomUUID().toString() + "_" + poster.getOriginalFilename();
            Path filePath = uploadDir.resolve(fileName);

            // Create the 'uploads' directory if it doesn't exist
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // Save the file to disk
            poster.transferTo(filePath);

            // Return the image URL (adjust URL path as needed)
            String imageUrl = "/posts/Images/" + fileName;
            return ResponseEntity.ok("File uploaded successfully. URL: " + imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while uploading the image.");
        }
    }

    // API to serve the image from the 'uploads' folder
    @GetMapping("/images/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        try {
            Path filePath = uploadDir.resolve(imageName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
