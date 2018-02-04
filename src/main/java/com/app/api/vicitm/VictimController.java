package com.app.api.vicitm;

import com.app.model.folder.FolderListResponse;
import com.app.model.victim.Victim;
import com.app.model.victim.VictimListResponse;
import com.app.repo.VictimRepo;
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
@Api(tags = {"Victim"})
public class VictimController {

    @Autowired private VictimRepo victimRepo;

    @ApiOperation(value = "List of victims", response = FolderListResponse.class)
    @RequestMapping(value = "/victims", method = RequestMethod.GET , produces={"application/json; charset=UTF-8"})
    public VictimListResponse getVictims(Pageable pageable){
        VictimListResponse resp = new VictimListResponse();
        Page<Victim> pg = victimRepo.findAll(pageable);
        resp.setPageStats(pg, true);
        resp.setItems(pg.getContent());
        return resp;
    }

}
