package zz.itcast.redbody7.fragment;

import java.util.ArrayList;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.activity.CaptureActivity;
import zz.itcast.redbody7.activity.HotActivity;
import zz.itcast.redbody7.activity.MainActivity;
import zz.itcast.redbody7.activity.ProductActivity;
import zz.itcast.redbody7.activity.RecommendActivity;
import zz.itcast.redbody7.activity.SalesActivity;
import zz.itcast.redbody7.activity.SnatchActivity;
import zz.itcast.redbody7.dao.HomeTopic;
import zz.itcast.redbody7.dao.HomeTopic.HomeBanner;
import zz.itcast.redbody7.utils.LogPrint;
import zz.itcast.redbody7.utils.MyUtils;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 主页Fragment
 * 
 * @author zh
 * 
 */
@SuppressWarnings("deprecation")
public class HomeFragment extends BaseFragment {
	// 搜索按钮
	private LinearLayout ll_btn_search;
	// 滚动字
	private TextView tv_roll;
	// 搜索滚动字
	private TextView tv_search;
	// 初始化搜索框滚动字
	private int rolltext = 1;
	// 初始化listView条目标题滚动字
	private int roll = 1;

	private String items[] = new String[] { "限时抢购", "促销快报", "新品上架", "热门单品",
			"推荐品牌" };

	private int images[] = new int[] { R.drawable.home_classify_01,
			R.drawable.home_classify_02, R.drawable.home_classify_03,
			R.drawable.home_classify_04, R.drawable.home_classify_05 };

