package com.cbelhaffef.dajt.service.importer;

import com.cbelhaffef.dajt.MainApp;
import com.cbelhaffef.dajt.dataset.FoldersDS;
import com.cbelhaffef.dajt.model.folder.Folder;
import com.cbelhaffef.dajt.service.impl.ExelFileImporterServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {FolderExelImporterServiceImpl.class,FoldersDS.class})
@ContextConfiguration(classes = {MainApp.class})
public class ImportFolderJobExelTest {

    @Autowired
    private ExelFileImporterServiceImpl importerService;

    @Autowired
    private FoldersDS foldersDS;

    File exelForTest;

    @Before
    public void setUp(){
        exelForTest = new File (getClass().getResource("/exelForTest.xlsx").getFile());
        assertNotNull(exelForTest);
        assertTrue(exelForTest.exists());
    }

    @Test
    public void shouldImportExelFileIntoDTO() throws FileNotFoundException {
        Set<Folder> folders = importerService.doImport(exelForTest);
        assertNotNull(folders);
        assertFalse(folders.isEmpty());
        List<Folder> listFolders = new ArrayList<>(folders);
        List<Folder> expectedFolders = foldersDS.getAll();
        for(int i = 0; i< listFolders.size() ; i++){
            assertThat("test folder" + i, listFolders.get(i),is(equalTo(expectedFolders.get(i))));
            //assertEquals(listFolders.get(i),samePropertyValuesAs(expectedFolders.get(i)));
        }

    }


}
