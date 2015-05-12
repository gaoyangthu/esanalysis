<%--
  Created by IntelliJ IDEA.
  User: zhangzheming
  Date: 2014/4/2
  Time: 12:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>数据展示</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
    <script type="text/javascript" src="/js/map.js"></script>
    <script src="/js/modules/exporting.js"></script>
    <script src="http://echarts.baidu.com/build/echarts-plain.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/ebusiness.css">
    <link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
</head>
<body>
  <div style="width: 1020px; height: 1500px; margin: auto;">
      <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
     <div id="main" style="height:500px; min-width: 600px"></div>
      <script type="text/javascript">
          // 基于准备好的dom，初始化echarts图表
          var myChart = echarts.init(document.getElementById('main'));
          option = {
              tooltip : {
                  trigger: 'axis'
              },
              legend: {
                  data:['UV','PV']
              },
              toolbox: {
                  show : true,
                  feature : {
                      mark : {show: true},
                      dataZoom : {show : true},
                      dataView : {show: true, readOnly: false},
                      magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                      restore : {show: true},
                      saveAsImage : {show: true}
                  }
              },
              calculable : true,
              dataZoom : {
                  show : true,
                  realtime : true,
                  start : 20,
                  end : 80
              },
              xAxis : [
                  {
                      type : 'category',
                      boundaryGap : false,
                      data:[]
                  }
              ],
              yAxis : [
                  {
                      type : 'value',
                      splitArea : {show : true}
                  }
              ],
              series : [
                  {
                      name:'UV',
                      type:'line',
                      stack: '总量',
                      data:[]
                  },
                  {
                      name:'PV',
                      type:'line',
                      stack: '总量',
                      data:[]
                  }
              ]
          };


          $.post('/getServlet',{'param':'data'},function(data){
              var obj = $.parseJSON(data);

              var times = obj.time.substring(1,obj.time.length-2).split(",");
              for(var i=0;i< times.length;i++){
                  //alert(  times[i] );
                  option['xAxis'][0]['data'].push( times[i] );
              }

              var uvs = obj.uv.substring(1,obj.uv.length-2).split(",");
              var pvs = obj.pv.substring(1,obj.pv.length-2).split(",");

              for(var i=0;i< uvs.length;i++){
                  option['series'][0]['data'].push(uvs[i]);
                  option['series'][1]['data'].push(pvs[i]);
              }
              // 为echarts对象加载数据
              myChart.setOption(  option  );
          });
      </script>
    <div style="width: 1020px;border: 1px solid black; margin: auto;">
        <div class="list_Advertising clearfix">
            <%-- content --%>
        </div>
        <div style="clear: both; border: 1px solid black"></div>
        起始日期
        <input type="text" placeholder="请选择起始日期！" id="datepicker">
        终止日期
        <input type="text" placeholder="请选择终止日期！" id="endpicker">
        <input type="button" value="确定" id="btn">
        <input type="button" value="全选" id="selecAll">
        <input type="button" value="反选" id="unselec">
        <input type="button" value="取消全选" id="cancelselec">
    </div>
    <!--图表结果页面-->
    <div id="tb_info" style="width: 1020px;margin-top:100px;margin: auto"></div>
    <!--渠道用户详情-->
    <div id="detail_info" style="width: 100%;">

    </div>
</div>
<!--模板-->
<script type="text/x-template" id="listTpl">
    <ul>
        {{ _.each(lists, function(item){  }}
        <li class="pull-left">
            <label for="id_{{= item.id }}">
                <input type="checkbox" name="{{=item.name}}" id="id_{{= item.id }}">
                {{= item.name }}
            </label>
        </li>
        {{ }); }}
    </ul>
