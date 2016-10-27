package zz.itcast.redbody7.activity;

import java.util.List;

import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.activity.SnatchActivity.MyPanicHolder;
import zz.itcast.redbody7.activity.SnatchActivity.MyPanicItemBaseAdapter;
import zz.itcast.redbody7.adapter.DefaultBaseAdapter;
import zz.itcast.redbody7.bean.HotBean;
import zz.itcast.redbody7.bean.HotBean.Hot;
import zz.itcast.redbody7.bean.PanicBean.Trade;
import zz.itcast.redbody7.bean.PanicBean;
import zz.itcast.redbody7.utils.MyHttpUtils;
import zz.itcast.redbody7.utils.MyHttpUtils.OnRequestCallBack;
import zz.itcast.redbody7.view.LoadAnimationView;
import zz.itcast.redbody7.view.TittleBarView;

/**
 * 热门商品页面
 * 
 * @author Administrator
 * 
 */
public class HotActivity extends BaseActivity {

	private List<Hot> productlist;

	private GridView gv;

	private TittleBarView tbv;

	@Override
	public void initRegister() {

	}

	@Override
	public void initView() {
		setContentView(R.layout.hot_activity);
		tbv = (TittleBarView) findViewById(R.id.hot_tbv);

		gv = (GridView) findViewById(R.id.hot_gridview);
		lav = (LoadAnimationView) findViewById(R.id.hot_loading);
	}

	@Override
	public void initData() {

		tbv.setTittle("热门单品");
		tbv.setOperVisibility(false);

		tbv.setOnBackClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// 请求数据：http://192.168.12.253:8080/ECServicez8/hotproduct?page=1&pageNum=15
		sendRequest(MyConstants.BASE_URL + "/hotproduct?page=1&pageNum=15");
	}

	/**
	 * 请求数据
	 * 
	 * @param url
	 */
	private void sendRequest(String url) {
		MyHttpUtils myHttpUtils = new MyHttpUtils(url, true);
		myHttpUtils.setOnRequestCallBack(new OnRequestCallBack() {

			@Override
			public void onSuccess(String result) {
				String json = result;
				parseJson(json);
			}
		});

	}

	/**
	 * 解析数据
	 * 
	 * @param json
	 */
	protected void parseJson(String json) {
		Gson gson = new Gson();
		HotBean hotBean = gson.fromJson(json, HotBean.class);
		productlist = hotBean.productlist;
		lav.setVisibility(View.GONE);
		gv.setVisibility(View.VISIBLE);

		gv.setAdapter(new MyHotItemBaseAdapter(productlist));

		gv.setOnItemClickListener(listenerGV);
	}

	OnItemClickListener listenerGV = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			int pid = productlist.get(position).id;

			Intent intent = new Intent(HotActivity.this,
					ProductDetailActivity.class);
			intent.putExtra("PID", pid);
			startActivity(intent);

		}
	};

	private LoadAnimationView lav;

	class MyHotItemBaseAdapter extends DefaultBaseAdapter<Hot> {
		private BitmapUtils bUtils;

		public MyHotItemBaseAdapter(List<Hot> datas) {
			super(datas);
			bUtils = new BitmapUtils(mContext);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MyHotHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(mContext,
						R.layout.gridview_item_hot, null);
				holder = new MyHotHolder();
				holder.iv_img = (ImageView) convertView
						.findViewById(R.id.iv_img);
				holder.tv_name = (TextView) convertView
						.findViewById(R.id.tv_name);
				holder.tv_price_num = (TextView) convertView
						.findViewById(R.id.tv_price_num);
				holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
				holder.tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
				convertView.setTag(holder);
			} else {
				holder = (MyHotHolder) convertView.getTag();
			}
			// 填充数据

			bUtils.display(holder.iv_img,
					MyConstants.BASE_URL + datas.get(position).pic);
			holder.tv_name.setText(datas.get(position).name);
			holder.tv_price_num.setText("￥:"
					+ (datas.get(position).price + ""));
			holder.tv_price.setText("￥:" + (datas.get(position).marketprice + ""));
			
			return convertView;
		}

	}

	class MyHotHolder {

		public TextView tv_price;
		public TextView tv_price_num;
		public ImageView iv_img;
		public TextView tv_name;

	}

}
