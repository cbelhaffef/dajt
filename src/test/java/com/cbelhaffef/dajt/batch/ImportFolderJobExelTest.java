package com.cbelhaffef.dajt.batch;

import com.app.model.folder.Folder;
import com.cbelhaffef.dajt.batch.model.FolderExel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ImportFolderJobExel.class)
public class ImportFolderJobExelTest {

    @Autowired
    private ImportFolderJobExel importFolderJobExel;

    File exelForTest;

    @Before
    public void setUp(){
        exelForTest = new File (getClass().getResource("classpath:exelForTest.xlsx").getFile());
        assertNotNull(exelForTest);
        assertTrue(exelForTest.exists());
    }

    @Test
    public void shouldImportExelFileIntoDTO(){
        List<FolderExel> folders = importFolderJobExel.doImport(exelForTest);

        assertNotNull(folders);
        assertFalse(folders.isEmpty());

    }


}
