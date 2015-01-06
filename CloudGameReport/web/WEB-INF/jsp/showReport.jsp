<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Cloud Game Report</title>
        <link href="../res/css/bootstrap.css" rel="stylesheet">
        <link href="../res/css/bootstrap-responsive.css" rel="stylesheet">
        <link href="../res/css/intro.css" rel="stylesheet">
    </head>

    <body>
        <div class="container-narrow">

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

            <div class="mainbox">

                <h1 align="Center" class="form-signin-heading">${GameEntryName} Report</h1>
                <h4 align="Center" class="form-signin-heading">GameType: ${GameTypeName} | Game Reference: ${GameReference}</h4>

                <c:forEach var="entry" items="${ResultList}">
                    <h3> ${entry.gameType.valueName} Results <font size="2">${entry.gameType.displayType}</font></h3> 
                    <table style="width:100%">
                            <tr>
                                <td> ${entry.gameType.valueName} </td>  
                                <td> Player </td>    
                                <td> Match ID</td>    

                            </tr>
                            <c:forEach var="logline" items="${entry.gamelogList}">
                            
                            <tr>
                                <td> ${logline.dataValue} </td>
                                <td> ${logline.subscription.playerID.fullName} </td>
                                <td> ${logline.matchID} </td>

                            </tr>
                            </c:forEach>
                        
                    </table>
                </c:forEach>



            </div>



            <div align="center" class="footer">
                <p>&copy; Pedro Correa - DEL - UFRJ 2014</p>
            </div>

        </div> <!-- /container -->

        <!-- Le javascript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="res/js/jquery.js"></script>
        <script src="res/js/bootstrap-transition.js"></script>
        <script src="res/js/bootstrap-alert.js"></script>
        <script src="res/js/bootstrap-modal.js"></script>
        <script src="res/js/bootstrap-dropdown.js"></script>
        <script src="res/js/bootstrap-scrollspy.js"></script>
        <script src="res/js/bootstrap-tab.js"></script>
        <script src="res/js/bootstrap-tooltip.js"></script>
        <script src="res/js/bootstrap-popover.js"></script>
        <script src="res/js/bootstrap-button.js"></script>
        <script src="res/js/bootstrap-collapse.js"></script>
        <script src="res/js/bootstrap-carousel.js"></script>
        <script src="res/js/bootstrap-typeahead.js"></script>

    </body>
</html>
