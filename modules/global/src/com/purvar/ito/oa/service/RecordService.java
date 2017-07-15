package com.purvar.ito.oa.service;


import com.haulmont.cuba.core.global.LoadContext;
import com.purvar.ito.oa.entity.Record;

import java.util.List;

public interface RecordService {
    String NAME = "oa_RecordService";


    List<Record> getEntities(LoadContext context);
}