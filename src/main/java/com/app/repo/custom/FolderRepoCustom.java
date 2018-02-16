package com.app.repo.custom;

import com.app.enums.FolderStatus;
import com.app.model.folder.Folder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FolderRepoCustom {

    Page<Folder> findByFilter(Folder fQuery, Pageable pageable);

}
