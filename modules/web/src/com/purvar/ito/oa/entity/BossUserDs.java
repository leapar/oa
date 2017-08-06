package com.purvar.ito.oa.entity;

import com.haulmont.charts.gui.data.MapDataItem;
import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.UuidProvider;
import com.purvar.ito.oa.entity.ExtUser;
import com.haulmont.cuba.gui.data.impl.CustomCollectionDatasource;
import com.purvar.ito.oa.service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class BossUserDs extends CustomCollectionDatasource<ExtUser, UUID> {
    private UserService userService =  AppBeans.get(UserService.NAME);


    @Override
    protected Collection<ExtUser> getEntities(Map<String, Object> params) {
        LoadContext context = beforeLoadData(params);

        List<Object[]> list = userService.getEntities(context);

        List<ExtUser> users =  new ArrayList<ExtUser>();

        for (int i = 0; i < list.size();i++) {
            Object[] items = (Object[]) list.get(i);
            String name = (String) items[0];
            ExtUser user = new ExtUser();
            user.setName(name);
            user.setId(UuidProvider.fromString((String) items[2]) );
            user.setLogin((String) items[1]);
            users.add(user);
        }
        return users;
    }
}