var makeSelectArr = function(codes){
    if(!codes.length) return false;
    var result = [];
    for(var i in codes){

    }
}
var arrRand = function(){

}
var $randCode = {
    play:{},
    codes:[],
    getRand:function(play,number){
        this.play = play;
        //this[play.codeSelect](number);
        //取得号码位
        this.codes = codeArrToArr(this.play.selectRule.codeZone.code); 
        this[play.codeSelect](number);
    },
    directly:function(number){
        var rule;
        return this.math(rule);
    },
    //计算
    math:function(rule){
        var result = [];

    }
    
}
var $codeSelect = {

    //
    getBetData : function(p){ 
        var codeBetLength = p['codeBetLength'];//号码计算长度
        var codeLength = p['codeLength'];//号码位长度
        var codeZone = p['codeZone'];//投注区
        var codeSelect = p['codeSelect'];//已选择的号码
        var allowRepeat = p['codeRepeat'];//是否允许重复
        
        //计算开始 
        var count = 0; 
        var codeResult = [];
        var betComplate = true;
        //检测完整性
        for(var ind in codeZone['code']){ 
            var codeZoneItem = codeZone[ind];
            codeResult[ind] = [];
            for(var code in p['codeSelect'][ind]){
                if(p['codeSelect'][ind][code]){
                    codeResult[ind].push(code);
                }
            } 
            if(codeResult[ind].length == 0) betComplate = false;
        }
        if(!betComplate) return 0; 
        return;
    },
    dantuo:function(p){
        var count = 0; 
        count = $gameMath.formula_dt(p['selectArr'],0,p['play']['codeBetLength']-1);
        return {'count':count};
    },
    //定位胆
    location:function(p){
        var c = 0; 
        for (var i in p.selectArr) { 
            c += p.selectArr[i].length;
        }
        return {'count':c?c:0};
    },
    //单式 - 任选玩法
    optionalSingle:function(p){
       return {'count':0};
    },
    //直选
    directly: function(p) {
        var c = 1; 
        for (var i in p.selectArr) { 
            c *= p.selectArr[i].length;
        }
        return {'count':c?c:0};
    },
    //和值
    sum:function(p){
        var c = 0;
        var bingoCode = p.play.bingoCode.split('|');
         
        c = $gameMath.formula_value_add(p['selectArr'], NUM_VALUE["sum" + bingoCode.length]);
         
        return {'count':c?c:0};
    },
    //组选和值
    sumGroup:function(p){
        var c = 0;
        var bingoCode = p.play.bingoCode.split('|'); 
        c = $gameMath.formula_value_add(p['selectArr'], NUM_VALUE["sumGroup" + bingoCode.length]);
         
        return {'count':c?c:0};
    },
    //组选
    group : function(p){
        var count = 0;
        if(p.codeLength == 1){ // 组选120 
            count = $gameMath.formula_zx_fs(p['selectArr'],p['play']['codeBetLength']);
        }
        if(p.codeLength == 2){

            var betNums = [];
            var oneRow=false,moreRow=false,baseLen=false;

            for(var i in p['selectRule'].codeZone.title){ 
                var betNum = p['selectRule'].codeZone['betNum'][i]; 
                if(betNum == 1 && oneRow === false){
                    oneRow = i;
                }
                if(betNum > 1){
                    moreRow = i;
                    baseLen = betNum;
                }
            }
            
            if(moreRow === false) moreRow = (oneRow == 0) ? 1 : p['selectRule'].codeZone.title[0]?0:2;
            if(baseLen === false) baseLen = p['selectRule'].codeZone.betNum[moreRow];
            baseLen = baseLen ? baseLen : 1; 
            // if(oneRow === moreRow) moreRow  = !moreRow;
            if(p['baseLen']) baseLen = p['baseLen'];
            //baseLen = 1;
            count = $gameMath.formula_zx_one_more(p['selectArr'], oneRow, moreRow, baseLen);
 
        }
        return {'count':count?count:0};
    },
    //任选玩法复式
    optionalCompound:function(p){

        count = $gameMath.formula_rx_zhi_fs(p.selectArr, p.codeBetLength);
        return {'count':count?count:0};
    },
    //任选玩法组选
    optionalGroup:function(p){
        var p2 = p;
        //p2.selectArr.splice(0,1); 
        delete p2['selectRule'].codeZone['title'][0]; 
        p2.selectArr.splice(0,1);

        p2.codeLength--;

        var result = $codeSelect.group(p2);
        var count = result['count'] ? result['count'] : 0;
 
        var count0 = p.play.faCount;
        // count = $gameMath.formula_zx_fs(p['selectArr'][1],p['play']['codeBetLength']);
 
        count *= count0; 
        //count *= count0;
        return {'count':count?count:0};
    },
    //组合
    group1: function(p) {
        var result = $gameMath.formula_zx_fs(p['arrSelect'], p['codeBidLength']);
        return result;
    },
    group2: function(p) {
        var has = false;
        for (var i in p['codeSelect'][0]) {
            var v = p['codeSelect'][0][i];

            if (v == true) {
                if (p['codeSelect'][1][i]) has = true;
            }
        }
        var codeBidLength = p['codeBidLength'];
        var N = p.lengths[1] - (has ? 1 : 0);

        var M = codeBidLength - 2;
        var result = 0;
        result = computeSelection(N, M);
        result *= p.lengths[0] < 0 ? 0 : p.lengths[0];
        return result;
    },
    group22: function(p) {
        result = $gameMath.formula_zx_one_more(p['arrSelect'], 1, 0, 2);
        return result;
    },
    group3: function(p) {
        result = $gameMath.formula_zx_one_more(p['arrSelect'], 0, 1, p['codeBidLength'] - 3);
        return result;
    },
    group32: function(p) {
        result = $gameMath.formula_zx_one_more(p['arrSelect'], 0, 1, 1);
        return result;
    },
    group4: function(p) {
        result = $gameMath.formula_zx_one_more(p['arrSelect'], 0, 1, 1);
        return result;
    },
    sumval: function(p) {
        return $gameMath.formula_value_add(p['arrSelect'], NUM_VALUE["sumval" + p['codeBidLength']]);
    },
    span: function(p) {
        var c = 0;
        var bingoCode = p.play.bingoCode.split('|');
        var c = $gameMath.formula_value_add(p['selectArr'], NUM_VALUE["span" + bingoCode.length]);
        return {'count':c};
    },
    fold: function(p) {
        return $gameMath.formula_num_fold(p['arrSelect'], p['codeBidLength']);
    },
    getOriginal: function() {
        var me = this,
            balls = me.getBallData(),
            len = balls.length,
            len2 = balls[0].length;
        i = 0,
            j = 0,
            row = [],
            result = [];
        for (; i < len; i++) {
            row = [];
            for (j = 0; j < len2; j++) {
                if (balls[i][j] > 0) {
                    row.push(j);
                }
            }
            result.push(row);
        }
        return result;
    },
    //isGetNum
    getLottery: function(p) {

        var isGetNum = p['isGetNum'];
        var data = p['codeSelect'];
        var len = p.codeLength;
        var complete = false;
        var me = this,
            i = 0,
            row, isEmptySelect = true,
            _tempRow = [],
            j = 0,
            len2 = 0,
            result = [],
            //总注数
            total = 1,
            rowNum = 0;

        //检测球是否完整

        for (; i < len; i++) {
            result[i] = [];
            row = data[i];
            len2 = row.length;
            isEmptySelect = true;
            for (var j in row) {
                if (row[j]) {
                    isEmptySelect = false;
                    //需要计算组合则推入结果
                    if (!isGetNum) {
                        result[i].push(j);
                    }
                    rowNum++;
                }
            }
            if (isEmptySelect) {
                complete = false;
                return [];
            }
            //计算注数 
            total *= rowNum;
        }
        complete = true;

        //返回注数
        if (isGetNum) {
            return total;
        }

        if (complete) {
            //组合结果
            var a = combination(result);
            return a;
        } else {
            return [];
        }
    },
}

