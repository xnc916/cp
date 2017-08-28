package com.xiongyuan.lottery.homepage.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.xiongyuan.lottery.LoginActivity;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseFragment;
import com.xiongyuan.lottery.bean.LoginEvent;
import com.xiongyuan.lottery.homepage.activity.CodeHistoryActivity;
import com.xiongyuan.lottery.homepage.activity.OnlineActivity;
import com.xiongyuan.lottery.homepage.activity.PKActivity;
import com.xiongyuan.lottery.homepage.bean.NewsBean;
import com.xiongyuan.lottery.homepage.bean.TopImageBean;
import com.xiongyuan.lottery.homepage.view.FadingScrollView;
import com.xiongyuan.lottery.mypage.activity.HelpCenterActivity;
import com.xiongyuan.lottery.mypage.activity.HelpCenterNewtActivity;
import com.xiongyuan.lottery.secondpage.activity.MoreActivity;
import com.xiongyuan.lottery.thirdpage.fragment.ThirdFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {
    @BindView(R.id.home_kjxx)
    TextView homeKjxx;
    @BindView(R.id.home_ttjc)
    TextView homeTtjc;
    @BindView(R.id.home_jchd)
    TextView homeJchd;
    @BindView(R.id.home_yxwfjs)
    TextView homeYxwfjs;
    @BindView(R.id.home_cpzx)
    TextView homeCpzx;
    @BindView(R.id.home_gywm)
    TextView homeGywm;
    @BindView(R.id.home_zxkf)
    TextView homeZxkf;

    private ArrayList<TopImageBean> imgLists;
    @BindView(R.id.nac_layout)
    RelativeLayout layout;
    @BindView(R.id.nac_root)
    FadingScrollView fadingScrollView;
    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    @BindView(R.id.title_name)
    TextView titleName;
//    @BindView(R.id.text)
//    VerticalTextview text;
    private ArrayList<String> titleLists;
    private ArrayList<String> titleList;
    private ArrayList<NewsBean> newsBeanList;
    private boolean isLogin = false;
    private String userid;
    private ThirdFragment thirdFragment;//精彩内容
    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_home;
    }


    @Override
    public void initView() {
        titleName.setText("首页购彩");
        layout.setAlpha(0);
        fadingScrollView.setFadingView(layout);
        fadingScrollView.setFadingHeightView(convenientBanner);

//        text.setTextList(titleList);//加入显示内容,集合类型
//        text.setText(15, 15, Color.GRAY);//设置属性,具体跟踪源码
//        text.setTextStillTime(3000);//设置停留时长间隔
//        text.setAnimTime(300);//设置进入和退出的时间间隔
//
//        //对单条文字的点击监听
//        text.setOnItemClickListener(new VerticalTextview.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                if (isLogin) {
//                    Intent intent = new Intent(getActivity(), TextItemActivity.class);
//                    intent.putExtra("title", newsBeanList.get(position).getTitle());
//                    intent.putExtra("content", newsBeanList.get(position).getContent());
//                    startActivity(intent);
//                } else {
//                    Intent intent = new Intent(getActivity(), LoginActivity.class);
//                    startActivity(intent);
//                }
//
//            }
//        });


        if (convenientBanner != null) {
            convenientBanner.setPages(new CBViewHolderCreator() {
                @Override
                public Object createHolder() {
                    return new LocalImageHolderView();
                }
            }, imgLists) //设置需要切换的View
                    .setPointViewVisible(true)    //设置指示器是否可见
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                    .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                    //设置指示器位置（左、中、右）
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                    .setManualPageable(true);//设置手动影响（设置了该项无法手动切换）

            if (!convenientBanner.isTurning()) {
                if (imgLists.size() > 1) {
                    convenientBanner.startTurning(4000);     //设置自动切换（同时设置了切换时间间隔）
                }
            }
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onShowDataEvent(LoginEvent event) {
        //将获取的数据展示在界面上
        userid = event.getUserId();
        isLogin = event.isLogin();
//        if (isLogin) {
//
//            getNews();
//
//        } else {
//            initViews();
//        }
    }

    //获取系统公告
//    private void getNews() {
//
//        if (titleLists != null) {
//            titleList.clear();
//        }
//        if (newsBeanList != null) {
//            newsBeanList.clear();
//        }
//        Call call = LotteryClient.getInstance().getArticle(userid, "88");
//        call.enqueue(new UICallBack() {
//            @Override
//            public void onFailureUI(Call call, IOException e) {
//                ToastUtils.showToast(getActivity(), e.getMessage());
//            }
//
//            @Override
//            public void onResponseUI(Call call, String body) {
//
//                SysNewsResult result = new Gson().fromJson(body, SysNewsResult.class);
//                List<SysNewsResult.ResultBean.DataBean> list = result.getResult().getData();
//
//                for (int i = 0; i < list.size(); i++) {
//                    String id = list.get(i).getId();
//                    String title = list.get(i).getTitle();
//                    String content = list.get(i).getContent();
//                    newsBeanList.add(new NewsBean(id, title, content));
//
//                }
//
//                for (NewsBean newsBean : newsBeanList) {
//                    titleLists.add(newsBean.getTitle());
//                }
//                text.setTextList(titleLists);
//            }
//        });
//
//    }

//    private void initViews() {
//        text.setTextList(titleList);
//    }

    @Override
    public void initData() {
        newsBeanList = new ArrayList<>();
        titleList = new ArrayList<>();
        titleLists = new ArrayList<>();
        titleList.add("正在加载...");

        imgLists = new ArrayList<>();
        imgLists.add(new TopImageBean(R.mipmap.bbb));
        imgLists.add(new TopImageBean(R.mipmap.topimg));
    }

    @OnClick({R.id.home_kjxx,R.id.home_ttjc,R.id.home_jchd,R.id.home_yxwfjs,R.id.home_cpzx,R.id.home_gywm,R.id.home_zxkf})
    public void click(View view){
        switch (view.getId()){
            case R.id.home_kjxx:
                Intent intent0=new Intent(getActivity(), CodeHistoryActivity.class);
                startActivity(intent0);
                break;
            case R.id.home_ttjc:
                if (isLogin){
                    Intent intent = new Intent(getContext(), PKActivity.class);
                    intent.putExtra("userId", userid);
                    intent.putExtra("id","1");
                    startActivity(intent);
                } else {
                    Intent intents = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intents);
                }
                break;
            case R.id.home_jchd:
                if (isLogin){
                    Intent intent1 = new Intent(getContext(), MoreActivity.class);
                    intent1.putExtra("uid",userid);
                    startActivity(intent1);
                } else {
                    Intent intents = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intents);
                }
                break;
            case R.id.home_yxwfjs:
                if (isLogin) {
                    Intent intent2 = new Intent(getContext(), HelpCenterNewtActivity.class);
                    intent2.putExtra("musid", "76");
                    intent2.putExtra("title", homeYxwfjs.getText());
                    intent2.putExtra("userId", userid);
                    startActivity(intent2);
                } else {
                    Intent intents = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intents);
                }
                break;
            case R.id.home_cpzx:
                if (isLogin){
                    Intent intent3 = new Intent(getContext(),HelpCenterActivity.class);
                    intent3.putExtra("userId",userid);
                    startActivity(intent3);
                }else {
                    Intent intent2 = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent2);
                }
                break;
            case R.id.home_gywm:
                if (isLogin) {
                    Intent intent2 = new Intent(getContext(), HelpCenterNewtActivity.class);
                    intent2.putExtra("musid", "83");
                    intent2.putExtra("title", homeGywm.getText());
                    intent2.putExtra("userId", userid);
                    startActivity(intent2);
                } else {
                    Intent intents = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intents);
                }

                break;
            case R.id.home_zxkf:
                if (isLogin) {
                    Intent intent2 = new Intent(getContext(), OnlineActivity.class);
                    intent2.putExtra("title", homeZxkf.getText());
                    startActivity(intent2);
                } else {
                    Intent intents = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intents);
                }

                break;
        }

    }

    private class LocalImageHolderView implements Holder<TopImageBean> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            View view = View.inflate(context, R.layout.topimg_view, null);
            imageView = (ImageView) view.findViewById(R.id.top_img);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, TopImageBean data) {
            /*//视频图片
            ImageLoader.getInstance().displayImage(data.getThumb(),
                    imageView, ImageLoaderOptions.list_options);*/
            imageView.setImageResource(data.getImg());
        }
    }

    //开始滚动
//    @Override
//    public void onResume() {
//        super.onResume();
//        text.startAutoScroll();
//
//    }
//
//    //停止滚动
//    @Override
//    public void onPause() {
//        super.onPause();
//        text.stopAutoScroll();
//    }
}