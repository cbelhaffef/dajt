package com.app.repo;

import com.app.model.folder.Folder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepo extends JpaRepository<Folder, Long> {
    public List<Folder> findAll();
    public Page<Folder> findAll(Pageable p);
}
