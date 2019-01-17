package com.cbelhaffef.dajt.dao.repositories.custom.impl;

import com.cbelhaffef.dajt.dao.entities.*;
import com.cbelhaffef.dajt.dao.repositories.custom.FolderRepoCustom;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Repository;

@Repository
public class FolderRepoCustomImpl extends QueryDslRepositorySupport implements FolderRepoCustom {

    public FolderRepoCustomImpl() {
        super(Folder.class);
    }

    @Override
    public Page<Folder> findByFilter(Folder fQuery, Pageable pageable) {

        QFolder folder = QFolder.folder;
        QOffice office = QOffice.office;
        QVictim victim = QVictim.victim;
        QAccused accused = QAccused.accused;
        QStatus status = QStatus.status;

        Querydsl querydsl = getQuerydsl();
        JPQLQuery<Folder> query = querydsl.createQuery();

        query.select(folder).from(folder);

        if (fQuery.getNumber() != null) {
            query.where(folder.number.contains(fQuery.getNumber()));
        }

        if (fQuery.getOffice() != null && fQuery.getOffice().getId() != null) {
            query.leftJoin(folder.office(), office).where(folder.office().id.eq(fQuery.getOffice().getId()));
        }

        if (fQuery.getStatus() != null) {
            query.leftJoin(folder.status(), status).where(folder.status().id.eq(fQuery.getStatus().getId()));
        }

        if (fQuery.getVictims() != null && !fQuery.getVictims().isEmpty()) {
            query.leftJoin(folder.victims, victim).where(folder.victims.any().name.contains(fQuery.getVictims().iterator().next().getName()));
        }

        if (fQuery.getAccused() != null && !fQuery.getAccused().isEmpty() ) {
            query.leftJoin(folder.accused, accused).where(folder.accused.any().name.contains(fQuery.getAccused().iterator().next().getName()));
        }
        querydsl.applyPagination(pageable, query);

        Long total = query.fetchCount();

        return new PageImpl<Folder>(query.fetch(), pageable, total);

    }

}
