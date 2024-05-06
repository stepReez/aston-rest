package org.aston.task.repository;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.TagEntity;
import org.aston.task.model.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfigTest.class)
@Testcontainers
@Transactional
class RecordEntityRepositoryTest {

    @Autowired
    private RecordEntityRepository recordEntityRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private TagEntityRepository tagEntityRepository;

    private RecordEntity recordEntity;

    private String title;

    private String text;

    private UUID authorId;

    private UserEntity user;

    private String name;

    private TagEntity tag;

    private int tagId;

    private String tagName;

    @BeforeEach
    void beforeEach() {
        user = new UserEntity();
        name = "name";
        user.setName(name);

        user = userEntityRepository.save(user);
        authorId = user.getId();

        tag = new TagEntity();
        tagName = "tag";
        tag.setName(tagName);

        tagId = tagEntityRepository.save(tag).getId();

        recordEntity = new RecordEntity();
        title = "title";
        text = "text";

        recordEntity.setTitle(title);
        recordEntity.setText(text);
        recordEntity.setAuthor(user);
        recordEntity.setTag(List.of(tag));
    }

    @Test
    void createRecordTest() {
        RecordEntity savedRecord = recordEntityRepository.save(recordEntity);

        Assertions.assertEquals(title, savedRecord.getTitle());
        Assertions.assertEquals(text, savedRecord.getText());
        Assertions.assertEquals(authorId, savedRecord.getAuthor().getId());
        Assertions.assertEquals(tagId, savedRecord.getTag().get(0).getId());
    }

    @Test
    void getRecordTest() {
        UUID savedUUID = recordEntityRepository.save(recordEntity).getId();

        RecordEntity savedRecord = recordEntityRepository.findById(savedUUID).get();

        Assertions.assertEquals(title, savedRecord.getTitle());
        Assertions.assertEquals(text, savedRecord.getText());
        Assertions.assertEquals(authorId, savedRecord.getAuthor().getId());
        Assertions.assertEquals(tagId, savedRecord.getTag().get(0).getId());
    }

    @Test
    void deleteTest() {
        UUID savedUUID = recordEntityRepository.save(recordEntity).getId();
        recordEntityRepository.deleteById(savedUUID);

        Assertions.assertFalse(recordEntityRepository.findById(savedUUID).isPresent());
    }

    @Test
    void findByAuthorId() {
        recordEntityRepository.save(recordEntity);

        List<RecordEntity> recordEntities = recordEntityRepository.findByAuthorId(authorId);
        RecordEntity savedRecord = recordEntities.get(0);

        Assertions.assertEquals(title, savedRecord.getTitle());
        Assertions.assertEquals(text, savedRecord.getText());
        Assertions.assertEquals(authorId, savedRecord.getAuthor().getId());
        Assertions.assertEquals(tagId, savedRecord.getTag().get(0).getId());
    }

    @Test
    void findByTagId() {
        recordEntityRepository.save(recordEntity);

        List<RecordEntity> recordEntities = recordEntityRepository.findByTagId(tagId);
        RecordEntity savedRecord = recordEntities.get(0);

        Assertions.assertEquals(title, savedRecord.getTitle());
        Assertions.assertEquals(text, savedRecord.getText());
        Assertions.assertEquals(authorId, savedRecord.getAuthor().getId());
        Assertions.assertEquals(tagId, savedRecord.getTag().get(0).getId());
    }
}
