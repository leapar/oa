package com.purvar.ito.oa.web.record;

import com.google.gwt.maps.client.LoadApi;
import com.haulmont.charts.gui.components.charts.PieChart;
import com.haulmont.charts.gui.components.map.MapViewer;
import com.haulmont.charts.web.gui.components.map.google.WebGoogleMapViewer;
import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.components.AbstractMainWindow;
import com.haulmont.cuba.gui.components.Embedded;
import com.haulmont.cuba.gui.components.mainwindow.FtsField;
import com.haulmont.cuba.gui.components.mainwindow.SideMenu;
import com.haulmont.charts.gui.data.DataItem;
import com.haulmont.charts.gui.data.ListDataProvider;
import com.haulmont.charts.gui.data.MapDataItem;
import com.haulmont.charts.gui.components.charts.Chart;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.security.entity.Group;
import com.haulmont.cuba.security.entity.GroupHierarchy;
import com.haulmont.cuba.security.entity.UserRole;
import com.haulmont.cuba.security.global.UserSession;
import com.purvar.ito.oa.entity.Area;
import com.purvar.ito.oa.entity.RecordGroup;
import com.purvar.ito.oa.entity.UserType;
import com.purvar.ito.oa.service.RecordService;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.GoogleMapState;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExtAppMainWindow extends AbstractMainWindow {
    @Inject
    private FtsField ftsField;

    @Inject
    private Embedded logoImage;

    @Inject
    private SideMenu sideMenu;

    @Inject
    private Chart serialChart;
    @Inject
    private DataManager dataManager;

    private RecordService recordService =  AppBeans.get(RecordService.NAME);
    @Inject
    private CollectionDatasource<RecordGroup, UUID> countryGrowthDs;
    @Inject
    private UserSession userSession;
    @Inject
    private PieChart pie3dChart;
    @Inject
    private PieChart piechart_record;

    public static Object genericInvokMethod(Object obj, String methodName,
                                            int paramCount, Object... params) {
        Method method;
        Object requiredObj = null;
        Object[] parameters = new Object[paramCount];
        Class<?>[] classArray = new Class<?>[paramCount];
        for (int i = 0; i < paramCount; i++) {
            parameters[i] = params[i];
            classArray[i] = params[i].getClass();
        }
        try {
            method = obj.getClass().getDeclaredMethod(methodName, classArray);
            method.setAccessible(true);
            requiredObj = method.invoke(obj, params);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return requiredObj;
    }
/*
    @Override
    public void ready() {
        super.ready();

        WebGoogleMapViewer mapViewer = (WebGoogleMapViewer)googleMap;
        GoogleMap map = (GoogleMap) mapViewer.getComponent();

        GoogleMapState state = (GoogleMapState) genericInvokMethod(map,"getState",0);

        state.apiUrl = "www.google.cn";
    }
*/
    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        sideMenu.requestFocus();

        initLayoutAnalyzerContextMenu(logoImage);
        initLogoImage(logoImage);
        initFtsField(ftsField);

        initRecordPieChart();
        initUserPieChart();
        initRecordSerialsChart();

        /*WebGoogleMapViewer mapViewer = (WebGoogleMapViewer)googleMap;
        GoogleMap map = (GoogleMap) mapViewer.getComponent();

        GoogleMapState state = (GoogleMapState) genericInvokMethod(map,"getState",0);

        state.apiUrl = "www.google.cn";*/

       // map.gets

/*
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
*/
        //      serialChart.setDatasource(countryGrowthDs);
        //  serialChart.setDataProvider(dataProvider);

    }
    private RecordGroup recordGroup(Long value, Date date) {
        RecordGroup cg = new RecordGroup();
        cg.setValue(value);
        cg.setDate(date);
        return cg;
    }
    private DataItem transportCount(int year, int cars, int motorcycles, int bicycles) {
        MapDataItem item = new MapDataItem();
        item.add("year", year);
        item.add("cars", cars);
        item.add("motorcycles", motorcycles);
        item.add("bicycles", bicycles);
        return item;
    }

    void initRecordSerialsChart() {
        List list = recordService.getGroups(userSession.getUser().getGroup().getId());
        countryGrowthDs.refresh();
        for (int i = 0; i < list.size();i++) {
            Object[] item = (Object[]) list.get(i);
            try {
                countryGrowthDs.includeItem(recordGroup((Long) item[0],new SimpleDateFormat("yyyy/MM/dd").parse((String) item[1])));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    void initUserPieChart() {
        /*Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_MONTH,1);
        now.set(Calendar.HOUR_OF_DAY,0);
        now.set(Calendar.MINUTE,0);
        now.set(Calendar.SECOND,0);
        ValueLoadContext.createQuery(
                "select count(e.id),e.type from oa$ExtUser e where e.createTs > :date  group by e.type")
                .setParameter("date",now.getTime())*/

        ListDataProvider dataProvider = new ListDataProvider();
        ValueLoadContext context = ValueLoadContext.create()
                .setQuery(
                        ValueLoadContext.createQuery(
                                "select count(e.id),e.type from oa$ExtUser e group by e.type")
                )
                .addProperty("count")
                .addProperty("type");



        List<KeyValueEntity> list2 = dataManager.loadValues(context);

        for (int i = 0; i < list2.size();i++) {
            KeyValueEntity item = list2.get(i);
            UserType type = item.getValue("type");
            //Area area = item.getValue("area");
            dataProvider.addItem(new MapDataItem().add("count",item.getValue("count")).add("type",type.getName()/*+"-"+area.getName()*/));
        }

        pie3dChart.setDataProvider(dataProvider);
    }

    void initRecordPieChart() {
        ListDataProvider dataProvider = new ListDataProvider();

        String query = "select count(e.id),e.district from oa$Record e  where e.createTs > :date and  (e.bpm_flag is null or e.bpm_flag = 2) group by e.district";



        LoadContext loadContext =  LoadContext.create(GroupHierarchy.class);
        loadContext.setQueryString("select a from sec$GroupHierarchy a where a.group.id=:groupId order by a.level desc").setParameter("groupId",userSession.getUser().getGroup());

        List<GroupHierarchy> list = dataManager.loadList(loadContext);
        if(list.size() == 0 || list.get(0).getLevel() < 2) {
           // if(userSession.getUser().getGroup().getName().equals("Company")) {
                query = "select count(e.id),e.province as district from oa$Record e where e.createTs > :date  and  (e.bpm_flag is null or e.bpm_flag = 2) group by e.province";
           // }
        }

/*
        LoadContext<UserRole> ctx = LoadContext.create(UserRole.class)

                .setQuery(new LoadContext.Query("select ur from sec$UserRole ur where ur.user.id = :user")
                        .setParameter("user", userSession.getUser())
                );

        List<UserRole> userRoles = dataManager.loadList(ctx);
*/
       // new Date(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_MONTH,1);
        now.set(Calendar.HOUR_OF_DAY,0);
        now.set(Calendar.MINUTE,0);
        now.set(Calendar.SECOND,0);

        ValueLoadContext context = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(
                        query).setParameter("date",now.getTime())
                )
                .addProperty("count")
                .addProperty("district");



        List<KeyValueEntity> list2 = dataManager.loadValues(context);

        for (int i = 0; i < list2.size();i++) {
            KeyValueEntity item = list2.get(i);
            //UserType type = item.getValue("district");
            //Area area = item.getValue("area");
            dataProvider.addItem(new MapDataItem().add("count",item.getValue("count")).add("district",item.getValue("district")));
        }

        piechart_record.setDataProvider(dataProvider);
    }
}