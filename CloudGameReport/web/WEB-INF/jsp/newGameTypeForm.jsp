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
                    <li class="active"><a>New Game Type</a></li>
                    <li ><a href="../logoff">Log Off</a></li>
                </ul>
                <h3 >Cloud Game Report</h3>
                
            </div>

            <center> <div  class="alert alert-danger" role="alert">${erromsg}</div></center>
            <center> <div  class="alert alert-success" role="alert">${sucessmsg}</div></center>

            <div class="mainbox">

                <h1 align="Center" class="form-signin-heading">Game Type Creator</h1>
<br>
                <center>
               <form action="../saveGameType" method="POST">
                   New GameType Name: <input name="gameTypeName" type="text">
                   <input id="amountOfFields" type="hidden" name="amoutOfFields" value="1">
                   <input type="hidden" name="classID" value="${ClassID}">
                   <hr>
                   <div id="Fields">
                   <table>
                            <tr><td>Field Name: </td> <td><input name="fieldName_1" type="text"><br></td></tr>
                            <tr><td>Field Identificator: </td> <td><input name="fieldIdentificator_1" type="text"><br></td></tr>
                            
                            <tr >
                                <td>Display Type: </td>
                                <td align="center">    
                            <select class="selectpicker" name="displaytype_1">
                                    <option value="SUM">Sum</option>
                                    <option value="LIST">List</option>
                                </select>
                                </td></tr>
                        </table>
                   </div>     
                   
                   

                        <br>

                        <input type="hidden" name="classID" value="${ClassInfo.classID}">
                        <button type="submit" class="btn btn-default btn-success">Create Game Type</button>
                        <a onClick="addMoreField()"><button type="button" class="btn btn-default">Add Another Field</button></a>
                    </form>
                </center>


            </div>



            <div align="center" class="footer">
                <p>&copy; Pedro Correa - DEL - UFRJ 2014</p>
            </div>

        </div> <!-- /container -->

        <!-- Le javascript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="../res/js/jquery.js"></script>
        <script src="../res/js/bootstrap-transition.js"></script>
        <script src="../res/js/bootstrap-alert.js"></script>
        <script src="../res/js/bootstrap-modal.js"></script>
        <script src="../res/js/bootstrap-dropdown.js"></script>
        <script src="../res/js/bootstrap-scrollspy.js"></script>
        <script src="../res/js/bootstrap-tab.js"></script>
        <script src="../res/js/bootstrap-tooltip.js"></script>
        <script src="../res/js/bootstrap-popover.js"></script>
        <script src="../res/js/bootstrap-button.js"></script>
        <script src="../res/js/bootstrap-collapse.js"></script>
        <script src="../res/js/bootstrap-carousel.js"></script>
        <script src="../res/js/bootstrap-typeahead.js"></script>
        <script src="../res/js/addNewField.js"></script>

    </body>
</html>
