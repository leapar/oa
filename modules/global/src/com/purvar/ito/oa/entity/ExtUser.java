package com.purvar.ito.oa.entity;

import javax.persistence.Entity;
import com.haulmont.cuba.core.entity.annotation.Extends;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.chile.core.annotations.NamePattern;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.text.MessageFormat;

//@NamePattern("%s|name")
@NamePattern("#getCaption|login,name,area,company")
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

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    protected Company company;

    @ManyToOne
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

    public String getCaption() {
        String pattern = AppContext.getProperty("cuba.user.namePattern");
        if (StringUtils.isBlank(pattern)) {
            pattern = "{2} [{1}-{0}]";
        }



        MessageFormat fmt = new MessageFormat(pattern);
        return StringUtils.trimToEmpty(fmt.format(new Object[]{
                StringUtils.trimToEmpty(area == null ? "" :area.getName()),
                StringUtils.trimToEmpty(company == null ? "" : company.getName()),
                StringUtils.trimToEmpty(name)
        }));
    }
}