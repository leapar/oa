package com.purvar.ito.oa.service;


import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.security.entity.User;
import com.purvar.ito.oa.entity.ExtUser;

import java.util.List;

public interface UserService {
    String NAME = "oa_UserService";

    public List<Object[]> getEntities(LoadContext context);

    public List<Object[]> getBoss(User user,int level);
}