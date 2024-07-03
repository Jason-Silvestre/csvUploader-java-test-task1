package com.haken.csvUploader.service;

import com.haken.csvUploader.entities.Musician;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MusicianService {

    private final MusicianRepository repository;

    public Integer uploadMusicians(MultipartFile file) throws IOException {
        Set<Musician> musicians = parseCsv(file);
        repository.saveAll(musicians);
        return musicians.size();
    }

    private Set<Musician> parseCsv(MultipartFile file) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<MusicianRepresentation> strategy =
                    new HeaderColumnNameMappingStrategy<>();
            strategy.setType(MusicianRepresentation.class);
            CsvToBean<MusicianRepresentation> csvToBean =
                    new CsvToBeanBuilder<MusicianRepresentation>(reader)
                            .withMappingStrategy(strategy)
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
}
