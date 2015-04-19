/*We need to manually start angular as we need to
wait for the google charting libs to be ready*/  
google.setOnLoadCallback(function () {  
    angular.bootstrap(document.body, ['charter']);
});
google.load('visualization', '1', {packages: ['corechart', 'table']});
/************************************************************/

var charter = angular.module('charter',["chart.js"]);

charter.service('chartsManager', function(){
    this.getChartsList = function(){ return charts.list; };
    // Main Model
    var charts = [];
    charts.list =[];
    // a chart data model
    charts.list = { "options": [],
                    "drawlist":[]   };
    //reset chart
    this.resetData = function() {  charts.list = { "options": [],
                                                   "drawlist":[]   };}
    // add a new chart
    this.addChart = function() {charts.list.drawlist.push([]);}
    // set the parameters
    this.setParameters = function(data) { charts.list.parameters = data; };
    // set the data
    this.setData = function(data) { charts.list.data = data; };
    // google chart data builder
    this.buildChartData = function(i){
        // set empty option dictionary
        charts.list.drawlist[i].options = {};
        
        // get values
        chart = charts.list.drawlist[i];
        XAxis = {};
        YAxis = {};
        Weight = {};
        // set XAxis if available
        XAxis = charts.list.parameters[chart.XAxisID];
        chart.XAxis = XAxis;
        // set YAxis if available
        if(chart.YAxisID){
            YAxis = charts.list.parameters[chart.YAxisID];
            chart.YAxis = YAxis;
        }
        // set Weight if available
        if(chart.weightID){
            Weight = charts.list.parameters[chart.weightID];
            chart.Weight = Weight;
        }
        
        // create new datatable
        charts.list.dt = new google.visualization.DataTable();
        
        // chef if its a table special case
        if (chart.charttype === "Table"){
            charts.list.dt.addColumn("string", "Match ID");
            // add all columns
            charts.list.parameters.forEach(function(dt_elm, dt_inx, dt_ar){
                charts.list.dt.addColumn(dt_elm['value'], dt_elm['name']);
            });
            // add every row
            charts.list.data.forEach(function(dt_elm, dt_inx, dt_arg){
                row = [];
                // add basic rows
                row.push("Match ID " + dt_elm['matchid']);
                // generate generate row to be 
                charts.list.parameters.forEach(function(param_elm, param_inx, param_arg){
                    row.push(parseInt(dt_elm[param_elm['identifier']]));
                });
                charts.list.dt.addRow(row);
            });    
        }
        else {
            charts.list.dt.addColumn("string", "Match ID");
            charts.list.dt.addColumn(XAxis.value, XAxis.name);

            // check if its a bubblechart spcial case
            if (chart.charttype === "BubbleChart"){
                // add weight column
                charts.list.dt.addColumn("number", YAxis.name);
                charts.list.dt.addColumn("number", Weight.name);
                // data loop chart
                charts.list.data.forEach(function(dt_elm, dt_inx, dt_ar){
                    charts.list.dt.addRow([ "Match ID " + dt_elm["matchid"],
                                            parseInt(dt_elm[XAxis.identifier]), 
                                            parseInt(dt_elm[YAxis.identifier]), 
                                            parseInt(dt_elm[Weight.identifier])             ]);
                }); 
                // set axis properties
                charts.list.drawlist[i].options.hAxis = {title:  XAxis.identifier + " Axis" };
                charts.list.drawlist[i].options.vAxis = {title:  YAxis.identifier + " Axis"};       
                charts.list.drawlist[i].options.colorAxis = {legend:  { position: "bottom" } }; 
                charts.list.drawlist[i].options.title = XAxis.name + " by " + YAxis.name + " weighted by " + Weight.name + " Graph";

            }
            // normal chart if not a bubblechart
            else{
                // data loop chart
                charts.list.data.forEach(function(dt_elm, dt_inx, dt_ar){
                    charts.list.dt.addRow([ "Match ID " + dt_elm["matchid"], 
                                            parseInt(dt_elm[XAxis.identifier])    ]);
                }); 
                // set axis
                charts.list.drawlist[i].options.hAxis = {title: "Matches" + " Axis" , textPosition: "none"};
                charts.list.drawlist[i].options.vAxis = {title: XAxis.name + " Axis"};
                charts.list.drawlist[i].options.title = XAxis.name + " Graph";
            }
        }
        
        // sort and limit datatable
        if (charts.list.drawlist[i].orderby)
            charts.list.dt.sort([ {column: parseInt(charts.list.drawlist[i].orderby) } ]);
        if (charts.list.drawlist[i].limit)
            charts.list.dt.removeRows(parseInt(charts.list.drawlist[i].limit),9999);
        
        // Return the datatable
        return charts.list.dt;
    };
});

