<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Cloud Game Report</title>
        <link href="res/css/bootstrap.css" rel="stylesheet">
        <link href="res/css/bootstrap-responsive.css" rel="stylesheet">
        <link href="res/css/intro.css" rel="stylesheet">
    </head>

    <body>
        <div class="container-narrow">

            <div class="masthead">
                <ul class="nav nav-pills pull-right">

                    <li class="active"><a href="./ClassesManager">Classes Manager</a></li>
                    <li><a href="./logoff">Log Off</a></li>
                </ul>
                <h3 >Cloud Game Report</h3>
            </div>


            <center> <div  class="alert alert-danger" role="alert">${erromsg}</div></center>
            <center> <div  class="alert alert-success" role="alert">${sucessmsg}</div></center>

            <div class="mainbox">
                <table style="width:100%">
                    <h1 align="Center" class="form-signin-heading">Classes Manager:</h1>
                    <h3 class="form-signin-heading">Classes:</h3>
                    <c:forEach var="ClassItem" items="${ClassesList}">
                        <tr>
                            <td>${ClassItem.className}</td>
                            <td>
                                <div class="btn-group pull-right">

                                    <a href="./manageInstances/${ClassItem.classID}">
                                        <button type="button" class="btn btn-default btn-primary">Manage Class</button></a>
                                    <a href="./deleteClass/${ClassItem.classID}">
                                        <button type="button" onclick="return confirm('Are you sure you want delete ${ClassItem.className} Class?')" class="btn btn-default btn-danger">Delete</button></a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </table>


                <h3>Create Class:</h3>
                <form action="./createClass" method="POST">
                    <table align="center" >
                        
                        <tr> <td>Class Name:</td><td> <input name="ClassName" type="text" required/></td> </tr>

                        <tr><td>Class Description:</td><td> <input name="ClassDescription" type="text" required/>   </td> </tr>

                        <tr><td align="center" colspan="3"><button type="submit" class="btn btn-default">Save Class</button><td></tr>
                    </table>

                </form>

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
