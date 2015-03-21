<html>
    <head>
        <title>Cloud Game Report</title>
        <link href="/CloudGameReport/res/css/intro.css" rel="stylesheet">
        <link href="/CloudGameReport/res/css/mine-charts.css" rel="stylesheet">
        <link href="/CloudGameReport/res/angular-chart/angular-chart.css" rel="stylesheet">
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
    </head>
     <script>
        var rdata = ${ReportData};
        var rparams = ${ReportParameters};
     </script>
    <body>
        <div class="container">

            <div class="masthead">
                <ul class="nav nav-pills pull-right">
                    <li><a href="../ClassesManager">Classes Manager</a></li>
                    <li ><a href="../manageInstances/${ClassID}">Class Manager</a></li>
                    <li class="active"><a>Game Report</a></li>
                    <li ><a href="../logoff">Log Off</a></li>
                </ul>
                <h3 >Cloud Game Report</h3>
            </div>

            <center> <div  class="alert alert-danger" role="alert">${erromsg}</div></center>
            <center> <div  class="alert alert-success" role="alert">${sucessmsg}</div></center>

            <!--Main Box Div-->
            <div class="container mainbox"  ng-controller="chartsController">
                <h1 class="center"> Report Charter </h1>
                <h4 class="center"> -- ${GameTypeName} | ${GameEntryName} -- </h4>
                
                <div ng-repeat="(key, param) in charts.list.drawlist" 
                     google-chart chart-no="{{key}}" ></div>
                
                <button id="addchart" class="btn btn-large btn-primary">ADD A NEW CHART</button>
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
