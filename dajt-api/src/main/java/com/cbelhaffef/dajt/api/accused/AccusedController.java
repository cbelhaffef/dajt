package com.cbelhaffef.dajt.api.accused;

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

//@RestController
//@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
//@Api(tags = {"Accused"})
public class AccusedController {

//    @Autowired private AccusedRepo accusedRepo;
//
//    @ApiOperation(value = "List of accused", response = FolderListResponse.class)
//    @RequestMapping(value = "/accusedd", method = RequestMethod.GET , produces={"application/json; charset=UTF-8"})
//    public AccusedListResponse getAccused(Pageable pageable){
//        AccusedListResponse resp = new AccusedListResponse();
//        Page<Accused> pg = accusedRepo.findAll(pageable);
//        resp.setPageStats(pg, true);
//        resp.setItems(pg.getContent());
//        return resp;
//    }

}
