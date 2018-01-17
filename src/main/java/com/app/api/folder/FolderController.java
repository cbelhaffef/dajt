package com.app.api.folder;

import com.app.enums.FolderStatus;
import com.app.model.folder.Folder;
import com.app.model.folder.FolderListResponse;
import com.app.model.folder.FolderStatusResponse;
import com.app.repo.FolderRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

//import springfox.documentation.annotations.*;
//import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"Folder"})
public class FolderController {

    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private FolderRepo folderRepo;

    @ApiOperation(value = "List of folders", response = FolderListResponse.class)
    @RequestMapping(value = "/folders", method = RequestMethod.GET , produces={"application/json; charset=UTF-8"})
    public FolderListResponse getFoldersByPage(
        @ApiParam(value = ""    )               @RequestParam(value = "page"  ,  defaultValue="0"   ,  required = false) Integer page,
        @ApiParam(value = "between 1 to 1000" ) @RequestParam(value = "size"  ,  defaultValue="20"  ,  required = false) Integer size,
        @RequestParam(value = "folderNumber"     , required = false) String folderNumber,
        @RequestParam(value = "folderStatus"      , required = false) FolderStatus folderStatus,
        Pageable pageable
    ) {
        FolderListResponse resp = new FolderListResponse();
        Folder qry = new Folder();
        if (folderNumber != null)     { qry.setNumber(folderNumber); }
        if (folderStatus != null)  { qry.setStatus(folderStatus); }

        Page<Folder> pg = folderRepo.findAll(Example.of(qry), pageable);
        resp.setPageStats(pg, true);
        resp.setItems(pg.getContent());
        return resp;
    }

    @ApiOperation(value = "List of folders", response = FolderStatusResponse.class)
    @RequestMapping(value = "/folders/status", method = RequestMethod.GET)
    public FolderStatusResponse getFoldersStatus() {
        FolderStatusResponse resp = new FolderStatusResponse();
        resp.setItems(Arrays.asList(FolderStatus.values()));
        return resp;
    }

    @ApiOperation(value = "Order Details", response = Folder.class)
    @RequestMapping(value = "/folders/{folderNumber}", method = RequestMethod.GET)
    public Folder getFolderDetail( @PathVariable("folderNumber") String folderNumber) {
        Optional<Folder> folder = folderRepo.findByNumber(folderNumber);
        return folder.isPresent() ? folder.get() : null;
    }
}
