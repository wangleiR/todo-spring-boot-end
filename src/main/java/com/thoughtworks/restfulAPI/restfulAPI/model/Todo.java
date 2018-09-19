package com.thoughtworks.restfulAPI.restfulAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table(name = "uu_table")
public class Todo {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String status;
    private Date dueDate;
    private String tags;

}
