package zz.itcast.redbody7.activity;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;

import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.bean.SearchBean;
import zz.itcast.redbody7.bean.SearchBean.ProductList;
import zz.itcast.redbody7.utils.MyHttpUtils;
import zz.itcast.redbody7.utils.MyHttpUtils.OnRequestCallBack;
import zz.itcast.redbody7.utils.PreferenceUtils;
import zz.itcast.redbody7.view.TittleBarView;
import zz.itcast.redbody7.R;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SearchResultActivity1 extends BaseActivity implements
		OnClickListener {
	private TittleBarView search_result1_bar;
	private TextView tv_search_result_bar_1;
	private TextView tv_search_result_bar_2;
	private TextView tv_search_result_bar_3;
	private TextView tv_search_result_bar_4;
	private BitmapUtils bitmapUtils;
	private String key;
	private ListView lv;
	private SearchBean fromJson;

	private void initBar() {
		tv_search_result_bar_1.setOnClickListener(this);
		tv_search_result_bar_2.setOnClickListener(this);
		tv_search_result_bar_3.setOnClickListener(this);
		tv_search_result_bar_4.setOnClickListener(this);

	}

	@Override
	public void initRegister() {

	}

	@Override
	public void initData() {

		String url = MyConstants.BASE_URL + "/search?keyword=" + key;
		MyHttpUtils httpUtils = new MyHttpUtils(url,false);
		httpUtils.setOnRequestCallBack(new OnRequestCallBack() {

			@Override
			public void onSuccess(String result) {
				bitmapUtils = new BitmapUtils(mContext);
				parserData(result);
			}
		});
	}

	/*
	 * 解析json'
	 */
	protected void parserData(String result) {
		Gson gson = new Gson();

		fromJson = gson.fromJson(result, SearchBean.class);
		productlist = fromJson.productlist;
		String name = "";
		for (int i = 0; i < productlist.size(); i++) {
			name = name+productlist.get(i).name.trim();
		}
		PreferenceUtils.putString(mContext, "keyword", name);
		MySearchAdapter adapter = new MySearchAdapter();
			lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(mContext,
						ProductDetailActivity.class);
				intent.putExtra("PID", productlist.get(arg2).id );
				startActivity(intent);
			}
		});
	}

	@Override
	public void initView() {
		setContentView(R.layout.activity_search_result1);
		search_result1_bar = (TittleBarView) findViewById(R.id.search_result1_bar);
		tv_search_result_bar_1 = (TextView) findViewById(R.id.tv_search_result_bar_1);
		tv_search_result_bar_2 = (TextView) findViewById(R.id.tv_search_result_bar_2);
		tv_search_result_bar_3 = (TextView) findViewById(R.id.tv_search_result_bar_3);
		tv_search_result_bar_4 = (TextView) findViewById(R.id.tv_search_result_bar_4);
		lv = (ListView) findViewById(R.id.lv_search_result1);

		search_result1_bar.setTittle("搜索结果");
		tv_search_result_bar_1.setEnabled(false);
		search_result1_bar.setOnBackClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		search_result1_bar.setBackVisibility(true);
		search_result1_bar.setOperVisibility(false);

		key = getIntent().getStringExtra("key");
		initBar();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_search_result_bar_1:
			tv_search_result_bar_1.setEnabled(false);
			tv_search_result_bar_2.setEnabled(true);
			tv_search_result_bar_3.setEnabled(true);
			tv_search_result_bar_4.setEnabled(true);
			break;
		case R.id.tv_search_result_bar_2:
			tv_search_result_bar_1.setEnabled(true);
			tv_search_result_bar_2.setEnabled(false);
			tv_search_result_bar_3.setEnabled(true);
			tv_search_result_bar_4.setEnabled(true);
			break;
		case R.id.tv_search_result_bar_3:
			tv_search_result_bar_1.setEnabled(true);
			tv_search_result_bar_2.setEnabled(true);
			tv_search_result_bar_3.setEnabled(false);
			tv_search_result_bar_4.setEnabled(true);
			break;
		case R.id.tv_search_result_bar_4:
			tv_search_result_bar_1.setEnabled(true);
			tv_search_result_bar_2.setEnabled(true);
			tv_search_result_bar_3.setEnabled(true);
			tv_search_result_bar_4.setEnabled(false);
			break;

		default:
			break;
		}

	}

	private ArrayList<ProductList> productlist;

	class MySearchAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (fromJson != null) {
				return fromJson.productlist.size();
			}
			return 0;
		}

		@Override
		public ProductList getItem(int position) {
			ProductList productList = null;
			if (fromJson != null) {
				productList = fromJson.productlist.get(position);
			}

			return productList;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			ViewHolder vh = new ViewHolder();
			if (convertView == null) {
				view = View
						.inflate(mContext, R.layout.search_result_list, null);
			} else {
				view = convertView;
			}
			vh.iv_search_result = (ImageView) view
					.findViewById(R.id.iv_search_result);
			vh.tv_search_result_name = (TextView) view
					.findViewById(R.id.tv_search_result_name);
			vh.tv_search_result_price1 = (TextView) view
					.findViewById(R.id.tv_search_result_price1);
			vh.tv_search_result_price2 = (TextView) view
					.findViewById(R.id.tv_search_result_price2);
			vh.tv_search_result_say = (TextView) view
					.findViewById(R.id.tv_search_result_say);
			vh.tv_search_result_name.setText(productlist.get(position).name);
			vh.tv_search_result_price1.setText("现价:"
					+ productlist.get(position).price);
			vh.tv_search_result_price2.setText("原价:"
					+ productlist.get(position).marketprice);
			vh.tv_search_result_say.setText(999 + "");
			String pic = productlist.get(position).pic;
			if (TextUtils.isEmpty(pic)) {
				productlist.remove(position);
			}
			bitmapUtils
					.display(vh.iv_search_result, MyConstants.BASE_URL + pic);
			view.setTag(vh);
			return view;
		}
	}

	public class ViewHolder {

		public TextView tv_search_result_price2;
		public TextView tv_search_result_price1;
		public TextView tv_search_result_say;
		public TextView tv_search_result_name;
		public View iv_search_result;

	}

}
