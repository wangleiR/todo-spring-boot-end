package com.thoughtworks.restfulAPI.restfulAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.restfulAPI.restfulAPI.model.Todo;
import com.thoughtworks.restfulAPI.restfulAPI.repository.TodoRepository;
import com.thoughtworks.restfulAPI.restfulAPI.services.TodoService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest
@RunWith(SpringRunner.class)
public class TodoControllerTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void shouldFindTodoByName() {
        todoRepository.save(Todo.builder()
        .name("meet L").status("to do").build());

        Pageable pageable = null;
        List<Todo> todoList = todoRepository.findByNameContains("meet",pageable);
        assertThat(todoList.size(), Matchers.is(1));
        assertThat(todoList.get(0).getName(), Matchers.is("meet L"));
    }



    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
