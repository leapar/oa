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
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

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
    @Inject
    private Embedded map;


    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        

        upload.addFileUploadSucceedListener(event -> {
            FileDescriptor fd = upload.getFileDescriptor();
            try {
                // save file to FileStorage
                fileUploadingAPI.putFileIntoStorage(upload.getFileId(), fd);
            } catch (FileStorageException e) {
                throw new RuntimeException("Error saving file to FileStorage", e);
            }
            // save file descriptor to database
            FileDescriptor committedFd = dataSupplier.commit(fd);
            getItem().setFile(committedFd);
            getItem().setUser(userSession.getUser());

            FileDataProvider dataProvider = new FileDataProvider(committedFd);
            embedded.setSource(committedFd.getId() + "." + committedFd.getExtension(), dataProvider);
            showNotification("Uploaded file: " + upload.getFileName(), NotificationType.HUMANIZED);
        });

        upload.addFileUploadErrorListener(event ->
                showNotification("File upload error", NotificationType.HUMANIZED));



        
    }

    @Override
    public void commitAndClose() {
        getItem().setUser(userSession.getUser());
        super.commitAndClose();
    }

    @Override
    public void ready() {
        super.ready();
        // src="url://https://doc.cuba-platform.com/manual-latest/index.html"
        String htmlData = "<html>  \n" +
                "<head>  \n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />  \n" +
                "<title>JSAPI与URLAPI结合示例</title>  \n" +
                "<script type=\"text/javascript\" src=\"http://api.map.baidu.com/getscript?v=1.5&ak=ev8KAaHPxMsFZEpGv0pTWz4D7KFUVLSq&services=&t=20150522094656\"></script>" +
                "<!--script type=\"text/javascript\" src=\"http://api.map.baidu.com/api?v=1.5&ak=ev8KAaHPxMsFZEpGv0pTWz4D7KFUVLSq\"></script-->  \n" +
                "<script src=\"http://d1.lashouimg.com/static/js/release/jquery-1.4.2.min.js\" type=\"text/javascript\"></script>  \n" +
                "<style type=\"text/css\">  \n" +
                "html,body{  \n" +
                "    width:100%;  \n" +
                "    height:400px;\n" +
                "    margin:0;\n" +
                "    overflow:hidden;  \n" +
                "    }\n" +
                "</style>  \n" +
                "</head>  \n" +
                "<body>  \n" +
                "    <div style=\"width:100%;height:400px;border:1px solid gray\" id=\"container\"</div>  \n" +
                "</body>  \n" +
                "</html>  \n" +
                "<script type=\"text/javascript\">  \n" +
                "    var map = new BMap.Map(\"container\");  \n" +
                "    map.centerAndZoom(new BMap.Point(116.403884,39.914887), 13);  \n" +
                "    map.enableScrollWheelZoom();  \n" +
                "    var marker=new BMap.Marker(new BMap.Point(116.403884,39.914887));  \n" +
                "    map.addOverlay(marker);  </script>";
        String browserCacheVersion = UUID.randomUUID().toString();


        map.setSource("report" + browserCacheVersion + ".htm",
                new ByteArrayInputStream(htmlData.getBytes(StandardCharsets.UTF_8)));
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