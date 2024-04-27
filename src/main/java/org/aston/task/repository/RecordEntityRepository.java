package org.aston.task.repository;

import org.aston.task.model.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RecordEntityRepository extends JpaRepository<RecordEntity, UUID> {

    @Query("SELECT r FROM RecordEntity AS r " +
            "JOIN r.author AS u " +
            "WHERE u.id = ?1")
    List<RecordEntity> findByAuthorId(UUID authorId);

    @Query("SELECT r FROM RecordEntity AS r " +
            "JOIN r.tag AS t " +
            "WHERE t.id = ?1")
    List<RecordEntity> findByTagId(Integer tagId);
}
