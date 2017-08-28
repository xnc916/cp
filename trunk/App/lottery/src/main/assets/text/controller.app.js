app.controller('selectCode', function($scope, $req, $timeout) {

    $scope.play = {};
    $scope.test = function(p) {
        $scope.$apply(function() {
            $scope.play = p;
            $scope.init();
        });
        return;
        // $req.g('Games.getRule', { id: '3' }, function(responce) {
        //     $scope.play = responce;
        //     $scope.init();
        // });
    }

    //$scope.test();
    //初始化方法
    $scope.init = function() {
            $scope.singleCode = '';

            $scope.result = {};
            $scope.selectCode = [];
            $scope.selectPos = '';
            $scope.rowunsame = false; //两行不一致
            if ($scope.play.codeSelect == 'dantuo') $scope.rowunsame = true;
            //任选 - 添加号码选择

            $scope.posCheckBoxData = {};
            $scope.play.selectPos = [];
            $scope.playCodeSelect = $scope.play.codeSelect;
            if ($scope.playCodeSelect && $scope.playCodeSelect.indexOf('ingle') > 0) {

            } else {
                $scope.playCodeSelect = 'numberZone';
            }
            //任选玩法,号码位处理
            if ($scope.play.codeSelect == 'optionalGroup' || $scope.play.codeSelect == 'optionalSingle') {
                //设置默认值
                $timeout(function() {
                    for (var i = 0; i < $scope.play.codeBetLength; i++) {
                        $scope.selectCode[0][i] = true;
                        $scope.selectPosCheckbox[i] = true;
                        $scope.clickSelectPos();
                    }
                }, 500);
                $scope.selectPosCheckbox = {};
                $scope.posCheckBoxData = $scope.play.selectRule.codeZone.code[0].split('|');
                $scope.play.posCheckBoxData = $scope.posCheckBoxData;
                $scope.clickSelectPos = function() {
                    $scope.play.selectPos = [];
                    $scope.selectCode[0] = {};
                    $scope.faCount = 0;
                    for (var i in $scope.posCheckBoxData) {
                        $scope.selectCode[0][i] = false;
                        if ($scope.selectPosCheckbox[i]) {
                            $scope.play.selectPos.push(i);
                            $scope.selectCode[0][i] = $scope.selectPosCheckbox[i];
                        }
                    }
                    $scope.faCount = $gameMath.formula_zx_fs($scope.play.selectPos, $scope.play['codeBetLength']);
                    $scope.play.faCount = $scope.faCount;

                    $scope.refreshBidData();
                }


            }
            //和值 - 生成号码位
            if ($scope.play.codeSelect == 'sum' || $scope.play.codeSelect == 'sumGroup') {

            }
            //投注变化
            $scope.refreshBidData = function() {
                    $scope.result = GameMath.bidData($scope.selectCode, $scope.play);
                    $scope.result.selectExp = $scope.result.selectExp.toString();
                    appapiout('selectCode', $scope.result);
                }
                //去重
            $scope.clearRepeat = function() {
                    $scope.selectCode = arrUnique($scope.result.arrSelect);
                    $scope.selectCode = $scope.selectCode.join("\n");
                    $("#singleCodeTextarea").val($scope.selectCode);
                    $scope.refreshBidData();
                    return;
                }
                //清空
            $scope.clearCode = function() { 
                   // $scope.selectCode = ''; 
                   for(var i in $scope.selectCode){
                        for(var ii in $scope.selectCode[i]){
                            if($scope.selectCode[i][ii]){
                                $scope.selectCode[i][ii] = false;
                            }  
                            
                        }
                   } 
                    $("#singleCodeTextarea").val('');
                    $scope.$apply(function() {
                        $scope.refreshBidData();
                    });
                    
                    return;
                }
                //单式
            if ($scope.play.codeSelect == 'single' || $scope.play.codeSelect == 'optionalSingle') {
                $("#singleCodeTextarea").bind('keyup', function() {
                    $scope.selectCode = $(this).val();
                    $scope.refreshBidData();
                });
                $("#optionalSingleCodeTextarea").bind('keyup', function() {
                    $scope.selectCode = $scope.selectCode ? $scope.selectCode : {};
                    $scope.selectCode[1] = $(this).val();
                    $scope.refreshBidData();
                });
            }
            //复式 
            if ($scope.play.codeSelect != 'single' && $scope.play.codeSelect != 'optionalSingle') {
                if (!$scope.play.selectRule) return;
                $scope.codeZone = $scope.play.selectRule.codeZone;
                $scope.codes = {};
                if ($scope.codeZone && $scope.codeZone.title)
                    for (var i in $scope.codeZone.title) {
                        var v = $scope.codeZone.title;
                        if ($scope.codeZone.code[i].indexOf('-') > 0) {
                            var t = [];
                            var c = $scope.codeZone.code[i].split('-');
                            var padLength = 0;
                            if (c[1] >= 10) padLength = 2;
                            for (var d = parseInt(c[0]); d <= c[1] && d < 100; d++) {
                                if (padLength) {
                                    d = PrefixInteger(d, 2);
                                }
                                t.push(d);
                            }
                            $scope.codeZone.code[i] = t.join('|');
                        }
                        $scope.codes[i] = $scope.codeZone.code[i].split('|');

                        $scope.selectCode[i] = {};
                        for (var j = 0; j < $scope.codes[i].length; j++) {
                            $scope.selectCode[i][j] = false;
                        }

                    }
                    //选择号码
                $scope.chooseNumber = function(codePosK, val) {
                        $scope.selectCode[codePosK][val] = !$scope.selectCode[codePosK][val];
                        //胆拖作特殊处理
                        if ($scope.play.codeSelect == 'dantuo') {
                            $scope.play.codeZone['nobtn0'] = false;
                            //两行禁止重复
                            if ($scope.selectCode[codePosK][val]) {
                                for (var i in $scope.selectCode) {
                                    if (i == codePosK) continue;
                                    $scope.selectCode[i][val] = false;
                                }
                            }
                            //胆码号码数量限制 第一行必须为胆码
                            var danLen = 0;
                            for (var i in $scope.selectCode[0])
                                if ($scope.selectCode[0][i]) danLen++;
                                //消除一位
                            if (danLen >= $scope.play.codeBetLength) {
                                for (var i in $scope.selectCode[0])
                                    if ($scope.selectCode[0][i] && val != i) {
                                        $scope.selectCode[0][i] = false;
                                        break;
                                    }
                            }
                        }

                        $scope.refreshBidData();
                    }
                    //格式化号码至数组
                $scope.formatToSelectCodeArr = function() {
                    for (var rowK in $scope.selectCode) {
                        var v0 = $scope.selectCode[rowK];
                    }
                } 
                //附加功能
                $scope.addBtn = function(rowid, action) { 
                    var row = $scope.codes[rowid];
                        var result = {};
                        var allnum = 0;
                        for (var i in row) { allnum++; }
                        var midnum = allnum / 2; 
                        for (var i in row) {
                            var v = parseInt(row[i]);

                            result[i] = false;
                            if (action == 'all') {
                                result[i] = true;
                            }
                            if (action == 'big' && v >= midnum) {
                                result[i] = true;
                            }
                            if (action == 'small' && v < midnum) {
                                result[i] = true;
                            }
                            if (action == 'odd' && ((v) % 2)) {
                                result[i] = true;
                            }
                            if (action == 'even' && ((v) % 2) == 0) {
                                result[i] = true;
                            }

                        }
                        $scope.selectCode[rowid] = result;
                        $scope.refreshBidData();
                }
            }
            //单式
        }
        //  $scope.test();
});
