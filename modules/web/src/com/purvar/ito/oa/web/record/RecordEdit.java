package com.purvar.ito.oa.web.record;

import com.haulmont.chile.core.datatypes.Datatypes;
import com.haulmont.chile.core.model.Session;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileLoader;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.AppConfig;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.export.FileDataProvider;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.security.global.UserSession;
import com.purvar.ito.oa.entity.Record;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import com.haulmont.cuba.gui.data.Datasource;
import com.purvar.ito.oa.gui.components.BaiduMap;

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
    @Inject
    private FileLoader fileLoader;
    @Inject
    private ComponentsFactory componentsFactory;

    private TextField txtLatLng;
    @Inject
    private SplitPanel split;
    @Inject
    private BaiduMap baiduMap;

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

        txtLatLng = componentsFactory.createComponent(TextField.class);


        FieldGroup fields = (FieldGroup) getComponent("fieldGroup");
        FieldGroup.FieldConfig field = fields.getField("latlng");
        field.setComponent(txtLatLng);

    }

    @Override
    public void commitAndClose() {
        getItem().setUser(userSession.getUser());
        super.commitAndClose();
    }

    @Override
    public void ready() {
        super.ready();

        baiduMap.setLat(getItem().getLat());
        baiduMap.setLng(getItem().getLng());
        // src="url://https://doc.cuba-platform.com/manual-latest/index.html"
        /*
        <embedded id="map"
            height="600px"
            src="url://https://doc.cuba-platform.com/manual-latest/index.html"
            type="BROWSER"
            width="100%"/>
        Double lat = getItem().getLat();
        Double lng = getItem().getLng();
        String coor = String.format("%f,%f",lng,lat);

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
                "    height:100%;\n" +
                "    margin:0;\n" +
                "    overflow:hidden;  \n" +
                "    }\n" +
                "</style>  \n" +
                "</head>  \n" +
                "<body>  \n" +
                "    <div style=\"width:100%;height:100%;border:1px solid gray\" id=\"container\"</div>  \n" +
                "</body>  \n" +
                "</html>  \n" +
                "<script type=\"text/javascript\">  \n" +
                "    var map = new BMap.Map(\"container\");  \n" +
                "    map.centerAndZoom(new BMap.Point("+coor+"), 19);  \n" +
                "    map.enableScrollWheelZoom();  \n" +
                "    var marker=new BMap.Marker(new BMap.Point("+coor+"));  \n" +
                "    map.addOverlay(marker);    " +
                "   // 添加带有定位的导航控件\n" +
                "  var navigationControl = new BMap.NavigationControl({\n" +
                "    // 靠左上角位置\n" +
                "    anchor: BMAP_ANCHOR_TOP_LEFT,\n" +
                "    // LARGE类型\n" +
                "    type: BMAP_NAVIGATION_CONTROL_LARGE,\n" +
                "    // 启用显示定位\n" +
                "    enableGeolocation: true\n" +
                "  });\n" +
                "  map.addControl(navigationControl);</script>";
        String browserCacheVersion = UUID.randomUUID().toString();


        map.setSource("report" + browserCacheVersion + ".htm",
                new ByteArrayInputStream(htmlData.getBytes(StandardCharsets.UTF_8)));
         */
    }

    @Override
    protected void postInit() {
        split.setSplitPosition(140,Component.UNITS_PIXELS);
        txtLatLng.setEditable(false);
        txtLatLng.setValue(getItem().getLat()+","+getItem().getLng());
        FileDescriptor userImageFile = getItem().getFile();
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

    }

    public void onButtonClick() {
        this.close(Editor.WINDOW_CLOSE);
    }

}