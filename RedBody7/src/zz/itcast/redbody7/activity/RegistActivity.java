package zz.itcast.redbody7.activity;

import org.json.JSONException;
import org.json.JSONObject;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.utils.LogPrint;
import zz.itcast.redbody7.utils.MyUtils;
import zz.itcast.redbody7.view.TittleBarView;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class RegistActivity extends BaseActivity {
	private EditText et_input_username, et_input_pwd, et_input_confirm;
	private TextView btn_regist;
	private TittleBarView tbv_title;
	private HttpUtils httpUtils = new HttpUtils();

	@Override
	public void initRegister() {

	}

	@Override
	public void initData() {

		tbv_title.setTittle("注册");
		tbv_title.setOnBackClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		tbv_title.setOperVisibility(false);

	}

	@Override
	public void initView() {

		setContentView(R.layout.activity_regist);
		tbv_title = (TittleBarView) findViewById(R.id.tbv_title);
		et_input_username = (EditText) findViewById(R.id.et_input_username);
		et_input_pwd = (EditText) findViewById(R.id.et_input_pwd);
		et_input_confirm = (EditText) findViewById(R.id.et_input_confirm);
		btn_regist = (TextView) findViewById(R.id.btn_regist_acti);

		btn_regist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				regist();

			}
		});
	}

	private void regist() {

		final String name = et_input_username.getText().toString().trim();
		final String pwd = et_input_pwd.getText().toString().trim();
		String rePwd = et_input_confirm.getText().toString().trim();

		String Regex = "^1[3578][0-9]{9}$";
		//加入正则匹配
		if (!name.matches(Regex)) {
			Toast.makeText(mContext,"手机号码格式不正确", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
			Toast.makeText(mContext, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
			return;
		
		}

		if (!pwd.equals(rePwd)) {
			Toast.makeText(mContext, "密码不相同", Toast.LENGTH_SHORT).show();
			return;
		}

		RequestParams params = new RequestParams();
		params.addBodyParameter("username", name);
		params.addBodyParameter("password", pwd);
		httpUtils.send(HttpMethod.POST, MyConstants.BASE_URL + "/register",
				params, new RequestCallBack<String>() {
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String resultJson = responseInfo.result;
						parseJson(resultJson, name, pwd);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						error.printStackTrace();
						Log.i("MainActivity", " RegisterActivity --请求失败:" + msg);

					}

				});

	}

	protected void parseJson(String resultJson, String name, String pwd) {

		try {

			JSONObject jsonObject = new JSONObject(resultJson);
			String response = jsonObject.getString("response");

			if ("error".equals(response)) {

				JSONObject errorObj = jsonObject.getJSONObject("error");
				String errorText = errorObj.getString("text");

				Toast.makeText(getApplicationContext(), errorText,
						Toast.LENGTH_SHORT).show();
				LogPrint.logI("lsj", "用户名存在");
			} else {
				// 解析成功的结果

				if ("register".equals(response)) {

					JSONObject userinfo = jsonObject.getJSONObject("userinfo");

					int userid = userinfo.getInt("userid");

					Toast.makeText(this, "注册成功:" + userid, Toast.LENGTH_SHORT)
							.show();
					LogPrint.logI("lsj", "注册成功");

					// startActivity(new
					// Intent(RegistActivity.this,Login2Activity.class));

					login(name, pwd);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void login(final String name, String pwd) {

		RequestParams params = new RequestParams();
		params.addBodyParameter("username", name);
		params.addBodyParameter("password", pwd);

		httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.POST, MyConstants.BASE_URL + "/login",
				params, new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						String responseJson = responseInfo.result;

						parseJson(responseJson, name);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						error.printStackTrace();
						LogPrint.logI("Login2Activity", "错误信息" + msg);
					}
				});

	}

	protected void parseJson(String responseJson, String userName) {
		try {

			JSONObject jsonObject = new JSONObject(responseJson);
			String response = jsonObject.getString("response");

			if ("login".equals(response)) {

				JSONObject userInfo = jsonObject.getJSONObject("userinfo");
				int userId = userInfo.getInt("userid");
				LogPrint.logI("lsj", "Loginactivity -userId: " + userId);

				MyUtils.userId = userId;
				MyUtils.userName = userName;

				finish();

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
