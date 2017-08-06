package com.purvar.ito.oa.web.screens;

import com.haulmont.bpm.entity.ProcActor;
import com.haulmont.bpm.entity.ProcRole;
import com.haulmont.bpm.gui.procactor.ProcActorsFrame;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.UuidProvider;
import com.haulmont.cuba.security.global.UserSession;
import com.purvar.ito.oa.entity.BossUserDs;
import com.purvar.ito.oa.entity.ExtUser;
import com.purvar.ito.oa.service.UserService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtProcActorsFrame extends ProcActorsFrame {

    @Inject
    private BossUserDs extUsersDs;
    @Inject
    private UserService userService;
    @Inject
    private UserSession userSession;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        initProcRoles();
    }

    protected void initProcRoles() {
        int level = 2;
        for(ProcRole role:procRolesDs.getItems()){
            addActor(role,level);
            level--;
         }
    }

    protected void addActor(ProcRole procRole,int level) {

        List<Object[]> list = userService.getBoss(userSession.getUser(),level);

        Object[] items = (Object[]) list.get(0);
        String name = (String) items[0];
        ExtUser user = new ExtUser();
        user.setName(name);
        user.setId(UuidProvider.fromString((String) items[2]) );
        user.setLogin((String) items[1]);

        ProcActor procActor = metadata.create(ProcActor.class);
        procActor.setProcRole(procRole);
        procActor.setProcInstance(procInstance);
        procActor.setOrder(getLastOrder(procRole) + 1);

        procActor.setUser(user);
        procActorsDs.addItem(procActor);
    }

}