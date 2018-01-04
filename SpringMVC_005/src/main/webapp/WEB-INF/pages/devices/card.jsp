<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>设备登记系统</title>
    <!-- Bootstrap -->
    <link href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <style type="text/css">
        .body {
            height: 100%;
            overflow: hidden;
            background: #FAFAFC;
        }

        .main {
            position: absolute;
            top: 50px;
            right: 0;
            bottom: 0;
            left: 0;
        }
    </style>
</head>

<body>
<div class="body">
    <!-- 头部  -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">
                <img alt="Brand" src="http://oimagec1.ydstatic.com/image?id=7687229541746158801&product=ferarri"/>
            </a>
            <a class="navbar-brand" href="#">设备登记</a>
        </div>
        <a class="navbar-brand pull-right" href="mailto:123@qq.com?subject=设备登记系统意见反馈">
            <span class="glyphicon glyphicon-envelope" title="意见反馈"></span>
        </a>
    </nav>
    <!-- 主页面  -->
    <div class="main">
        <!-- ##翻译质量数据列表 -->
        <div id="curDailyTask" class="col-xs-12" style="margin-top:20px">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <ul id="mobileTab" class="nav  nav-pills" role="tablist">
                        <c:if test="${!empty productLists}">
                            <c:forEach items="${productLists}" var="product">
                                <c:if test="${product.id == product_current.id}">
                                    <li class="active"><a href="/devices/${product.product}">${product.nickname}</a></li>
                                </c:if>
                                <c:if test="${product.id != product_current.id}">
                                    <li><a href="/devices/${product.product}">${product.nickname}</a></li>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </ul>
                </div>
                <div class="panel-body">
                    <div id="mobileTabContent" class="tab-content">
                        <!-- other list -->
                        <div class="tab-pane fade in active" id="other-list">
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <td>资产号</td>
                                    <td>型号</td>
                                    <td>号码</td>
                                    <td>到期时间</td>
                                    <td>网络制式</td>
                                    <td>持有者</td>
                                    <td>借用人 <input type="text" class="borrower_2" placeholder="请向持有者借用后再修改"></td>
                                    <td>时间</td>
                                    <td>备注</td>
                                </tr>
                                </thead>
                                <tbody>
                                <!-- 如果其他设备列表为空 -->
                                <c:if test="${empty deviceLists}">
                                </c:if>
                                <!-- 如果其他设备列表非空 -->
                                <c:if test="${!empty deviceLists}">
                                    <c:forEach items="${deviceLists}" var="device">
                                        <tr>
                                            <td style="display: none;">${device.id}</td>
                                            <td>${device.assetId}</td>
                                            <td>${device.model}</td>
                                            <td>${device.os}</td>
                                            <td>${device.resolution}</td>
                                            <td>${device.network}</td>
                                            <td id="owner_${device.id}">${device.owner}</td>
                                            <td>
                                                <button id="borrow_${device.id}"  type="button"
                                                        class="btn btn-sm btn-warning" onclick=submit_ajax(${device.id},${device.type})>借用</button>
                                            </td>
                                            <td id="time_${device.id}">${device.time}</td>
                                            <td>${device.desc}</td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="/js/index.js"></script>
</body>
</html>
