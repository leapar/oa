package com.purvar.ito.oa.service;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.LoadContext;
import com.purvar.ito.oa.entity.Record;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

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
    @Transactional
    @Override
    public List<KeyValueEntity> getGroups(UUID groupId) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_MONTH,1);
        now.set(Calendar.HOUR_OF_DAY,0);
        now.set(Calendar.MINUTE,0);
        now.set(Calendar.SECOND,0);

        String month = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now.getTime());

        EntityManager em = persistence.getEntityManager();
        Query query = em.createNativeQuery(
                "SELECT \n" +
                        "    count(*), DATE_FORMAT(o.create_ts,'%d/%m/%Y') \n" +
                        "FROM\n" +
                        "    oa_record o,sec_user s\n" +
                        "    where \n" +
                        "    o.user_id = s.id and \n" +
                        "  o.create_ts > #month and " +
                        "    s.group_id in (\n" +
                        "  select h.group_id from sec_group_hierarchy h\n" +
                        "  where h.group_id = #groupId or h.parent_id = #groupId\n" +
                        ") \n" +
                        "GROUP BY DATE_FORMAT(o.create_ts,'%d/%m/%Y') \n" +
                        "order by o.create_ts "
        );

        query.setParameter("groupId",groupId.toString().replace("-",""));
        query.setParameter("month",month);


        List queryResult = query.getResultList();
        return queryResult;
    }


}