package com.thoughtworks.restfulAPI.restfulAPI.controller;

import com.thoughtworks.restfulAPI.restfulAPI.exception.HttpStateCode404Exception;
import com.thoughtworks.restfulAPI.restfulAPI.model.Tag;
import com.thoughtworks.restfulAPI.restfulAPI.model.Todo;
import com.thoughtworks.restfulAPI.restfulAPI.model.User;
import com.thoughtworks.restfulAPI.restfulAPI.services.TagService;
import com.thoughtworks.restfulAPI.restfulAPI.services.TodoService;
import com.thoughtworks.restfulAPI.restfulAPI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public Todo editTodo(@RequestBody Todo todo){
         return todoService.editTodo(todo);
    }

    @DeleteMapping(value = "/{id}")
    public Map<String,Long> deleteTodo(@PathVariable(value = "id") Long id){
        return todoService.deleteTodo(id);
    }


    @GetMapping(value = "/page/{page}/size/{size}")
    public Page<Todo> getListWithPage(
            @PathVariable(value = "page") Integer page,
            @PathVariable(value = "size") Integer size) {
        return todoService.getListWithPage(page,size);
    }

    @GetMapping("/search")
    public  Page<Todo> getListWithSearch(@RequestParam(required = false)  String from,
                                         @RequestParam(required = false)  String to,
                                         @RequestParam(required = false)  String searchNameOrTagsValue,
                                         Pageable pageable){

            return todoService.getListWithSearchCondintion(from,to,searchNameOrTagsValue,pageable);

    }


}
