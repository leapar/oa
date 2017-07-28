package com.purvar.ito.oa.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum EnumBpmFlag implements EnumClass<Integer> {

    NONE(0),
    START(1),
    REJECT(-1),
    OK(2);

    private Integer id;

    EnumBpmFlag(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static EnumBpmFlag fromId(Integer id) {
        for (EnumBpmFlag at : EnumBpmFlag.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}