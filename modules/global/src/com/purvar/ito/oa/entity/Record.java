package com.purvar.ito.oa.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.security.entity.User;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.validation.constraints.NotNull;

@NamePattern("%s %s|user,address")
@Table(name = "OA_RECORD")
@Entity(name = "oa$Record")
public class Record extends StandardEntity {
    private static final long serialVersionUID = -2851719732678531126L;

    @Column(name = "LAT")
    protected Double lat;

    @Column(name = "LNG")
    protected Double lng;

    @Column(name = "DISTRICT", length = 512)
    protected String district;

    @Column(name = "PROVINCE")
    protected String province;

    @Column(name = "ADDRESS", length = 1024)
    protected String address;

    @Column(name = "IP", length = 32)
    protected String ip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID")
    protected FileDescriptor file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    protected User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "THUMB_ID")
    protected FileDescriptor thumb;

    @Column(name = "BPM_FLAG")
    protected Integer bpm_flag;

    public void setBpm_flag(EnumBpmFlag bpm_flag) {
        this.bpm_flag = bpm_flag == null ? null : bpm_flag.getId();
    }

    public EnumBpmFlag getBpm_flag() {
        return bpm_flag == null ? null : EnumBpmFlag.fromId(bpm_flag);
    }


    public void setThumb(FileDescriptor thumb) {
        this.thumb = thumb;
    }

    public FileDescriptor getThumb() {
        return thumb;
    }


    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLat() {
        return lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLng() {
        return lng;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict() {
        return district;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return province;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setFile(FileDescriptor file) {
        this.file = file;
    }

    public FileDescriptor getFile() {
        return file;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }


}