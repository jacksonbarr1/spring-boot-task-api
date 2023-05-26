package com.example.taskmaster.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private TaskmasterUser user;

    private String projectName;

    private String description;

    private LocalDate createdDate;

    private LocalDate lastUpdatedDate;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Task> tasks;


}
