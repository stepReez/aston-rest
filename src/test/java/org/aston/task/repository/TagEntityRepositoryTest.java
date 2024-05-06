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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfigTest.class)
@Testcontainers
@Transactional
class TagEntityRepositoryTest {

    @Autowired
    private TagEntityRepository tagEntityRepository;

    @Autowired
    private RecordEntityRepository recordEntityRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    private TagEntity tag;

    private String tagName;

    @BeforeEach
    void beforeEach() {
        tag = new TagEntity();
        tagName = "name";
        tag.setName(tagName);
    }

    @Test
    void createTagTest() {
        TagEntity savedTag = tagEntityRepository.save(tag);

        Assertions.assertEquals(tagName, savedTag.getName());
    }

    @Test
    void getTagTest() {
        int tagId = tagEntityRepository.save(tag).getId();
        TagEntity savedTag = tagEntityRepository.findById(tagId).get();

        Assertions.assertEquals(tagName, savedTag.getName());
    }

    @Test
    void deleteTagTest() {
        int tagId = tagEntityRepository.save(tag).getId();
        tagEntityRepository.deleteById(tagId);

        Assertions.assertFalse(tagEntityRepository.findById(tagId).isPresent());
    }

    @Test
    void findByRecordIdTest() {
        TagEntity savedTag = tagEntityRepository.save(tag);

        UserEntity userEntity = new UserEntity();
        userEntity.setName("name");

        UserEntity savedUser = userEntityRepository.save(userEntity);

        RecordEntity recordEntity = new RecordEntity();
        recordEntity.setTitle("title");
        recordEntity.setText("text");
        recordEntity.setAuthor(savedUser);
        recordEntity.setTag(List.of(savedTag));

        RecordEntity savedRecord = recordEntityRepository.save(recordEntity);

        TagEntity tagByRecord = tagEntityRepository.findByRecordId(savedRecord.getId()).get(0);

        Assertions.assertEquals(tagName, tagByRecord.getName());
    }
}
