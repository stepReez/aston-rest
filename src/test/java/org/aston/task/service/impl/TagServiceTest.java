package org.aston.task.service.impl;

import org.aston.task.exceptions.NotFoundException;
import org.aston.task.model.TagEntity;
import org.aston.task.repository.TagEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class TagServiceTest {
    TagServiceImpl tagService;

    TagEntityRepository tagRepository;

    @BeforeEach
    void beforeEach() {
        tagRepository = Mockito.mock(TagEntityRepository.class);
        tagService = new TagServiceImpl(tagRepository);
    }

    @Test
    void getAllTagsTest() {
        TagEntity tag = new TagEntity();
        int id = 1;
        String name = "Tag name";
        tag.setId(id);
        tag.setName(name);

        List<TagEntity> tags = new ArrayList<>();
        tags.add(tag);

        Mockito
                .when(tagRepository.findAll())
                .thenReturn(tags);

        List<TagEntity> tagEntities = tagService.getAllTags();

        Assertions.assertEquals(1, tagEntities.size());
        Assertions.assertEquals(tag.getName(), tagEntities.get(0).getName());
        Assertions.assertEquals(tag.getId(), tagEntities.get(0).getId());
    }

    @Test
    void createTagTest() {
        TagEntity tag = new TagEntity();
        int id = 1;
        String name = "Tag name";
        tag.setId(id);
        tag.setName(name);

        Mockito
                .when(tagRepository.save(tag))
                .thenReturn(tag);

        TagEntity tagEntity = tagService.createTag(tag);

        Assertions.assertEquals(name, tagEntity.getName());
        Assertions.assertEquals(id, tagEntity.getId());
    }

    @Test
    void findTagByIdTest() {
        TagEntity tag = new TagEntity();
        int id = 1;
        String name = "Tag name";
        tag.setId(id);
        tag.setName(name);
        tag.setRecords(new ArrayList<>());

        Mockito
                .when(tagRepository.findById(id))
                .thenReturn(Optional.of(tag));

        TagEntity tagEntity = tagService.findTagById(id);

        Assertions.assertEquals(name, tagEntity.getName());
        Assertions.assertEquals(id, tagEntity.getId());
        Assertions.assertNotNull(tagEntity.getRecords());
    }

    @Test
    void findTagByWrongTest() {
        Mockito
                .when(tagRepository.findById(0))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> tagService.findTagById(0));
    }

    @Test
    void deleteTagByIdTest() {
        int id = 1;

        tagService.removeTag(id);

        Mockito
                .verify(tagRepository).deleteById(id);
    }
}
