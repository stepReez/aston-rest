package org.aston.task.service.impl;

import org.aston.task.model.TagEntity;
import org.aston.task.repository.TagEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

class TagServiceTest {
    TagServiceImpl tagService;

    @BeforeEach
    void beforeEach() {
        tagService = new TagServiceImpl();
    }

    @Test
    void getAllTagsTest() {
        TagEntityRepository tagRepository = Mockito.mock(TagEntityRepository.class);
        tagService.setTagRepository(tagRepository);

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
}
