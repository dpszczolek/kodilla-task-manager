package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskController taskController;

    @Test
    public void shouldFetchEmptyTaskList() throws Exception {
        List<TaskDto> getTasks = new ArrayList<>();
        when(taskController.getTasks()).thenReturn(getTasks);

        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchTaskList() throws Exception {
        List<TaskDto> getTasks = new ArrayList<>();
        getTasks.add(new TaskDto(10L, "task title test", "task content test"));

        when(taskController.getTasks()).thenReturn(getTasks);

        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(10)))
                .andExpect(jsonPath("$[0].title", is("task title test")))
                .andExpect(jsonPath("$[0].content", is("task content test")));
    }

    @Test
    public void shouldDeleteTask() throws Exception {

        TaskController taskControllerMock = mock(TaskController.class);
        TaskDto taskDto = new TaskDto(10L, "task title", "task content");
        taskControllerMock.createTask(taskDto);
        taskControllerMock.deleteTask(10);

        verify(taskControllerMock, times(1)).deleteTask(10);
}

    @Test
    public void shouldUpdateTask() throws Exception{

        TaskDto taskDto = new TaskDto(1L, "task title test", "task content test");

        taskController.updateTask(1, new TaskDto(1L, "task title updated", "task content updated"));

        when(taskController.getTask(1)).thenReturn(taskDto);

        mockMvc.perform(put("/v1/task/updateTask/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    /*    mockMvc.perform(get("v1/task/getTask/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("task title updated")))
                .andExpect(jsonPath("$.content", is("task content updated"))); */
    }

    @Test
    public void shouldCreateTask() throws Exception{

        TaskController taskControllerMock = mock(TaskController.class);
        TaskDto taskDto = new TaskDto(10L, "task title", "task content");
        taskControllerMock.createTask(taskDto);

        verify(taskControllerMock, times(1)).createTask(taskDto);
    }
}
