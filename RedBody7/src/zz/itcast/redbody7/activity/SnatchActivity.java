package zz.itcast.redbody7.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.adapter.DefaultBaseAdapter;
import zz.itcast.redbody7.bean.PanicBean;
import zz.itcast.redbody7.bean.PanicBean.Trade;
import zz.itcast.redbody7.utils.MyHttpUtils;
import zz.itcast.redbody7.utils.MyHttpUtils.OnRequestCallBack;
import zz.itcast.redbody7.view.LoadAnimationView;
import zz.itcast.redbody7.view.TittleBarView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;

/**
 * 限时抢购页面
 */
public class SnatchActivity extends BaseActivity {

	private List<Trade> productList;

	private ListView lv;

	@Override
	public void initRegister() {

	}

	@Override
	public void initView() {
		setContentView(R.layout.panic_activity);

		tbv = (TittleBarView) findViewById(R.id.panic_tbv);

		lv = (ListView) findViewById(R.id.panic_listview);
		loadingAnim = (LoadAnimationView) findViewById(R.id.panic_loading);
	}

	@Override
	public void initData() {
		tbv.setTittle("限时抢购");
		tbv.setOperVisibility(false);

		tbv.setOnBackClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// 请求数据
		// http://192.168.12.253:8080/ECServicez8/limitbuy?page=1&pageNum=10
		sendRequest(MyConstants.BASE_URL + "/limitbuy?page=1&pageNum=10");
	}

	/**
	 * 请求数据
	 */

	private void sendRequest(String url) {
		MyHttpUtils myHttpUtils = new MyHttpUtils(url, true);
		myHttpUtils.setOnRequestCallBack(new OnRequestCallBack() {

			@Override
			public void onSuccess(String result) {
				String json = result;
				// 解析
				parseJson(json);
			}
		});
	}

	/**
	 * 解析json
	 * 
	 * @param json
	 *            服务器数据
	 */
	protected void parseJson(String json) {
		Gson gson = new Gson();
		PanicBean panicBean = gson.fromJson(json, PanicBean.class);
		productList = panicBean.productlist;

		adapter = new MyPanicItemBaseAdapter(productList);
		loadingAnim.setVisibility(View.GONE);
		lv.setVisibility(View.VISIBLE);

		lv.setAdapter(adapter);

		handler.post(updateThread);

	}

	@SuppressLint("UseSparseArrays")
	Map<Integer, MyCountTime> map = new HashMap<Integer, MyCountTime>();

	class MyPanicItemBaseAdapter extends DefaultBaseAdapter<Trade> {
		private BitmapUtils bUtils;

		public MyPanicItemBaseAdapter(List<Trade> datas) {
			super(datas);
			bUtils = new BitmapUtils(mContext);
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			MyPanicHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(SnatchActivity.this,
						R.layout.listview_item_panic, null);
				holder = new MyPanicHolder();
				holder.iv_img = (ImageView) convertView
						.findViewById(R.id.iv_img);
				holder.tv_title = (TextView) convertView
						.findViewById(R.id.tv_title);
				holder.tv_price = (TextView) convertView
						.findViewById(R.id.tv_price);
				holder.tv_special_price = (TextView) convertView
						.findViewById(R.id.tv_special_price);
				holder.tv_surplus_time = (TextView) convertView
						.findViewById(R.id.tv_surplus_time);
				holder.btn_panic = (Button) convertView
						.findViewById(R.id.btn_panic);
				convertView.setTag(holder);
			} else {
				holder = (MyPanicHolder) convertView.getTag();
			}
			// 填充数据
			bUtils.display(holder.iv_img,
					MyConstants.BASE_URL + datas.get(position).pic);

			holder.tv_title.setText(datas.get(position).NAME);
			holder.tv_price.setText("￥:" + (datas.get(position).price + ""));
			holder.tv_special_price.setText("￥:"+ (datas.get(position).limitprice + ""));
			
			holder.tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			if (!map.containsKey(position)) {
				MyCountTime ct = new MyCountTime();
				// ct.total = 5000;
				ct.total = (long) (Math.random() * 1000000);
				ct.tv = holder.tv_surplus_time;
				ct.btn_QG = holder.btn_panic;
				map.put(position, ct);

			} else {

			}

			holder.btn_panic.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 立即抢购，跳转页面
					Intent intent = new Intent(SnatchActivity.this,
							ProductDetailActivity.class);
					intent.putExtra("PID", datas.get(position).id);
					startActivity(intent);
				}
			});

			return convertView;
		}

	}

	class MyPanicHolder {

		Button btn_panic;
		TextView tv_surplus_time;
		TextView tv_special_price;
		TextView tv_time;
		TextView tv_special;
		TextView tv_price;
		TextView tv_title;
		ImageView iv_img;

	}

	private MyPanicItemBaseAdapter adapter;

	private String DEFAULT_TIME_FORMAT;

	private String time;
	// 创建Handler对象
	Handler handler = new Handler();
	// 新建一个线程对象
	Runnable updateThread = new Runnable() {
		// 将要执行的操作写在线程对象的run方法当中
		public void run() {
			handler.postDelayed(updateThread, 1000);
			// 调用Handler的postDelayed()方法
			// 这个方法的作用是：将要执行的线程对象放入到队列当中，待时间结束后，运行制定的线程对象
			// 第一个参数是Runnable类型：将要执行的线程对象
			// 第二个参数是long类型：延迟的时间，以毫秒为单位
			// mm = mm - 1000;
			Set<Integer> keySet = map.keySet();

			for (Integer integer : keySet) {
				MyCountTime ct = map.get(integer);
				ct.total = ct.total - 1000;
				if (ct.total < 3600000) {
					DEFAULT_TIME_FORMAT = "mm:ss";
				} else {

					DEFAULT_TIME_FORMAT = "hh:mm:ss";
				}
				time = DateFormat.format(DEFAULT_TIME_FORMAT, ct.total)
						.toString();
				ct.tv.setText(time);

				if (ct.total <= 0) {
					ct.btn_QG.setEnabled(false);
					ct.btn_QG
							.setBackgroundResource(R.drawable.button_long_selected);
					ct.btn_QG.setText("时间到期");
					ct.btn_QG.setTextColor(Color.BLACK);
					ct.tv.setText("已下架");
				}

			}

		}
	};

	private TittleBarView tbv;

	private LoadAnimationView loadingAnim;

	class MyCountTime {
		/**
		 * 总时间
		 */
		public long total;
		/**
		 * 倒计时
		 */
		public TextView tv;
		/**
		 * 抢购
		 */
		public Button btn_QG;
	}

}
