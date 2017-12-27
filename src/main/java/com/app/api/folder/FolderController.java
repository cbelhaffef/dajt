package com.app.api.order;

import com.app.enums.FolderStatus;
import com.app.model.folder.Folder;
import com.app.model.folder.FolderResponse;
import com.app.model.folder.FolderStatusResponse;
import io.swagger.annotations.*;
//import springfox.documentation.annotations.*;
import org.springframework.data.domain.Example;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.*;
//import static org.springframework.http.MediaType.*;

import java.util.*;
import java.math.BigDecimal;
import com.app.api.*;
import com.app.model.order.*;
import com.app.repo.*;
import static com.app.model.response.OperationResponse.*;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"Order"})
public class FolderController {

    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private FolderRepo folderRepo;
    @Autowired private OrderRepo orderRepo;


    @ApiOperation(value = "List of folders", response = OrderResponse.class)
    @RequestMapping(value = "/folders", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public FolderResponse getFoldersByPage(
        @ApiParam(value = ""    )               @RequestParam(value = "page"  ,  defaultValue="0"   ,  required = false) Integer page,
        @ApiParam(value = "between 1 to 1000" ) @RequestParam(value = "size"  ,  defaultValue="20"  ,  required = false) Integer size,
        @RequestParam(value = "folderNumber"     , required = false) String folderNumber,
        @RequestParam(value = "folderStatus"      , required = false) FolderStatus folderStatus,
        Pageable pageable
    ) {
        FolderResponse resp = new FolderResponse();
        Folder qry = new Folder();
        if (folderNumber != null)     { qry.setNumber(folderNumber); }
        if (folderStatus != null)  { qry.setStatus(folderStatus); }

        Page<Folder> pg = folderRepo.findAll(Example.of(qry), pageable);
        resp.setPageStats(pg, true);
        resp.setItems(pg.getContent());
        return resp;
    }

    @ApiOperation(value = "List of folders", response = OrderResponse.class)
    @RequestMapping(value = "/folders/status", method = RequestMethod.GET)
    public FolderStatusResponse getFoldersStatus() {
        FolderStatusResponse resp = new FolderStatusResponse();
        resp.setItems(Arrays.asList(FolderStatus.values()));
        return resp;
    }

}
