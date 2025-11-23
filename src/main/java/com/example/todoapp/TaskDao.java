package com.example.todoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TaskDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tasks (id BIGINT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255), description TEXT, completed BOOLEAN)";
        jdbcTemplate.execute(sql);
    }

    // Уязвимый метод для поиска задач - SQL инъекция
    public List<Task> findTasksByTitle(String title) {
        String sql = "SELECT id, title, description, completed FROM tasks WHERE title LIKE '" + title + "%'";
        RowMapper<Task> rowMapper = new TaskRowMapper();
        return jdbcTemplate.query(sql, rowMapper);
    }

    // Безопасный метод для получения всех задач
    public List<Task> findAllTasks() {
        String sql = "SELECT id, title, description, completed FROM tasks";
        RowMapper<Task> rowMapper = new TaskRowMapper();
        return jdbcTemplate.query(sql, rowMapper);
    }

    // Безопасный метод для добавления задачи
    public void saveTask(Task task) {
        String sql = "INSERT INTO tasks (title, description, completed) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, task.getTitle(), task.getDescription(), task.isCompleted());
    }

    // Безопасный метод для обновления задачи
    public void updateTask(Task task) {
        String sql = "UPDATE tasks SET title = ?, description = ?, completed = ? WHERE id = ?";
        jdbcTemplate.update(sql, task.getTitle(), task.getDescription(), task.isCompleted(), task.getId());
    }

    // Безопасный метод для удаления задачи
    public void deleteTask(Long id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class TaskRowMapper implements RowMapper<Task> {
        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            Task task = new Task();
            task.setId(rs.getLong("id"));
            task.setTitle(rs.getString("title"));
            task.setDescription(rs.getString("description"));
            task.setCompleted(rs.getBoolean("completed"));
            return task;
        }
    }
}
