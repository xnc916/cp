package com.xiongyuan.lottery.homepage.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.xiongyuan.lottery.CachePreferences;
import com.xiongyuan.lottery.LoginActivity;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.homepage.activity.PKActivity;
import com.xiongyuan.lottery.homepage.bean.GameListResult;
import com.xiongyuan.lottery.homepage.bean.Gamelist3Bean;
import com.xiongyuan.lottery.homepage.model.DargChildInfo;
import com.xiongyuan.lottery.homepage.model.DragIconInfo;
import com.xiongyuan.lottery.thirdpage.bean.Maplistbean;
import com.xiongyuan.lottery.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


public class CustomGroup extends ViewGroup  {

	//时时彩
	private int[] childImgssc={R.mipmap.button_icon_ssc1,R.mipmap.button_icon_ssc2,R.mipmap.button_icon_ssc3,R.mipmap.button_icon_ssc2};
	//11选5
	private int[] childImgx={R.mipmap.button_icon_sh11x5,R.mipmap.button_icon_sd11x5,R.mipmap.button_icon_gd11x5,R.mipmap.button_icon_jx11x5,R.mipmap.button_icon_ah11x5};
	//官方时时彩
	private int[] childImggfssc={R.mipmap.button_icon_gfssc06,R.mipmap.button_icon_gfssc05,R.mipmap.button_icon_gfssc04,R.mipmap.button_icon_gfssc03,R.mipmap.button_icon_gfssc02,R.mipmap.button_icon_gfssc01,R.mipmap.button_icon_ssc0};
	//快乐10
	private int[] childImgklsf={R.mipmap.button_icon_gd10,R.mipmap.button_icon_tj10fen};
	//快三
	private int[] childImgk3={R.mipmap.kuai3_4,R.mipmap.kuai3_3,R.mipmap.kuai3_2};
	//低频彩
	private int[] childImgdpc={R.mipmap.button_icon_pl5,R.mipmap.button_icon_fc3d,R.mipmap.button_icon_pl3,R.mipmap.button_icon_shssl};

	//PK10
	private int[] childImgpk10 = {R.mipmap.button_icon_pk10_0};
	private CustomAboveView mCustomAboveView;
	private CustomBehindParent mCustomBehindParent;
	private boolean isEditModel = false;
	public static final int COLUMNUM = 2;
	private Context mContext;
	//所有以的list
	private ArrayList<DragIconInfo> allInfoList = new ArrayList<DragIconInfo>();
	/**显示的带more的list*/
	private ArrayList<DragIconInfo> homePageInfoList = new ArrayList<DragIconInfo>();
	/**可展开的list*/
	private ArrayList<DragIconInfo> expandInfoList = new ArrayList<DragIconInfo>();

	/**不可展开的list*/
	private ArrayList<DragIconInfo> onlyInfoList = new ArrayList<DragIconInfo>();

	private InfoEditModelListener editModelListener;
	private List<Gamelist3Bean> zlist = new ArrayList<>();
	private List<Gamelist3Bean> ssc = new ArrayList<>();
	private List<Gamelist3Bean> ex = new ArrayList<>();
	private List<Gamelist3Bean> gfssc = new ArrayList<>();
	private List<Gamelist3Bean> klsf = new ArrayList<>();
	private List<Gamelist3Bean> k3 = new ArrayList<>();
	private List<Gamelist3Bean> dpc = new ArrayList<>();
	private List<Gamelist3Bean> beanList = new ArrayList<>();
	private List<Gamelist3Bean> pk10 = new ArrayList<>();


	private OnConfirmListener mOnConfirmListener;
	private Map<String, String> map=new HashMap<String, String>();


	public interface InfoEditModelListener {
		public void onModleChanged(boolean isEditModel);
	}


	public CustomGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		LayoutParams upParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mCustomAboveView = new CustomAboveView(context, this);
		addView(mCustomAboveView, upParams);
		LayoutParams downParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mCustomBehindParent = new CustomBehindParent(mContext, this);
		addView(mCustomBehindParent, downParams);

