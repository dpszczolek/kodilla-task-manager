package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/task")
public class TaskController {

    @Autowired
    private DbService service;

    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getTasks")
    public List<TaskDto> getTasks() {
        return taskMapper.mapToTaskDtoList(service.getAllTasks());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTask/{id}")
    public TaskDto getTask (@PathVariable ("id") Long taskId) {
          return taskMapper.mapToTaskDto(service.getOneTask(taskId).get());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteTask/{id}")
    public void deleteTask (@PathVariable("id") Long taskId) {
    }

    @PutMapping("updateTask/{id}")
    public TaskDto updateTask (@PathVariable ("id") Long taskId, TaskDto taskDto) {
        return new TaskDto((long)1, "updated task title", "task content");
    }

    @PostMapping("createTask/{id}")
    public void createTask (@PathVariable ("id") Long taskId, TaskDto taskDto) {
    }
}
