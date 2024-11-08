package com.example.todo_posgersql;

import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class TaskController {

    private TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

    }

    @GetMapping("/tasks")
    public List<Task> index() {
        return taskRepository.findAll();
    }

    @GetMapping("/tasks/search/{keyword}")
    public List<Task> search(@PathVariable("keyword") String keyword) {
        return taskRepository.search(keyword.toLowerCase());
    }



    @PostMapping("/tasks")
    public Task create(@RequestBody Task task) {
        return taskRepository.save(task);
    }


    @PutMapping("/tasks/{id}/complete")
    public Task comlete(@PathVariable("id") Long id) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        existingTask.setCompleted(true);
        taskRepository.save(existingTask);
        return existingTask;
    }

    @GetMapping("/tasks/{id}")
    public Task show(@PathVariable("id") Long id) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return existingTask;
    }

    @PutMapping("/tasks/{id}")
    public Task update(@PathVariable("id") Long id, @RequestBody Task task) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        return taskRepository.save(existingTask);
    }


    @DeleteMapping("/tasks/{id}")
    public void destroy(@PathVariable("id") Long id) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        taskRepository.delete(existingTask);
    }

}
