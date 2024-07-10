package com.haken.csvUploader.controller;

import com.haken.csvUploader.entities.Musician;
import com.haken.csvUploader.service.MusicianService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/musicians")
@RequiredArgsConstructor
public class MusicianController {

    private final MusicianService service;

    @PostMapping(value = "/upload", consumes ={"multipart/form-data"})
    public ResponseEntity<Integer> uploadMusicians (
            @RequestPart("file")MultipartFile file,
            @RequestParam(value = "delimiter", defaultValue = ",") String delimiter
            )  throws IOException {
        return ResponseEntity.ok(service.uploadMusicians(file, delimiter));
    }

    @GetMapping("/findAllMusicians")
    public List<Musician> findAllMusicians() {
        return service.findAllMusicians();
    }

    @GetMapping("/searchByNameOrAge")
    public ResponseEntity<List<Musician>> searchByNameOrAge(@RequestParam("query") String query) {
        return ResponseEntity.ok(service.searchByNameOrAge(query));
    }

    @GetMapping("/{id}")
    public Musician getMusicianById(@PathVariable Integer id) {
        return service.getMusicianById(id);
    }
}

