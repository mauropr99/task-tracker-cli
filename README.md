# Task Tracker CLI

A simple CLI application to manage personal tasks using a JSON file as local storage.

## Features

-   Add, update, and delete tasks
-   Mark tasks as `todo`, `in-progress`, or `done`
-   List all tasks or filter by status
-   Data stored locally in `tasks.json`
-   Built with pure Java (no external libraries)
-   Packaged and run via Maven

## Project Structure

-   `cli/` → Command-line interface logic
-   `core/` → Task entity and status enum
-   `manager/` → Task management logic and storage

Also includes:

-   `task-cli.bat` → launcher for Windows
-   `pom.xml` → Maven build file
-   `tasks.json` → task data (auto-created)

## Requirements

-   Java 17+
-   Maven installed

## Build and run the project

```bash
mvn clean package
```

```bash
task-cli list
```

## Supported Commands

| Command                         | Description                        |
| ------------------------------- | ---------------------------------- |
| `add "description"`             | Add a new task                     |
| `update <id> "new description"` | Update task description            |
| `delete <id>`                   | Delete a task                      |
| `list`                          | List all tasks                     |
| `list todo`                     | List only tasks with status `todo` |
| `list in-progress`              | List tasks in progress             |
| `list done`                     | List completed tasks               |
| `mark-in-progress <id>`         | Mark a task as in progress         |
| `mark-done <id>`                | Mark a task as done                |
