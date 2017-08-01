package com.purvar.ito.oa.web.gui.components;

import com.purvar.ito.oa.gui.components.BaiduMap;
import com.haulmont.cuba.web.gui.components.WebAbstractComponent;

public class WebBaiduMap extends WebAbstractComponent<com.purvar.ito.oa.web.toolkit.ui.baidumapview.BaiduMapView> implements BaiduMap {
    public WebBaiduMap() {
        this.component = new com.purvar.ito.oa.web.toolkit.ui.baidumapview.BaiduMapView();
    }

    @Override
    public void setLat(Double lat) {
        this.component.setLat(lat);
    }

    @Override
    public void setLng(Double lng) {
        this.component.setLng(lng);
    }

    @Override
    public void setZoom(Integer zoom) {
        this.component.setZoom(zoom);
    }

    @Override
    public Double getLat() {
        return this.component.getLat();
    }

    @Override
    public Double getLng() {
        return this.component.getLng();
    }

    @Override
    public Integer getZoom() {
        return this.component.getZoom();
    }

    @Override
    public boolean isJustShow() {
        return this.component.isJustShow();
    }

    @Override
    public void setJustShow(boolean justShow) {
        this.component.setJustShow(justShow);
    }

    @Override
    public boolean isShowZoomCtrl() {
        return this.component.isShowZoomCtrl();
    }

    @Override
    public void setShowZoomCtrl(boolean showZoomCtrl) {
        this.component.setShowZoomCtrl(showZoomCtrl);
    }
}