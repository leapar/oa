package com.purvar.ito.oa.web.record;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.data.impl.CustomCollectionDatasource;
import com.haulmont.cuba.gui.data.impl.CustomGroupDatasource;
import com.purvar.ito.oa.entity.Record;
import com.purvar.ito.oa.service.RecordService;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Created by wangxiaohua on 2017/7/13.
 */

//CustomCollectionDatasource<Record, UUID>
public class RecordDatasource extends CustomGroupDatasource<Record, UUID> {
    private RecordService recordService =  AppBeans.get(RecordService.NAME);


    @Override
    protected Collection<Record> getEntities(Map<String, Object> params) {
        LoadContext context = beforeLoadData(params);
        return recordService.getEntities(context);
    }
}
