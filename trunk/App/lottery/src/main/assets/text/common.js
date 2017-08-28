var app = angular.module("app", [ 
]);
function cl(msg){
	console.log(msg);
}
$plugins = {

    g: function(ids) {
        var data = {
            'nouisider': [
                'Public/plugins/nouislider/nouislider.js'
            ],

            'clock': [
                'Public/plugins/flipclock/css.css',
                'Public/plugins/flipclock/main.js',
            ],
            'dateTimePicker': [
                'Public/plugins/dateTimePicker/css/bootstrap-datetimepicker.min.css',
                'Public/plugins/dateTimePicker/js/bootstrap-datetimepicker.min.js',
            ],
            'datePicker': [
                'Public/plugins/datepicker/css/bootstrap-datepicker.min.css',
                'Public/plugins/datepicker/js/bootstrap-datepicker.min.js',
            ],
            'select2': [
                'Public/plugins/select2/select2.css',
                'Public/plugins/select2/select2.js',
            ],
            'zclip': [
                'Public/plugins/zclip/zclip.js',
            ],

        };
        var ids = ids.split(',');
        var result = [];
        for (var i in ids) {
            var id = ids[i];
            if (data[id]) {
                result = result.concat(data[id]);
            }
        }
        return result;
    }
}
function arrUnique(array) {
    var n = {},
        r = [],
        len = array.length,
        val, type;
    for (var i = 0; i < array.length; i++) {
        val = array[i];
        type = typeof val;
        if (!n[val]) {
            n[val] = [type];
            r.push(val);
        } else if (n[val].indexOf(type) < 0) {
            n[val].push(type);
            r.push(val);
        }
    }
    return r;
}
var scroll = function(id) { 
    $('html,body').animate({scrollTop:$('#'+id).offset().top},200);
}
    //取得cookie
function getCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';'); //把cookie分割成组
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i]; //取得字符串
        while (c.charAt(0) == ' ') { //判断一下字符串有没有前导空格
            c = c.substring(1, c.length); //有的话，从第二位开始取
        }
        if (c.indexOf(nameEQ) == 0) { //如果含有我们要的name
            return unescape(c.substring(nameEQ.length, c.length)); //解码并截取我们要值
        }
    }
    return false;
}
//清除cookie
function clearCookie(name) {
    setCookie(name, "", -1);
}
//clearCookie('user_id');
//设置cookie
function setCookie(name, value) {
    var seconds = 8640000; //seconds有值就直接赋值，没有为0，这个根php不一样。
    //var seconds = 1;
    var expires = "";
    if (seconds != 0) { //设置cookie生存时间
        var date = new Date();
        date.setTime(date.getTime() + (seconds * 1000));
        expires = "; expires=" + date.toGMTString();
    }
    document.cookie = name + "=" + escape(value) + expires + "; path=/"; //转码并赋值
}

var jsonMath = {
    trueLength: function(json) {
        var length = 0;
        for (var i in json) {
            if (json[i]) length++;
        }
        return length;
    }
}
var array_unique_code = function($codeArr) {
    $codeArr.sort(); //先排序 
    var res = [$codeArr[0]];
    for (var i = 1; i < $codeArr.length; i++) {
        if ($codeArr[i] !== res[res.length - 1]) {
            res.push($codeArr[i]);
        }
    }
    var result = res.length; 
    return result;
}
var checkCode_115 = function($codeArr){
    for(var i in $codeArr){
        if(parseInt($codeArr[i]) > 11 || parseInt($codeArr[i]) <= 0) return false;
    }
    return array_unique_code($codeArr);
}
    //号码解析为数组
var codeToArr = function(code) {
    if(code.indexOf('|')){
        return code.split('|');
    }
    var codeArr = code.split('');
    return codeArr;
}
var codeArrToArr = function(code) {
    if(!code.length) return false;
    var result = [];
    for(var i in code){
        result.push(codeToArr(code[i]));
    }
    return result;
}
String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

