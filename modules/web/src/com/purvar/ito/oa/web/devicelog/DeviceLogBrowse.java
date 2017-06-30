package com.purvar.ito.oa.web.devicelog;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.purvar.ito.oa.entity.DeviceLog;

import javax.inject.Inject;

public class DeviceLogBrowse extends AbstractLookup {

    @Inject
    private ComponentsFactory componentsFactory;

    public Component generateUserCell(DeviceLog entity) {
        Label userName = componentsFactory.createComponent(Label.class);
        userName.setValue(entity.getUser().getName());
        return userName;
    }
}