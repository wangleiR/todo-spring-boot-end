package com.thoughtworks.restfulAPI.restfulAPI.repository;

import com.thoughtworks.restfulAPI.restfulAPI.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Repository
public interface TodoRepository  extends JpaRepository<Todo,Long> {


    List<Todo> findByNameContains(String name, Pageable pageable);

    Page<Todo> findAllByUserId(Long userId, Pageable pageable);

    List<Todo> findByTags_IdIn(List<Long> tags);

    Todo findByIdAndUserId(Long id,Long userId);

    @Modifying
    @Transactional
    @Query("delete from Todo t where t.id = ?1 and t.userId = ?2")
    void deleteByIdAndUserId(Long id,Long userId);

}