charter.directive("googleChart",function(chartsManager){  
    return{
        restrict : "A",
        scope: true,
        templateUrl: "/CloudGameReport/res/htmlParts/chartTemplate.html",
        link: function($scope, $elem, $attr){
            // set default parameters
            $scope.chart = $scope.charts.list.drawlist[$attr.chartNo];
            $scope.chart.orderby = "0";
            $scope.chart.XAxisID = "0";
            $scope.chart.charttype = "AreaChart";
            chartplace = $elem.find(".chartplace")[0];
            // draw function
            $scope.chart.draw =
                function(){
                    googleChart = new google.visualization[$scope.chart.charttype](chartplace);
                    googleChart.draw(chartsManager.buildChartData($attr.chartNo),
                                     $scope.chart.options);
                    
                    // hide what must be hidden
                    if ($scope.chart.charttype === "Table")
                    {
                        $scope.is_field_hidden.X = true;
                        $scope.is_field_hidden.Y = true;
                        $scope.is_field_hidden.Weight = true;
                        $scope.is_field_hidden.OrderBy = true;
                        $scope.is_field_hidden.Limit = true;
                    }
                    else if ($scope.chart.charttype === "BubbleChart")
                    {
                        $scope.is_field_hidden.X = false;
                        $scope.is_field_hidden.Y = false;
                        $scope.is_field_hidden.Weight = false;
                        $scope.is_field_hidden.OrderBy = false;
                        $scope.is_field_hidden.Limit = false;
                    }
                    else
                    {
                        $scope.is_field_hidden.X = false;
                        $scope.is_field_hidden.Y = true;
                        $scope.is_field_hidden.Weight = true;
                        $scope.is_field_hidden.OrderBy = false;
                        $scope.is_field_hidden.Limit = false;
                    }
                };
            // first draw 2times for "reasons"
            $scope.chart.draw();
            $scope.chart.draw();
            // set a resizer redraw
            $( window ).resize(function() { $scope.charts.list.drawlist[$attr.chartNo].draw(); });
            
        }
    };
});

charter.controller("chartsController",function($scope,$compile,chartsManager,$http){
    $scope.charts = [];
    $scope.is_field_hidden = {};
    $scope.charts.list = [];
    $scope.charts.drawlist = [];
    
    addChart = function(){
        chartsManager.addChart();
        charttemplate = "<div google-chart chart-no=\""+i+"\"></div>";
        $('#charts_place').append($compile(charttemplate)($scope));
        i++;  
        console.log($scope.charts);
    }
    
    // set a watcher for the add chart button
    i = 0;
    $('#addchart').click(addChart());
    
    $scope.getEntryData = function(){
        console.log("/CloudGameReport/generateJSONreport/"+$scope.gameEntryID+"/"+userID);
        chartsManager.resetData();

        $http.get("/CloudGameReport/generateJSONreport/"+$scope.gameEntryID+"/"+userID)
            // if sucessful set data to the model
            .success(function(responseData){
                    chartsManager.setData(responseData.data);
                    chartsManager.setParameters(responseData.parameters);
                    console.log(responseData);
                    i = 0;
                    //get chart reference
                    $scope.charts.list = chartsManager.getChartsList();
                    
                    if(responseData.data.length === 0  || responseData.parameters.length === 0){
                        $('#buttonplace').addClass("hide");
                        $('#erroplace').removeClass("hide");
                        $('#erroplace').html("<hr><h3> No data found for this game </h3><hr>")
                    }
                    else{
                        $('#erroplace').addClass("hide");
                        $('#buttonplace').removeClass("hide");
                    }

                    
            })
            .error(function(){
                $('#erroplace').removeClass("hide")
                $('#erroplace').html("<hr><h3> Error downloading chart data</h3><hr>")
            })
    }

});