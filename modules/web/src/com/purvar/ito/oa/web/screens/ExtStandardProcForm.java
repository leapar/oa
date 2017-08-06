package com.purvar.ito.oa.web.screens;

import com.haulmont.bpm.gui.form.standard.StandardProcForm;

import java.util.Map;

public class ExtStandardProcForm extends StandardProcForm {

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

       // ((ExtProcActorsFrame)procActorsFrame).addProcActor();
    }
}