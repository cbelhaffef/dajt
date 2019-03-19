package com.cbelhaffef.dajt.api.folder;


import com.cbelhaffef.dajt.api.folder.Folder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FolderRepoCustom {

    Page<Folder> findByFilter(Folder fQuery, Pageable pageable);

}
