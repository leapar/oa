package com.purvar.ito.oa.web.record;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.FieldGroup;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.cuba.web.gui.components.WebTextField;
import com.purvar.ito.oa.entity.EnumBpmFlag;
import com.purvar.ito.oa.entity.Record;
import javax.inject.Inject;
import javax.jms.Session;

import com.haulmont.cuba.gui.components.Table;
import com.haulmont.bpm.entity.ProcAttachment;
import com.haulmont.bpm.gui.procactions.ProcActionsFrame;
import com.purvar.ito.oa.gui.components.BaiduMap;
import com.purvar.ito.oa.web.gui.components.WebBaiduMap;
import com.purvar.ito.oa.web.toolkit.ui.baidumapview.BaiduMapView;
import org.apache.poi.ss.formula.functions.T;

public class RecordBpmEdit extends AbstractEditor<Record> {
    private static final String PROCESS_CODE = "recordBpm";
    @Inject
    private ProcActionsFrame procActionsFrame;
    @Inject
    private WebBaiduMap baiduMap;
    @Inject
    private FieldGroup fieldGroup;
    WebTextField districtField;
    WebTextField provinceField;
    WebTextField addressField;
    WebTextField latField;
    WebTextField lngField;
    @Inject
    private UserSession userSession;
    @Override
    protected void initNewItem(Record item) {
        super.initNewItem(item);
        baiduMap.setJustShow(false);
        fieldGroup.getField("bpm_date").setEditable(true);
        item.setUser(userSession.getUser());
        item.setBpm_flag(EnumBpmFlag.START);
    }

    @Override
    public void setItem(Entity item) {
        if (!PersistenceHelper.isNew(item)) {
            baiduMap.setLat(((Record)item).getLat());
            baiduMap.setLng(((Record)item).getLng());
        }
        super.setItem(item);
    }
    @Override
    protected boolean preCommit() {
        procActionsFrame.getProcInstance().setEntityEditorName("oa$Record.bpm.edit");

        return super.preCommit();
    }

    @Override
    protected void postInit() {
        initProcActionsFrame();
        districtField = (WebTextField) fieldGroup.getField("district").getComponent();
        provinceField = (WebTextField) fieldGroup.getField("province").getComponent();
        addressField = (WebTextField) fieldGroup.getField("address").getComponent();
        latField = (WebTextField) fieldGroup.getField("lat").getComponent();
        lngField = (WebTextField) fieldGroup.getField("lng").getComponent();
    }

    @Override
    public void ready() {
        super.ready();
        BaiduMapView map = (BaiduMapView) baiduMap.getComponent();
        map.setListener(state -> {

            districtField.setValue(state.district);
            provinceField.setValue(state.province);
            addressField.setValue(state.address);
            latField.setValue(state.lat);
            lngField.setValue(state.lng);

          //  this.getItem().setLat(state.lat);
          //  this.getItem().setLng(state.lng);
            //cp = fieldGroup.getField("province").getComponent();
        });
    }


    private void initProcActionsFrame() {

        procActionsFrame.initializer()
                .setBeforeStartProcessPredicate(this::commit)
                .setAfterStartProcessListener(() -> {

                    showNotification(getMessage("processStarted"), NotificationType.HUMANIZED);
                    close(COMMIT_ACTION_ID);
                })
                .setBeforeCompleteTaskPredicate(this::commit)
                .setAfterCompleteTaskListener(() -> {
                    showNotification(getMessage("taskCompleted"), NotificationType.HUMANIZED);
                    close(COMMIT_ACTION_ID);
                })
                .init(PROCESS_CODE, getItem());
    }
}