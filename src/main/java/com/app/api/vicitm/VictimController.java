package com.app.api.vicitm;

import com.app.model.folder.FolderListResponse;
import com.app.model.victim.Victim;
import com.app.model.victim.VictimListResponse;
import com.app.repo.VictimRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"Victim"})
public class VictimController {

    @Autowired private VictimRepo victimRepo;

    @ApiOperation(value = "List of folders", response = FolderListResponse.class)
    @RequestMapping(value = "/victims", method = RequestMethod.GET , produces={"application/json; charset=UTF-8"})
    public VictimListResponse getVictims(
        @ApiParam(value = "")                  @RequestParam(value = "page"  ,  defaultValue="0"   ,  required = false) Integer page,
        @ApiParam(value = "between 1 to 1000") @RequestParam(value = "size"  ,  defaultValue="20"  ,  required = false) Integer size,
        @RequestParam(value = "firstName"     , required = false) String firstName,
        @RequestParam(value = "lastName"      , required = false) String lastName,
        Pageable pageable){

        VictimListResponse resp = new VictimListResponse();
        Victim qry = new Victim();
        if (firstName != null)  { qry.setFirstName(firstName); }
        if (lastName != null)  { qry.setLastName(lastName); }

        ExampleMatcher matcher = ExampleMatcher.matching()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
            .withIgnoreNullValues()
            .withIgnoreCase()
            .withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.contains())
            .withMatcher("lastName", ExampleMatcher.GenericPropertyMatchers.contains());

        Page<Victim> pg = victimRepo.findAll(Example.of(qry,matcher), pageable);
        resp.setPageStats(pg, true);
        resp.setItems(pg.getContent());
        return resp;
    }

}
