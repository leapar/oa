package com.purvar.ito.oa.web.extuser;

import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.purvar.ito.oa.entity.ExtUser;

import javax.inject.Inject;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class ExtUserBrowse2 extends AbstractLookup {

    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private GroupTable<ExtUser> extUsersTable;
    @Inject
    private DataService dataService;
    @Inject
    private GroupDatasource<ExtUser, UUID> extUsersDs;

    public Component generateActionCell(ExtUser entity) {

         Button btnUnlock = componentsFactory.createComponent(Button.class);

        btnUnlock.setCaption(messages.getMessage(ExtUserBrowse2.class,"unlock"));
        btnUnlock.setAction(new AbstractAction("unlock") {
            @Override
            public void actionPerform(Component component) {
                showNotification("Your logic here!");
                entity.setDevice_no("");
                CommitContext ctx = new CommitContext(Collections.singletonList(entity));
                dataService.commit(ctx);
                extUsersDs.refresh();
            }
        });

		return btnUnlock;
    }
}