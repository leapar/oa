package com.purvar.ito.oa.core;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.purvar.ito.oa.entity.EnumBpmFlag;
import com.purvar.ito.oa.entity.Record;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.UUID;

/**
 * Created by wangxh on 2017/7/28.
 */
@Component("oa_RecordBpmHelper")
public class RecordBpmHelper {

    @Inject
    private Persistence persistence;

    public void reject(UUID entityId) {
        try (Transaction tx = persistence.getTransaction()) {
            Record record = persistence.getEntityManager().find(Record.class, entityId);
            if (record != null) {
                record.setBpm_flag(EnumBpmFlag.REJECT);
            }
            tx.commit();
        }
    }

    public void confirm(UUID entityId) {
        try (Transaction tx = persistence.getTransaction()) {
            Record record = persistence.getEntityManager().find(Record.class, entityId);
            if (record != null) {
                record.setBpm_flag(EnumBpmFlag.OK);
            }
            tx.commit();
        }
    }

}