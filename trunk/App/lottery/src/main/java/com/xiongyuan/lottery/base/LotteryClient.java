package com.xiongyuan.lottery.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xiongyuan.lottery.CachePreferences;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by gameben on 2017-06-06.
 */

public class LotteryClient {

        private static LotteryClient lotteryClient;

        private OkHttpClient okHttpClient;
        private Gson gson;

        public static LotteryClient getInstance() {
            if (lotteryClient == null) {
                lotteryClient = new LotteryClient();
            }
            return lotteryClient;
        }


        private LotteryClient() {
            // 设置GSON的非严格模式setLenient()
            gson = new GsonBuilder().setLenient().create();
            //初始化日志拦截器
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            //设置拦截级别
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder()
                    //添加日志拦截器
                    .addInterceptor(httpLoggingInterceptor)
                    .build();

        }

        //生成注册链接码
        public Call getRegisterLink(String uid, String maxr, String minR, String type){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","RegCode.add")
                    .add("user_id",uid)
                    .add("maxRebate",maxr)
                    .add("minRebate",minR)
                    .add("type",type)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }

        //注册
        public Call getRegister(String code, String username, String password, String repsw){
            RequestBody requestbody = new FormBody.Builder()
                    .add("action","Users.register")
                    .add("code",code)
                    .add("username",username)
                    .add("password",password)
                    .add("rePassword",repsw)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestbody)
                    .build();
            return okHttpClient.newCall(request);
        }
        //获取银行卡
        public Call getBankcard(String uid){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","Users.getBankCards")
                    .add("user_id",uid)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }
        //提现说明
        public Call getReflectThat(String uid){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","Users.getCashConf")
                    .add("user_id",uid)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }

        //余额提现
        public Call getBalance(String uid, String moneynum, String bank_id, String bank_psw){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","Users.cash")
                    .add("user_id",uid)
                    .add("amount",moneynum)
                    .add("card_id",bank_id)
                    .add("bankPassword",bank_psw)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }
        //获取系统消息
        public Call getSystemmsg(String uid,String page){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","Messages.getSystems")
                    .add("user_id",uid)
                    .add("page",page)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }
        //保存已读消息状态
        public Call getStatus(String uid, String msgid){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","Messages.setRead")
                    .add("user_id",uid)
                    .add("id",msgid)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }
        //取得彩种分类/玩法
        public Call getGamePaly(String game_id){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","Games.getData")
                    .add("id",game_id)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }
        //取得最近三期开奖时间
        public Call getGameTime(String game_id){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","Games.getCurIssue")
                    .add("id",game_id)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }
        //获取帮助菜单
        public Call getHelpMenu(String uid){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","Users.getHelpCatalogs")
                    .add("user_id",uid)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }

        //获取文章
        public Call getArticle(String uid,String menus_id){

            RequestBody requestboy = new FormBody.Builder()
                    .add("action","News.getNews")
                    .add("user_id",uid)
                    .add("id",menus_id)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }
        //投注
        public Call getBet(String uid, String ztou, String zqian, String data){

            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url()+"action=Games.bet&user_id="+uid+"&allCount="+ztou+"&allAmount="+zqian+"&betData="+data)
                    .get()
                    .build();

            return okHttpClient.newCall(request);

        }
        //游戏种类列表
        public Call getGameList(String group){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","Games.getNameList")
                    .add("group",group)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }
        //用户信息
        public Call getUserInfo(String uid){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","Users.getUserInfo")
                    .add("user_id",uid)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }
        //投注记录
        public Call getTouzhuji(String uid,String pages,String search){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","Users.getBetLogs")
                    .add("user_id",uid)
                    .add("type","1")
                    .add("search",search)
                    .add("page",pages)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }
        //取得追号数据
        public Call getzhuih(String uid,String page){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","Games.getChaseList")
                    .add("user_id",uid)
                    .add("page",page)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url()+"action=Games.getChaseList&user_id="+uid+"&page="+page)
                    .get()
                    .build();
            return okHttpClient.newCall(request);
        }
        //当日首冲礼包领奖
        public Call getFirstCharge(String uid){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","Users.firstRecGetPrize")
                    .add("user_id",uid)
                    .add("news_id","17")
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }
        //充值抢红包
        public Call getQHb(String uid,String qualif){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","Users.recGetPrize")
                    .add("user_id",uid)
                    .add("news_id","16")
                    .add("qualif",qualif)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }
        //获取充值抢红包活动详情
        public Call getQHbxq(String uid){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","Users.gerRecActivInfo")
                    .add("user_id",uid)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }
        //当日消费积分抽奖
        public Call getDrxfjfcj(String uid){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","Users.integralLottery")
                    .add("user_id",uid)
                    .add("news_id","6")
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }
        //获取积分活动详情
        public Call getHqjfhdxq(String uid){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","Users.getInteLotInfo")
                    .add("user_id",uid)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }
        //积分记录
        public Call getJfjl(String search,String gt,String page){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","Users.getConsIntegLogs")
                    .add("user_id",CachePreferences.getUser().getUser_id())
                    .add("search",search)
                    .add("page",page)
                    .add("type",gt)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }
        //获取用户当日积分
        public Call getDrjf(String uid,String type){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","Users.getDailyIntegral")
                    .add("user_id",uid)
                    .add("type",type)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }
        //取得未来X期的开奖数据
        public Call getFuture(String gameid,String num){
            RequestBody requestboy = new FormBody.Builder()
                    .add("action","Games.getFutureIssue")
                    .add("id",gameid)
                    .add("num",num)
                    .build();
            Request request = new Request.Builder()
                    .url(CachePreferences.getUser().getUser_url())
                    .post(requestboy)
                    .build();
            return okHttpClient.newCall(request);
        }
    //追号
    public Call getchaseBet(String gameid,String uid,String winstop, String ztou, String data){

        Request request = new Request.Builder()
                .url(CachePreferences.getUser().getUser_url()+"action=Games.chaseBet&game_id="+gameid+"&user_id="+uid+"&winStop="+winstop+"&issueData="+ztou+"&betData="+data)
                .get()
                .build();

        return okHttpClient.newCall(request);
    }

    //登陆
    public Call getLogin(String name,String password){
        Request request = new Request.Builder()
                .url(CachePreferences.getUser().getUser_url()+"action=Users.login&username="+name+"&password="+password)
                .get()
                .build();
        return okHttpClient.newCall(request);

    }

    //获取支付信息
    public Call getPay(String userid){
        Request request = new Request.Builder()
                .url(CachePreferences.getUser().getUser_url()+"action=Pay.getConfig&user_id="+userid)
                .get()
                .build();
        return okHttpClient.newCall(request);

    }
    //提交支付
    public Call getPayjiao(String userid,String payway,String money){
        Request request = new Request.Builder()
                .url(CachePreferences.getUser().getUser_url()+"action=Pay.newOrder&user_id="+userid+"&pay_way="+payway+"&order_amount="+money)
                .get()
                .build();
        return okHttpClient.newCall(request);
    }
    //查询支付结果
    public Call getPayjieg(String userid,String log_id){
        Request request = new Request.Builder()
                .url(CachePreferences.getUser().getUser_url()+"action=Pay.checkOrder&user_id="+userid+"&log_id="+log_id)
                .get()
                .build();
        return okHttpClient.newCall(request);
    }

    //App版本
    public Call getVersions(){
        Request request = new Request.Builder()
                .url(CachePreferences.getUser().getUser_url()+"action=Configure.appVersion")
                .get()
                .build();
        return okHttpClient.newCall(request);
    }

    //撤单
    public Call getCHe(String userid,String id){
        Request request = new Request.Builder()
                .url(CachePreferences.getUser().getUser_url()+"action=Games.cancelBet&user_id="+userid+"&id="+id)
                .get()
                .build();
        return okHttpClient.newCall(request);
    }
    //团队报表
    public Call getBaob(String page,String start,String end){
        Request request = new Request.Builder()
                .url(CachePreferences.getUser().getUser_url()+"action=Users.teamReport&user_id="+CachePreferences.getUser().getUser_id()+"&page="+page+"&startTime="+start+"&endTime="+end)
                .get()
                .build();
        return okHttpClient.newCall(request);
    }

}
