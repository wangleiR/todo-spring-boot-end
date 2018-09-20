package com.thoughtworks.restfulAPI.restfulAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RequestParam {
    private Date beginDate;
    private Date endDate;
    private List<Long> tagIds;

}
