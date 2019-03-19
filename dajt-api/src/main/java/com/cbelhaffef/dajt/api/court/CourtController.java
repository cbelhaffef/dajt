package com.cbelhaffef.dajt.api.court;

import com.cbelhaffef.dajt.api.folder.FolderListResponse;
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
@Api(tags = {"Accused"})
public class CourtController {

    @Autowired private CourtRepo courtRepo;

    @ApiOperation(value = "List of courts", response = FolderListResponse.class)
    @RequestMapping(value = "/courts", method = RequestMethod.GET , produces={"application/json; charset=UTF-8"})
    public CourtListResponse getVictims(Pageable pageable){
        CourtListResponse resp = new CourtListResponse();
        Page<Court> pg = courtRepo.findAll(pageable);
        resp.setPageStats(pg, true);
        resp.setItems(pg.getContent());
        return resp;
    }

}
