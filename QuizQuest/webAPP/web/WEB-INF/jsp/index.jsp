<html>
    <head>
        <title>Quiz Quest</title>
        <link href="res/css/bootstrap.css" rel="stylesheet">
        <link href="res/css/bootstrap-responsive.css" rel="stylesheet">
        <link href="res/css/intro.css" rel="stylesheet">
    </head>

    <body>
        <div class="container-narrow">

            <div class="masthead">
                <ul class="nav nav-pills pull-right">
                    <li class="active"><a href="index.jsp">Home</a></li>
                    <li><a href="./signup">Sign up</a></li>
                    <li><a href="about.jsp">About</a></li>

                    <li><form action="./login" method="POST" class="form-inline" >
                            <input type="text"  name="Nickname" style="width:100px;height:30px; " placeholder="Login"/>
                            <input type="password" name="Password" style="width:100px;height:30px;" placeholder="Password"/>
                            <button type="submit"  class="btn btn-primary">Login</button>
                        </form>
                    </li>


                </ul>
                <h2 >Quiz Quest</h2>
            </div>


            <br>
        <center> <div  class="alert alert-danger" role="alert">${erromsg}</div></center>
        <br>

            <div class="jumbotron">
                <h1>The Learning RPG!</h1>
                <p class="lead">Learn while playing!</p>
                <a class="btn btn-large btn-success" href="./signup">Sign up today to create your own adventure</a>
            </div>



            <div class="row-fluid marketing">
                <div class="span6">
                    <h4>Fight Monsters!</h4>
                    <p>Donec id elit non mi porta gravida at eget metus. Maecenas faucibus mollis interdum.</p>

                    <h4>Win Awesome Loot!</h4>
                    <p>Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Cras mattis consectetur purus sit amet fermentum.</p>

                    <h4>Learn Something!</h4>
                    <p>Maecenas sed diam eget risus varius blandit sit amet non magna.</p>
                </div>
                <div class="span6">
                    <h4>Fight Monsters!</h4>
                    <p>Donec id elit non mi porta gravida at eget metus. Maecenas faucibus mollis interdum.</p>

                    <h4>Win Awesome Loot!</h4>
                    <p>Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Cras mattis consectetur purus sit amet fermentum.</p>

                    <h4>Learn Something!</h4>
                    <p>Maecenas sed diam eget risus varius blandit sit amet non magna.</p>
                </div>

            </div>


            <br>

            <div class="footer">
                <center> <p>&copy; Pedro Correa - DEL - UFRJ 2014</p></center>
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
