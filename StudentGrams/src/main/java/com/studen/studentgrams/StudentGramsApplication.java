package com.studen.studentgrams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.studen.studentgrams")
public class StudentGramsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentGramsApplication.class, args);
    }
}
