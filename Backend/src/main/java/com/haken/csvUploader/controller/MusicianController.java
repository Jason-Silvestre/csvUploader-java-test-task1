package com.haken.csvUploader.controller;

import com.haken.csvUploader.entities.Musician;
import com.haken.csvUploader.service.MusicianService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/musicians")
@RequiredArgsConstructor
public class MusicianController {

    @Autowired
    private final MusicianService service;

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadMusicians(
            @RequestPart("file") MultipartFile file,
            @RequestParam(value = "delimiter", defaultValue = ",") String delimiter
    ) {
        try {
            int result = service.uploadMusicians(file, delimiter);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/findAllMusicians")
    public ResponseEntity<?> findAllMusicians() {
        try {
            List<Musician> musicians = service.findAllMusicians();
            return ResponseEntity.ok(musicians);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving musicians: " + e.getMessage());
        }
    }

    @GetMapping("/searchByNameOrAge")
    public ResponseEntity<?> searchByNameOrAge(@RequestParam("query") String query) {
        try {
            List<Musician> musicians = service.searchByNameOrAge(query);
            if (musicians.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No musicians found matching the query: " + query);
            } else {
                return ResponseEntity.ok(musicians);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error searching musicians: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMusicianById(@PathVariable Integer id) {
        try {
            Optional<Musician> musician = Optional.ofNullable(service.getMusicianById(id));
            if (musician.isPresent()) {
                return ResponseEntity.ok(musician.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Musician not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving musician: " + e.getMessage());
        }
    }
}