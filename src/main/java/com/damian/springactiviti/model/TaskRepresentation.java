package com.damian.springactiviti.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TaskRepresentation {
    private String id;
    private String name;
    private String processInstanceId;
    private String taskStatus;
}