package com.onrender.todolistjeed.todolistapi.Controllers;

import com.onrender.todolistjeed.todolistapi.Models.StatusTaskModel;
import com.onrender.todolistjeed.todolistapi.Models.TaskInfoModel;
import com.onrender.todolistjeed.todolistapi.Models.TaskModel;
import com.onrender.todolistjeed.todolistapi.Models.UserModel;
import com.onrender.todolistjeed.todolistapi.Repositories.iTaskRepository;
import com.onrender.todolistjeed.todolistapi.Repositories.iUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/user")
public class TaskController {

    @Autowired
    private iUserRepository userRepository;

    @Autowired
    private iTaskRepository taskRepository;

    @PostMapping("/task")
    public ResponseEntity<Void> createTask(@RequestBody TaskInfoModel taskInfoModel) {
        UserModel user = this.userRepository.findByUsername(taskInfoModel.getUsername());

        try {
            if (user != null) {
                TaskModel taskModel = new TaskModel();
                taskModel.setUser(user);
                taskModel.setTaskContent(taskInfoModel.getTaskUser());

                this.taskRepository.save(taskModel);

                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/task/read-user-tasks")
    @Transactional
    public ResponseEntity<Set<TaskModel>> readTasks(@RequestParam String username) {
        try {
            UserModel user = this.userRepository.findByUsername(username);
            if (user != null) {
                Set<TaskModel> tasks = this.taskRepository.findByUser(user);

                if (tasks != null && !tasks.isEmpty()) {
                    System.out.println(tasks);
                    return ResponseEntity.status(HttpStatus.OK).body(tasks);
                } else {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/task/delete-user-task/{task_id}")
    public void deleteTask(@PathVariable Long task_id) {
        try {
            TaskModel task = this.taskRepository.findByTaskId(task_id);

            this.taskRepository.delete(task);
        } catch (Exception error) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/task/read-user-tasks/status")
    public ResponseEntity<Integer> modifyStatus(@RequestBody StatusTaskModel statusTaskModel) {
        try {
            TaskModel task = this.taskRepository.findByTaskId(statusTaskModel.getTaskID());

            task.setCompleted(statusTaskModel.getStatus());

            this.taskRepository.save(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(201);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/task/read-user-tasks/active")
    public ResponseEntity<Set<TaskModel>> readActiveTasks(@RequestParam String username) {
        try {
            UserModel user = this.userRepository.findByUsername(username);

            if (user != null) {
                Set<TaskModel> tasks = this.taskRepository.findByUserAndCompleted(user, false);

                return ResponseEntity.status(HttpStatus.OK).body(tasks);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/task/read-user-tasks/complete")
    public ResponseEntity<Set<TaskModel>> readCompleteTasks(@RequestParam String username) {
        try {
            UserModel user = this.userRepository.findByUsername(username);

            if (user != null) {
                Set<TaskModel> tasks = this.taskRepository.findByUserAndCompleted(user, true);

                return ResponseEntity.status(HttpStatus.OK).body(tasks);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
