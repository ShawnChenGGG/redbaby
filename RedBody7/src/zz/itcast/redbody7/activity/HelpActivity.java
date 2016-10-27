package zz.itcast.redbody7.activity;

import java.util.List;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.adapter.DefaultBaseAdapter;
import zz.itcast.redbody7.bean.HelpBean;
import zz.itcast.redbody7.bean.HelpBean.HelpData;
import zz.itcast.redbody7.utils.LogPrint;
import zz.itcast.redbody7.utils.MyUtils;
import zz.itcast.redbody7.view.TittleBarView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class HelpActivity extends Activity {

	private TittleBarView tbv_help_title;
	/**
	 * 从服务器获得的新的version
	 */
	private int newVersion;
	/**
	 * 从服务器获得的帮助=数据列表
	 */
	private List<HelpData> helpList;
	/**
	 * lIstView的对象
	 */
	private ListView lv_help;
	private MyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView();

		initData();

		initListener();

	}

	private void initListener() {

	}

	private void initData() {

		requestData();

	}

	private void requestData() {
		HttpUtils hu = new HttpUtils();
		String url = MyConstants.BASE_URL + "/help";

		RequestParams params = new RequestParams();
		params.addBodyParameter("Vsersion", 0 + "");

		hu.send(HttpMethod.GET, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

				LogPrint.logI("lsj", "/help 联网失败");
				// pd.dismiss();
				// tb_fragment_shopping_Pd.setVisibility(View.GONE);

				// 显示联网失败对话框
				AlertDialog.Builder adb = new Builder(HelpActivity.this);
				final AlertDialog ad = adb.create();
				ad.setMessage("联网失败");
				ad.setButton(-1, "确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						ad.dismiss();
					}
				});

				ad.show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {

				LogPrint.logI("lsj", "/help 联网成功");

				String json = arg0.result;
				parseJson(json);
			}
		});
	}

	protected void parseJson(String json) {

		if (MyUtils.checkJson(json)) {

			LogPrint.logI("lsj", "/help json解析成功 ");

			// 消失进度条
			// pd.dismiss();
			// tb_fragment_shopping_Pd.setVisibility(View.GONE);

			Gson gson = new Gson();
			HelpBean helpBean = gson.fromJson(json, HelpBean.class);

			newVersion = helpBean.version;

			helpList = helpBean.helpList;

			// 设置适配器
			adapter = new MyAdapter(helpList);

			lv_help.addFooterView(View.inflate(HelpActivity.this,
					R.layout.item_help_foot, null));

			lv_help.setAdapter(adapter);

			lv_help.setOnItemClickListener(new OnMyItemClickListener());

		} else {
			LogPrint.logI("lsj", "/help json解析失败 ");
		}
	}

	private void initView() {

		setContentView(R.layout.activity_help);

		lv_help = (ListView) findViewById(R.id.lv_help);

		tbv_help_title = (TittleBarView) findViewById(R.id.tbv_help_title);

		tbv_help_title.setTittle("帮助中心");

		tbv_help_title.setOperVisibility(false);

		tbv_help_title.setOnBackClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	/**
	 * LIstVie列表的每个条目的点击事件
	 * 
	 * @author 李胜杰
	 * 
	 */
	private final class OnMyItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			if (position == adapter.getCount()) {
				
				// 打电话
				// 意图 想法 企图
				Intent intent = new Intent();
				// 你想干啥
				// 我想打电话
				intent.setAction(Intent.ACTION_CALL);
				// 我要打给谁
				intent.setData(Uri.parse("tel:18860233257"));

				// 让意图按照我的要求干活去吧
				startActivity(intent);
				return;
			}

			int id2 = helpList.get(position).id;
			LogPrint.logI("lsj", "帮助中心获取每个条目的id2 : " + id2);
			// 请求数据，进行帮助内容获取界面

			Intent intent = new Intent(HelpActivity.this,
					HelpContentActivity.class);

			intent.putExtra("id", id2);

			startActivity(intent);

		}
	}

	private class MyAdapter extends DefaultBaseAdapter<HelpData> {

		public MyAdapter(List<HelpData> datas) {
			super(datas);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder vh = null;

			if (convertView == null) {

				convertView = View.inflate(HelpActivity.this,
						R.layout.help_item, null);

				vh = new ViewHolder();

				vh.tv_desc = (TextView) convertView
						.findViewById(R.id.tv_help_desc);

				convertView.setTag(vh);

			} else {
				vh = (ViewHolder) convertView.getTag();

			}

			// 填充数据

			vh.tv_desc.setText(datas.get(position).title);
			vh.id = datas.get(position).id;

			return convertView;
		}
	}

	private class ViewHolder {
		private TextView tv_desc;
		private int id;
	}

}
