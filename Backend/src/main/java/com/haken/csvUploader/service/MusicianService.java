package com.haken.csvUploader.service;

import com.haken.csvUploader.entities.Musician;
import com.haken.csvUploader.exceptions.ResourceNotFoundException;
import com.haken.csvUploader.repository.MusicianRepository;
import com.haken.csvUploader.representation.MusicianRepresentation;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

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

    private final MusicianRepository repository;

    public Integer uploadMusicians(MultipartFile file, String delimiter) throws IOException {
        Set<Musician> musicians = parseCsv(file, delimiter);
        repository.saveAll(musicians);
        return musicians.size();
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
        }
    }

    public List<Musician> searchByNameOrAge(String query) {
        return repository.findAll().stream()
                .filter(musician -> musician.getFirstname().contains(query) ||
                        musician.getLastname().contains(query) ||
                        String.valueOf(musician.getAge()).contains(query))
                .collect(Collectors.toList());
    }

    public List<Musician> findAllMusicians() {
        return repository.findAll();
    }

    public Musician getMusicianById(Integer id) {
        return  repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Musician not found"));
    }
}

