package com.jonnyle.checklist;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class School {
    @Id
    @NotNull(message = "Please enter a tea.")
    private Integer tea;

    @Size(min = 3, message = "Name must have minimum of 2 characters.")
    private String name;

    @Size(min = 3, max = 3, message = "Please enter correct route")
    private String route;

    School(){
    }

    School(Integer tea, String name, String route){
        this.tea = tea;
        this.name = name;
        this.route = route;
    }
}