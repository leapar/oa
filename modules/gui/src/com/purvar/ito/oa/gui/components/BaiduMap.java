package com.purvar.ito.oa.gui.components;

import com.haulmont.cuba.gui.components.Component;

public interface BaiduMap extends Component {
    String NAME = "baiduMap";

    void setLat(Double lat);
    void setLng(Double lng);
    void setZoom(Integer zoom);

    Double getLat();
    Double getLng();
    Integer getZoom();


    boolean isJustShow() ;
    void setJustShow(boolean justShow);
    boolean isShowZoomCtrl();
    void setShowZoomCtrl(boolean showZoomCtrl);
}