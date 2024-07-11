package com.haken.csvUploader.service;

import com.haken.csvUploader.entities.Musician;
import com.haken.csvUploader.exceptions.CsvProcessingException;
import com.haken.csvUploader.exceptions.ResourceNotFoundException;
import com.haken.csvUploader.repositories.MusicianRepository;
import com.haken.csvUploader.representation.MusicianRepresentation;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MusicianService {

    @Autowired
    private final MusicianRepository repository;

    @Transactional
    public Integer uploadMusicians(MultipartFile file, String delimiter) throws IOException {
        try {
            Set<Musician> musicians = parseCsv(file, delimiter);
            repository.saveAll(musicians);
            return musicians.size();
        } catch (IOException e) {
            throw new CsvProcessingException("Error processing CSV file", e);
        }
    }

    private Set<Musician> parseCsv(MultipartFile file, String delimiter) throws IOException {

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<MusicianRepresentation> strategy =
                    new HeaderColumnNameMappingStrategy<>();
            strategy.setType(MusicianRepresentation.class);

            CsvToBean<MusicianRepresentation> csvToBean =
                    new CsvToBeanBuilder<MusicianRepresentation>(reader)
                            .withMappingStrategy(strategy)
                            .withSeparator(delimiter.charAt(0))
                            .withIgnoreEmptyLine(true)
                            .withIgnoreLeadingWhiteSpace(true)
                            .build();

            return csvToBean.parse()
                    .stream()
                    .map(csvLine -> Musician.builder()
                            .firstname(csvLine.getFirst_name())
                            .lastname(csvLine.getLast_name())
                            .age(csvLine.getAge())
                            .build()
                    )
                    .collect(Collectors.toSet());
        } catch (RuntimeException e) {
            throw new CsvProcessingException("Error processing CSV file", e);
        }
    }

    @Transactional
    public List<Musician> searchByNameOrAge(String query) {
        try {
            return repository.findAll().stream()
                    .filter(musician -> musician.getFirstname().contains(query) ||
                            musician.getLastname().contains(query) ||
                            String.valueOf(musician.getAge()).contains(query))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CsvProcessingException("Error searching musician by name or age", e);
        }
    }

    @Transactional(readOnly = true)
    public List<Musician> findAllMusicians() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new CsvProcessingException("Error finding all musicians", e);
        }
    }

    @Transactional(readOnly = true)
    public Musician getMusicianById(Integer id) {
        try {
            return repository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Musician not found"));
        } catch (Exception e) {
            throw new CsvProcessingException("Error finding musician by ID", e);
        }
    }
}

