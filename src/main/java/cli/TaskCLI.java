package cli;

import manager.TaskManager;
import manager.TaskManagerImp;
import core.StatusEnum;

public class TaskCLI {

    private final TaskManager taskManager;

    public TaskCLI(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void run(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide a command.");
            return;
        }

        String command = args[0];

        switch (command) {
            case "add":
                addTask(args);
                break;
            case "update":
                updateTask(args);
                break;
            case "delete":
                deleteTask(args);
                break;
            case "mark-in-progress":
                markInProgress(args);
                break;
            case "mark-done":
                markAsDone(args);
                break;
            case "list":
                listTasks(args);
                break;
            default:
                System.out.println("Unknown command: " + command);
        }
    }

    private void addTask(String[] args) {
        if (args.length < 2) {
            System.out.println("Please provide a task description.");
            return;
        }
        String description = args[1];
        taskManager.add(description);
    }

    private void updateTask(String[] args) {
        if (args.length < 3) {
            System.out.println("Please provide a task ID and a new description.");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid task ID: " + args[1]);
            return;
        }
        String description = args[2];
        taskManager.update(id, description);
    }

    private void deleteTask(String[] args) {
        if (args.length < 2) {
            System.out.println("Please provide a task ID.");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid task ID: " + args[1]);
            return;
        }
        taskManager.delete(id);
    }

    private void markInProgress(String[] args) {
        if (args.length < 2) {
            System.out.println("Please provide a task ID.");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid task ID: " + args[1]);
            return;
        }
        taskManager.updateStatus(id, StatusEnum.PROGRESS);
    }

    private void markAsDone(String[] args) {
        if (args.length < 2) {
            System.out.println("Please provide a task ID.");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid task ID: " + args[1]);
            return;
        }
        taskManager.updateStatus(id, StatusEnum.DONE);
    }

    private void listTasks(String[] args) {
        if (args.length == 1) {
            taskManager.listAll();
        } else if (args.length == 2) {
            String status = args[1];
            StatusEnum statusEnum = StatusEnum.fromValue(status);
            taskManager.listByStatus(statusEnum);
        } else {
            System.out.println("Usage: list [status]");
        }
    }

    public static void main(String[] args) {
        TaskManager manager = new TaskManagerImp();
        TaskCLI cli = new TaskCLI(manager);
        cli.run(args);
    }

}
