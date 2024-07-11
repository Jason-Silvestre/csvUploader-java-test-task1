package com.haken.csvUploader.repositories;

import com.haken.csvUploader.entities.Musician;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface MusicianRepository extends JpaRepository<Musician, Integer> {
}
