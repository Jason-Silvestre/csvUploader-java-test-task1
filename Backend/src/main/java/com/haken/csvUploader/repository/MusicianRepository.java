package com.haken.csvUploader.repository;

import com.haken.csvUploader.entities.Musician;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface MusicianRepository extends JpaRepository<Musician, Integer> {
    Integer uploadMusicians(MultipartFile file);
}
