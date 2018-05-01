package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private long id;
    private String title;
    private String content;


}
