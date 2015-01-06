<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title> Quiz Quest</title>
        <link href="res/css/bootstrap.css" rel="stylesheet">
        <link href="res/css/bootstrap-responsive.css" rel="stylesheet">
        <link href="res/css/intro.css" rel="stylesheet">
    </head>

    <body>
        <div class="container-narrow">

            <div class="masthead">
                <ul class="nav nav-pills pull-right">

                    <li class="active"><a href="./showBattleList">Battle Manager</a></li>
                    <li><a href="./logoff">Log Off</a></li>
                </ul>
                <h2 >Quiz Quest</h2>
            </div>




            <div class="mainbox">
            <table style="width:100%">
                <h1 align="Center" class="form-signin-heading">Battle Manager</h1>
                <h2 class="form-signin-heading">Your Battles:</h2>
                <c:forEach var="BattleList" items="${BattleList}">
                    <tr>
                        <td>${BattleList.battleTitle}</td>
                        <td>
                            <div class="btn-group pull-right">
                                <a href="./deleteBattleByID/${BattleList.battleID}">
                                    <button type="button" class="btn btn-default btn-danger">Delete</button></a>
                                <a href="./showquestions/${BattleList.battleID}">
                                    <button type="button" class="btn btn-default btn-primary">Manage Questions</button></a>
                                <a href="./checkBattleResults/${BattleList.battleID}">
                                    <button type="button" class="btn btn-default btn-success">Check Results</button></a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
                
                
                <h3>Create a new Battle:</h3>
      <form action="./createBattle" method="POST">
          <table align="center" >
              
              <tr> <td>Battle Name:</td><td> <input name="Battlename" type="text"/></td> </tr>
                
              <tr><td>Battle Description:</td><td> <input name="BattleDescription" type="text"/>   </td> </tr>

              <tr><td align="center" colspan="3"><button type="submit" class="btn btn-default">Save Question</button><td></tr>
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
