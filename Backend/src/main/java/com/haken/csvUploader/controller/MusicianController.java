package com.haken.csvUploader.controller;

import com.haken.csvUploader.service.MusicianService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/musicians")
@RequiredArgsConstructor
public class MusicianController {

    private final MusicianService service;

    @PostMapping(value = "/upload", consumes ={"multipart/form-data"})
    public ResponseEntity<Integer> uploadMusicians (
            @RequestPart("file")MultipartFile file
            )  throws IOException {
        return ResponseEntity.ok(service.uploadMusicians(file));
    }
}

