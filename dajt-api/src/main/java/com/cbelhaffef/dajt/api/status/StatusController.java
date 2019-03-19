package com.cbelhaffef.dajt.api.status;

import com.cbelhaffef.dajt.api.folder.FolderListResponse;
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
@Api(tags = {"Status"})
public class StatusController {

    @Autowired
    private StatusRepo statusRepo;

    @ApiOperation(value = "List of status", response = FolderListResponse.class)
    @RequestMapping(value = "/status", method = RequestMethod.GET , produces={"application/json; charset=UTF-8"})
    public StatusListResponse getStatus() {

        StatusListResponse resp = new StatusListResponse();

        List<Status> status = statusRepo.findAll();

        resp.setItems(status);
        return resp;
    }

}

