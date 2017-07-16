package com.purvar.ito.oa.web.record;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileLoader;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.export.FileDataProvider;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.web.app.loginwindow.AppLoginWindow;
import com.purvar.ito.oa.entity.Record;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class RecordBrowse extends AbstractLookup {

    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private FileLoader fileLoader;
    @Inject
    private GroupTable<Record> recordsTable;
    @Inject
    private GroupDatasource<Record, UUID> recordsDs;

    @Override
    public void init(Map<String, Object> params) {
        /*recordsTable.addGeneratedColumn("colour", new Table.ColumnGenerator() {
            @Override
            public Component generateCell(Entity entity) {
                Label userName = componentsFactory.createComponent(Label.class);
                userName.setValue(((Record)entity).getUser().getName());
                return userName;
                /*LookupPickerField field = (LookupPickerField) componentsFactory.createComponent(LookupPickerField.NAME);
                field.setDatasource(recordsTable.getItemDatasource(entity), "file.name");
                field.setOptionsDatasource(recordsDs);
                field.addLookupAction();
                field.addOpenAction();
                return field;//
            }
        });*/
    }

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

        FileDescriptor userImageFile = entity.getThumb();

        try {
            if(userImageFile != null && fileLoader.fileExists(userImageFile)) {
                FileDataProvider dataProvider = new FileDataProvider(userImageFile);
                embedded.setSource(userImageFile.getId() + "." + userImageFile.getExtension(), dataProvider);

            } else {
                embedded.setSource("theme://images/default-img.png");
            }
        } catch (FileStorageException e) {
            embedded.setSource("theme://images/default-img.png");
        }

        return embedded;
    }

    
}