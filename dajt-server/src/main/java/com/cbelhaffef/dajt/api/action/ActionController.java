package com.cbelhaffef.dajt.api.action;

import com.cbelhaffef.dajt.entities.Action;
import com.cbelhaffef.dajt.models.action.ActionListResponse;
import com.cbelhaffef.dajt.models.folder.FolderListResponse;
import com.cbelhaffef.dajt.repositories.ActionRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"Action"})
public class ActionController {

    @Autowired
    private ActionRepo actionRepo;

    @ApiOperation(value = "List of actions", response = FolderListResponse.class)
    @RequestMapping(value = "/actions", method = RequestMethod.GET , produces={"application/json; charset=UTF-8"})
    public ActionListResponse getActions(@ApiParam(value = ""    )               @RequestParam(value = "page"  ,  defaultValue="0"   ,  required = false) Integer page,
                                         @ApiParam(value = "between 1 to 1000" ) @RequestParam(value = "size"  ,  defaultValue="20"  ,  required = false) Integer size,
                                         Pageable pageable) {

        ActionListResponse resp = new ActionListResponse();

        Page<Action> pg = actionRepo.findAll(pageable);
        resp.setItems(pg.getContent());
        return resp;
    }

}

