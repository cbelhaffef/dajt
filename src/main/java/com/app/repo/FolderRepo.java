package com.app.repo;

import com.app.model.folder.Folder;
import com.app.repo.custom.FolderRepoCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FolderRepo extends JpaRepository<Folder, Long>, FolderRepoCustom {

    List<Folder> findAll();
    Optional<Folder> findById(Long d);
    List<Folder> findByIdIn(Collection<Long> ids);
    Page<Folder> findAll(Pageable p);
    Optional<Folder> findByNumber(String number);
}
