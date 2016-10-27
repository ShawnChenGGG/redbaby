package zz.itcast.redbody7.activity;

import java.util.ArrayList;
import java.util.List;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.MyConstant.VariableClass;
import zz.itcast.redbody7.adapter.DefaultBaseAdapter;
import zz.itcast.redbody7.dao.SalesTopic;
import zz.itcast.redbody7.dao.SalesTopic.Topic;
import zz.itcast.redbody7.utils.LogPrint;
import zz.itcast.redbody7.utils.MyHttpUtils;
import zz.itcast.redbody7.utils.MyHttpUtils.OnRequestCallBack;
import zz.itcast.redbody7.view.LoadAnimationView;
import zz.itcast.redbody7.view.TittleBarView;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;

/**
 * 促销快报页面
 * 
 * @author Administrator
 * 
 */
public class SalesActivity extends BaseActivity {

	private ListView lv;
	private ArrayList<Topic> topic;
	private TittleBarView tbv;

	@Override
	public void initView() {
		setContentView(R.layout.sales_activity);

		tbv = (TittleBarView) findViewById(R.id.sales_tbv);

		lv = (ListView) findViewById(R.id.sales_listview);
		lav = (LoadAnimationView) findViewById(R.id.sales_loading);
	}

	@Override
	public void initData() {

		tbv.setTittle("促销快报");
		tbv.setOperVisibility(false);

		tbv.setOnBackClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		getServerData();
	}

	/**
	 * 获取网络数据
	 */
	private void getServerData() {

		MyHttpUtils mUtils = new MyHttpUtils(MyConstants.BASE_URL + "/topic",
				true);

		mUtils.setOnRequestCallBack(new OnRequestCallBack() {

			@Override
			public void onSuccess(String result) {

				LogPrint.logI("zh", result);
				parserData(result);

			}
		});

	}

	/*
	 * 解析数据
	 */
	protected void parserData(String result) {
		Gson gson = new Gson();
		SalesTopic fromJson = gson.fromJson(result, SalesTopic.class);

		topic = fromJson.topic;
		lav.setVisibility(View.GONE);
		lv.setVisibility(View.VISIBLE);

		lv.setAdapter(new MyAdapter(topic));

		lv.setOnItemClickListener(listener);
	}

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(SalesActivity.this, MainActivity.class);
			startActivity(intent);
			MainActivity ma = VariableClass.mainActivity;
			ma.isToBrandFragment = true;

		}
	};
	private LoadAnimationView lav;

	@Override
	public void initRegister() {

	}

	class MyAdapter extends DefaultBaseAdapter<Topic> {

		private BitmapUtils bUtils;

		public MyAdapter(List<Topic> datas) {
			super(datas);

			bUtils = new BitmapUtils(mContext);
			bUtils.configDefaultLoadingImage(R.drawable.image1);
		}

		class ViewHolder {
			ImageView iv;
			TextView title;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			ViewHolder mHolder = null;
			if (convertView == null) {
				view = View.inflate(mContext, R.layout.sales_listview_item,
						null);
				mHolder = new ViewHolder();
				mHolder.iv = (ImageView) view.findViewById(R.id.sales_item_iv);
				mHolder.title = (TextView) view
						.findViewById(R.id.sales_item_title);
				view.setTag(mHolder);
			} else {
				view = convertView;
				mHolder = (ViewHolder) view.getTag();
			}

			bUtils.display(mHolder.iv,
					MyConstants.BASE_URL + datas.get(position).pic);

			mHolder.title.setText(datas.get(position).name);

			return view;
		}

	}

}