function combination(arr2) {
    if (arr2.length < 1) {
        return [];
    }
    var w = arr2[0].length,
        h = arr2.length,
        i, j,
        m = [],
        n,
        result = [],
        _row = [];
    m[i = h] = 1;
    while (i--) {
        m[i] = m[i + 1] * arr2[i].length;
    }
    n = m[0];
    for (i = 0; i < n; i++) {
        _row = [];
        for (j = 0; j < h; j++) {
            _row[j] = arr2[j][~~(i % m[j] / m[j + 1])];
        }
        result[i] = _row;
    }

    return result;
};
var $gameMath = {
    //任选-直选复式
    formula_rx_zhi_fs: function (num_arr, len) {
        var sum = 0;
        var arr = new Array();
        for (var key in num_arr) {
            if (num_arr[key].length > 0)
                arr[arr.length] = num_arr[key].length;
        }
        if (arr.length < len)
            return sum;
        var index = new Array();
        for (var i = 0; i < len; i++) {
            index[i] = i;
        }
        var m = 1;
        for (var i = 0; i < index.length; ++i) {
            m *= arr[index[i]];
        }
        sum += m;
        var _index = index;
        while (true) {
            var i;
            for (i = _index.length - 1; i >= 0; --i) {
                if (index[i] != i + arr.length - len) {
                    break;
                }
            }
            if (i < 0) {
                return sum;
            }
            index[i] += 1;
            for (var j = i + 1; j < len; ++j)
                index[j] = index[j - 1] + 1;
            var m = 1;
            for (var i = 0; i < index.length; ++i)
                m *= arr[index[i]];
            sum += m;
        }
        return sum;
    },
    //注数算法：选中号码的数量按倍数相加,参数为每选中一个号码增加的倍数
    formula_num_fold: function(num_arr, fold) {
        var count = 0;
        for (var key in num_arr) {
            for (var i = 0; i < num_arr[key].length; i++) {
                count += i * fold;
            }
        }
        return count;
    },
    //注数算法：选中号码的值相加
    formula_value_add: function(num_arr, val) {
        var count = 0;
        for (var key in num_arr) {
            for (var i = 0; i < num_arr[key].length; i++) {
                var text = num_arr[key][i];
                if (/[0][0-9]/.test(text) && text.length < 3) text = text.charAt(1);
                count += val[text];
            }
        }
        return count;
    },
    //组选120、24算法
    formula_zx_fs: function(num_arr, base_len) {
        var num_count = 0;
        for (var key in num_arr) {
            num_count += num_arr[key].length;
        }
        if (num_count < base_len)
            return 0;
        return $gameMath.formula_multiply_divide(num_count, base_len);
    },
    //组选
    formula_zx_one_more: function(num_arr, one_row, more_row, base_len) {

        if (num_arr[one_row].length < 1 || num_arr[more_row].length < base_len) {
            return 0;
        }
        var count = 0;
        var one_arr = num_arr[one_row];
        var more_arr = num_arr[more_row];
        for (var i = 0; i < one_arr.length; i++) {
            var one_val = one_arr[i];
            var more_len = more_arr.length;
            for (var j = 0; j < more_arr.length; j++) {
                if (one_val == more_arr[j]) {
                    more_len--;
                }
            }
            if (more_len < base_len) continue;
            count += $gameMath.formula_multiply_divide(more_len, base_len);
        }
        return count;
    },

    formula_multiply_divide: function(numLen, baseLen) {
        var count = 1;
        for (var i = 0; i < baseLen; i++) {
            count *= (numLen--);
        }
        for (; baseLen > 1; baseLen--) {
            count /= baseLen;
        }
        return count;
    },
    //胆拖
    formula_dt: function (num_arr, limit_row, base_len) {
        var count = 0;
        var limit_arr = num_arr[limit_row];
        //拖码长度大于0
        //拖码不能等于选号长度
        if (limit_arr.length == 0 || limit_arr.length > base_len) return count;
        var arr = new Array();
        for (var i = 0; i < num_arr.length; i++) {
            if (i != limit_row) arr[arr.length] = num_arr[i];
        } 
        if (limit_arr.length != 0 && limit_arr.length <= base_len) {
            for (var i = 0; i < arr.length; i++) {
                if (arr[i].length == 0) {
                    return 0;
                }
                else {
                    count += arr[i].length;
                }
            }
        } 
        return $gameMath.formula_multiply_divide(count, base_len - limit_arr.length + 1);
    },
}

