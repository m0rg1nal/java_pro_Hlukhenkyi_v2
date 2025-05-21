package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class Account implements Serializable {
    private int id;
    private String name;

    public Account(String name) {
        this.name = name;
    }
}
