package com.spring.blog.dto;

import lombok.*;

@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @Builder @ToString
public class BmiDTO {
    private double height;
    private double weight;
}
