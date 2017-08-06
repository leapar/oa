com_purvar_ito_oa_web_toolkit_ui_baidumapview_BaiduMapView = function() {
    var connector = this;
    var element = connector.getElement();
    var state = connector.getState();
    $(element).html("<div id=\"allmap\"></div>");
   // $("#allmap").css("padding", "5px 10px");
    $("#allmap").css("width", state.width).css("height", state.height);


    if(state.justShow) {
        console.log("onStateChange","init",JSON.stringify(state),state.width,state.height);
        connector.onStateChange = function() {
            var state = connector.getState();
            /*slider.slider("values", state.values);
             slider.slider("option", "min", state.minValue);
             slider.slider("option", "max", state.maxValue);
             $(element).width(state.width);*/
            //alert(JSON.stringify(info));
            console.log("onStateChange",JSON.stringify(state),state.width,state.height);

            $("#allmap").css("width", state.width).css("height", state.height);
            var point = new BMap.Point(state.lng, state.lat);
            map.panTo(point);
        }
    }



    // 百度地图API功能
    var map = new BMap.Map("allmap");    // 创建Map实例
    map.centerAndZoom(new BMap.Point(state.lng, state.lat), state.zoom);  // 初始化地图,设置中心点坐标和地图级别
    //map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
    if(parseFloat(state.lng) == 0)
        map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
    var size = new BMap.Size(1, 1);
    map.addControl(new BMap.CityListControl({
        anchor: BMAP_ANCHOR_TOP_RIGHT,
        offset: size,
        // 切换城市之间事件
        // onChangeBefore: function(){
        //    alert('before');
        // },
        // 切换城市之后事件
        // onChangeAfter:function(){
        //   alert('after');
        // }
    }));

    // 添加带有定位的导航控件
    var navigationControl = new BMap.NavigationControl({
        // 靠左上角位置
        anchor: BMAP_ANCHOR_TOP_LEFT,
        // LARGE类型
        type: BMAP_NAVIGATION_CONTROL_LARGE,
        // 启用显示定位
        //enableGeolocation: true
    });
    if(state.showZoomCtrl)
        map.addControl(navigationControl);


    var point = new BMap.Point(state.lng, state.lat);
    var geoc = new BMap.Geocoder();
    var marker = new BMap.Marker(point);// 创建标注
    map.addOverlay(marker);             // 将标注添加到地图中
    if(state.justShow)
        marker.disableDragging();
    else {
        marker.enableDragging();           // 不可拖拽
        marker.addEventListener("dragend",attribute);
    }

    function attribute(){
        var p = marker.getPosition();  //获取marker的位置
        //alert("marker的位置是" + p.lng + "," + p.lat);
        map.panTo(p);
    }
    // map.addEventListener("click", showInfo);
    function showInfo(e){
        marker.setPosition(e.point);
        //	alert(e.point.lng + ", " + e.point.lat);
    }
    // map.addEventListener("moving",function(){
    if(!state.justShow) {
        map.addEventListener("moveend",function(){
            marker.setPosition(map.getCenter());
            // alert("地图加载完毕");
            doGeoCoder(map.getCenter());
        });
    }
    if(!state.justShow) {
        doGeoCoder(map.getCenter());
    }

    function doGeoCoder(pt) {
        geoc.getLocation(pt, function(rs){
            var info = {};
            info.province = rs.addressComponents.province;
            info.district  = rs.addressComponents.district ;
            info.address = rs.address;
            info.point = pt;
            if(rs.surroundingPois != null && rs.surroundingPois.length > 0) {
                info.address = rs.surroundingPois[0].address + "("+ rs.surroundingPois[0].title + ")";
            }
            connector.valueChanged(info);
            console.log(JSON.stringify(info));
        });
    }

}