package com.onrender.todolistjeed.todolistapi.Repositories;

import com.onrender.todolistjeed.todolistapi.Models.TaskModel;
import com.onrender.todolistjeed.todolistapi.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface iTaskRepository extends JpaRepository<TaskModel, Long> {
    Set<TaskModel> findByUser(UserModel user);
    TaskModel findByTaskId(Long taskId);
    Set<TaskModel> findByUserAndCompleted(UserModel user, boolean completed);
}
