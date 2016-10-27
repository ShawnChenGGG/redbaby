package zz.itcast.redbody7.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.fragment.MoreFragment;
import zz.itcast.redbody7.utils.LogPrint;
import zz.itcast.redbody7.utils.MyUtils;
import zz.itcast.redbody7.view.TittleBarView;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

	private TittleBarView tbv_title;
	private TextView moper;

	private ImageView mback;
	private static EditText et_username, et_pwd;
	private TextView btn_login;
	private TextView tv_fast_regist, tv_forget;
	public HttpUtils httpUtils;
	private static int userId;
	private String username;
	private String pwd;
	private String userNameText;
	private String pwdText;
	
	@Override
	public void initRegister() {

	}

	@Override
	public void initData() {
		tbv_title.setTittle("用户登陆");
		tbv_title.setOperVisibility(false);

	}

	private boolean isLoad;

	private void login() {

		userNameText = et_username.getText().toString().trim();
		pwdText = et_pwd.getText().toString().trim();

		if (TextUtils.isEmpty(userNameText) || TextUtils.isEmpty(pwdText)) {

			Toast.makeText(getApplicationContext(), "请输入用户名或密码", 0).show();
			return;
		}

		RequestParams params = new RequestParams();
		params.addBodyParameter("username", userNameText);
		params.addBodyParameter("password", pwdText);
		httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.POST, MyConstants.BASE_URL + "/login",
				params, new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String responseJson = responseInfo.result;
						System.out.println(responseJson);
						try {
							parseJson(responseJson);
							isLoad = true;
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						error.printStackTrace();
						LogPrint.logI("LoginActivity", "错误信息" + msg);
					}
				});

	}

	protected void parseJson(String responseJson) throws JSONException {
		JSONObject jsonObject = new JSONObject(responseJson);
		String response = jsonObject.getString("response");
		parseUserInfo(response, jsonObject);
	}

	private void parseUserInfo(String response, JSONObject jsonObject)
			throws JSONException {

		if ("login".equals(response)) {
			JSONObject userInfo = jsonObject.getJSONObject("userinfo");
			userId = userInfo.getInt("userid");
			LogPrint.logI("lsj", "Loginactivity -userId: " + userId);
			
			MyUtils.userId = userId;
			MyUtils.userName = userNameText;
			
			Intent data = new Intent();
			data.putExtra("userId", userId);
			setResult(10, data);
			finish();

		}

	}


	@Override
	public void initView() {

		setContentView(R.layout.activity_login);

		mback = (ImageView) findViewById(R.id.iv_back);
		moper = (TextView) findViewById(R.id.tv_oper);
		et_username = (EditText) findViewById(R.id.et_username);
		et_pwd = (EditText) findViewById(R.id.et_pwd);
		btn_login = (TextView) findViewById(R.id.btn_login);
		tv_fast_regist = (TextView) findViewById(R.id.tv_fast_reist);
		tv_forget = (TextView) findViewById(R.id.tv_forget);

		tbv_title = (TittleBarView) findViewById(R.id.tbv_title);
		tv_fast_regist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(LoginActivity.this,
						RegistActivity.class);
				startActivity(intent);
			}
		});

		// 注册
		mback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();
			}
		});

		// 登录
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				login();
				

			}
		});
	}

}
