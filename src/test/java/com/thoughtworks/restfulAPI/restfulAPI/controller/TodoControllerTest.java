package com.thoughtworks.restfulAPI.restfulAPI.controller;

import com.thoughtworks.restfulAPI.restfulAPI.model.Todo;
import com.thoughtworks.restfulAPI.restfulAPI.repository.TodoRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;


@DataJpaTest
@RunWith(SpringRunner.class)
public class TodoControllerTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void shouldFindTodoByName() {
        todoRepository.save(Todo.builder()
        .name("meet L").status("to do").build());

        List<Todo> todoList = todoRepository.findByNameContains("meet",null);
        assertThat(todoList.size(), Matchers.is(1));
        assertThat(todoList.get(0).getName(), Matchers.is("meet L"));
    }

    @Test
    public void shouldFindTodoWhenAdd() {
        todoRepository.save(Todo.builder()
        .name("meet L").status("to do").build());

        List<Todo> todoList = todoRepository.findAll();
        assertThat(todoList.size(), Matchers.is(1));
        assertThat(todoList.get(0).getName(), Matchers.is("meet L"));
    }

    @Test
    public void shouldFindTodoById() {
        todoRepository.save(Todo.builder()
        .name("meettt L").status("to do").build());


        Todo todo = todoRepository.findOne(1l);
        assertThat(todo.getId(), Matchers.is(1l));
        assertThat(todo.getName(), Matchers.is("cc L"));
    }

    @Test
    public void shouldFindNoTodoWhenDeleteById() {
        todoRepository.save(Todo.builder()
        .name("meet L").status("to do").build());
        todoRepository.save(Todo.builder().name("cc L").status("to do").build());
        todoRepository.delete(2l);
        Todo todo = todoRepository.findOne(2l);
        assertThat(todo, Matchers.is(Matchers.nullValue()));
    }

}