var GameMath = {

    //计算投注数量,格式化号码
    bidData: function(code, play) {
        var result = {};
        var selectRule = play.selectRule;
        //单式 
        if (play.codeSelect == 'single') {
            result['codeSelect'] = [];
            var code = code.replace(/[,;\n\s]/g, "!");

            result['arrSelect'] = code.split('!');
            //匹配模式
            var mathType = '';
            if (play.codeReg.substr(0, 1) == '/') mathType = 'reg';

            result['failCodes'] = [];

            for (var i in result['arrSelect']) {
                result['arrSelect'][i] = result['arrSelect'][i].replace(/\s/g, "|")
                var v = result['arrSelect'][i];
                //正则匹配
                if (mathType == 'reg') {

                    //var reg = new RegExp(play.codeReg);
                    var reg = eval(play.codeReg);
                }
                //检测重复
                var isFail = false;
                for (var j in result['codeSelect']) {
                    if (result['codeSelect'][j] == v) {
                        isFail = true;
                    }
                }

                if (!isFail && reg.test(v)) {

                    result['codeSelect'].push(v);
                } else {
                    result['failCodes'].push(v);
                }
            }
            result['count'] = result['codeSelect'].length;

            result['selectExp'] = result['codeSelect'].join(','); 
            
            var scriptRule = play.selectRule.scriptRule;
            if (result['count'] > 0 && $.trim(scriptRule) != '') {

                var selectExp = result['selectExp'].split(',');
                result['selectExp'] = '';
                result['codeSelect'] = [];

                for (var i in selectExp) {
                    var isSuccess = false;
                    var v = selectExp[i];
                    var $codeArr = codeToArr(selectExp[i]);
                    var evalStr = "if(" + scriptRule + ") isSuccess=true;"; 
                    eval(evalStr);
                    if (isSuccess) {
                        result['codeSelect'].push(v);
                    }else{
                        result['failCodes'].push(v);
                    }
                }
                result['count'] = result['codeSelect'].length;
                result['selectExp'] = result['codeSelect'].join(',');
            }


            return result;
        }
        if (play.codeSelect == 'optionalSingle') {
            var codes = code;
            result['codeSelect'] = [];
            if (!codes[1]) return;

            var code = codes[1].replace(/[,;\n\s]/g, "!").trim(); 
            result['arrSelect'] = code.split('!');
            //匹配模式
            var mathType = '';
            if (play.codeReg.substr(0, 1) == '/') mathType = 'reg';
            result['failCodes'] = [];
            for (var i in result['arrSelect']) {
                var v = result['arrSelect'][i];
                //正则匹配
                if (mathType == 'reg') {
                    var last = play.codeReg.lastIndexOf('/');
                    var regStr = play.codeReg.substr(1, last - 1);
                    var mode = play.codeReg.substr(last + 1);
                    //var reg = new RegExp(play.codeReg);
                    var reg = eval(play.codeReg);
                }
                //检测重复
                var isFail = false;
                for (var j in result['codeSelect']) {
                    if (result['codeSelect'][j] == v) {
                        isFail = true;
                    }
                }
                if (!isFail && reg.test(v)) {
                    result['codeSelect'].push(v);
                } else {
                    result['failCodes'].push(v);
                }
            }
            var c0 = [];
            for (var i in codes[0]) {
                if (codes[0][i]) {
                    c0.push(play.posCheckBoxData[i]);
                }
            }
            var c0 = c0.join('|');

            result['count'] = result['codeSelect'].length * play.faCount;
            result['selectExp'] = c0 + "," + result['codeSelect'].join(',');

            var scriptRule = play.selectRule.scriptRule;

            if (result['count'] > 0 && $.trim(scriptRule) != '') {

                var selectExp = result['codeSelect'];

                result['selectExp'] = '';
                result['codeSelect'] = [];

                for (var i in selectExp) {
                    var isSuccess = false;
                    var v = selectExp[i];
                    var $codeArr = codeToArr(selectExp[i]);

                    var evalStr = "if(" + scriptRule + ") isSuccess=true;"; 
                    eval(evalStr);
                    if (isSuccess) {
                        result['codeSelect'].push(v);
                    }else{
                        result['failCodes'].push(v);
                    }
                } 
                result['count'] = result['codeSelect'].length * play.faCount;
                result['selectExp'] = c0 + "," + result['codeSelect'].join(',');
            }
            return result;
        }
        //复式

        var selectCodeArr = [];
        for (var i in play.selectRule.codeZone.code) {
            selectCodeArr[i] = selectRule.codeZone.code[i].split('|');
        }

        var arr = [];
        var bidCount = 1;
        var selectExp = [];
        var codeLength = [];
        for (var i in code) {
            arr[i] = [];
            selectExp[i] = '';
            var row = code[i];
            var temp = [];
            var isSingleNumber = true;
            for (var ii in row) {
                if (row[ii]) {
                    arr[i].push(selectCodeArr[i][ii]);
                    //如果不是单位数字则分隔
                    var reg = /^[0-9]{1}$/gim;

                    if (!reg.test(selectCodeArr[i][ii])) isSingleNumber = false; 

                    temp.push(selectCodeArr[i][ii]);
                }

            }
            var fg = isSingleNumber ? '' : '|';
            selectExp[i] = temp.join(fg);
            codeLength[i] = jsonMath.trueLength(row);
        }
        // //计算BetLength
        // if(play.codeSelect == "directly"){ 
        //     play.codeBetLength = selectRule.codeZone.code.length;
        // }
        // if(play.codeSelect == "group"){

        //     for(var i in selectRule.codeZone.code){
        //         var v = selectRule.codeZone.code;
        //         var betNum = selectRule.codeZone.betNum[i];
        //         betNum = betNum ? betNum : 1;
        //         var 

        //     } 
        //     play.codeBetLength = selectRule.codeZone.code.length;
        // }

        var params = { 'selectLengths': codeLength, 'codeLength': play.selectRule.codeZone.code.length, 'codeBetLength': play.codeBetLength };

        params['play'] = play;
        params['selectRule'] = play.selectRule;

        result['selectCode'] = params['selectCode'] = code;
        result['selectArr'] = params['selectArr'] = arr;
        result['selectExp'] = selectExp;
        //params['codeZone'] = play.selectRule.;
        // 
        var r = $codeSelect[play.codeSelect](params);
        result['count'] = r['count'];
        if (parseInt(play.codeBetMultiple) > 0) result['count'] *= play.codeBetMultiple;
        //result['codeResult'] = $codeSelect.getLottery(params);

        //result['count'] = result['codeResult'].length;
        return result;

    }
}

