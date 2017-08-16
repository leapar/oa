package com.purvar.ito.oa.portal.controllers;

import com.purvar.ito.oa.entity.Record;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangxiaohua on 2017/8/11.
 */
@Controller
@RequestMapping("/api/brands")
public class ApiController {

    @RequestMapping(value="{name}", method = RequestMethod.GET)
    @ResponseBody
    public Record getShopInJSON(@PathVariable String name) {
        Record shop = new Record();
       // shop.setUser(userSession.);
       // shop.setStaffName(new String[]{"mkyong1", "mkyong2"});
        return shop;
    }
}
