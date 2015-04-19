//**************************************************************************************************//
google.setOnLoadCallback(function () { angular.bootstrap(document.body, ['charter']); });
google.load('visualization', '1', {packages: ['corechart', 'table']});
var charter = angular.module('charter',["chart.js"]);
//**************************************************************************************************//
//**************************************************************************************************//
charter.service('chartsManager', function(){
    // Main Model
    var charts = { 'list':{"options": [], "drawlist":[]} };
    //get chart list
    this.getChartsList = function(){ return charts.list; };
    //reset chart
    this.resetData = function() {  charts.list = { "options": [], "drawlist":[] };}
    // add a new chart
    this.addChart = function() {charts.list.drawlist.push([]); }
    // set the data
    this.setData = function(data) { charts.data = data; };
    // set the parameters
    this.setParameters = function(data) { charts.list.parameters = data; };
    // google chart data builder
    this.buildChartData = function(i){
        current_chart = charts.list.drawlist[i];
        if (current_chart.XAxisID == "0")
            current_chart.XAxisID = charts.list.parameters[0].identifier;
        current_data = charts.data[current_chart.XAxisID];
        // create new datatable
        current_data = transposeArray(current_data);
        for(j=0; j< current_data.length; j++){
            current_data[j].unshift("Match: "+j);
            for(k=0; k < current_data[j].length; k++){
                if(!current_data[j][k])
                    current_data[j][k] = 0;
            }
        }
        
        current_data[0][0] = "MatchID";
        charts.list.drawlist[i].dt = google.visualization.arrayToDataTable(current_data);
        // sort and limit datatable
        if (charts.list.drawlist[i].limit)
            charts.list.drawlist[i].dt.removeRows(parseInt(charts.list.drawlist[i].limit),9999);
        
        // Return the datatable
        return charts.list.drawlist[i].dt;
    };
});
//**************************************************************************************************//
//**************************************************************************************************//
charter.directive("googleChart",function(chartsManager){  
    return{
        restrict : "A",
        scope: true,
        templateUrl: "/CloudGameReport/res/htmlParts/gameChartTemplate.html",
        link: function($scope, $elem, $attr){
            // set default parameters
            $scope.chart = $scope.charts.list.drawlist[$attr.chartNo];
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
                        $scope.is_field_hidden.X = false;
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

//**************************************************************************************************//
//**************************************************************************************************//
charter.controller("chartsController",function($scope,$compile,chartsManager,$http){
    $scope.charts = [];
    $scope.is_field_hidden = {};
    $scope.charts.list = [];

    $scope.charts.list = chartsManager.getChartsList();
    
    chartsManager.setData(chartData);
    chartsManager.setParameters(parameters);
    
     addChart = function(){
        chartsManager.addChart();
        charttemplate = "<div google-chart chart-no=\""+i+"\"></div>";
        $('#charts_place').append($compile(charttemplate)($scope));
        i++;  
        console.log($scope.charts);
    }
    // set a watcher for the add chart button
    i = 0;
    $('#addchart').click(function(){addChart()});
});

function transposeArray(array) {
  return array[0].map(function (col, i) {
    return array.map(function (row) {
      return row[i];
    });
  });
}