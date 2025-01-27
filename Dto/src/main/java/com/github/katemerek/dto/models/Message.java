package com.github.katemerek.dto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String name;
    private String text;
}
