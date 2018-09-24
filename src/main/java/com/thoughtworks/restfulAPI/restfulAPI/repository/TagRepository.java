package com.thoughtworks.restfulAPI.restfulAPI.repository;

import com.thoughtworks.restfulAPI.restfulAPI.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TagRepository  extends JpaRepository<Tag,Long> {

    Page<Tag> findAllByUserId(Long id, Pageable pageable);

    Tag findOneByUserIdAndValue(Long id, String value);

    Page<Tag> findByValueAndUserId(String value, Long id, Pageable pageable);
}
