package com.jonnyle.checklist;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;

import lombok.Data;

@Entity
@Data
public class CheckIn {
    @Id
    private Integer tea;

    @Min(value = 0, message = "Cannot have negative number")
    private Integer box;

    @Min(value = 0, message = "Cannot have negative nubmer")
    private Integer envelope;

    private String comment;

    @MapsId
    @OneToOne
    private School school;

    CheckIn(){
    }

    CheckIn(Integer box, Integer envelope, String comment){
        this.box = box;
        this.envelope = envelope;
        this.comment = comment;
    }
}