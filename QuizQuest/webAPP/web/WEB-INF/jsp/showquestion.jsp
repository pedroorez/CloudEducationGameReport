<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html >
    
  <head>
    <title>Quiz Quest</title>
    <link href="../res/css/bootstrap.css" rel="stylesheet">
    <link href="../res/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="../res/css/intro.css" rel="stylesheet">
  </head>

  <body>
    <div class="container-narrow">

      <div class="masthead">
        <ul class="nav nav-pills pull-right">

                    <li class="active"><a href="../showBattleList">Battle Manager</a></li>
                    <li><a href="/logoff">Log Off</a></li>
                </ul>
        <h2 >Quiz Quest</h2>
      </div>

       
        <div class="mainbox">
            <h1 align="Center" class="form-signin-heading">Battle Manager</h1>
      <h3>Battle: ${battleInfo.battleTitle}</h3>
      <h5>Battle Reference: ${battleInfo.battleID}</h5>
      
      
      <table style="width:100%">
      <c:forEach var="question" items="${questions}">
          <tr>
          <td>${question.questionText}</td>
          <td>
              <div class="btn-group pull-right">
                  <a href="../deleteQuestionByID/${question.questionID}/${question.battleID}">
                <button type="button" class="btn btn-default btn-danger">Delete</button></a>
                <button type="button" class="btn btn-default btn-primary">Edit Question</button>
              </div>
          </td>
      </tr>
      </c:forEach>
      
      </table>
      
      <h3>Add New Question:</h3>
      <form action="../addQuestion" method="POST">
          <input name="BattleID"  type="hidden" value="${battleInfo.battleID}"/>
          <table align="center" >
              
              <tr> <td>Question:</td><td> <input name="questionText" type="text"/>   </td> </tr>
                
              <tr><td>Option 1:</td><td> <input name="option1" type="text"/>   </td> <td><input type="radio" name="awnser" value="1"></td></tr>
              <tr> <td>Option 2:</td><td> <input name="option2"  type="text"/>   </td><td><input type="radio" name="awnser" value="2"></td>  </tr>
              <tr> <td>Option 3:</td><td> <input name="option3"  type="text"/>   </td> <td><input type="radio" name="awnser" value="3"></td> </tr>
              <tr> <td>Option 4:</td><td> <input name="option4"  type="text"/>   </td> <td><input type="radio" name="awnser" value="4"></td> </tr>

              <tr><td align="center" colspan="3"><button type="submit" class="btn btn-default">Save Question</button><td></tr>
          </table>
          
      </form>
      
        </div>
         

      <div class="footer">
          <center> <p>&copy; Pedro Correa - DEL - UFRJ 2014</p> </center>
      </div>

    </div> <!-- /container -->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="../res/js/bootstrap.js"></script>
    <script src="../res/js/parsley.min.js"></script>
  </body>
</html>
