package com.example.TaskUserService.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "task")
public class Task {


    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable =false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable =false)
    private TaskStatus taskStatus;

    @Column(nullable =false)
    private TaskPriority taskPriority;

    @Column(nullable =false)
    private String author;

    @Column(nullable =false)
    private String performer;

    @Column(nullable =false)
    private String comment;

    private Long assignedUserId;



}
