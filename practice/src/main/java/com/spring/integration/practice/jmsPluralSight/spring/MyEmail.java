package com.spring.integration.practice.jmsPluralSight.spring;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter
public class MyEmail {
    private String emailAddress;
    private String name;
    private String emailBody;
}
