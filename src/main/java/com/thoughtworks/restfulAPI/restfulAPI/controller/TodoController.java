package com.thoughtworks.restfulAPI.restfulAPI.controller;

import com.thoughtworks.restfulAPI.restfulAPI.exception.HttpStateCode404Exception;
import com.thoughtworks.restfulAPI.restfulAPI.model.Todo;
import com.thoughtworks.restfulAPI.restfulAPI.model.User;
import com.thoughtworks.restfulAPI.restfulAPI.services.TodoService;
import com.thoughtworks.restfulAPI.restfulAPI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired  private TodoService todoService;
    @Autowired  private UserService userService;

    @GetMapping
    public Page<Todo> getTodo(Pageable pageable){
        return todoService.getTodoLists(pageable);
    }

    @GetMapping(value = "/{id}")
    public Todo getListById(@PathVariable(value = "id") Long id) throws HttpStateCode404Exception {
        return todoService.getTodoById(id);
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo todo){
        return todoService.addTodo(todo);
    }

    @PutMapping
    public void editTodo(@RequestBody Todo todo){
         todoService.editTodo(todo);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTodo(@PathVariable(value = "id") Long id){
        todoService.deleteTodo(id);
    }


    @GetMapping(value = "/name/{name}")
    public List<Todo> getListById(@PathVariable(value = "name") String name,
                                  Pageable pageable) {
        return todoService.findTodoByName(name, pageable);
    }

    @GetMapping(value = "/page/{page}/size/{size}")
    public Page<Todo> getListWithPage(
            @PathVariable(value = "page") Integer page,
            @PathVariable(value = "size") Integer size) {
        return todoService.getListWithPage(page,size);
    }

    @GetMapping("/search")
    public  List<Todo> getListWithSearch(@RequestParam(required = false)  String from,
                                         @RequestParam(required = false)  String to,
                                         @RequestParam(required = false)  List<Long> tagsId){
        return todoService.getListWithSearchCondintion(tagsId,from,to);
    }


}
