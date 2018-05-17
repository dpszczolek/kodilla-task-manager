package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.internal.matchers.Null;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DbService dbService;
    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldFetchEmptyTaskList() throws Exception {
        List<Task> getTasks = new ArrayList<>();
        when(dbService.getAllTasks()).thenReturn(getTasks);

        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchTaskList() throws Exception {
        List<TaskDto> getTasks = new ArrayList<>();
        getTasks.add(new TaskDto(10L, "task title test", "task content test"));

        when(taskMapper.mapToTaskDtoList(dbService.getAllTasks())).thenReturn(getTasks);

        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(10)))
                .andExpect(jsonPath("$[0].title", is("task title test")))
                .andExpect(jsonPath("$[0].content", is("task content test")));
    }

    @Test (expected = Exception.class)
    public void shouldDeleteTask() throws Exception {

        doThrow(new Exception()).when(dbService).delete(1L);

        mockMvc.perform(delete("v1/task/deleteTask/1L").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
}

    @Test
    public void shouldUpdateTask() throws Exception{

        TaskDto taskDto = new TaskDto(1L, "task title updated", "task content updated");


        when(taskMapper.mapToTaskDto(dbService.saveTask(taskMapper.mapToTask(taskDto)))).thenReturn(taskDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        mockMvc.perform(put("/v1/task/updateTask/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.title", is("task title updated")))
                .andExpect(jsonPath("$.content", is("task content updated")));
    }

    @Test
    public void shouldCreateTask() throws Exception{

        TaskDto taskDto = new TaskDto(10L, "task title", "task content");
        Task task = new Task(10L, "task title", "task content");

        when(dbService.saveTask(taskMapper.mapToTask(taskDto))).thenReturn(task);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);

        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200));
        verify(dbService, times(1)).saveTask(taskMapper.mapToTask(taskDto));
        verifyNoMoreInteractions(dbService);
    }
}
