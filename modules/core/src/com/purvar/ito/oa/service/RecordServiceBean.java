package com.purvar.ito.oa.service;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.global.LoadContext;
import com.purvar.ito.oa.entity.Record;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service(RecordService.NAME)
public class RecordServiceBean implements RecordService {
    @Inject
    private Persistence persistence;

    @Transactional
    @Override
    public List<Record> getEntities(LoadContext context) {
        EntityManager em = persistence.getEntityManager();
        Query query = em.createNativeQuery(
                "select *,concat(lat,concat(',',lng)) from oa_record"
        ).setFirstResult(context.getQuery().getFirstResult()).setMaxResults(context.getQuery().getMaxResults());


        List queryResult = query.getResultList();
        return queryResult;
    }
}