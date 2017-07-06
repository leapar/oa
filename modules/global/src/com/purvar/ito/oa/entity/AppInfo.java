package com.purvar.ito.oa.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|version")
@Table(name = "OA_APP_INFO")
@Entity(name = "oa$AppInfo")
public class AppInfo extends StandardEntity {
    private static final long serialVersionUID = -7058959810625120067L;

    @Column(name = "ANDROID", length = 512)
    protected String android;

    @Column(name = "VER")
    protected String ver;

    @Column(name = "IOS", length = 512)
    protected String ios;

    public void setIos(String ios) {
        this.ios = ios;
    }

    public String getIos() {
        return ios;
    }


    public void setAndroid(String android) {
        this.android = android;
    }

    public String getAndroid() {
        return android;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getVer() {
        return ver;
    }


}