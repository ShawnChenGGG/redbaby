package zz.itcast.redbody7.activity;

import java.util.List;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.bean.HelpContentBean;
import zz.itcast.redbody7.bean.HelpContentBean.HelpContentData;
import zz.itcast.redbody7.utils.LogPrint;
import zz.itcast.redbody7.utils.MyUtils;
import zz.itcast.redbody7.view.TittleBarView;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class HelpContentActivity extends BaseActivity {

	private TextView tv_help_content;
	private TextView tv_help_title;
	private TittleBarView tbv_title;
	private String id;

	@Override
	public void initRegister() {

	}

	@Override
	public void initData() {
		
		tbv_title.setTittle("帮助中心内容");
		tbv_title.setOnBackClick(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				finish();
			}
		});
		tbv_title.setOperVisibility(false);

		requestData();

	}

	private void requestData() {
		HttpUtils hu = new HttpUtils();
		String url = MyConstants.BASE_URL + "/help_detail";

		RequestParams params = new RequestParams();
		params.addBodyParameter("id", id + "");

		hu.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

				LogPrint.logI("lsj", "/help_detail 联网失败");
				// pd.dismiss();
				// tb_fragment_shopping_Pd.setVisibility(View.GONE);

				// 显示联网失败对话框
				AlertDialog.Builder adb = new Builder(HelpContentActivity.this);
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

				LogPrint.logI("lsj", "/help_detail 联网成功");

				String json = arg0.result;
				LogPrint.logI("lsj", "/help_detail json "+json);
				parseJson(json);
			}
		});
	}

	@Override
	public void initView() {

		Intent intent = getIntent();

		Bundle extras = intent.getExtras();
		id = extras.get("id")+"";
		LogPrint.logI("lsj", "/help_detail Id： "+id);
		
		setContentView(R.layout.activity_help_content);

		tv_help_content = (TextView) findViewById(R.id.tv_help_content);
		tv_help_title = (TextView) findViewById(R.id.tv_help_title);
		tbv_title = (TittleBarView) findViewById(R.id.tbv_title);

	}

	protected void parseJson(String json) {

		if (MyUtils.checkJson(json)) {

			LogPrint.logI("lsj", "/help_detail json解析成功 ");

			// 消失进度条
			// pd.dismiss();
			// tb_fragment_shopping_Pd.setVisibility(View.GONE);

			Gson gson = new Gson();
			HelpContentBean fromJson = gson.fromJson(json,
					HelpContentBean.class);
			List<HelpContentData> help = fromJson.help;

			tv_help_title.setText(help.get(0).title);
			tv_help_content.setText(help.get(0).content);

		} else {
			LogPrint.logI("lsj", "/help_detail json解析失败 ");
		}
	}
}
