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
    public Page<Folder> findByNumberAndOfficeIdAndStatusAndVictimsNameAndGuiltiesName(String number,
                                                                            Long officeId,
                                                                            FolderStatus status,
                                                                            String victimName,
                                                                            String guiltyName,
                                                                            Pageable pageable) {

        QFolder folder = QFolder.folder;
        QOffice office = QOffice.office;
        QVictim victim = QVictim.victim;
        QGuilty guilty = QGuilty.guilty;

        Querydsl querydsl = getQuerydsl();
        JPQLQuery<Folder> query = querydsl.createQuery();

        query.select(folder).from(folder);

        if (number != null) {
            query.where(folder.number.like("%" + number + "%"));
        }

        if (officeId != null) {
            query.leftJoin(folder.office(), office).where(office.id.eq(officeId));
        }

        if (status != null) {
            query.where(folder.status.eq(status));
        }

        if (victimName != null) {
            query.leftJoin(folder.victims, victim).where(victim.name.like("%"+victimName));
        }

        if (guiltyName != null ) {
            query.leftJoin(folder.guilties, guilty).where(guilty.name.like("%"+guiltyName));
        }
        querydsl.applyPagination(pageable, query);

        Long total = query.fetchCount();
//        ((JPAQuery<Folder>) query).setHint(QueryHints.HINT_LOADGRAPH, getEntityManager().getEntityGraph("server.site"));

        return new PageImpl<Folder>(query.fetch(), pageable, total);

    }

}