function overView(obj) {
    var obj = $("#" + obj);

    var objWidth = obj.width();

    var screenWidth = document.body.clientWidth;
    obj.css('margin-left', (screenWidth - objWidth) / 2);
}

function overViewAll() {
    //overView('footer');
    overView('bodybg1');
}

function jsonMatch(json, k, v) {
    var result = {};
    var has = 0;
    for (var i in json) {
        if (has != 0) continue;
        if (json[i][k] == v) {
            result = json[i];
            has = 1;
        }
    }
    return result;
}

function unixToDate(str) {
    if (!str) return '';
    str = str.replace(/-/g, "/");
    var date = new Date(str);
    var humanDate = new Date(Date.UTC(date.getFullYear(), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds()));
    var result = humanDate.getTime() / 1000 - 8 * 60 * 60;
    return result;
}

var T = {};

function U(a, b) {
    var c, d;
    2 * b > a && (b = a - b);
    for (c = 0; c <= a; c++) c in T || (T[c] = [], T[c][0] = 1);
    for (d = 1; d <= b; d++) T[0][d] = 0;
    if (a in T && b in T[a]) return T[a][b];
    for (c = a; 1 < c && !(b in T[c]); c--);
    for (var e = b; 1 < e && !(e in T[a]); e--);
    for (; c <= a; c++)
        for (d = e; d <= b; d++) T[c][d] = T[c - 1][d - 1] + T[c - 1][d];

    return T[a][b]
}





//组合计算
function computeSelection(N, M) {
    if (N < M) return 0;
    var a = false, //是否具有顺序
        b = false, //是否可以选择一次以上
        c = N,
        d = M,
        k = "";
    var c = c,
        n, h, p;
    if (b) a ? (n = Math.pow(c, d), p = h = "") : (n = U(parseInt(c + d - 1), parseInt(d)), h = "");
    else if (a) {
        n = 1;
        p = parseInt(c);
        for (h = p - parseInt(d) + 1; h <= p; h++) n *= h;

    } else n = U(parseInt(c), parseInt(d)),
        h = "",
        p = "";
    return n;


}
