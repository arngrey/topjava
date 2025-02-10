package ru.javawebinar.topjava.repository;

import java.util.List;

public interface Repository<Entity> {
    List<Entity> getAll();

    void create(Entity entity);

    void update(Entity entity);

    void delete(Entity entity);
}
