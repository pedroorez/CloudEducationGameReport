<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Cloud Game Report</title>
        <link href="../res/css/bootstrap.css" rel="stylesheet">
        <link href="../res/css/bootstrap-responsive.css" rel="stylesheet">
        <link href="../res/css/intro.css" rel="stylesheet">
        <link href="../res/css/bootstrap-select.min" rel="stylesheet">
    </head>

    <body>
        <div class="container-narrow">

            <div class="masthead">
                <ul class="nav nav-pills pull-right">

                    <li><a href="../ClassesManager">Classes Manager</a></li>
                    <li class="active"><a>Class Manager</a></li>
                    <li ><a href="../logoff">Log Off</a></li>
                </ul>
                <h3 >Cloud Game Report</h3>
            </div>


            <center> <div  class="alert alert-danger" role="alert">${erromsg}</div></center>
            <center> <div  class="alert alert-success" role="alert">${sucessmsg}</div></center>

            <div class="mainbox">

                <h1 align="Center" class="form-signin-heading">Class Manager:</h1>
                <h3 class="form-signin-heading">Class Information:</h3>
                Class Name: ${ClassInfo.className}<br>
                Class Description: ${ClassInfo.classDescription}<br>
                Students Subscribed: ${StudentsSubscribed}<br>

                <h3 class="form-signin-heading">Games:</h3>
                <center>
                    
                    <table style="width:100%">
                        <tr> 
                            <td><b>Game Name</b></td> 
                            <td><b>Game Type</b></td> 
                            <td><b>Reference</b></td> 
                        </tr>
                        <c:forEach var="EntryItem" items="${GameEntryList}">
                            <tr> 
                                <td>${EntryItem.gameName}</td>
                                <td>${EntryItem.gameType.gametypeName}</td> 
                                <td>${EntryItem.gameReference}</td> 
                                <td>
                                    <div class="btn-group pull-right">
                                        <a href="../generateReport/${EntryItem.gameEntryID}">
                                            <button type="button" class="btn btn-default btn-success">Game Report</button></a>
                                        <a href="../deleteGameEntry/${ClassInfo.classID}/${EntryItem.gameEntryID}">
                                            <button type="button" onclick="return confirm('Are you sure you want delete ${EntryItem.gameType.gametypeName} Class?')" class="btn btn-default btn-danger">Delete</button></a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>

                    <hr>
                    
                    <form action="../addGame" method="POST">
                        <table>
                            <tr>  <td>Game Name:</td> <td><input name="gameName" type="text" required><br></td></tr>
                            <tr>  <td>Game Reference: </td> <td><input name="gameReference" type="text" required></td> </tr>
                        </table>

                        <select class="selectpicker" name="gameType">
                            <c:forEach var="GameType" items="${GameTypeList}">
                                <option value="${GameType.gametypeID}">(ID:${GameType.gametypeID}) ${GameType.gametypeName}</option>
                            </c:forEach>
                        </select>

                        <br>

                        <input type="hidden" name="classID" value="${ClassInfo.classID}">
                        <button type="submit" class="btn btn-default btn-success">Add Game</button>
                        <a href="../createNewGameType/${ClassInfo.classID}"><button type="button" class="btn btn-default">Create Your GameType</button></a>
                    </form>

                </center>

                <h3 class="form-signin-heading">Students Subscribed:</h3>
                
                <table style="width:100%">
                    <c:forEach var="SubscriptionItem" items="${SubscriptionList}">
                        <tr>
                            <td>${SubscriptionItem.playerID.fullName} </td>
                            <td> <div class="btn-group pull-right">

                                    <a href="../Unsubscribe/${ClassInfo.classID}/${SubscriptionItem.subscriptionID}">
                                        <button type="button" onclick="return confirm('Unsubscribe ${SubscriptionItem.playerID.fullName}?')" class="btn btn-default btn-danger">Unsubscribe</button></a>
                                <a href="../generateReport/${ClassInfo.classID}/${SubscriptionItem.playerID.userID}">
                                        <button type="button" class="btn btn-default btn-success">Report</button></a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

                <h3 class="form-signin-heading">Pending Requests</h3>
                
                <table style="width:100%">
                    <c:forEach var="SubscriptionItem" items="${PendingSubscripitions}">
                        <tr>
                            <td>${SubscriptionItem.playerID.nickname}</td>
                            <td> <div class="btn-group pull-right">

                                    <a href="../AcceptSubscription/${ClassInfo.classID}/${SubscriptionItem.subscriptionID}">
                                        <button type="button" class="btn btn-default btn-success">Accept Request</button></a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                
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
