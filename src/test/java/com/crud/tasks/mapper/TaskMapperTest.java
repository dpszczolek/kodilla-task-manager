package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTest {

    @InjectMocks
    private TaskMapper taskMapper;

    @Test
    public void testMapToTaskDto() {
        Task task = new Task(
                1L,
                "task title",
                "task content"
        );

        taskMapper.mapToTaskDto(task);

        Assert.assertEquals("task title", taskMapper.mapToTaskDto(task).getTitle());
        Assert.assertEquals("task content", taskMapper.mapToTaskDto(task).getContent());
    }

    @Test
    public void testMapToTask() {
        TaskDto taskDto = new TaskDto(
                2L,
                "taskDto title",
                "taskDto content"
        );

        taskMapper.mapToTask(taskDto);

        Assert.assertEquals("taskDto title", taskMapper.mapToTask(taskDto).getTitle());
        Assert.assertEquals("taskDto content", taskMapper.mapToTask(taskDto).getContent());
    }

    @Test
    public void testMapToTaskDtoList() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "task title", "content"));
        taskList.add(new Task(2L, "task2 title", "second content"));

        taskMapper.mapToTaskDtoList(taskList);

        Assert.assertEquals(2, taskMapper.mapToTaskDtoList(taskList).size());
        Assert.assertEquals("task title", taskMapper.mapToTaskDtoList(taskList).get(0).getTitle());
        Assert.assertEquals("second content", taskMapper.mapToTaskDtoList(taskList).get(1).getContent());
    }
}
