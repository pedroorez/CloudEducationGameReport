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

          <li><a href="index.jsp">Home</a></li>
          <li class="active"><a href="./signup">Sign up</a></li>
          <li><a href="about.jsp">About</a></li>
        </ul>
        <h2 >Cloud Game Report</h2>
      </div>
        
        
        
        <br>
        <center> <div  class="alert alert-danger" role="alert">${erromsg}</div></center>
        <br>
      <form class="form-signin" action="./signup" method="POST">
        <h2 align="Center" class="form-signin-heading">Sign In!</h2>
        <input type="text" class="input-block-level" placeholder="Full Name" name="FullName">
        <input type="text" class="input-block-level" placeholder="Nickname" name="Nickname">
        <input type="password" class="input-block-level" placeholder="Password" name="Password">
        <center> <button class="btn btn-large btn-primary" type="submit">Sign in</button></center>
      </form>
   

     <br><br>

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
