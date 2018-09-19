package com.thoughtworks.restfulAPI.restfulAPI.repository;

import com.thoughtworks.restfulAPI.restfulAPI.model.Todo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository  extends JpaRepository<Todo,Long> {


    List<Todo> findByNameContains(String name, Pageable pageable);

}
