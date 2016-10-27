package zz.itcast.redbody7.activity;

import java.util.List;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.adapter.DefaultBaseAdapter;
import zz.itcast.redbody7.bean.ProductBean;
import zz.itcast.redbody7.bean.ProductBean.Product;
import zz.itcast.redbody7.utils.MyHttpUtils;
import zz.itcast.redbody7.utils.MyHttpUtils.OnRequestCallBack;
import zz.itcast.redbody7.view.LoadAnimationView;
import zz.itcast.redbody7.view.TittleBarView;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;

/**
 * 新品上架页面
 * 
 * @author Administrator
 * 
 */
public class ProductActivity extends BaseActivity {

	private GridView gv;

	private List<Product> productlist;

	private TittleBarView tbv;

	private LoadAnimationView lav;

	@Override
	public void initView() {
		setContentView(R.layout.product_activity);
		tbv = (TittleBarView) findViewById(R.id.product_title);

		gv = (GridView) findViewById(R.id.product_gridview);
		lav = (LoadAnimationView) findViewById(R.id.product_loading);

	}

	@Override
	public void initRegister() {

	}

	@Override
	public void initData() {

		tbv.setTittle("新品上架");

		tbv.setOperVisibility(false);

		tbv.setOnBackClick(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();
			}
		});
		// 请求数据：http://192.168.12.253:8080/ECServicez8/newproduct?page=0&pageNum=10
		sendRequest(MyConstants.BASE_URL + "/newproduct?page=0&pageNum=10");

	}

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
	private void parseJson(String json) {
		Gson gson = new Gson();
		ProductBean productBean = gson.fromJson(json, ProductBean.class);
		productlist = productBean.productlist;
		lav.setVisibility(View.GONE);
		gv.setVisibility(View.VISIBLE);
		gv.setAdapter(new MyProductItemAdapter(productlist));

		OnItemClickListener listenerGV = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				int pid = productlist.get(position).id;

				Intent intent = new Intent(ProductActivity.this,
						ProductDetailActivity.class);
				intent.putExtra("PID", pid);
				startActivity(intent);
			}
		};
		gv.setOnItemClickListener(listenerGV);
	}

	class MyProductItemAdapter extends DefaultBaseAdapter<Product> {
		private BitmapUtils bUtils;

		public MyProductItemAdapter(List<Product> datas) {
			super(datas);
			bUtils = new BitmapUtils(mContext);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MyProductHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(mContext,
						R.layout.gridview_item_product, null);
				holder = new MyProductHolder();
				holder.iv_img = (ImageView) convertView
						.findViewById(R.id.iv_img);
				holder.tv_name = (TextView) convertView
						.findViewById(R.id.tv_name);
				holder.tv_price = (TextView) convertView
						.findViewById(R.id.tv_price);
				holder.tv_spy_price = (TextView) convertView
						.findViewById(R.id.tv_spy_price);
				convertView.setTag(holder);
			} else {
				holder = (MyProductHolder) convertView.getTag();
			}
			// 填充数据
			bUtils.display(holder.iv_img,
					MyConstants.BASE_URL + datas.get(position).pic);
			holder.tv_name.setText(datas.get(position).name);
			holder.tv_price.setText("￥:" + (datas.get(position).price + ""));
			holder.tv_spy_price.setText("￥:" + (datas.get(position).marketprice+""));
			
			holder.tv_spy_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			
			return convertView;
		}

	}

	class MyProductHolder {

		public TextView tv_spy_price;
		public TextView tv_price;
		public TextView tv_name;
		public ImageView iv_img;

	}

}
