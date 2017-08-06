package com.purvar.ito.oa.service;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.security.entity.User;
import com.purvar.ito.oa.entity.ExtUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service(UserService.NAME)
public class UserServiceBean implements UserService {
    @Inject
    private Persistence persistence;

    @Transactional
    @Override
    public List<Object[]> getEntities(LoadContext context) {
        EntityManager em = persistence.getEntityManager();
        Query query = em.createNativeQuery(
                "select name,login,id from sec_user"
        ).setFirstResult(context.getQuery().getFirstResult()).setMaxResults(context.getQuery().getMaxResults());


        List queryResult = query.getResultList();
        return queryResult;
    }

    @Transactional
    @Override
    public List<Object[]> getBoss(User user,int level) {
        EntityManager em = persistence.getEntityManager();
        Query query = em.createNativeQuery(
                "SELECT \n" +
                        "    name, login, id \n" +
                        "FROM \n" +
                        "    sec_user \n" +
                        "WHERE \n" +
                        "    group_id IN (SELECT \n" +
                        "            parent_id \n" +
                        "        FROM \n" +
                        "            sec_group_hierarchy\n" +
                        "        WHERE\n" +
                        "            hierarchy_level = #level \n" +
                        "                AND group_id IN (SELECT \n" +
                        "                    group_id \n" +
                        "                FROM \n" +
                        "                    sec_user \n" +
                        "                WHERE \n" +
                        "                    id = #userId))"
        );
        query.setParameter("userId",user.getId().toString().replace("-",""));
        query.setParameter("level",level);

        List queryResult = query.getResultList();
        return queryResult;
    }
}