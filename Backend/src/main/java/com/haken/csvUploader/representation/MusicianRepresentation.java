package com.haken.csvUploader.representation;

import com.opencsv.bean.CsvBindByName;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusicianRepresentation implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    @CsvBindByName(column = "firstname")
    private String first_name;
    @CsvBindByName(column = "lastname")
    private String last_name;
    @CsvBindByName(column = "age")
    private int age;


}
