package org.aston.task.repository;

import org.aston.task.model.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TagEntityRepository extends JpaRepository<TagEntity, Integer> {

    @Query("SELECT t FROM TagEntity AS t " +
            "JOIN t.records AS r " +
            "WHERE r.id = ?1")
    List<TagEntity> findByRecordId(UUID id);
}
