<html ng-app="ESIapp">

<link rel="stylesheet" href="/ESIa/res/css/style.css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<body>

2222
<div class="container" ng-controller="mainapp">

<div class="row"> 
    <div class="col-md-12">
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="navbar-header">
              <a class="navbar-brand" >ESIa</a>
            </div>
            
            <div>
              <ul class="nav navbar-nav">
                <li ng-hide="gamestatus.logged" ng-class="{active:isActive('/home')}"><a href="#/home">Home</a></li>
                <li ng-class="{active:isActive('/about')}"><a href="#/about">About</a></li>
                <li ng-show="gamestatus.logged" ng-class="{active:isActive('/gamelist')}"><a href="#/gamelist">Your Games</a></li> 
                <li ng-show="gamestatus.logged" ng-class="{active:isActive('/editor')}"><a href="#/editor">Editor</a></li>
                <li ng-show="gamestatus.logged" ng-class="{active:isActive('/API')}"><a href="#/API">API</a></li> 
                <li ng-hide="gamestatus.logged" ng-class="{active:isActive('/signup')}"><a href="#/signup">Sign Up</a></li> 

              </ul>
            </div>

      <form class="navbar-form navbar-right" style="margin-right:15px;" ng-hide="gamestatus.logged">
        <div class="form-group">
            <input type="text" ng-model="nickname" class="form-control" placeholder="Nickname">
            <input type="password" ng-model="password" class="form-control" placeholder="Password">
        </div>
          <button type="submit" ng-click="loguser()" class="btn btn-primary">Login</button>
      </form>
            <form class="navbar-form navbar-right" style="margin-right:15px;" ng-show="gamestatus.logged">
        <button type="submit" ng-click="logoffuser()"  class="btn btn-danger">Logoff</button>
            </form>
        </nav>    
    </div>

</div>
    </div>
    <div class="container-fluid"></div>
    <div style="height:90%; bottom: 0px; width: 100%; position: fixed">
        <ng-view ></ng-view>
    </div>
</div>


</body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.0-beta.1/angular.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.0-beta.1/angular-route.min.js"></script>
    <script src="/ESIa/res/js/ESIa-app.js"></script>
    <script src="/ESIa/res/js/uploaderApp.js"></script>
    <script src="/ESIa/res/js/services.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.0-beta.1/angular-cookies.min.js"></script>
</html>