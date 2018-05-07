package com.cbelhaffef.dajt.service.importer;

import com.cbelhaffef.dajt.model.folder.Folder;
import com.cbelhaffef.dajt.service.impl.FolderExelImporterServiceImpl;
import com.cbelhaffef.dajt.service.model.FolderExel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FolderExelImporterServiceImpl.class)
public class ImportFolderJobExelTest {

    @Autowired
    private FolderExelImporterServiceImpl importerService;

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

    }


}
