package com.cbelhaffef.dajt.repositories.custom;


import com.cbelhaffef.dajt.entities.Folder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FolderRepoCustom {

    Page<Folder> findByFilter(Folder fQuery, Pageable pageable);

}
