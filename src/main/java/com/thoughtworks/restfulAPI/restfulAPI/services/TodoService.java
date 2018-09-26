package com.thoughtworks.restfulAPI.restfulAPI.services;

import com.thoughtworks.restfulAPI.restfulAPI.exception.HttpStateCode404Exception;
import com.thoughtworks.restfulAPI.restfulAPI.model.Tag;
import com.thoughtworks.restfulAPI.restfulAPI.model.Todo;
import com.thoughtworks.restfulAPI.restfulAPI.model.User;
import com.thoughtworks.restfulAPI.restfulAPI.repository.TagRepository;
import com.thoughtworks.restfulAPI.restfulAPI.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TodoService {

    @Autowired private TodoRepository todoRepository;
    @Autowired private TagRepository tagRepository;
    @Autowired private UserService userService;
    @Autowired private TagService tagService;


    public Page<Todo> getTodoLists(Pageable pageable){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Todo> allLists = todoRepository.findAllByUserId(user.getId(), pageable);
        return allLists;
    }


    public Todo getTodoById(Long id) throws HttpStateCode404Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (todoRepository.findByIdAndUserId(id,user.getId()) != null) {
            return todoRepository.findByIdAndUserId(id,user.getId());
        }
        else{
            throw new HttpStateCode404Exception();
        }
    }

    public Todo addTodo(Todo todo) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        todo.setUserId(user.getId());
        return todoRepository.save(todo);
    }

    public Todo editTodo(Todo todo) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Todo editTodo = todoRepository.findByIdAndUserId(todo.getId(),user.getId());
        editTodo.setName(todo.getName());
        editTodo.setDueDate(todo.getDueDate());
        editTodo.setStatus(todo.getStatus());
        editTodo.setTags(todo.getTags());
        editTodo.setUserId(user.getId());
        return todoRepository.save(editTodo);
    }

    public  Map<String,Long> deleteTodo(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Long> map = new HashMap<>();
        todoRepository.deleteByIdAndUserId(id,user.getId());
        map.put("id",id);
        return map;
    }

    public Page<Todo> getListWithPage(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page-1,size);
        Page<Todo> result = todoRepository.findAll(pageable);
        return result;

    }

    public Page<Todo> getListWithSearchCondintion( String from, String to, String searchNameOrTagsValue,Pageable pageable) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Page<Tag> tagList = tagService.getTagListByValueAndUserId(searchNameOrTagsValue,user.getId(),pageable);

        if (tagList.getContent().size() > 0){// if tagValue equal queryParam, search it as tag, else search it as todoName

            return todoRepository.findByUserIdAndTags_Value(user.getId(), searchNameOrTagsValue, pageable);
        }else {

            return todoRepository.findByUserIdAndNameContains(user.getId(), searchNameOrTagsValue, pageable);
        }

    }
}
