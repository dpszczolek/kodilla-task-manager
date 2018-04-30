package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
public class TaskDto {
    private long id;
    private String title;
    private String content;

    public long getId() {
        return id;
    }

    public TaskDto(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

}
