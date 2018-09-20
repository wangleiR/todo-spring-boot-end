package com.thoughtworks.restfulAPI.restfulAPI.repository;

import com.thoughtworks.restfulAPI.restfulAPI.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository  extends JpaRepository<Tag,Long> {

}
