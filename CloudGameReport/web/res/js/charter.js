/*We need to manually start angular as we need to
wait for the google charting libs to be ready*/  
google.setOnLoadCallback(function () {  
    angular.bootstrap(document.body, ['charter']);
});
google.load('visualization', '1', {packages: ['corechart', 'table']});
/************************************************************/

var charter = angular.module('charter',["chart.js"]);

charter.service('chartsManager', function(){
    this.getCharts = function(){ return charts; };
    // Main Model
    var charts = [];
    charts.list =[];
    // a chart data model
    charts.list = { "parameters": [ {"type":"column", "identifier":"points", "name":"Points","value":"number"},
                                    {"type":"column", "identifier":"time", "name":"Time","value":"number"},
                                    {"type":"column", "identifier":"rightans", "name":"Right Answers","value":"number"},
                                    {"type":"column", "identifier":"wrongans", "name":"Wrong Answers","value":"number"},
                                    {"type":"column", "identifier":"numofshots", "name":"# of Shots","value":"number"}],
                    "data":[{"matchid":"1", "player":"josh",  "time":"666", "rightans":"3", "wrongans":"22", "numofshots":"12"},
                            {"matchid":"2", "player":"josh", "points":"846", "time":"066", "rightans":"4", "wrongans":"12", "numofshots":"87"},
                            {"matchid":"3", "player":"josh",  "time":"006", "rightans":"2", "wrongans":"34", "numofshots":"12"},
                            {"matchid":"4", "player":"josh", "points":"987", "time":"012", "rightans":"7", "wrongans":"01", "numofshots":"87"},
                            {"matchid":"5", "player":"josh",  "time":"623", "rightans":"5", "wrongans":"04", "numofshots":"34"},
                            {"matchid":"6", "player":"josh", "points":"542", "time":"623", "rightans":"5", "wrongans":"04", "numofshots":"34"},
                            {"matchid":"7", "player":"josh", "points":"846", "time":"066", "rightans":"4", "wrongans":"12", "numofshots":"87"},
                            {"matchid":"8", "player":"josh",  "time":"006", "rightans":"2", "wrongans":"34", "numofshots":"12"},
                            {"matchid":"9", "player":"josh", "points":"542", "time":"623", "rightans":"5", "wrongans":"04", "numofshots":"34"},
                            {"matchid":"10", "player":"josh", "points":"444", "time":"056", "rightans":"9", "wrongans":"06", "numofshots":"65"}], 
                    "options": [],
                    "drawlist":[]};
                   
    // google chart data builder
    this.buildChartData = function(i){
        // set empty option dictionary
        charts.list.drawlist[i].options = {};
        // get values
        chart = charts.list.drawlist[i];
        XAxis = chart.XAxis = charts.list.parameters[i];
        YAxis = {};
        Weight = {};
        
        // set YAxis if available
        if(chart.YAxisID){
            YAxis = charts.list.parameters[chart.YAxisID];
            console.log(YAxis);
            chart.YAxis = YAxis;
        }
        // set Weight if available
        if(chart.weightID){
            Weight = charts.list.parameters[chart.weightID];
            chart.Weight = Weight;
        }

        // create new datatable
        charts.list.dt = new google.visualization.DataTable();
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
        templateUrl: "../res/htmlParts/chartTemplate.html",
        link: function($scope, $elem, $attr){
            // create a draw function callback
            $scope.charts.list.drawlist[$attr.chartNo] = [];
            $scope.chart = $scope.charts.list.drawlist[$attr.chartNo];
            // set default order
            $scope.chart.orderby = 0;
            // draw function
            $scope.chart.draw =
                function(){
                    // set options  --- redo --
                    options = $scope.chart.options;
                    // get charttype
                    if (!$scope.chart.charttype)
                       $scope.chart.charttype = "AreaChart";
                    // create chart element
                    var googleChart = new google.visualization[$scope.chart.charttype]($elem.find(".chartplace")[0]);
                    // build chart datatable
                    dt = chartsManager.buildChartData($attr.chartNo);
                    // draw
                    googleChart.draw(dt,options);
                    // check if hidden
                    if ($scope.chart.charttype === "BubbleChart" || 
                        $scope.chart.charttype === "ScatterChart" )
                        $scope.chart.is_hidden = false;
                    else
                        $scope.chart.is_hidden = true;
                };
            // first draw 2times for "reasons"
            $scope.chart.draw();
            $scope.chart.draw();
            // set a resizer redraw
            $( window ).resize(function() { $scope.charts.list.drawlist[$attr.chartNo].draw(); });
            
        }
    };
});

charter.controller("chartsController",function($scope,chartsManager){
    // twoway data bind charts data
    $scope.charts = chartsManager.getCharts();
});