var NUM_VALUE = {
    'sum3': {
        '0': 1,
        '1': 3,
        '2': 6,
        '3': 10,
        '4': 15,
        '5': 21,
        '6': 28,
        '7': 36,
        '8': 45,
        '9': 55,
        '10': 63,
        '11': 69,
        '12': 73,
        '13': 75,
        '14': 75,
        '15': 73,
        '16': 69,
        '17': 63,
        '18': 55,
        '19': 45,
        '20': 36,
        '21': 28,
        '22': 21,
        '23': 15,
        '24': 10,
        '25': 6,
        '26': 3,
        '27': 1
    },
    'span3': {
        '0': 10,
        '1': 54,
        '2': 96,
        '3': 126,
        '4': 144,
        '5': 150,
        '6': 144,
        '7': 126,
        '8': 96,
        '9': 54
    },
    'sumGroup3': {
        '1': 1,
        '2': 2,
        '3': 2,
        '4': 4,
        '5': 5,
        '6': 6,
        '7': 8,
        '8': 10,
        '9': 11,
        '10': 13,
        '11': 14,
        '12': 14,
        '13': 15,
        '14': 15,
        '15': 14,
        '16': 14,
        '17': 13,
        '18': 11,
        '19': 10,
        '20': 8,
        '21': 6,
        '22': 5,
        '23': 4,
        '24': 2,
        '25': 2,
        '26': 1
    },
    'sum2': {
        '0': 1,
        '1': 2,
        '2': 3,
        '3': 4,
        '4': 5,
        '5': 6,
        '6': 7,
        '7': 8,
        '8': 9,
        '9': 10,
        '10': 9,
        '11': 8,
        '12': 7,
        '13': 6,
        '14': 5,
        '15': 4,
        '16': 3,
        '17': 2,
        '18': 1
    },
    'span2': {
        '0': 10,
        '1': 18,
        '2': 16,
        '3': 14,
        '4': 12,
        '5': 10,
        '6': 8,
        '7': 6,
        '8': 4,
        '9': 2
    },
    'sumGroup2': {
        '1': 1,
        '2': 1,
        '3': 2,
        '4': 2,
        '5': 3,
        '6': 3,
        '7': 4,
        '8': 4,
        '9': 5,
        '10': 4,
        '11': 4,
        '12': 3,
        '13': 3,
        '14': 2,
        '15': 2,
        '16': 1,
        '17': 1
    },
    'fc_spice_012': {
        '000': 64,
        '001': 48,
        '002': 48,
        '010': 48,
        '011': 36,
        '012': 36,
        '020': 48,
        '021': 36,
        '022': 36,
        '100': 48,
        '101': 36,
        '102': 36,
        '110': 36,
        '111': 27,
        '112': 27,
        '120': 27,
        '121': 36,
        '122': 27,
        '200': 48,
        '201': 36,
        '202': 36,
        '210': 36,
        '211': 27,
        '212': 27,
        '220': 36,
        '221': 27,
        '222': 27
    },
    'ks_sbt': {
        '6': 1,
        '7': 1,
        '8': 2,
        '9': 3,
        '10': 3,
        '11': 3,
        '12': 3,
        '13': 2,
        '14': 1,
        '15': 1
    }
};
