/**
 * Created by Snail on 2016/3/17.
 */

var nullData = '--';
$(window).resize(function(){
    var height = $(window).height();
    $("#container").css("height",height-106);
});
$(function(){

    var height = $(window).height();
    $("#container").css("height",height-106);
    //首页chart操作
    $(".selectCh").change(function(){
        var type = $(this).data("type");
        var val = $(this).val();

    });

    //存量统计切换
    $("#stock").load("allland");//初始加载
    $(".typeMenu.stock").on("click","li",function(){
        $(".stock li").removeClass("on");
        $(this).addClass("on")
        var typeStock = $(this).data("stock");
        $(".contentBox").load(typeStock);
    });

    //市场情况
    $(".typeMenu.market").on("click","li",function(){
        var flag = $(this).attr("data-market");
        var typePomp = $(this).data("pomp");
        var title = $(this).data("title");
        if(flag){
            htmlPomp(typePomp,title);
        }
    });
    $("body").on("click",".close",function(){
        $("body").css("overflow-y","auto");
        $(".module").remove();
    });

    $("body").on("click",".regionClick",function(){
        var text = $(this).text();
        var typePomp = $(this).data("pomp");
        htmlPomp(typePomp,text);
    });


    function htmlPomp(typePomp, title){
        $("body").css("overflow","hidden");
        var height = $(window).height();
        var width = $("body").width();
        //构造弹出层
        var html = "<div class='module' id='moduleBox' style='width: "+ width +";height:"+ height +"'></div>";
        $("body").append(html);
        var htmlPomp = "<div class='pompBox'><div id='reginName' class='pomp-title'>"+ title +"<div class='close'>X</div></div>";
        htmlPomp += '<div class="pompContent"></div></div>';
        $("#moduleBox").html(htmlPomp);
        $(".pompContent").load(typePomp);
    }


    //设置当前时间
    formatterDate = function(date, addDay) {
        var dd = date;
        dd.setDate(dd.getDate() + addDay);
        var day = dd.getDate() > 9 ? dd.getDate() : "0" + dd.getDate();
        var month = (dd.getMonth() + 1) > 9 ? (dd.getMonth() + 1) : "0"
        + (dd.getMonth() + 1);
        return dd.getFullYear() + '/' + month + '/' + day;
    };

    setTimeout(function(){
        $('#startTime').datebox('setValue', '2008/01/01');
        $('#endTime').datebox('setValue', formatterDate(new Date(),0));
    },500)
});



//图表加载方法
function doQuery(formId,Url,chartName,option){
    var paramObj = {};
    $.each($('#'+formId+'').serializeArray(),function(i,d){
        paramObj[d.name] = d.value;
    });
    var loadingFlag = false;
    $.ajaxSettings.async = false;
    $.get(Url,paramObj,function(data){
        if(data.success == true){
            var list = data.result.list;
            $.each(list,function(i,d){
//                    console.debug(d);
                if(i==0){
                    option.xAxis[0].data=d;
                }
                if(i>0){
                    option.series[i-1].data=d;
                }
                if(d.length>0){
                    loadingFlag=true;
                }
            });
        }
    });
    //console.log(JSON.stringify(option));
    chartName.setOption(option,true);
    //console.debug(paramObj);
    if(loadingFlag){
        chartName.hideLoading();
    }
}




//全局默认查询起始年份
function check(Url,checked,yearTemp,jumpUrl){
    var paramObj = {};
    //alert(checked);
    //选中
    if(checked){
        paramObj['checked'] = checked;
        paramObj['yearTemp'] = yearTemp;
    }else {
        paramObj['checked'] = checked;
        paramObj['yearTemp'] = null;
    }
    $.get(Url,paramObj,function(data){
        window.location=jumpUrl;
    });
}

function validateNum(obj) {
    var currentVal = $(obj).val();
    var reg = /^[0-9]+\.?[0-9]*$/;//用来验证数字，包括小数的正则
    var r = currentVal.match(reg);
    if (!reg.test(currentVal)) {
        $(obj).val('');
    }
}

function validateXSS(obj) {
    var html = $(obj).val();
    // html = filterXSS(html);
    $(obj).val(html.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g, "&quot;").replace(/'/g, "&#039;"));
}