		getdatalist();


	}


	public CustomGroup(Context context) {
		this(context, null);

	}

	public InfoEditModelListener getEditModelListener() {
		return editModelListener;
	}

	public void setEditModelListener(InfoEditModelListener editModelListener) {
		this.editModelListener = editModelListener;
	}

	/**
	 *
	 * 方法: initData <p>
	 * 描述: 初始化监听和数据 <p>
	 * 参数:  <p>
	 * 返回: void <p>
	 * 异常  <p>
	 */
	private void initData() {



		setCustomViewClickListener(new CustomAboveView.CustomAboveViewClickListener() {

			@Override
			public void onSingleClicked(DragIconInfo iconInfo) {
				// TODO Auto-generated method stub
				dispatchSingle(iconInfo);
			}

			@Override
			public void onChildClicked(DargChildInfo childInfo) {
				// TODO Auto-generated method stub
				dispatchChild((childInfo));
			}
		});

		initIconInfo();
	}
	public void getdatalist() {
		Call call = LotteryClient.getInstance().getGameList("0");
		call.enqueue(new UICallBack() {
			@Override
			public void onFailureUI(Call call, IOException e) {
				ToastUtils.showToast(mContext,"没网了吧！");

			}

			@Override
			public void onResponseUI(Call call, String body) {
				beanList.clear();
				GameListResult result = new Gson().fromJson(body,GameListResult.class);
				Gamelist3Bean g1 = new Gamelist3Bean();
				g1.setId(result.getResult().getData().get_$1().getId());
				g1.setPid(result.getResult().getData().get_$1().getPid());
				g1.setTitle(result.getResult().getData().get_$1().getTitle());
				g1.setShortTitle(result.getResult().getData().get_$1().getShortTitle());
				beanList.add(g1);
				Gamelist3Bean g38 = new Gamelist3Bean();
				g38.setId(result.getResult().getData().get_$38().getId());
				g38.setPid(result.getResult().getData().get_$38().getPid());
				g38.setTitle(result.getResult().getData().get_$38().getTitle());
				g38.setShortTitle(result.getResult().getData().get_$38().getShortTitle());
				beanList.add(g38);
				Gamelist3Bean g37 = new Gamelist3Bean();
				g37.setId(result.getResult().getData().get_$37().getId());
				g37.setPid(result.getResult().getData().get_$37().getPid());
				g37.setTitle(result.getResult().getData().get_$37().getTitle());
				g37.setShortTitle(result.getResult().getData().get_$37().getShortTitle());
				beanList.add(g37);
				Gamelist3Bean g36 = new Gamelist3Bean();
				g36.setId(result.getResult().getData().get_$36().getId());
				g36.setPid(result.getResult().getData().get_$36().getPid());
				g36.setTitle(result.getResult().getData().get_$36().getTitle());
				g36.setShortTitle(result.getResult().getData().get_$36().getShortTitle());
				beanList.add(g36);
				Gamelist3Bean g35 = new Gamelist3Bean();
				g35.setId(result.getResult().getData().get_$35().getId());
				g35.setPid(result.getResult().getData().get_$35().getPid());
				g35.setTitle(result.getResult().getData().get_$35().getTitle());
				g35.setShortTitle(result.getResult().getData().get_$35().getShortTitle());
				beanList.add(g35);
				Gamelist3Bean g34 = new Gamelist3Bean();
				g34.setId(result.getResult().getData().get_$34().getId());
				g34.setPid(result.getResult().getData().get_$34().getPid());
				g34.setTitle(result.getResult().getData().get_$34().getTitle());
				g34.setShortTitle(result.getResult().getData().get_$34().getShortTitle());
				beanList.add(g34);
				Gamelist3Bean g33 = new Gamelist3Bean();
				g33.setId(result.getResult().getData().get_$33().getId());
				g33.setPid(result.getResult().getData().get_$33().getPid());
				g33.setTitle(result.getResult().getData().get_$33().getTitle());
				g33.setShortTitle(result.getResult().getData().get_$33().getShortTitle());
				beanList.add(g33);
				Gamelist3Bean g25 = new Gamelist3Bean();
				g25.setId(result.getResult().getData().get_$25().getId());
				g25.setPid(result.getResult().getData().get_$25().getPid());
				g25.setTitle(result.getResult().getData().get_$25().getTitle());
				g25.setShortTitle(result.getResult().getData().get_$25().getShortTitle());
				beanList.add(g25);
				Gamelist3Bean g23 = new Gamelist3Bean();
				g23.setId(result.getResult().getData().get_$23().getId());
				g23.setPid(result.getResult().getData().get_$23().getPid());
				g23.setTitle(result.getResult().getData().get_$23().getTitle());
				g23.setShortTitle(result.getResult().getData().get_$23().getShortTitle());
				beanList.add(g23);
				Gamelist3Bean g22 = new Gamelist3Bean();
				g22.setId(result.getResult().getData().get_$22().getId());
				g22.setPid(result.getResult().getData().get_$22().getPid());
				g22.setTitle(result.getResult().getData().get_$22().getTitle());
				g22.setShortTitle(result.getResult().getData().get_$22().getShortTitle());
				beanList.add(g22);
				Gamelist3Bean g21 = new Gamelist3Bean();
				g21.setId(result.getResult().getData().get_$21().getId());
				g21.setPid(result.getResult().getData().get_$21().getPid());
				g21.setTitle(result.getResult().getData().get_$21().getTitle());
				g21.setShortTitle(result.getResult().getData().get_$21().getShortTitle());
				beanList.add(g21);
				Gamelist3Bean g20 = new Gamelist3Bean();
				g20.setId(result.getResult().getData().get_$20().getId());
				g20.setPid(result.getResult().getData().get_$20().getPid());
				g20.setTitle(result.getResult().getData().get_$20().getTitle());
				g20.setShortTitle(result.getResult().getData().get_$20().getShortTitle());
				beanList.add(g20);
				Gamelist3Bean g19 = new Gamelist3Bean();
				g19.setId(result.getResult().getData().get_$19().getId());
				g19.setPid(result.getResult().getData().get_$19().getPid());
				g19.setTitle(result.getResult().getData().get_$19().getTitle());
				g19.setShortTitle(result.getResult().getData().get_$19().getShortTitle());
				beanList.add(g19);
				Gamelist3Bean g18 = new Gamelist3Bean();
				g18.setId(result.getResult().getData().get_$18().getId());
				g18.setPid(result.getResult().getData().get_$18().getPid());
				g18.setTitle(result.getResult().getData().get_$18().getTitle());
				g18.setShortTitle(result.getResult().getData().get_$18().getShortTitle());
				beanList.add(g18);
				Gamelist3Bean g17 = new Gamelist3Bean();
				g17.setId(result.getResult().getData().get_$17().getId());
				g17.setPid(result.getResult().getData().get_$17().getPid());
				g17.setTitle(result.getResult().getData().get_$17().getTitle());
				g17.setShortTitle(result.getResult().getData().get_$17().getShortTitle());
				beanList.add(g17);
//				Gamelist3Bean g32 = new Gamelist3Bean();
//				g32.setId(result.getResult().getData().get_$32().getId());
//				g32.setPid(result.getResult().getData().get_$32().getPid());
//				g32.setTitle(result.getResult().getData().get_$32().getTitle());
//				g32.setShortTitle(result.getResult().getData().get_$32().getShortTitle());
//				beanList.add(g32);
				Gamelist3Bean g31 = new Gamelist3Bean();
				g31.setId(result.getResult().getData().get_$31().getId());
				g31.setPid(result.getResult().getData().get_$31().getPid());
				g31.setTitle(result.getResult().getData().get_$31().getTitle());
				g31.setShortTitle(result.getResult().getData().get_$31().getShortTitle());
				beanList.add(g31);
				Gamelist3Bean g30 = new Gamelist3Bean();
				g30.setId(result.getResult().getData().get_$30().getId());
				g30.setPid(result.getResult().getData().get_$30().getPid());
				g30.setTitle(result.getResult().getData().get_$30().getTitle());
				g30.setShortTitle(result.getResult().getData().get_$30().getShortTitle());
				beanList.add(g30);
				Gamelist3Bean g28 = new Gamelist3Bean();
				g28.setId(result.getResult().getData().get_$28().getId());
				g28.setPid(result.getResult().getData().get_$28().getPid());
				g28.setTitle(result.getResult().getData().get_$28().getTitle());
				g28.setShortTitle(result.getResult().getData().get_$28().getShortTitle());
				beanList.add(g28);
				Gamelist3Bean g27 = new Gamelist3Bean();
				g27.setId(result.getResult().getData().get_$27().getId());
				g27.setPid(result.getResult().getData().get_$27().getPid());
				g27.setTitle(result.getResult().getData().get_$27().getTitle());
				g27.setShortTitle(result.getResult().getData().get_$27().getShortTitle());
				beanList.add(g27);
				Gamelist3Bean g24 = new Gamelist3Bean();
				g24.setId(result.getResult().getData().get_$24().getId());
				g24.setPid(result.getResult().getData().get_$24().getPid());
				g24.setTitle(result.getResult().getData().get_$24().getTitle());
				g24.setShortTitle(result.getResult().getData().get_$24().getShortTitle());
				beanList.add(g24);
				Gamelist3Bean g14 = new Gamelist3Bean();
				g14.setId(result.getResult().getData().get_$14().getId());
				g14.setPid(result.getResult().getData().get_$14().getPid());
				g14.setTitle(result.getResult().getData().get_$14().getTitle());
				g14.setShortTitle(result.getResult().getData().get_$14().getShortTitle());
				beanList.add(g14);
				Gamelist3Bean g13 = new Gamelist3Bean();
				g13.setId(result.getResult().getData().get_$13().getId());
				g13.setPid(result.getResult().getData().get_$13().getPid());
				g13.setTitle(result.getResult().getData().get_$13().getTitle());
				g13.setShortTitle(result.getResult().getData().get_$13().getShortTitle());
				beanList.add(g13);
				Gamelist3Bean g12 = new Gamelist3Bean();
				g12.setId(result.getResult().getData().get_$12().getId());
				g12.setPid(result.getResult().getData().get_$12().getPid());
				g12.setTitle(result.getResult().getData().get_$12().getTitle());
				g12.setShortTitle(result.getResult().getData().get_$12().getShortTitle());
				beanList.add(g12);
				Gamelist3Bean g11 = new Gamelist3Bean();
				g11.setId(result.getResult().getData().get_$11().getId());
				g11.setPid(result.getResult().getData().get_$11().getPid());
				g11.setTitle(result.getResult().getData().get_$11().getTitle());
				g11.setShortTitle(result.getResult().getData().get_$11().getShortTitle());
				beanList.add(g11);
				Gamelist3Bean g10 = new Gamelist3Bean();
				g10.setId(result.getResult().getData().get_$10().getId());
				g10.setPid(result.getResult().getData().get_$10().getPid());
				g10.setTitle(result.getResult().getData().get_$10().getTitle());
				g10.setShortTitle(result.getResult().getData().get_$10().getShortTitle());
				beanList.add(g10);
				Gamelist3Bean g9 = new Gamelist3Bean();
				g9.setId(result.getResult().getData().get_$9().getId());
				g9.setPid(result.getResult().getData().get_$9().getPid());
				g9.setTitle(result.getResult().getData().get_$9().getTitle());
				g9.setShortTitle(result.getResult().getData().get_$9().getShortTitle());
				beanList.add(g9);
				Gamelist3Bean g8 = new Gamelist3Bean();
				g8.setId(result.getResult().getData().get_$8().getId());
				g8.setPid(result.getResult().getData().get_$8().getPid());
				g8.setTitle(result.getResult().getData().get_$8().getTitle());
				g8.setShortTitle(result.getResult().getData().get_$8().getShortTitle());
				beanList.add(g8);
				Gamelist3Bean g7 = new Gamelist3Bean();
				g7.setId(result.getResult().getData().get_$7().getId());
				g7.setPid(result.getResult().getData().get_$7().getPid());
				g7.setTitle(result.getResult().getData().get_$7().getTitle());
				g7.setShortTitle(result.getResult().getData().get_$7().getShortTitle());
				beanList.add(g7);
				Gamelist3Bean g6 = new Gamelist3Bean();
				g6.setId(result.getResult().getData().get_$6().getId());
				g6.setPid(result.getResult().getData().get_$6().getPid());
				g6.setTitle(result.getResult().getData().get_$6().getTitle());
				g6.setShortTitle(result.getResult().getData().get_$6().getShortTitle());
				beanList.add(g6);
				Gamelist3Bean g5 = new Gamelist3Bean();
				g5.setId(result.getResult().getData().get_$5().getId());
				g5.setPid(result.getResult().getData().get_$5().getPid());
				g5.setTitle(result.getResult().getData().get_$5().getTitle());
				g5.setShortTitle(result.getResult().getData().get_$5().getShortTitle());
				beanList.add(g5);
				Gamelist3Bean g2 = new Gamelist3Bean();
				g2.setId(result.getResult().getData().get_$2().getId());
				g2.setPid(result.getResult().getData().get_$2().getPid());
				g2.setTitle(result.getResult().getData().get_$2().getTitle());
				g2.setShortTitle(result.getResult().getData().get_$2().getShortTitle());
				beanList.add(g2);
				Gamelist3Bean g3 = new Gamelist3Bean();
				g3.setId(result.getResult().getData().get_$3().getId());
				g3.setPid(result.getResult().getData().get_$3().getPid());
				g3.setTitle(result.getResult().getData().get_$3().getTitle());
				g3.setShortTitle(result.getResult().getData().get_$3().getShortTitle());
				beanList.add(g3);
				Gamelist3Bean g4 = new Gamelist3Bean();
				g4.setId(result.getResult().getData().get_$4().getId());
				g4.setPid(result.getResult().getData().get_$4().getPid());
				g4.setTitle(result.getResult().getData().get_$4().getTitle());
				g4.setShortTitle(result.getResult().getData().get_$4().getShortTitle());
				beanList.add(g4);

				zlist.clear();
				ssc.clear();
				ex.clear();
				gfssc.clear();
				klsf.clear();
				k3.clear();
				dpc.clear();
				pk10.clear();
				map.clear();
				for (int i = 0; i < beanList.size(); i++){
					String s = beanList.get(i).getShortTitle() + "";
					if (s.equals("null")){
						beanList.get(i).setShortTitle("");
					}
					if (beanList.get(i).getPid().equals("0")){
						zlist.add(beanList.get(i));
					}
					if (beanList.get(i).getPid().equals("23")){
						gfssc.add(beanList.get(i));
					}
					if (beanList.get(i).getPid().equals("21")){
						dpc.add(beanList.get(i));
					}
					if (beanList.get(i).getPid().equals("20")){
						klsf.add(beanList.get(i));
					}
					if (beanList.get(i).getPid().equals("19")){
						k3.add(beanList.get(i));
					}
					if (beanList.get(i).getPid().equals("18")){
						ex.add(beanList.get(i));
					}
					if (beanList.get(i).getPid().equals("17")){
						ssc.add(beanList.get(i));
					}
					if (beanList.get(i).getPid().equals("22")){
						pk10.add(beanList.get(i));
					}
					if (!beanList.get(i).getPid().equals("0")){
						map.put(beanList.get(i).getId(),beanList.get(i).getTitle());
					}
				}

				//mOnConfirmListener.onConfirm(map);
				Maplistbean maplistbean = new Maplistbean();
				maplistbean.setMap(map);
				initData();
			}
		});
	}

	public void show(OnConfirmListener onConfirmListener){
		this.mOnConfirmListener = onConfirmListener;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int widthMeasure = 0;
		int heightMeasure = 0;
		if (isEditModel) {
			mCustomBehindParent.measure(widthMeasureSpec, heightMeasureSpec);
			widthMeasure = mCustomBehindParent.getMeasuredWidth();
			heightMeasure = mCustomBehindParent.getMeasuredHeight();
		} else {
			mCustomAboveView.measure(widthMeasureSpec, heightMeasureSpec);
			widthMeasure = mCustomAboveView.getMeasuredWidth();
			heightMeasure = mCustomAboveView.getMeasuredHeight();
		}
		setMeasuredDimension(widthMeasure, heightMeasure);

	}

	/**
	 * 方法: onLayout <p>
	 * 描述: TODO<p>
	 * @param changed
	 * @param l
	 * @param t
	 * @param r
	 * @param b <p>
	 * @see ViewGroup#onLayout(boolean, int, int, int, int) <p>
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (isEditModel) {
			int behindHeight = mCustomBehindParent.getMeasuredHeight();
			mCustomBehindParent.layout(l, 0, r, behindHeight + t);
		} else {
			int aboveHeight = mCustomAboveView.getMeasuredHeight();
			mCustomAboveView.layout(l, 0, r, aboveHeight + t);
		}
	}

	/**
	 *
	 * 方法: initIconInfo <p>
	 * 描述: 初始化数据 <p>
	 * 参数:  <p>
	 * 返回: void <p>
	 * 异常  <p>
	 */
	private void initIconInfo() {
		allInfoList.clear();
		allInfoList.addAll(initAllOriginalInfo());
		getPageInfoList();
		refreshIconInfo();
	}

	/**
	 *
	 * 方法: initAllOriginalInfo <p>
	 * 描述: 初始化Icon info <p>
	 * 参数: @return <p>
	 * 返回: ArrayList<DragIconInfo> <p>
	 */
	private ArrayList<DragIconInfo> initAllOriginalInfo() {


		ArrayList<DragIconInfo> iconInfoList = new ArrayList<DragIconInfo>();
		ArrayList<DargChildInfo> childListssc = initChildListssc();
		ArrayList<DargChildInfo> childListx = initChildListx();
		ArrayList<DargChildInfo> childListgfssc = initChildListgfssc();
		ArrayList<DargChildInfo> childListklsf = initChildListklsf();
		ArrayList<DargChildInfo> childListk3 = initChildListk3();
		ArrayList<DargChildInfo> childListdpc = initChildListdpc();
		ArrayList<DargChildInfo> childListpk10 = initChildListpk10();

		iconInfoList.add(new DragIconInfo(1, zlist.get(6).getTitle(),zlist.get(6).getShortTitle()+"", R.mipmap.button_icon_ssc0, DragIconInfo.CATEGORY_EXPAND, childListssc));
		iconInfoList.add(new DragIconInfo(2, zlist.get(5).getTitle(),zlist.get(5).getShortTitle()+"", R.mipmap.button_icon_11x5, DragIconInfo.CATEGORY_EXPAND, childListx));
		iconInfoList.add(new DragIconInfo(3, zlist.get(0).getTitle(),zlist.get(0).getShortTitle()+"", R.mipmap.button_icon_gfssc, DragIconInfo.CATEGORY_EXPAND, childListgfssc));
		iconInfoList.add(new DragIconInfo(4, zlist.get(3).getTitle(),zlist.get(3).getShortTitle()+"", R.mipmap.button_icon_10fen, DragIconInfo.CATEGORY_EXPAND, childListklsf));
		iconInfoList.add(new DragIconInfo(5, zlist.get(4).getTitle(),zlist.get(4).getShortTitle()+"",R.mipmap.kuai3_1, DragIconInfo.CATEGORY_EXPAND, childListk3));
		iconInfoList.add(new DragIconInfo(6, zlist.get(1).getTitle(),zlist.get(1).getShortTitle()+"", R.mipmap.button_icon_pk10_0, DragIconInfo.CATEGORY_ONLY,childListpk10));
		iconInfoList.add(new DragIconInfo(7, zlist.get(2).getTitle(),zlist.get(2).getShortTitle()+"", R.mipmap.button_icon_dp, DragIconInfo.CATEGORY_EXPAND, childListdpc));


		return iconInfoList;
	}


	/**
	 *
	 * 方法: initChildList <p>
	 * 描述: 初始化child list <p>
	 * 参数: @return <p>
	 * 返回: ArrayList<DargChildInfo> <p>
	 */
	private ArrayList<DargChildInfo> initChildListssc() {
		ArrayList<DargChildInfo> childList = new ArrayList<DargChildInfo>();
		for (int i=0;i<ssc.size();i++){
			childList.add(new DargChildInfo(i+1,ssc.get(i).getTitle(),ssc.get(i).getShortTitle()+"",childImgssc[i]));
		}
		return childList;
	}
	private ArrayList<DargChildInfo> initChildListx() {
		ArrayList<DargChildInfo> childList = new ArrayList<DargChildInfo>();
		for (int i=0;i<ex.size();i++){
			childList.add(new DargChildInfo(i+1,ex.get(i).getTitle(),ex.get(i).getShortTitle()+"",childImgx[i]));
		}
		return childList;
	}
	private ArrayList<DargChildInfo> initChildListgfssc() {
		ArrayList<DargChildInfo> childList = new ArrayList<DargChildInfo>();
		for (int i=0;i<gfssc.size();i++){
			childList.add(new DargChildInfo(i+1,gfssc.get(i).getTitle(),gfssc.get(i).getShortTitle()+"",childImggfssc[i]));
		}
		return childList;
	}
	private ArrayList<DargChildInfo> initChildListklsf() {
		ArrayList<DargChildInfo> childList = new ArrayList<DargChildInfo>();
		for (int i=0;i<klsf.size();i++){
			childList.add(new DargChildInfo(i+1,klsf.get(i).getTitle(),klsf.get(i).getShortTitle()+"",childImgklsf[i]));
		}
		return childList;
	}
	private ArrayList<DargChildInfo> initChildListk3() {
		ArrayList<DargChildInfo> childList = new ArrayList<DargChildInfo>();
		for (int i=0;i<k3.size();i++){
			childList.add(new DargChildInfo(i+1,k3.get(i).getTitle(),k3.get(i).getShortTitle()+"",childImgk3[i]));
		}
		return childList;
	}
	private ArrayList<DargChildInfo> initChildListdpc() {
		ArrayList<DargChildInfo> childList = new ArrayList<DargChildInfo>();
		for (int i=0;i<dpc.size();i++){
			childList.add(new DargChildInfo(i+1,dpc.get(i).getTitle(),dpc.get(i).getShortTitle()+"",childImgdpc[i]));
		}
		return childList;
	}
	private ArrayList<DargChildInfo> initChildListpk10() {
		ArrayList<DargChildInfo> childList = new ArrayList<DargChildInfo>();
		for (int i=0;i<pk10.size();i++){
			childList.add(new DargChildInfo(i+1,pk10.get(i).getTitle(),pk10.get(i).getShortTitle()+"",childImgpk10[i]));
		}
		return childList;
	}
	/**
	 *
	 * 方法: getPageInfoList <p>
	 * 描述: 初始化显示 <p>
	 * 参数:  <p>
	 * 返回: void <p>
	 */
	private void getPageInfoList() {
		homePageInfoList.clear();
		int count = 0;
		for (DragIconInfo info : allInfoList) {
			if (count < 9) {
				homePageInfoList.add(info);
				count++;
			} else {
				break;
			}
		}
	}

	/**
	 *
	 * 方法: refreshIconInfo <p>
	 * 描述: 刷新信息 <p>
	 * 参数:  <p>
	 * 返回: void <p>
	 */
	private void refreshIconInfo() {

		ArrayList<DragIconInfo> moreInfo = getMoreInfoList(allInfoList, homePageInfoList);
		expandInfoList = getInfoByType(moreInfo, DragIconInfo.CATEGORY_EXPAND);
		onlyInfoList = getInfoByType(moreInfo, DragIconInfo.CATEGORY_ONLY);
		setIconInfoList(homePageInfoList);
	}



	/**
	 *
	 * 方法: judeHomeInfoValid <p>
	 * 描述: 判断下显示里面是否包含更多 或者看下是否是最后一个 固定更多的位置 <p>
	 * 参数:  <p>
	 * 返回: void <p>
	 */
	private void judeHomeInfoValid() {
		boolean hasMoreInfo = false;
		int posit = 0;
		for(int index = 0;index<homePageInfoList.size();index++){
			DragIconInfo tempInfo = homePageInfoList.get(index);
			if(tempInfo.getId()==CustomAboveView.MORE){
				hasMoreInfo = true;
				posit = index;
				break;
			}
		}
		if(!hasMoreInfo){
			//没有更多 增加
			//homePageInfoList.add(new DragIconInfo(CustomAboveView.MORE, "更多", R.mipmap.icon_home_more, 0, new ArrayList<DargChildInfo>()));
		}else{
			if(posit!=homePageInfoList.size()-1){
				//不是最后一个
				DragIconInfo moreInfo = homePageInfoList.remove(posit);
				homePageInfoList.add(moreInfo);
			}
		}
	}


	/**
	 *
	 * 方法: getInfoByType <p>
	 * 描述: TODO <p>
	 * 参数: @param moreInfo
	 * 参数: @param categorySpt
	 * 参数: @return <p>
	 * 返回: ArrayList<DragIconInfo> <p>
	 */
	private ArrayList<DragIconInfo> getInfoByType(ArrayList<DragIconInfo> moreInfo, int categorySpt) {
		ArrayList<DragIconInfo> typeList = new ArrayList<DragIconInfo>();
		for (DragIconInfo info : moreInfo) {
			if (info.getCategory() == categorySpt) {
				typeList.add(info);
			}
		}
		return typeList;
	}


	public void setCustomViewClickListener(CustomAboveView.CustomAboveViewClickListener gridViewClickListener) {
		mCustomAboveView.setGridViewClickListener(gridViewClickListener);
	}

	/**
	 *
	 * 方法: setIconInfoList <p>
	 * 描述: 设置信息 <p>
	 * 参数: @param iconInfoList <p>
	 * 返回: void <p>
	 * 异常  <p>
	 */
	public void setIconInfoList(ArrayList<DragIconInfo> iconInfoList) {
		mCustomAboveView.refreshIconInfoList(iconInfoList);
		mCustomBehindParent.refreshIconInfoList(iconInfoList);
	}


	public boolean isEditModel() {
		return isEditModel;
	}

	public void cancleEidtModel(){
		setEditModel(false, 0);
	}


	public void setEditModel(boolean isEditModel, int position) {
		this.isEditModel = isEditModel;
		if (isEditModel) {
			mCustomAboveView.setViewCollaps();
			mCustomAboveView.setVisibility(View.GONE);
			mCustomBehindParent.notifyDataSetChange(mCustomAboveView.getIconInfoList());
			mCustomBehindParent.setVisibility(View.VISIBLE);
			mCustomBehindParent.drawWindowView(position, mCustomAboveView.getFirstEvent());
		} else {
			homePageInfoList.clear();
			homePageInfoList.addAll(mCustomBehindParent.getEditList());
			mCustomAboveView.refreshIconInfoList(homePageInfoList);
			mCustomAboveView.setVisibility(View.VISIBLE);
			mCustomBehindParent.setVisibility(View.GONE);
			if(mCustomBehindParent.isModifyedOrder()){
				mCustomBehindParent.cancleModifyOrderState();
			}
			mCustomBehindParent.resetHidePosition();
		}
		if(editModelListener!=null){
			editModelListener.onModleChanged(isEditModel);
		}
	}


	public void sendEventBehind(MotionEvent ev) {
		mCustomBehindParent.childDispatchTouchEvent(ev);
	}

	/**
	 *
	 * 方法: getMoreInfoList <p>
	 * 描述: TODO <p>
	 * 参数: @param allInfoList
	 * 参数: @param homePageInfoList
	 * 参数: @return <p>
	 * 返回: ArrayList<DragIconInfo> <p>
	 */
	private ArrayList<DragIconInfo> getMoreInfoList(ArrayList<DragIconInfo> allInfoList, ArrayList<DragIconInfo> homePageInfoList) {
		ArrayList<DragIconInfo> moreInfoList = new ArrayList<DragIconInfo>();
		moreInfoList.addAll(allInfoList);
		moreInfoList.removeAll(homePageInfoList);
		return moreInfoList;
	}



	/**
	 *
	 * 方法: deletHomePageInfo <p>
	 * 描述: TODO <p>
	 * 参数: @param dragIconInfo <p>
	 * 返回: void <p>
	 */
	public void deletHomePageInfo(DragIconInfo dragIconInfo) {
		homePageInfoList.remove(dragIconInfo);
		mCustomAboveView.refreshIconInfoList(homePageInfoList);
		int category = dragIconInfo.getCategory();
		switch (category) {
			case DragIconInfo.CATEGORY_ONLY:
				onlyInfoList.add(dragIconInfo);
				break;
			case DragIconInfo.CATEGORY_EXPAND:
				expandInfoList.add(dragIconInfo);
				break;
			default:
				break;
		}
		allInfoList.remove(dragIconInfo);
		allInfoList.add(dragIconInfo);
	}




	/**
	 *
	 * 方法: dispatchChild <p>
	 * 描述: 点击child <p>
	 * 参数: @param childInfo <p>
	 * 返回: void <p>
	 */
	protected void dispatchChild(DargChildInfo childInfo) {
		if (childInfo == null) {
			return;
		}
		String id = null;
		for (int i = 0;i < beanList.size();i++){
			if (childInfo.getName().equals(beanList.get(i).getTitle())){
				 id = beanList.get(i).getId();
			}
		}


		//Toast.makeText(mContext, "点击了item"+childInfo.getName(), Toast.LENGTH_SHORT).show();

		if (CachePreferences.getUser().getUser_id() != null){
			Intent intent=new Intent(mContext,PKActivity.class);
			intent.putExtra("userId", CachePreferences.getUser().getUser_id());
			intent.putExtra("id",id);
			intent.putExtra("titlename",childInfo.getName());
			mContext.startActivity(intent);
		}else {
			Intent intent = new Intent(mContext, LoginActivity.class);
			mContext.startActivity(intent);
		}


	}


	/**
	 *
	 * 方法: dispatchSingle <p>
	 * 描述: 没child的点击 <p>
	 * 参数: @param dragInfo <p>
	 * 返回: void <p>
	 */
	public void dispatchSingle(DragIconInfo dragInfo) {
		if (dragInfo == null) {
			return;

		}
//		if (CachePreferences.getUser().getUser_id() != null){
//			Intent intent=new Intent(mContext,PKActivity.class);
//			intent.putExtra("userId", CachePreferences.getUser().getUser_id());
//			intent.putExtra("id","13");
//			mContext.startActivity(intent);
//		}else {
//			Intent intent = new Intent(mContext, LoginActivity.class);
//			mContext.startActivity(intent);
//		}
	}




	public boolean isValideEvent(MotionEvent ev, int scrolly) {
		return mCustomBehindParent.isValideEvent(ev,scrolly);
	}


	public void clearEditDragView() {
		mCustomBehindParent.clearDragView();
	}



	// 利用接口回调：将选择的数量传递出去
	public interface OnConfirmListener{
		void onConfirm(Map<String,String> map);
	}

}