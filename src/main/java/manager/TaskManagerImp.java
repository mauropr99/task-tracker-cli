package manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import core.StatusEnum;
import core.Task;

public class TaskManagerImp implements TaskManager {

    private static final String FILE_NAME = "tasks.json";

    public TaskManagerImp() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("File created: " + FILE_NAME);
                }
            } catch (IOException e) {
                System.err.println("An error occurred while creating the file: " + e.getMessage());
            }
        }
    }

    @Override
    public void add(String description) {
        List<Task> tasks = loadTasks();
        int nextId = tasks.stream().mapToInt(t -> t.getId()).max().orElse(0) + 1;
        Task task = new Task(nextId, description);
        tasks.add(task);
        saveTasks(tasks);
        System.out.println("Task added successfully (ID: " + task.getId() + ")");
    }

    @Override
    public void update(int id, String description) {
        List<Task> tasks = loadTasks();
        Task taskToUpdate = tasks.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
        if (taskToUpdate != null) {
            taskToUpdate.setDescription(description);
            saveTasks(tasks);
            System.out.println("Task updated successfully (ID: " + taskToUpdate.getId() + ")");
        } else {
            System.out.println("Task not found.");
        }
    }

    @Override
    public void updateStatus(int id, StatusEnum status) {
        List<Task> tasks = loadTasks();
        Task taskToUpdate = tasks.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
        if (taskToUpdate != null) {
            taskToUpdate.setStatus(status);
            saveTasks(tasks);
            System.out.println("Task status updated successfully (ID: " + taskToUpdate.getId() + ")");
        } else {
            System.out.println("Task not found.");
        }
    }

    @Override
    public void delete(int id) {
        List<Task> tasks = loadTasks();
        Task taskToDelete = tasks.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
        if (taskToDelete != null) {
            tasks.remove(taskToDelete);
            saveTasks(tasks);
            System.out.println("Task deleted successfully (ID: " + taskToDelete.getId() + ")");
        } else {
            System.out.println("Task not found.");
        }
    }

    @Override
    public void listAll() {
        List<Task> tasks = loadTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (Task t : tasks) {
                System.out.printf("[%d] %s - %s%n", t.getId(), t.getDescription(), t.getStatus().getValue());
            }
        }
    }

    @Override
    public void listByStatus(StatusEnum status) {
        List<Task> tasks = loadTasks();
        List<Task> filteredTasks = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getStatus() == status) {
                filteredTasks.add(t);
            }
        }
        if (filteredTasks.isEmpty()) {
            System.out.println("No tasks found with status: " + status.getValue());
        } else {
            for (Task t : filteredTasks) {
                System.out.printf("[%d] %s - %s%n", t.getId(), t.getDescription(), t.getStatus().getValue());
            }
        }
    }

    // Private methods
    private List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        try {
            String content = Files.readString(Paths.get(FILE_NAME));
            String[] entries = content.replace("[", "").replace("]", "").split("(?<=\\}),\\s*");
            for (String json : entries) {
                if (!json.trim().isEmpty()) {
                    tasks.add(parseTask(json.trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading tasks.");
        }
        return tasks;
    }

    private void saveTasks(List<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write("[\n");
            for (int i = 0; i < tasks.size(); i++) {
                writer.write(tasks.get(i).toJSON());
                if (i < tasks.size() - 1)
                    writer.write(",\n");
            }
            writer.write("\n]");
        } catch (IOException e) {
            System.out.println("Error writing tasks.");
        }
    }

    private Task parseTask(String json) {
        int id = Integer.parseInt(json.replaceAll(".*\"id\":\\s*(\\d+).*", "$1"));
        String description = json.replaceAll(".*\"description\":\\s*\"(.*?)\".*", "$1");
        String status = json.replaceAll(".*\"status\":\\s*\"(.*?)\".*", "$1");
        String createdAt = json.replaceAll(".*\"createdAt\":\\s*\"(.*?)\".*", "$1");
        String updatedAt = json.replaceAll(".*\"updatedAt\":\\s*\"(.*?)\".*", "$1");
        Task task = new Task(id, description);
        task.setStatus(StatusEnum.fromValue(status));
        task.setCreatedAt(createdAt);
        task.setUpdatedAt(updatedAt);
        return task;
    }

}
