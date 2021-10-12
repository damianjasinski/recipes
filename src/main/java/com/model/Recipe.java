package com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@RestController
@Entity
public class Recipe {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @JsonIgnore
    int id;


    private LocalDateTime date;

    @NotBlank
    @NotEmpty
    private String category;


    @NotBlank
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotBlank
    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @NotEmpty(message = "Ingredients list cannot be empty")
    private String[] ingredients;


    @NotEmpty(message = "Directions list cannot be empty")
    private String[] directions;

    @JsonIgnore
    private String owner;
}