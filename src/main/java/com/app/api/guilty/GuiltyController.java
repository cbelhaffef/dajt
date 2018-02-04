package com.app.api.guilty;

import com.app.model.folder.FolderListResponse;
import com.app.model.guilty.Guilty;
import com.app.model.guilty.GuiltyListResponse;
import com.app.model.victim.VictimListResponse;
import com.app.repo.GuiltyRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"Guilty"})
public class GuiltyController {

    @Autowired private GuiltyRepo guiltyRepo;

    @ApiOperation(value = "List of guilties", response = FolderListResponse.class)
    @RequestMapping(value = "/guilties", method = RequestMethod.GET , produces={"application/json; charset=UTF-8"})
    public GuiltyListResponse getVictims(Pageable pageable){
        GuiltyListResponse resp = new GuiltyListResponse();
        Page<Guilty> pg = guiltyRepo.findAll(pageable);
        resp.setPageStats(pg, true);
        resp.setItems(pg.getContent());
        return resp;
    }

}
