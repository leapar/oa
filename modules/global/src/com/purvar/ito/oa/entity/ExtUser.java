package com.purvar.ito.oa.entity;

import javax.persistence.Entity;
import com.haulmont.cuba.core.entity.annotation.Extends;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NamePattern("%s %s %s|company,area,name")
@Extends(User.class)
@Entity(name = "oa$ExtUser")
public class ExtUser extends User {
    private static final long serialVersionUID = 7187382363470956579L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_ID")
    protected UserType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_ID")
    protected UserStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    protected Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AREA_ID")
    protected Area area;

    @Column(name = "DEVICE_NO")
    protected String device_no;

    @Column(name = "API_TOKEN")
    protected String api_token;


    public void setType(UserType type) {
        this.type = type;
    }

    public UserType getType() {
        return type;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Area getArea() {
        return area;
    }

    public void setDevice_no(String device_no) {
        this.device_no = device_no;
    }

    public String getDevice_no() {
        return device_no;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getApi_token() {
        return api_token;
    }


}