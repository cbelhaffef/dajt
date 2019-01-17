package com.cbelhaffef.dajt.dao.repositories.custom;


import com.cbelhaffef.dajt.dao.entities.Folder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FolderRepoCustom {

    Page<Folder> findByFilter(Folder fQuery, Pageable pageable);

}
