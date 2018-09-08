package com.cbelhaffef.dajt.api.priority;

import com.cbelhaffef.dajt.models.folder.FolderListResponse;
import com.cbelhaffef.dajt.entities.Priority;
import com.cbelhaffef.dajt.models.priority.PriorityListResponse;
import com.cbelhaffef.dajt.repositories.PriorityRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"Priorities"})
public class PriorityController {

    @Autowired
    private PriorityRepo priorityRepo;

    @ApiOperation(value = "List of priorities", response = FolderListResponse.class)
    @RequestMapping(value = "/priorities", method = RequestMethod.GET , produces={"application/json; charset=UTF-8"})
    public PriorityListResponse getPriorities() {

        PriorityListResponse resp = new PriorityListResponse();

        List<Priority> priorities = priorityRepo.findAll();

        resp.setItems(priorities);
        return resp;
    }

}

