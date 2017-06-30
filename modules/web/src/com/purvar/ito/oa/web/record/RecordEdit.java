package com.purvar.ito.oa.web.record;

import com.haulmont.chile.core.model.Session;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.Embedded;
import com.haulmont.cuba.gui.components.FileUploadField;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.export.FileDataProvider;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.haulmont.cuba.security.global.UserSession;
import com.purvar.ito.oa.entity.Record;

import javax.inject.Inject;
import java.util.Map;

public class RecordEdit extends AbstractEditor<Record> {

    @Inject
    private FileUploadField upload;
    @Inject
    private DataSupplier dataSupplier;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private UserSession userSession;
    @Inject
    private Embedded embedded;


    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        

        /*upload.addFileUploadSucceedListener(event -> {
            FileDescriptor fd = upload.getFileDescriptor();
            try {
                // save file to FileStorage
                fileUploadingAPI.putFileIntoStorage(upload.getFileId(), fd);
            } catch (FileStorageException e) {
                throw new RuntimeException("Error saving file to FileStorage", e);
            }
            // save file descriptor to database
            getItem().setFile(dataSupplier.commit(fd));
            getItem().setUser(userSession.getUser());
            showNotification("Uploaded file: " + upload.getFileName(), NotificationType.HUMANIZED);
        });

        upload.addFileUploadErrorListener(event ->
                showNotification("File upload error", NotificationType.HUMANIZED));*/



        
    }

    @Override
    protected void postInit() {
        FileDescriptor userImageFile = getItem().getFile();
        if(userImageFile != null) {

            FileDataProvider dataProvider = new FileDataProvider(userImageFile);
            embedded.setSource(userImageFile.getId() + "." + userImageFile.getExtension(), dataProvider);
        }
    }

    public void onButtonClick() {
        this.close(Window.CLOSE_ACTION_ID);
    }
}