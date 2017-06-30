package com.purvar.ito.oa.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "OA_USER_STATUS")
@Entity(name = "oa$UserStatus")
public class UserStatus extends StandardEntity {
    private static final long serialVersionUID = 6120942707999110562L;

    @Column(name = "NAME")
    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}