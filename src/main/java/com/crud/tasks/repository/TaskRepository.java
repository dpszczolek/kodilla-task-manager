package com.crud.tasks.repository;

import com.crud.tasks.domain.Task;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Id;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {
    @Override
    List<Task> findAll();

    @Override
    Task findOne(Long id);

    @Override
    Task save(Task task);

    @Override
    void delete(final Long id);

    @Override
    long count();
}
