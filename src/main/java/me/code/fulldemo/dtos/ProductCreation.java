package me.code.fulldemo.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreation {

    private String name, description;
    private double price;
}
