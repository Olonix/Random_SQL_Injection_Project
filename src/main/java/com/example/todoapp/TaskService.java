package com.example.todoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskDao taskDao;

    public List<Task> getAllTasks() {
        return taskDao.findAllTasks();
    }

    public List<Task> findTasksByTitle(String title) {
        return taskDao.findTasksByTitle(title);
    }

    public void addTask(Task task) {
        taskDao.saveTask(task);
    }

    public void updateTask(Task task) {
        taskDao.updateTask(task);
    }

    public void deleteTask(Long id) {
        taskDao.deleteTask(id);
    }
}
