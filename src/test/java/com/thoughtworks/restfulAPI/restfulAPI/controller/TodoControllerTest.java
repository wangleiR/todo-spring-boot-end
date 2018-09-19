package com.thoughtworks.restfulAPI.restfulAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.restfulAPI.restfulAPI.model.Todo;
import com.thoughtworks.restfulAPI.restfulAPI.services.TodoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private TodoService toDoService;

    @Test
    public void shouldGetTodoById() throws Exception {
        String contentAsString = mockMvc.perform(get("/todos/{id}",1))
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println(contentAsString);
    }

    /**
     * get by id
     * @throws Exception
     */
    @Test
    public void shouldGetTodoByIdWithAssertions() throws Exception {
        Todo todo = new Todo(1l, "meeting", "To Do", new Date());

        given(toDoService.getTodoById(todo.getId())).willReturn(todo);


        mockMvc.perform(get("/todos/{id}",todo.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.name").value("meeting"))
            .andExpect(jsonPath("$.status").value("To Do"));
    }

    @Test
    public void shouldNotGetTodoByIdWithErrorId() throws Exception {
        mockMvc.perform(get("/todos/{id}",11))
            .andExpect(status().isNotFound());
    }

    /**
     * delete by id
     * @throws Exception
     */
    @Test
    public void shouldGetNotFoundWhenDeleteById() throws Exception {
        Todo todo = new Todo(1l, "meeting", "finished", new Date());

        doNothing().when(toDoService).deleteTodo(todo.getId());

        mockMvc.perform(delete("/todos/{id}",todo.getId()))
                .andExpect(status().isOk());

        verify(toDoService, times(1)).deleteTodo(todo.getId());
        verifyNoMoreInteractions(toDoService);
    }

    /**
     * get all list
     * @throws Exception
     */
    @Test
    public void shouldGetAllLists() throws Exception {

        Todo todo = new Todo(1l, "meeting", "finished", new Date());
        Todo todoNew = new Todo(2l, "todoNew", "to do", new Date());

        List<Todo> todoList = Arrays.asList(todo, todoNew);

        given(toDoService.getTodo()).willReturn(todoList);

         mockMvc.perform(get("/todos"))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    /**
     * add a todoItem
     */
    @Test
    public void shouldGetTodoWhenAdd() throws Exception {
        Todo todo = new Todo(1l, "meeting", "finished", new Date());

        given(toDoService.addTodo(any())).willReturn(todo);

        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(todo)))
                .andExpect(status().isOk());
        verify(toDoService,times(1)).addTodo(todo);
        verifyNoMoreInteractions(toDoService);

    }

    /**
     * edit a todoItem
     */
    @Test
    public void shouldGetTodoWhenEdit() throws Exception {
        Todo todo = new Todo(1l, "meeting111", "finished", new Date());
        
        given(toDoService.getTodoById(todo.getId())).willReturn(todo);
        doNothing().when(toDoService).editTodo(todo);

        mockMvc.perform(put("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(todo)))
                .andExpect(status().isOk());

//        verify(toDoService, times(1)).getTodoById(todo.getId());
        verify(toDoService, times(1)).editTodo(todo);
        verifyNoMoreInteractions(toDoService);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
