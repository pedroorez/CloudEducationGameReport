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

                    <div id="Fields"></div>     
                    <hr>
                    <input type="hidden" name="classID" value="${ClassInfo.classID}">
                    <button type="submit" class="btn btn-default btn-success">Create Game Type</button>
                    <a onClick="addMoreField()"><button type="button" class="btn btn-default">Add Another Field</button></a>
                </form>
                </center>

            </div>

            <div align="center" class="footer">
                <p>&copy; Pedro Correa - DEL - UFRJ 2014</p>
            </div>

        </div>
        <!-- /container -->

        <!-- Le javascript -->
        <script src="../res/js/jquery.js"></script>
        <script>
            var i = 1;
            addMoreField();
            function addMoreField(){

                var html =  '<hr>'+
                            '<table>'+
                            '<tr><td>Parameter Name: </td> <td><input name="paramName_'+i+'" type="text"><br></td></tr>'+
                            '<tr><td>Parameter Identificator: </td> <td><input name="paramIdentificator_'+i+'" type="text"><br></td></tr>'+
                            '<tr >'+
                            '    <td>Parameter Type :</td>'+
                            '    <td align="center">    '+
                            '<select class="selectpicker" name="paramType_'+i+'">'+
                            '        <option value="string">String</option>'+
                            '        <option value="number">Number</option>'+
                            '    </select>'+
                            '    </td></tr>'+
                            ' </table>';

                $( "#Fields" ).append( html );
                $( '#amountOfFields' ).val(i);
                i++;
            }
        </script>
    </body>
</html>
