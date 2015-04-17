<%@ page language="java" import="com.cn.conwel.domain.FundBean,java.util.List" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<FundBean>  fundBeans  =  (List<FundBean>)request.getAttribute("fundBeans");
    if(fundBeans == null){

    }
%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>实时基金价格查询</title>
    <meta name="Keywords" content="html,javascript,gbtags,bootstrap" />
    <script src="http://libs.baidu.com/jquery/1.9.0/jquery.js"></script>
    <style>
        @import url('http://cdn.gbtags.com/twitter-bootstrap/3.2.0/css/bootstrap.css');
        body{
            font-family: 'microsoft yahei',Arial,sans-serif;
            color: #898989;
        }

        .shape{
            border-style: solid; border-width: 0 80px 80px 0; float:right; height: 0px; width: 0px;
            -ms-transform:rotate(360deg); /* IE 9 */
            -o-transform: rotate(360deg);  /* Opera 10.5 */
            -webkit-transform:rotate(360deg); /* Safari and Chrome */
            transform:rotate(360deg);
        }

        .speical{
            background:#fff; border:1px solid #ddd; box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2); margin: 15px 0; overflow:hidden;
        }

        .speical:hover {
            -webkit-transform: scale(1.1);
            -moz-transform: scale(1.1);
            -ms-transform: scale(1.1);
            -o-transform: scale(1.1);
            transform:rotate scale(1.1);
            -webkit-transition: all 0.4s ease-in-out;
            -moz-transition: all 0.4s ease-in-out;
            -o-transition: all 0.4s ease-in-out;
            transition: all 0.4s ease-in-out;
        }

        .shape {
            border-color: rgba(255,255,255,0) #d9534f rgba(255,255,255,0) rgba(255,255,255,0);
        }

        .speical-default{
            border: 1px solid #d9534f;
        }

        .speical-radius{
            border-radius:5px;
        }

        .shape-text{
            color:#fff; font-size:14px; position:relative; right:-35px; top:2px; white-space: nowrap;
            -ms-transform:rotate(45deg); /* IE 9 */
            -o-transform: rotate(45deg);  /* Opera 10.5 */
            -webkit-transform:rotate(45deg); /* Safari and Chrome */
            transform:rotate(45deg);
        }

        .text-special-default{
            color:#d9534f;
        }

        .speical-content{
            padding:0 20px 10px;
        }
        .container {
            width: 1270px;
        }
    </style>
    <script>
        var REFRESH_TIMES = 10 * 1000;
        var se,m=0,h=0,s=0,ss= 1,st;
        function second(){
            if((ss%100)==0){s+=1;ss=1;}
            if(s>0 && (s%60)==0){m+=1;s=0;}
            if(m>0 && (m%60)==0){h+=1;m=0;}
            t=h+"时"+m+"分"+s+"秒"+ss+"毫秒";
            $("#ctime1").html(t);
            $("#ctime2").html(t);
            $("#ctime3").html(t);
            $("#ctime4").html(t);
            $("#ctime5").html(t);
            $("#ctime6").html(t);
            $("#ctime7").html(t);
            ss+=1;
        }

        function refreshIframe(){
            var codes = $.trim($("#jjdm").val());
            var codess = [];
            if(codes == ""){
                codess.push("163113");
                codess.push("160625");
                codess.push("160620");
                codess.push("070001");
                codess.push("110029");
                codess.push("217010");
                codess.push("000251");
                codes = codess.join(",");
            }

            var size = codes.split(",").length;
            hideDiv(size);
            showDiv(size);

            $.post("/getData",{codes:codes},function(result){
                var countRich = 0;
                for(var i=0;i<result.length;i++){
                    $("#code"+(i+1)).html(result[i].code);
                    $("#netWorth"+(i+1)).html(result[i].netWorth);
                    $("#increase"+(i+1)).html(result[i].increase);
                    $("#interest"+(i+1)).html(result[i].interest+"%");
                    $("#time"+(i+1)).html(result[i].time);
                    $("#rich"+(i+1)).html(result[i].rich+"元");
                    if(result[i].code == "217010" || result[i].code == "000251"){
                        countRich += (parseInt(result[i].rich)/2);
                    }else{
                        countRich += parseInt(result[i].rich);
                    }
                }
                $("#countId").html("当日累计收益:"+countRich+"元");
            });
        }

        function hideDiv(size){
            if(size <= 0 || size > 7)
            return;
            for(var i = 1;i<8;i++){
                $("#content"+i).css("display","none");
            }
        }

        function showDiv(size){
            if(size <= 0 || size > 7)
                return;
            for(var i = 1;i<(size+1);i++){
                $("#content"+i).css("display","block");
            }
        }

        function start(){
            console.log("se:"+se);
            console.log("st:"+st);
            if(se != undefined || st != undefined)
              return;
//            var titles = ["申万证券","鹏华证券","鹏华资源","嘉实成长","易方达","招商大盘","工银金融"];
//            for(var i = 0;i<titles.length;i++){
//                $("#title"+(i+1)).html(titles[i]);
//            }
            se=setInterval("second()",1);
            refreshIframe();
            st = setInterval(function(){
                refreshIframe();
            },REFRESH_TIMES);
        }


        function stop(){
            clearInterval(se);
            se = undefined;
            clearInterval(st);
            st = undefined;
        }
    </script>
