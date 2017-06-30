package com.purvar.ito.oa.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.security.entity.User;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s %s|device_no,user")
@Table(name = "OA_DEVICE_LOG")
@Entity(name = "oa$DeviceLog")
public class DeviceLog extends StandardEntity {
    private static final long serialVersionUID = -2922626295733120131L;

    @Column(name = "DEVICE_NO")
    protected String device_no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    protected User user;

    public void setDevice_no(String device_no) {
        this.device_no = device_no;
    }

    public String getDevice_no() {
        return device_no;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }


}