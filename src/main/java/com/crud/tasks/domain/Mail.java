package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Getter
@AllArgsConstructor
//@NoArgsConstructor

public class Mail {
    private String mailTo;
    private String subject;
    private String message;
    private String toCc;
}
