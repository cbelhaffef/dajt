package com.app.repo.custom.impl;

import com.app.enums.FolderStatus;
import com.app.model.folder.Folder;
import com.app.model.folder.QFolder;
import com.app.model.guilty.QGuilty;
import com.app.model.office.QOffice;
import com.app.model.victim.QVictim;
import com.app.repo.custom.FolderRepoCustom;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.jpa.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.data.jpa.repository.support.Querydsl;

public class FolderRepoCustomImpl extends QueryDslRepositorySupport implements FolderRepoCustom{

    public FolderRepoCustomImpl() {
        super(Folder.class);
    }

    @Override
    public Page<Folder> findByFilter(Folder fQuery, Pageable pageable) {

        QFolder folder = QFolder.folder;
        QOffice office = QOffice.office;
        QVictim victim = QVictim.victim;
        QGuilty guilty = QGuilty.guilty;

        Querydsl querydsl = getQuerydsl();
        JPQLQuery<Folder> query = querydsl.createQuery();

        query.select(folder).from(folder);

        if (fQuery.getNumber() != null) {
            query.where(folder.number.contains(fQuery.getNumber()));
        }

        if (fQuery.getOffice() != null && fQuery.getOffice().getId() != null) {
            query.leftJoin(folder.office(), office).where(office.id.eq(fQuery.getOffice().getId()));
        }

        if (fQuery.getStatus() != null) {
            query.where(folder.status.eq(fQuery.getStatus()));
        }

        if (fQuery.getVictims() != null && !fQuery.getVictims().isEmpty()) {
            query.leftJoin(folder.victims, victim).where(victim.name.contains(fQuery.getVictims().iterator().next().getName()));
        }

        if (fQuery.getGuilties() != null && !fQuery.getGuilties().isEmpty() ) {
            query.leftJoin(folder.guilties, guilty).where(guilty.name.contains(fQuery.getGuilties().iterator().next().getName()));
        }
        querydsl.applyPagination(pageable, query);

        Long total = query.fetchCount();

        return new PageImpl<Folder>(query.fetch(), pageable, total);

    }

}
