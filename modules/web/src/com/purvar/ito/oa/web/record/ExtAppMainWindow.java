package com.purvar.ito.oa.web.record;

import com.haulmont.cuba.gui.components.AbstractMainWindow;
import com.haulmont.cuba.gui.components.Embedded;
import com.haulmont.cuba.gui.components.mainwindow.FtsField;
import com.haulmont.cuba.gui.components.mainwindow.SideMenu;
import com.haulmont.charts.gui.amcharts.model.charts.xyChart;
import com.haulmont.charts.gui.amcharts.model.data.DataItem;
import com.haulmont.charts.gui.amcharts.model.data.ListDataProvider;
import com.haulmont.charts.gui.amcharts.model.data.MapDataItem;
import com.haulmont.charts.gui.components.charts.Chart;

import javax.inject.Inject;
import java.util.Map;

public class ExtAppMainWindow extends AbstractMainWindow {
    @Inject
    private FtsField ftsField;

    @Inject
    private Embedded logoImage;

    @Inject
    private SideMenu sideMenu;

    @Inject
    private Chart xyChart;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        sideMenu.requestFocus();

        initLayoutAnalyzerContextMenu(logoImage);
        initLogoImage(logoImage);
        initFtsField(ftsField);
        ListDataProvider dataProvider = new ListDataProvider();


        dataProvider.addItem(transportCount(1994, 1587, 650, 121));
        dataProvider.addItem(transportCount(1995, 1567, 683, 146));
        dataProvider.addItem(transportCount(1996, 1617, 691, 138));
        dataProvider.addItem(transportCount(1997, 1630, 642, 127));
        dataProvider.addItem(transportCount(1998, 1660, 699, 105));
        dataProvider.addItem(transportCount(1999, 1683, 721, 109));
        dataProvider.addItem(transportCount(2000, 1691, 737, 112));
        dataProvider.addItem(transportCount(2001, 1298, 680, 101));
        dataProvider.addItem(transportCount(2002, 1275, 664, 97));
        dataProvider.addItem(transportCount(2003, 1246, 648, 93));
        dataProvider.addItem(transportCount(2004, 1318, 697, 111));
        dataProvider.addItem(transportCount(2005, 1213, 633, 87));
        dataProvider.addItem(transportCount(2006, 1199, 621, 79));
        dataProvider.addItem(transportCount(2007, 1110, 210, 81));
        dataProvider.addItem(transportCount(2008, 1165, 232, 75));
        dataProvider.addItem(transportCount(2009, 1145, 219, 88));
        dataProvider.addItem(transportCount(2010, 1163, 201, 82));
        dataProvider.addItem(transportCount(2011, 1180, 285, 87));
        dataProvider.addItem(transportCount(2012, 1159, 277, 71));

        xyChart.setDataProvider(dataProvider);

    }

    private DataItem transportCount(int year, int cars, int motorcycles, int bicycles) {
        MapDataItem item = new MapDataItem();
        item.add("year", year);
        item.add("cars", cars);
        item.add("motorcycles", motorcycles);
        item.add("bicycles", bicycles);
        return item;
    }
}