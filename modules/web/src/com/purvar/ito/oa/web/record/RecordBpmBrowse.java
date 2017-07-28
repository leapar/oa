package com.purvar.ito.oa.web.record;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.purvar.ito.oa.entity.Record;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class RecordBpmBrowse extends AbstractLookup {

    @Inject
    private Metadata metadata;
    @Inject
    private GroupDatasource<Record, UUID> recordsDs;
    @Inject
    private GroupTable<Record> recordsTable;


    public void create(Component source) {
        AbstractEditor editor = this.openEditor("oa$Record.bpm.edit", metadata.create(Record.class), WindowManager.OpenType.THIS_TAB);
        editor.addCloseWithCommitListener(() -> {
            recordsDs.refresh();
        });
    }

    public void onBpmEdit(Component source) {
        Set<Record> selected = recordsTable.getSelected();
        if (!selected.isEmpty()) {
            Record record = selected.iterator().next();
            Editor editor = openEditor("oa$Record.bpm.edit", record, WindowManager.OpenType.THIS_TAB);
            /*editor.addCloseListener(actionId -> {
                recordsDs.refresh();
                recordsTable.requestFocus();
            });*/
            editor.addCloseWithCommitListener(() -> {
                recordsDs.refresh();
                recordsTable.requestFocus();
            });
        }

    }
}