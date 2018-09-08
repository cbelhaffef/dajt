package com.cbelhaffef.dajt.api.office;

import com.cbelhaffef.dajt.models.folder.FolderListResponse;
import com.cbelhaffef.dajt.entities.Office;
import com.cbelhaffef.dajt.models.office.OfficeListResponse;
import com.cbelhaffef.dajt.repositories.OfficeRepo;
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
@Api(tags = {"Office"})
public class OfficeController {

    @Autowired
    private OfficeRepo officeRepo;

    @ApiOperation(value = "List of offices", response = FolderListResponse.class)
    @RequestMapping(value = "/offices", method = RequestMethod.GET , produces={"application/json; charset=UTF-8"})
    public OfficeListResponse getOffices(@ApiParam(value = ""    )               @RequestParam(value = "page"  ,  defaultValue="0"   ,  required = false) Integer page,
                                         @ApiParam(value = "between 1 to 1000" ) @RequestParam(value = "size"  ,  defaultValue="20"  ,  required = false) Integer size,
                                         Pageable pageable) {

        OfficeListResponse resp = new OfficeListResponse();

        Page<Office> pg = officeRepo.findAll(pageable);
        resp.setPageStats(pg, true);
        resp.setItems(pg.getContent());
        return resp;
    }

}

