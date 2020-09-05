package ru.itis.taskmanager.security.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class AuthResponse implements Serializable {
    private final String jwt;
}
