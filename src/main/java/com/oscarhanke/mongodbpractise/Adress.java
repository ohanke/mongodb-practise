package com.oscarhanke.mongodbpractise;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Adress {
    private String country;
    private String city;
    private String postCode;
}
