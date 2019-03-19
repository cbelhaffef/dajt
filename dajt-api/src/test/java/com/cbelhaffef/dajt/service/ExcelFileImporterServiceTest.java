
package com.cbelhaffef.dajt.service;

import com.cbelhaffef.dajt.api.court.Court;
import com.cbelhaffef.dajt.api.folder.Folder;
import com.cbelhaffef.dajt.api.office.Office;
import com.cbelhaffef.dajt.api.court.CourtRepo;
import com.cbelhaffef.dajt.api.folder.FolderRepo;
import com.cbelhaffef.dajt.api.imports.ImportRepo;
import com.cbelhaffef.dajt.api.office.OfficeRepo;
import com.cbelhaffef.dajt.service.impl.ExelFileImporterServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.*;


//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {ExelFileImporterServiceImpl.class})
//@ContextConfiguration(classes = {MainApp.class})
//@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ExcelFileImporterServiceTest {

    @Autowired
    @InjectMocks
    private ExelFileImporterServiceImpl importerService;

    @Mock
    private FolderRepo folderRepo;

    @Mock
    private OfficeRepo officeRepo;

    @Mock
    private CourtRepo courtRepo;

    @Mock
    private ImportRepo importRepo;

    File exelForTest;

    private final String EXCEL_FILE_TO_TEST = "/exelForTest.xlsx";


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        //notice different Mockito syntax for spy
        Mockito.when(folderRepo.findByNumber(Mockito.anyString())).thenReturn(null);
        //Mockito.doNothing().when(folderRepo.save(Mockito.any()));
//        Mockito.when(folderRepo.save(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());

        Office office = new Office();
        office.setId(1L);
        office.setName("B1");
        Mockito.when(officeRepo.getOne(Mockito.anyLong())).thenReturn(office);

        Court court = new Court();
        court.setId(1L);
        court.setName("Court1");
        Optional<Court> courtOpt = Optional.of(court);
        Mockito.when(courtRepo.findByName(Mockito.anyString())).thenReturn(courtOpt);

        //Mockito.doNothing().when(importRepo.save(Mockito.any()));
//        Mockito.when(importRepo.save(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());

        exelForTest = new File (getClass().getResource(EXCEL_FILE_TO_TEST).getFile());
        assertNotNull(exelForTest);
        assertTrue(exelForTest.exists());
    }

    @Test
    public void shouldImportExelFileIntoList() throws IOException, ParseException {
        Set<Folder> folders = importerService.doImport(exelForTest);
        assertNotNull(folders);
        assertFalse(folders.isEmpty());
        List<Folder> listFolders = new ArrayList<>(folders);

        assertEquals(15, listFolders.size());

        Folder folder = listFolders.get(5);
        assertNotNull(folder.getAccused());
        assertEquals(2,folder.getAccused().size());

    }


}
