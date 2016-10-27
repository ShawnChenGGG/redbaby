/**
 * @date 2016-4-24 下午7:23:57
 */
package zz.itcast.redbody7.activity;

import java.lang.reflect.Method;
import java.util.Currency;
import java.util.List;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.bean.InvoiceBean;
import zz.itcast.redbody7.bean.InvoiceBean.InvoiceList;
import zz.itcast.redbody7.utils.MyHttpUtils;
import zz.itcast.redbody7.utils.MyHttpUtils.OnPostRequestCallBack;
import zz.itcast.redbody7.view.TextContentView;
import zz.itcast.redbody7.view.TittleBarView;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

/**
 * @author ma
 * 
 * @date 2016-4-24 下午7:23:57
 */
public class IncoiceTypeActivity extends BaseActivity {

	private ListView mListViewTitle;
	private ListView mListViewContent;

	@Override
	public void initRegister() {
	}

	@Override
	public void initView() {
		setContentView(R.layout.activity_incoice_type);
		initTittleBar();
		mListViewTitle = (ListView) findViewById(R.id.listview_title);
		mListViewContent = (ListView) findViewById(R.id.listview_content);
		// 点击标题的条目
		mListViewTitle.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				titleIsclick = true;
				titlePosition = position;
				if (contentIsclick) {
					Intent data = new Intent();
					String title = titles[titlePosition]; 
					String content = contents[contentPositon];
					data.putExtra("title", title);
					data.putExtra("content", content);
					setResult(4, data);
					finish();
				}
				titleAdapter.notifyDataSetChanged();
			}
		});
		// 点击内容的条目
		mListViewContent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				contentIsclick = true;
				contentPositon = position;
				if (titleIsclick) {
					Intent data = new Intent();
					String title = titles[titlePosition]; 
					String content = contents[contentPositon];
					data.putExtra("title", title);
					data.putExtra("content", content);
					setResult(4, data);
					finish();
				}
				contentAdapter.notifyDataSetChanged();
			}
		});

	}

	private int titlePosition = 0;
	private int contentPositon = -1;
	
	private boolean titleIsclick = true;
	private boolean contentIsclick = false;
	private String[] contents;
	private String[] titles;

	/**
	 * @date 2016-4-24 下午8:14:28
	 */
	private void initTittleBar() {
		TittleBarView mTbv = (TittleBarView) findViewById(R.id.tbv);
		mTbv.setTittle("选择发票信息");
		mTbv.setOperText("添加");
		//默认不显示操作按钮
		mTbv.setOperVisibility(false);
		// 点击返回按钮
		mTbv.setOnBackClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void initData() {
		// http://192.168.12.253:8080/ECServicez8/invoice?userId=1
		// TODO,用户id的替换
		String url = MyConstants.BASE_URL + "/invoice";
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId", 1 + "");
		MyHttpUtils httpUtils = new MyHttpUtils(url, params,false);
		httpUtils.setPostRequestCallBack(new OnPostRequestCallBack() {

			@Override
			public void onPostSuccess(String result) {
				parseJson(result);
			}
		});

	}

	/**
	 * 解析json字符串数据
	 * 
	 * @date 2016-4-24 下午8:30:48
	 */
	protected void parseJson(String result) {
		Gson gson = new Gson();
		InvoiceBean invoiceBean = gson.fromJson(result, InvoiceBean.class);
		List<InvoiceList> invoiceList = invoiceBean.invoiceList;
		contents = new String[invoiceList.size()];
		titles = new String[invoiceList.size()];
		for (int i = 0; i < invoiceList.size(); i++) {
			contents[i] = invoiceList.get(i).content;
			titles[i] = invoiceList.get(i).title;
		}
		
		titleAdapter = new MyTitleAdapter();
		mListViewTitle.setAdapter(titleAdapter);
		contentAdapter = new MyContentAdapter();
		mListViewContent.setAdapter(contentAdapter);
	}
	//
	private MyTitleAdapter titleAdapter;

	class MyTitleAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (titles != null) {
				return titles.length;
			}
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View
					.inflate(mContext, R.layout.list_incoice_item, null);
			TextView mTitle = (TextView) view.findViewById(R.id.tv_title);
			ImageView mIcon = (ImageView) view.findViewById(R.id.iv_icon);
			mTitle.setText(titles[position]);
			if (titlePosition == position) {
				mIcon.setVisibility(View.VISIBLE);
			} else {
				mIcon.setVisibility(View.GONE);
			}
			return view;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
	}

	private MyContentAdapter contentAdapter;
	class MyContentAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (contents != null) {
				return contents.length;
			}
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View
					.inflate(mContext, R.layout.list_incoice_item, null);
			TextView mTitle = (TextView) view.findViewById(R.id.tv_title);
			ImageView mIcon = (ImageView) view.findViewById(R.id.iv_icon);
			mTitle.setText(contents[position]);
			if (contentPositon == position) {
				mIcon.setVisibility(View.VISIBLE);
			} else {
				mIcon.setVisibility(View.GONE);
			}
			return view;
		}
		
		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
	}
}
