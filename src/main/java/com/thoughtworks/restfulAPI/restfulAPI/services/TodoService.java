package com.thoughtworks.restfulAPI.restfulAPI.services;

import com.thoughtworks.restfulAPI.restfulAPI.exception.HttpStateCode404Exception;
import com.thoughtworks.restfulAPI.restfulAPI.model.Todo;
import com.thoughtworks.restfulAPI.restfulAPI.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired private TodoRepository todoRepository;

    public Page<Todo> getTodoLists(Pageable pageable){
        return todoRepository.findAll(pageable);
    }

    public Todo getTodoById(Long id) throws HttpStateCode404Exception {
        if (todoRepository.findOne(id) != null) {
            return todoRepository.findOne(id);
        }
        else{
            throw new HttpStateCode404Exception();
        }
    }

    public Todo addTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public void editTodo(Todo todo) {
        Todo editTodo = todoRepository.findOne(todo.getId());
        editTodo.setName(todo.getName());
        editTodo.setDueDate(todo.getDueDate());
        editTodo.setStatus(todo.getStatus());
        editTodo.setTags(todo.getTags());
        todoRepository.save(editTodo);
    }

    public void deleteTodo(Long id) {
        todoRepository.delete(id);
    }

    public List<Todo> findTodoByName(String name, Pageable pageable){
        return todoRepository.findByNameContains(name, pageable);
    }

    public Page<Todo> getListWithPage(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page-1,size);
        Page<Todo> result = todoRepository.findAll(pageable);
        return result;

    }
}