	Handler handler = new Handler() {
		private AnimationSet set;
		private Animation a;

		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				if (home_banner != null) {
					int currentItem = homeVp.getCurrentItem();
					if (currentItem == home_banner.size() - 1) {
						currentItem = -1;
					}
					homeVp.setCurrentItem(++currentItem);

					handler.sendEmptyMessageDelayed(1, 4000);
				}
			} else if (msg.what == 2) {
				if (roll == 1) {

					tv_roll.setText("促销快报");
					set = (AnimationSet) AnimationUtils.loadAnimation(mContext,
							R.drawable.set);

					tv_roll.startAnimation(set);
					++roll;
				} else if (roll == 2) {
					tv_roll.setText("新品上架");

					set = (AnimationSet) AnimationUtils.loadAnimation(mContext,
							R.drawable.set);

					tv_roll.startAnimation(set);
					++roll;
				} else if (roll == 3) {
					tv_roll.setText("热门单品");
					set = (AnimationSet) AnimationUtils.loadAnimation(mContext,
							R.drawable.set);

					tv_roll.startAnimation(set);
					++roll;
				} else if (roll == 4) {
					tv_roll.setText("推荐品牌");
					set = (AnimationSet) AnimationUtils.loadAnimation(mContext,
							R.drawable.set);

					tv_roll.startAnimation(set);
					roll = 1;
				}
				handler.sendEmptyMessageDelayed(2, 3000);

			} else if (msg.what == 3) {

				if (rolltext == 1) {
					tv_search.setText("红孩子母婴,正品特卖~~~~");
					a = new ScaleAnimation(0.1f, 1f, 0.1f, 1f);
					a.setDuration(1000);
					a.setRepeatCount(1);
					a.setFillAfter(true);
					tv_search.startAnimation(a);
					rolltext = 2;
				} else if (rolltext == 2) {
					tv_search.setText("你想要的都在这里！猛戳~~~~");
					a = new ScaleAnimation(0.1f, 1f, 0.1f, 1f);
					a.setDuration(1000);
					a.setRepeatCount(1);
					a.setRepeatMode(Animation.RELATIVE_TO_SELF);
					tv_search.startAnimation(a);
					rolltext = 3;
				} else if (rolltext == 3) {
					tv_search.setText("憋说话~~~~点我~~~~~");
					a = new ScaleAnimation(0.1f, 1f, 0.1f, 1f);
					a.setDuration(1000);
					a.setRepeatCount(1);
					a.setFillAfter(true);
					tv_search.startAnimation(a);
					rolltext = 1;

				}
				handler.sendEmptyMessageDelayed(3, 4000);
			}

		}
	};

	public void onPause() {
		handler.removeCallbacksAndMessages(null);
		super.onPause();
	};

	@Override
	public void onResume() {
		handler.sendEmptyMessageDelayed(1, 1000);
		handler.sendEmptyMessageDelayed(2, 3000);
		handler.sendEmptyMessageDelayed(3, 2000);
		homeVp.setOnPageChangeListener(onPageChangeListener);
		super.onResume();
	}

	@Override
	public View initView() {

		View view = View.inflate(getActivity(), R.layout.home_fragment, null);

		View headView = View.inflate(mContext, R.layout.home_listview_head,
				null);

		homeVp = (ViewPager) headView.findViewById(R.id.home_viewPager);

		ivScan = (ImageView) headView.findViewById(R.id.head_scan);

		homeLv = (ListView) view.findViewById(R.id.home_listView);

		gray_point_ll = (LinearLayout) headView
				.findViewById(R.id.gray_point_ll);

		red_point = headView.findViewById(R.id.red_point);

		tv_roll = (TextView) headView.findViewById(R.id.tv_roll);

		tv_search = (TextView) headView.findViewById(R.id.tv_search);

		ll_btn_search = (LinearLayout) headView
				.findViewById(R.id.ll_btn_search);
		// 为listView添加头布局
		homeLv.addHeaderView(headView);
		return view;
	}

	@Override
	public void initData() {
		getServerData();
		// 加载listView
		baseadapter = new MyBaseAdapter();
		homeLv.setAdapter(baseadapter);
		// 二维码动画
		Animation a = new AlphaAnimation(0.0f, 1.0f);
		a.setDuration(1000);
		a.setRepeatCount(-1);
		a.setRepeatMode(Animation.REVERSE);
		ivScan.startAnimation(a);
		register();
		// 搜索框点击事件
		ll_btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MainActivity ma = (MainActivity) mContext;
				ma.jumpPager(ma.pagerId[1]);
			}
		});
	}

	/**
	 * 注册监听
	 */
	private void register() {

		homeLv.setOnItemClickListener(listenerLV);

		ivScan.setOnClickListener(scanListener);
	}

	OnClickListener scanListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(mContext, CaptureActivity.class);

			startActivity(intent);
		}
	};
	/**
	 * listview条目点击监听
	 */
	OnItemClickListener listenerLV = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// 点击跳转逻辑
			position = position - homeLv.getHeaderViewsCount();
			switch (position) {
			case 0:
				// 限时抢购
				Intent intent0 = new Intent(getActivity(), SnatchActivity.class);
				startActivity(intent0);
				break;
			case 1:
				// 促销快报
				Intent intent1 = new Intent(getActivity(), SalesActivity.class);
				startActivity(intent1);
				break;
			case 2:
				// 新品上架
				Intent intent2 = new Intent(getActivity(),
						ProductActivity.class);
				startActivity(intent2);
				break;
			case 3:
				// 热门单品
				Intent intent3 = new Intent(getActivity(), HotActivity.class);
				startActivity(intent3);
				break;
			case 4:
				// 推荐品牌
				Intent intent4 = new Intent(getActivity(),
						RecommendActivity.class);
				startActivity(intent4);
				break;

			}

		}
	};

	/**
	 * 获取轮图数据
	 */
	private void getServerData() {

		HttpUtils http = new HttpUtils();

		http.send(HttpMethod.GET, MyConstants.BASE_URL + "/home",
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						arg0.printStackTrace();
						LogPrint.logI("zh", arg1);
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String result = arg0.result;

						LogPrint.logI("zh", result);
						parserData(result);
					}
				});
	}

	/**
	 * 解析数据
	 * 
	 * @param result
	 */
	protected void parserData(String result) {
		Gson gson = new Gson();
		HomeTopic fromJson = gson.fromJson(result, HomeTopic.class);
		home_banner = fromJson.home_banner;

		initViewPager();
	}

	private void initViewPager() {

		if (home_banner != null) {
			for (int i = 0; i < home_banner.size(); i++) {
				View view = new View(mContext);

				view.setBackgroundResource(R.drawable.gary_point_ll);

				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						MyUtils.dip2px(mContext, 5),
						MyUtils.dip2px(mContext, 5));

				if (i != 0) {
					// 设置边距
					params.leftMargin = MyUtils.dip2px(mContext, 5);
				}

				view.setLayoutParams(params);

				gray_point_ll.addView(view);
			}
			gray_point_ll.getViewTreeObserver().addOnGlobalLayoutListener(
					globalListener);
			adapter = new MyAdapter();

			homeVp.setAdapter(adapter);
		}
	}

	private int grayPointMargin;
	/**
	 * gray_point_ll线性布局监听
	 */
	OnGlobalLayoutListener globalListener = new OnGlobalLayoutListener() {

		@Override
		public void onGlobalLayout() {
			gray_point_ll.getViewTreeObserver().removeGlobalOnLayoutListener(
					this);

			grayPointMargin = gray_point_ll.getChildAt(1).getLeft()
					- gray_point_ll.getChildAt(0).getLeft();
		}
	};

	/**
	 * 页面改变监听
	 */
	OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {

		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			// 得到偏移量
			int margin = (int) (grayPointMargin * positionOffset + position
					* grayPointMargin);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) red_point
					.getLayoutParams();
			params.leftMargin = margin;
			red_point.setLayoutParams(params);

		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}
	};

	// listView的adapter
	private MyBaseAdapter baseadapter;

	class MyBaseAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return items.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MyHolder holder = null;

			if (convertView == null) {
				holder = new MyHolder();
				convertView = View.inflate(getActivity(),
						R.layout.home_listview_item, null);
				holder.iv_item = (ImageView) convertView
						.findViewById(R.id.iv_list_item);
				holder.tv_item = (TextView) convertView
						.findViewById(R.id.tv_list_item);

				convertView.setTag(holder);
			} else {
				holder = (MyHolder) convertView.getTag();
			}
			holder.iv_item.setImageResource(images[position]);
			holder.tv_item.setText(items[position]);

			return convertView;
		}

	}

	class MyHolder {
		ImageView iv_item;

		TextView tv_item;
	}

	private MyAdapter adapter;

	private ViewPager homeVp;

	private ListView homeLv;

	private ArrayList<HomeBanner> home_banner;

	private LinearLayout gray_point_ll;
	/**
	 * 小红点
	 */
	private View red_point;
	private ImageView ivScan;

	class MyAdapter extends PagerAdapter {

		private BitmapUtils bUtils;

		public MyAdapter() {
			bUtils = new BitmapUtils(mContext);
			bUtils.configDefaultLoadingImage(R.drawable.product_loading);
		}

		public int getCount() {
			if (home_banner != null)
				return home_banner.size();
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView iv = new ImageView(mContext);
			iv.setScaleType(ScaleType.FIT_XY);
			bUtils.display(iv, MyConstants.BASE_URL
					+ home_banner.get(position).pic);

			container.addView(iv);
			return iv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);

		}

	}

}
