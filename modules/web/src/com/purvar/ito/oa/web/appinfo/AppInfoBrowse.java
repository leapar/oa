package com.purvar.ito.oa.web.appinfo;

import com.purvar.ito.oa.entity.AppInfo;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.components.actions.RemoveAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.security.entity.EntityOp;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class AppInfoBrowse extends AbstractLookup {

    /**
     * The {@link CollectionDatasource} instance that loads a list of {@link AppInfo} records
     * to be displayed in {@link AppInfoBrowse#appInfoesTable} on the left
     */
    @Inject
    private CollectionDatasource<AppInfo, UUID> appInfoesDs;

    /**
     * The {@link Datasource} instance that contains an instance of the selected entity
     * in {@link AppInfoBrowse#appInfoesDs}
     * <p/> Containing instance is loaded in {@link CollectionDatasource#addItemChangeListener}
     * with the view, specified in the XML screen descriptor.
     * The listener is set in the {@link AppInfoBrowse#init(Map)} method
     */
    @Inject
    private Datasource<AppInfo> appInfoDs;

    /**
     * The {@link Table} instance, containing a list of {@link AppInfo} records,
     * loaded via {@link AppInfoBrowse#appInfoesDs}
     */
    @Inject
    private Table<AppInfo> appInfoesTable;

    /**
     * The {@link BoxLayout} instance that contains components on the left side
     * of {@link SplitPanel}
     */
    @Inject
    private BoxLayout lookupBox;

    /**
     * The {@link BoxLayout} instance that contains buttons to invoke Save or Cancel actions in edit mode
     */
    @Inject
    private BoxLayout actionsPane;

    /**
     * The {@link FieldGroup} instance that is linked to {@link AppInfoBrowse#appInfoDs}
     * and shows fields of the selected {@link AppInfo} record
     */
    @Inject
    private FieldGroup fieldGroup;

    /**
     * The {@link RemoveAction} instance, related to {@link AppInfoBrowse#appInfoesTable}
     */
    @Named("appInfoesTable.remove")
    private RemoveAction appInfoesTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    /**
     * {@link Boolean} value, indicating if a new instance of {@link AppInfo} is being created
     */
    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {

        /*
         * Adding {@link com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link appInfoesDs}
         * The listener reloads the selected record with the specified view and sets it to {@link appInfoDs}
         */
        appInfoesDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                AppInfo reloadedItem = dataSupplier.reload(e.getDs().getItem(), appInfoDs.getView());
                appInfoDs.setItem(reloadedItem);
            }
        });

        /*
         * Adding {@link CreateAction} to {@link appInfoesTable}
         * The listener removes selection in {@link appInfoesTable}, sets a newly created item to {@link appInfoDs}
         * and enables controls for record editing
         */
        appInfoesTable.addAction(new CreateAction(appInfoesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                appInfoesTable.setSelected(Collections.emptyList());
                appInfoDs.setItem((AppInfo) newItem);
                refreshOptionsForLookupFields();
                enableEditControls(true);
            }
        });

        /*
         * Adding {@link EditAction} to {@link appInfoesTable}
         * The listener enables controls for record editing
         */
        appInfoesTable.addAction(new EditAction(appInfoesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (appInfoesTable.getSelected().size() == 1) {
                    refreshOptionsForLookupFields();
                    enableEditControls(false);
                }
            }

            @Override
            public void refreshState() {
                if (target != null) {
                    CollectionDatasource ds = target.getDatasource();
                    if (ds != null && !captionInitialized) {
                        setCaption(messages.getMainMessage("actions.Edit"));
                    }
                }
                super.refreshState();
            }

            @Override
            protected boolean isPermitted() {
                CollectionDatasource ownerDatasource = target.getDatasource();
                boolean entityOpPermitted = security.isEntityOpPermitted(ownerDatasource.getMetaClass(), EntityOp.UPDATE);
                if (!entityOpPermitted) {
                    return false;
                }
                return super.isPermitted();
            }
        });

        /*
         * Setting {@link RemoveAction#afterRemoveHandler} for {@link appInfoesTableRemove}
         * to reset record, contained in {@link appInfoDs}
         */
        appInfoesTableRemove.setAfterRemoveHandler(removedItems -> appInfoDs.setItem(null));

        disableEditControls();
    }

    private void refreshOptionsForLookupFields() {
        for (Component component : fieldGroup.getOwnComponents()) {
            if (component instanceof LookupField) {
                CollectionDatasource optionsDatasource = ((LookupField) component).getOptionsDatasource();
                if (optionsDatasource != null) {
                    optionsDatasource.refresh();
                }
            }
        }
    }

    /**
     * Method that is invoked by clicking Ok button after editing an existing or creating a new record
     */
    public void save() {
        if (!validate(Collections.singletonList(fieldGroup))) {
            return;
        }
        getDsContext().commit();

        AppInfo editedItem = appInfoDs.getItem();
        if (creating) {
            appInfoesDs.includeItem(editedItem);
        } else {
            appInfoesDs.updateItem(editedItem);
        }
        appInfoesTable.setSelected(editedItem);

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Cancel button, discards changes and disables controls for record editing
     */
    public void cancel() {
        AppInfo selectedItem = appInfoesDs.getItem();
        if (selectedItem != null) {
            AppInfo reloadedItem = dataSupplier.reload(selectedItem, appInfoDs.getView());
            appInfoesDs.setItem(reloadedItem);
        } else {
            appInfoDs.setItem(null);
        }

        disableEditControls();
    }

    /**
     * Enabling controls for record editing
     * @param creating indicates if a new instance of {@link AppInfo} is being created
     */
    private void enableEditControls(boolean creating) {
        this.creating = creating;
        initEditComponents(true);
        fieldGroup.requestFocus();
    }

    /**
     * Disabling editing controls
     */
    private void disableEditControls() {
        initEditComponents(false);
        appInfoesTable.requestFocus();
    }

    /**
     * Initiating edit controls, depending on if they should be enabled/disabled
     * @param enabled if true - enables editing controls and disables controls on the left side of the splitter
     *                if false - visa versa
     */
    private void initEditComponents(boolean enabled) {
        fieldGroup.setEditable(enabled);
        actionsPane.setVisible(enabled);
        lookupBox.setEnabled(!enabled);
    }
}