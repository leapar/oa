package com.purvar.ito.oa.web.record;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Embedded;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.export.FileDataProvider;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.purvar.ito.oa.entity.Record;

import javax.inject.Inject;

public class RecordBrowse extends AbstractLookup {

    @Inject
    ComponentsFactory componentsFactory;

    public Component generateUserCell(Record entity) {
        Label userName = componentsFactory.createComponent(Label.class);
        userName.setValue(entity.getUser().getName());
        return userName;
    }

    public Component generateFileCell(Record entity) {


        Embedded embedded = componentsFactory.createComponent(Embedded.class);

        embedded.setType(Embedded.Type.IMAGE);
        embedded.setWidth("80px");
        //embedded.setHeight("40px");

        FileDescriptor userImageFile = entity.getFile();
        if(userImageFile != null) {

            FileDataProvider dataProvider = new FileDataProvider(userImageFile);
            embedded.setSource(userImageFile.getId() + "." + userImageFile.getExtension(), dataProvider);
        }

        return embedded;
    }

    
}