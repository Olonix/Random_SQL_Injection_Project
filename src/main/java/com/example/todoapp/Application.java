package com.example.todoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private TaskDao taskDao;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public void run(String... args) throws Exception {
        taskDao.createTable();

        // Добавление примерных данных
        taskDao.saveTask(new Task(null, "Sample Task 1", "Description 1", false));
        taskDao.saveTask(new Task(null, "Another Task", "Description 2", true));
        taskDao.saveTask(new Task(null, "Test Task", "Description 3", false));
    }
}
