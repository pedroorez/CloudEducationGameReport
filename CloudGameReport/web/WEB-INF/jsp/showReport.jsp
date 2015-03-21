<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Cloud Game Report</title>
        <link href="/CloudGameReport/res/css/intro.css" rel="stylesheet">
        <link href="/CloudGameReport/res/css/mine-charts.css" rel="stylesheet">
        <link href="/CloudGameReport/res/angular-chart/angular-chart.css" rel="stylesheet">
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
    </head>
     <script>
        var userID = ${UserID};
     </script>
    <body>
        <div class="container">

            <div class="masthead">
                <ul class="nav nav-pills pull-right">
                    <li><a href="/CloudGameReport/ClassesManager">Classes Manager</a></li>
                    <li class="active"><a>Game Report</a></li>
                    <li ><a href="/CloudGameReport/logoff">Log Off</a></li>
                </ul>
                <h3 >Cloud Game Report</h3>
            </div>

            <center> <div  class="alert alert-danger" role="alert">${erromsg}</div></center>
            <center> <div  class="alert alert-success" role="alert">${sucessmsg}</div></center>

            <!--Main Box Div-->
            <div class="container mainbox"  ng-controller="chartsController" style="text-align: center;">
                <h1 class="center"> Report Charter </h1>
                <!--<h4 class="center"> -- ${GameTypeName} | ${GameEntryName} -- </h4>
                - ${GameEntry.gameType.gametypeName}
                -->
                <label> Game Entry Name </label>
                <select ng-change="getEntryData()" ng-model="gameEntryID" class="selectpicker">
                    <option></option>
                    <c:forEach var="GameEntry" items="${GameList}">
                        <option value="${GameEntry.gameEntryID}">${GameEntry.gameName} </option>
                    </c:forEach>
                </select>
                <br><br>
                <div ng-repeat="(key, param) in charts.list.drawlist" 
                     google-chart chart-no="{{key}}" ></div>
                <div id="buttonplace">
                    <button id="addchart" class="btn btn-large btn-primary hide">ADD A NEW CHART</button>
                </div>
            
                <div id="erroplace" class="hide">  </div>
                
            </div>

            <div align="center" class="footer">
                <p>&copy; Pedro Correa - DEL - UFRJ 2014</p>
            </div>

        </div>
        <!-- end of container -->

    </body>
    <script src="https://www.google.com/jsapi" type="text/javascript"></script>
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.3.14/angular.js"></script>
    <script type="text/javascript" src="/CloudGameReport/res/angular-chart/Chart.js"></script>
    <script type="text/javascript" src="/CloudGameReport/res/angular-chart/angular-chart.js"></script>
    <script type="text/javascript" src="/CloudGameReport/res/js/charter.js"></script>
</html>
