package com.purvar.ito.oa.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "OA_USER_TYPE")
@Entity(name = "oa$UserType")
public class UserType extends StandardEntity {
    private static final long serialVersionUID = -5325543320145642766L;

    @Column(name = "NAME")
    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}