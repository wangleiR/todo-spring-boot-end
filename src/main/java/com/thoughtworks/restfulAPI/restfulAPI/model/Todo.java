package com.thoughtworks.restfulAPI.restfulAPI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Todo {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String status;
    private Date dueDate;
    private String tags;
    private Long userId;

    @ManyToMany
    @JoinTable(name = "todo_tag",
        joinColumns = @JoinColumn(name = "todo_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> useTags = new HashSet<>();


}
