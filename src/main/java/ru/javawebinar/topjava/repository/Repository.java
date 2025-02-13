package ru.javawebinar.topjava.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<Entity, Id> {
    List<Entity> getAll();

    Optional<Entity> get(Id id);

    void create(Entity entity);

    void update(Entity entity);

    void delete(Id id);
}
