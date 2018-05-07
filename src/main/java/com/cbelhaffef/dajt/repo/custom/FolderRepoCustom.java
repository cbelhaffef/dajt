package com.cbelhaffef.dajt.repo.custom;

import com.cbelhaffef.dajt.enums.FolderStatus;
import com.cbelhaffef.dajt.model.folder.Folder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FolderRepoCustom {

    Page<Folder> findByFilter(Folder fQuery, Pageable pageable);

}
