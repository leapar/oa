package com.purvar.ito.oa.gui.xml.layout.loaders;

import com.purvar.ito.oa.gui.components.BaiduMap;
import com.haulmont.cuba.gui.xml.layout.loaders.AbstractComponentLoader;

public class BaiduMapLoader extends AbstractComponentLoader<BaiduMap> {
    @Override
    public void createComponent() {
        resultComponent = factory.createComponent(BaiduMap.class);
        loadId(resultComponent, element);
    }

    @Override
    public void loadComponent() {
        assignXmlDescriptor(resultComponent, element);


        boolean enabled = loadEnable(resultComponent, element);
        boolean visible = loadVisible(resultComponent, element);

        loadStyleName(resultComponent, element);



        loadWidth(resultComponent, element);
        loadHeight(resultComponent, element);
        loadAlign(resultComponent, element);

        String zoom = element.attributeValue("zoom", "18");
        if (zoom != null) {
            resultComponent.setZoom(Integer.valueOf(zoom));
        }
        String lat = element.attributeValue("lat");
        if (lat != null) {
            resultComponent.setLat(Double.valueOf(lat));
        }
        String lng = element.attributeValue("lng");
        if (lng != null) {
            resultComponent.setLng(Double.valueOf(lng));
        }


        String justShow = element.attributeValue("just_show","true");
        if (lng != null) {
            resultComponent.setJustShow(Boolean.valueOf(justShow));
        }

        String zoomCtrl = element.attributeValue("zoom_ctrl","true");
        if (lng != null) {
            resultComponent.setShowZoomCtrl(Boolean.valueOf(zoomCtrl));
        }


    }
}