</script>
<script src="js/underscore-min.js"></script>
<script type="text/javascript">
    //名称与id号的映射
    var map = new HashMap();
    //id集合
    var namearr = new Array();

    $(function() {
        _.templateSettings = {
            evaluate:    /\{\{(.+?)\}\}/g,
            interpolate: /\{\{=(.+?)\}\}/g,
            escape:      /\{\{-(.+?)\}\}/g
        };

        //触发弹出日期控件
        $("#datepicker").datepicker();
        $("#endpicker").datepicker();

        //获取日期控件的日期数据
        $("#btn").on("click",function(){
            var datepicker = $("#datepicker").val();
            var endpicker = $("#endpicker").val();

            if (datepicker.length == 0 || endpicker.length == 0){
                alert("请选择日期!");
                return;
            }

            //起始日期
            var times = datepicker.split("/");
            datepicker = times[2]+"-"+times[0]+"-"+times[1];
            times = endpicker.split("/");
            //终止时间
            endpicker =  times[2]+"-"+times[0]+"-"+times[1];

            if(Date.parse(endpicker)-Date.parse(datepicker)<0){
                alert("时间选择不符合要求！");
                return;
            }


           $("input[type=checkbox]:checked").each(function(i,data) {
               var id = $(this).attr("id");
                    id = id.replace("id_","");
               var name = $(this).attr("name");
               map.put(id,name);
               //ID传入数组
               namearr[i] = id;
           });

            if(namearr.length == 0){
                alert("请您选择渠道！");
                return;
            }
              var id_lst = JSON.stringify(namearr);
              var html = "<table cellpadding=\"0px\" cellspacing=\"0px\">" +
                      "<tr>" +
                      "<td>渠道</td>" +
                      "<td>时间</td>" +
                      "<td>PV</td>" +
                      "<td>UV</td>" +
                      "<td>注册用户数</td>" +
                      "<td>下订单数</td>"+
                      "<td>支付订单数</td>"+
                      "<td>订单支付金额</td>"+
                      "<td>现金支付金额</td>"+
                      "<td>操作</td>"+
                      "</tr>";
                $.post('/getServlet',{'param':'channels_info','timeparam':datepicker,'endtime':endpicker,'id_lst':id_lst},function(data){
                    //解析json数据
                    var temp = jQuery.parseJSON(data);
                    $.each(temp,function(index,item){
                        var bg = index % 2 == 0 ? "#eee":"#fff";
                        html += "<tr class=\"Items\" style='background-color:" + bg + "' onmouseover=\"this.style.background='#ddeeff'\" onmouseout=\"this.style.background='" + bg + "'\">"
                                 +   "<td id="+item.tag+">" + map.get(item.tag) +"</td>"
                                 +   "<td>" + item.date +"</td>"
                                 +   "<td>" + item.pv +"</td>"
                                 +   "<td>" + item.uv +"</td>"
                                 +   "<td>" + item.registerCount +"</td>"
                                 +   "<td>" + item.orderCount +"</td>"
                                 +   "<td>" + item.payedCount +"</td>"
                                 +   "<td>" + item.payedAmount +"</td>"
                                 +   "<td>" + item.cashAmount  +"</td>"
                                 +   "<td><input type='button' name='detail' value='查看'></td>"
                                 + "</tr>"
                    });
                    html += "</table>";
                    $("#tb_info").html(html);
                });

        });

        $.post("/getServlet",{'param':'channels_list'},function(data){
            var result = {lists: jQuery.parseJSON(data)};
            var template = _.template($('#listTpl').html());
            $('.list_Advertising').html(template(result));
        });

       $("input[type=button][name=detail]").live('click',function(){
          var id = $(this).closest("tr").children().eq(0).attr("id")
          var datepicker = $("#datepicker").val();
          var endpicker = $("#endpicker").val();
           //起始日期
          var times = datepicker.split("/");
          datepicker = times[2]+"-"+times[0]+"-"+times[1];
          times = endpicker.split("/");
          endpicker =  times[2]+"-"+times[0]+"-"+times[1];

          var html = "<table cellpadding=\"0px\" cellspacing=\"0px\">" +
                   "<tr>" +
                   "<td>渠道</td>" +
                   "<td>用户</td>" +
                   "<td>首次访问时间</td>" +
                   "<td>Email</td>" +
                   "<td>下订单金额</td>" +
                   "<td>支付订单金额</td>"+
                   "<td>现金支付金额</td>"+
                   "</tr>";
           $.post('/getServlet',{'param':'detailinfo','id':id,'time':datepicker,'endtime':endpicker},function(data){
               var temp = jQuery.parseJSON(data);
               $.each(temp,function(index,item){
                   var bg = index % 2 == 0 ? "#eee":"#fff";
                   html += "<tr class=\"Items\" style='background-color:" + bg + "' onmouseover=\"this.style.background='#ddeeff'\" onmouseout=\"this.style.background='" + bg + "'\">"
                           +   "<td id="+item.user_channel_tag+">" + map.get(item.user_channel_tag) +"</td>"
                           +   "<td>" + item.user_name +"</td>"
                           +   "<td>" + item.first_channel_visit +"</td>"
                           +   "<td>" + item.email +"</td>"
                           +   "<td>" + item.total_order_amount +"</td>"
                           +   "<td>" + item.payed_order_amount +"</td>"
                           +   "<td>" + item.order_cash_amount +"</td>"
                           + "</tr>"
               });
               html += "</table>";
               $("#detail_info").html(html);
           });

         });
       //全选
       $('#selecAll').on('click',function(){
           $("input[type=checkbox]").attr("checked",'true');
       });

       //取消全选
       $('#cancelselec').on('click',function(){
           $("input[type=checkbox]").removeAttr("checked");
       });

       //反选
       $('#unselec').on('click',function(){
           $("input[type=checkbox]").each(function(){
               if($(this).attr("checked")){
                   $(this).removeAttr("checked");
               }else{
                   $(this).attr("checked",true);
               }
           });
       });
   });

</script>
</body>

</html>
