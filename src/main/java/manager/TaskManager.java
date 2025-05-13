package manager;

import core.StatusEnum;

public interface TaskManager {
    void add(String description);

    void update(int id, String description);

    void delete(int id);

    void updateStatus(int id, StatusEnum status);

    void listAll();

    void listByStatus(StatusEnum status);
}
