package com.codingShuttle.sushil.MVC.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    @NotBlank(message = "The employee name should not be blank")
    private String name;
    @Email(message = "Email should be valid email address")
    private String email;
    @Min(value = 18, message = "The age of employee should be greater than 18 years")
    @Max(value = 60, message = "The age of employee should be less than 60 years")
    private int age;
    @FutureOrPresent(message = "The joining date should be of today's or future date")
    private LocalDate dateOfJoining;
    @JsonProperty("isActive")
    @NotNull(message = "The status of employee should not be null")
    @AssertTrue(message = "The status of employee should not be false")
    private boolean isActive;
}
