package com.purvar.ito.oa.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import java.util.Date;
import com.haulmont.cuba.core.entity.AbstractNotPersistentEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s %s|date,value")
@MetaClass(name = "oa$RecordGroup")
public class RecordGroup extends AbstractNotPersistentEntity {
    private static final long serialVersionUID = 8067187333567170391L;

    @MetaProperty
    protected Long value;

    @MetaProperty
    protected Date date;

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }


}