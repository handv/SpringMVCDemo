
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>SpringMVC Demo 首页</title>

</head>
<body>
<h1>这里是SpringMVC 显示数据页</h1>

<input type="text" id="firstnum">+<input type="text" id="secnum"> <button type="button" onclick="add_ajax()">=</button> <span></span>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script>
    function add(){
        var data = parseInt($("#firstnum").val())+parseInt($("#secnum").val());
        $("span").html(data.toString());
    }
    function add_ajax(){
        $.ajax({
            type:'get',
            url:'/add',
            data:{"num1":$("#firstnum").val(),"num2":$("#secnum").val()},
            dataType:'html',
            success:function(data, textStatus){
                $("span").html(data);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest.readyState + "/n" +XMLHttpRequest.status + "/n" + XMLHttpRequest.responseText);
            }
        })
    }
</script>
</body>
</html>