<body>
<div class="container">
    <br> <br>
    <div class="input-group input-group-lg">
        <span class="input-group-addon">基金代码(英文字符逗号隔开)</span>
        <input type="text" class="form-control" id="jjdm" placeholder="请填写">
    </div>
    <br>
    <div class="row">
        <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3" id="content1">
            <div class="speical speical-default speical-radius">
                <div class="shape">
                    <div id="title1" class="shape-text">
                        基金
                    </div>
                </div>
                <div class="speical-content">
                    <h2 id="code1" class="text-special-default">
                        000000
                    </h2>
                    <br>
                    <p>
                        净值：<span id="netWorth1" class="label label-danger">0.0000</span>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        浮动：<span id="increase1" class="label label-danger">0.0000</span>
                    </p>
                    <p>
                        浮率：<span id="interest1" class="label label-danger">0.0000</span>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        时间：<span id="time1" class="label label-danger">0.0000</span>
                    </p>
                    <p>
                        万份盈利：<span id="rich1" class="label label-danger">0.0000+-</span>
                    </p>
                    <p>
                        刷新计时：<span id="ctime1" class="label label-danger">0.0000</span>
                    </p>
                </div>
            </div>
        </div>

        <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3" id="content2">
            <div class="speical speical-default speical-radius">
                <div class="shape">
                    <div id="title2" class="shape-text">
                        涨幅
                    </div>
                </div>
                <div class="speical-content">
                    <h2 id="code2" class="text-special-default">
                        000000
                    </h2>
                    <br>
                    <p>
                        净值：<span id="netWorth2" class="label label-danger">0.0000</span>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        浮动：<span id="increase2" class="label label-danger">0.0000</span>
                    </p>
                    <p>
                        浮率：<span id="interest2" class="label label-danger">0.0000</span>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        时间：<span id="time2" class="label label-danger">0.0000</span>
                    </p>
                    <p>
                        万份盈利：<span id="rich2" class="label label-danger">0.0000+-</span>
                    </p>
                    <p>
                        刷新计时：<span id="ctime2" class="label label-danger">0.0000</span>
                    </p>
                </div>
            </div>
        </div>

        <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3" id="content3">
            <div class="speical speical-default speical-radius">
                <div class="shape">
                    <div id="title3" class="shape-text">
                        涨幅
                    </div>
                </div>
                <div class="speical-content">
                    <h2 id="code3" class="text-special-default">
                        000000
                    </h2>
                    <br>
                    <p>
                        净值：<span id="netWorth3" class="label label-danger">0.0000</span>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        浮动：<span id="increase3" class="label label-danger">0.0000</span>
                    </p>
                    <p>
                        浮率：<span id="interest3" class="label label-danger">0.0000</span>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        时间：<span id="time3" class="label label-danger">0.0000</span>
                    </p>
                    <p>
                        万份盈利：<span id="rich3" class="label label-danger">0.0000+-</span>
                    </p>
                    <p>
                        刷新计时：<span id="ctime3" class="label label-danger">0.0000</span>
                    </p>
                </div>
            </div>
        </div>

        <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3" id="content4">
            <div class="speical speical-default speical-radius">
                <div class="shape">
                    <div id="title4" class="shape-text">
                        涨幅
                    </div>
                </div>
                <div class="speical-content">
                    <h2 id="code4" class="text-special-default">
                        000000
                    </h2>
                    <br>
                    <p>
                        净值：<span id="netWorth4" class="label label-danger">0.0000</span>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        浮动：<span id="increase4" class="label label-danger">0.0000</span>
                    </p>
                    <p>
                        浮率：<span id="interest4" class="label label-danger">0.0000</span>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        时间：<span id="time4" class="label label-danger">0.0000</span>
                    </p>
                    <p>
                        万份盈利：<span id="rich4" class="label label-danger">0.0000+-</span>
                    </p>
                    <p>
                        刷新计时：<span id="ctime4" class="label label-danger">0.0000</span>
                    </p>
                </div>
            </div>
        </div>

        <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3" id="content5">
            <div class="speical speical-default speical-radius">
                <div class="shape">
                    <div id="title5" class="shape-text">
                        涨幅
                    </div>
                </div>
                <div class="speical-content">
                    <h2 id="code5" class="text-special-default">
                        000000
                    </h2>
                    <br>
                    <p>
                        净值：<span id="netWorth5" class="label label-danger">0.0000</span>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        浮动：<span id="increase5" class="label label-danger">0.0000</span>
                    </p>
                    <p>
                        浮率：<span id="interest5" class="label label-danger">0.0000</span>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        时间：<span id="time5" class="label label-danger">0.0000</span>
                    </p>
                    <p>
                        万份盈利：<span id="rich5" class="label label-danger">0.0000+-</span>
                    </p>
                    <p>
                        刷新计时：<span id="ctime5" class="label label-danger">0.0000</span>
                    </p>
                </div>
            </div>
        </div>

        <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3" id="content6">
            <div class="speical speical-default speical-radius">
                <div class="shape">
                    <div id="title6" class="shape-text">
                        涨幅
                    </div>
                </div>
                <div class="speical-content">
                    <h2 id="code6" class="text-special-default">
                        000000
                    </h2>
                    <br>
                    <p>
                        净值：<span id="netWorth6" class="label label-danger">0.0000</span>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        浮动：<span id="increase6" class="label label-danger">0.0000</span>
                    </p>
                    <p>
                        浮率：<span id="interest6" class="label label-danger">0.0000</span>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        时间：<span id="time6" class="label label-danger">0.0000</span>
                    </p>
                    <p>
                        万份盈利：<span id="rich6" class="label label-danger">0.0000+-</span>
                    </p>
                    <p>
                        刷新计时：<span id="ctime6" class="label label-danger">0.0000</span>
                    </p>
                </div>
            </div>
        </div>

        <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3" id="content7">
            <div class="speical speical-default speical-radius">
                <div class="shape">
                    <div id="title7" class="shape-text">
                        涨幅
                    </div>
                </div>
                <div class="speical-content">
                    <h2 id="code7" class="text-special-default">
                        000000
                    </h2>
                    <br>
                    <p>
                        净值：<span id="netWorth7" class="label label-danger">0.0000</span>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        浮动：<span id="increase7" class="label label-danger">0.0000</span>
                    </p>
                    <p>
                        浮率：<span id="interest7" class="label label-danger">0.0000</span>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        时间：<span id="time7" class="label label-danger">0.0000</span>
                    </p>
                    <p>
                        万份盈利：<span id="rich7" class="label label-danger">0.0000+-</span>
                    </p>
                    <p>
                        刷新计时：<span id="ctime7" class="label label-danger">0.0000</span>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
<br>
<div style="text-align: center">
    <button type="button" id="countId" class="btn btn-danger btn-lg">实时收益</button>
    <button type="button" id="stop" onclick="stop()" class="btn btn-danger btn-lg">暂停</button>
    <button type="button" id="start" onclick="start()" class="btn btn-success btn-lg">开启</button>
</div>


<script src="http://cdn.gbtags.com/jquery/1.11.1/jquery.min.js"></script>


<script type="text/javascript">
    var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
    document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fac45a0f68a119fbe4d1c4c3ac3044dbd' type='text/javascript'%3E%3C/script%3E"));
</script>
</body>
</html>
