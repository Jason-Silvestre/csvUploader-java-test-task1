package com.haken.csvUploader.representation;

import com.opencsv.bean.CsvBindByName;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusicianRepresentation {

    @CsvBindByName(column = "firstname")
    private String first_name;
    @CsvBindByName(column = "lastname")
    private String last_name;
    @CsvBindByName(column = "age")
    private int age;


}
