$(function () {
    $.getJSON('/devices/datastat', function (datas) {
        if (datas) {
            loadEcharts(datas[0]);
            loadEcharts(datas[1]);
            loadEcharts(datas[2]);
        } else {
            alert(datas.readyState + datas.status + datas.responseText);
        }
    });
    $("#btn-searchDate").click(function () {
        var fromDate = $("#fromDate").val(),
            toDate = $("#toDate").val();
        if ((!fromDate) && !alert("请输入要查询的起始时间")) return;
        //location.href = "/dictoffline/show?from=" + fromDate + "&to=" + toDate;
        $.getJSON('/devices/datastats?from='+fromDate+"&to="+toDate, function (datas) {
            if (datas) {
                loadEcharts(datas[0]);
                loadEcharts(datas[1]);
                loadEcharts(datas[2]);
            } else {
                alert(datas.readyState + datas.status + datas.responseText);
            }
        });
    });
});
//载入echart
function loadEcharts(data) {
    //获取设备类型数据
    var deviceType = data.type;
    //获取设备名称
    var deviceName = [];
    //获取借用次数
    var deviceNum = [];
    $.each(data.deviceCount, function (i, value) {//index=0,=[{device:xiami,count:5},{...}]
        deviceName.push(value.device);//[xiaomi,xiaomi3,lg,sanxing]
        deviceNum.push(value.count);//[5,4,3,2]
    });
    seriesData = {
        name: deviceType,
        type: 'bar',
        data: deviceNum,
        itemStyle: {normal: {label: {show: true}}}
    };
    //type为user或andorid时，设置x轴文字斜着显示，防止x轴文字太多显示不全
    var eInterval = deviceType != 'ios' ? 0 : 'auto';
    var eRotate = deviceType != 'ios' ? 60 : 0;
    var dom = document.getElementById(deviceType);
    var myChart = echarts.init(dom);
    var option = {
        //设置标题
        title: {
            text:  deviceType + '借用情况统计'
        },
        //设置提示
        tooltip: {
            trigger: 'axis'
        },
        toolbox: {
            show: true,
            feature: {
                dataView: {
                    show: true,
                    title: '数据视图',
                    readOnly: false,
                },
                saveAsImage: {
                    show: true,
                    title: '保存为图片',
                    type: 'jpeg',
                    lang: ['点击本地保存']
                }
            }
        },
        //设置坐标轴
        xAxis: {
            type: 'category',
            axisLabel: {
                interval: eInterval,//横轴信息全部显示
                rotate: eRotate,//60度角倾斜显示
            },
            data: deviceName
        },
        grid: {
            x: 20,
            x2: 20,
            y2: 100
        },
        yAxis: {
            type: ''
        },
        //设置数据
        series: seriesData
    };
    // 为echarts对象加载数据
    myChart.setOption(option);
}
