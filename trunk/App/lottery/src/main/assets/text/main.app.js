var app = angular.module("app", [
    "ui.router",
    "oc.lazyLoad", 
]);

var DATA_URL = 'http://model.lottery.com/';
function PrefixInteger(num, n) {
    return (Array(n).join(0) + num).slice(-n);
}
app.factory('$req', function ($http, $rootScope, $filter,$location) {
    var batch = false;
    return {
        //
        // p:参数列表
        // p['error']  = 0 不弹出错误
        g: function (a, p, func) {
            if(batch){
                cl ('in batch');
                return;
            }
            var user_id;
            

            var params = $filter('json')(p);

            var url = DATA_URL + "?c=Jsonp&user_id="+user_id+"&action=" + a + "&params=" + params + "&rdm=" + Math.random() + "&callback=JSON_CALLBACK";
            var http = $http.jsonp(url);
            if (typeof func == 'function') {
                http.success(function (responce) {

                    
                    if (responce['errormsg']) { 
                        if(responce['errorCode'] == 'login_faild'){ 
                            $location.path( '/' );
                        }
                        if (p['error'] == 0) {
                            return;
                        }

                        jError(responce['errormsg']);
                    } else {

                        func(responce['result']);
                    }
                })
            } else {
                return http;
            }
            return;
        },
        batch:function(){
            batch = true;
        }
    }
});


var routeData = {};
routeData['selectCode'] = {
	url : '/selectCode',
	templateUrl:"selectCode"
};





app.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/");
    for (var i in routeData) {
        var data = routeData[i];
        data['templateUrl'] = data['templateUrl'].replace(".", "/");
        data['templateUrl'] = 'app/' + data['templateUrl'] + '.html';

        $stateProvider.state(i, data);
    }

     
}]);
