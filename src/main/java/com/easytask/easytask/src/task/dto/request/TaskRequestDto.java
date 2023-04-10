package com.easytask.easytask.src.task.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskRequestDto {
    private String taskName;
    private String details;
    private String categoryBig;
    private String categorySmall;
    private Integer numberOfIrumi;
}
