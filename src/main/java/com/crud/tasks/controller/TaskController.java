package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/task")
public class TaskController {

    @RequestMapping(method = RequestMethod.GET, value = "getTasks")
    public List<TaskDto> getTasks() {
        return new ArrayList<>();
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTask/{id}")
    public TaskDto getTask (@PathVariable ("id") Long taskId) {
        return new TaskDto((long) 1, "task title", "task content");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteTask/{id}")
    public void deleteTask (@PathVariable("id") Long taskId) {
    }

    @PutMapping("updateTask/{id}")
    public TaskDto updateTask (@PathVariable Long id, @RequestParam TaskDto taskDto) {
        return new TaskDto((long)1, "updated task title", "task content");
    }

    @PostMapping("createTask/{id}")
    public void createTask (@PathVariable Long id, @RequestParam TaskDto taskDto) {
    }
}
