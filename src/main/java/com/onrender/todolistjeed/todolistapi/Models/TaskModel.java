package com.onrender.todolistjeed.todolistapi.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

@Data
@Entity(name = "tasks")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "key_tasks_users"))
    private UserModel user;

    @Column(nullable = false, length = 300)
    private String taskContent;

    private boolean completed = false;
}
