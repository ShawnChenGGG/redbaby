/**
 * @date 2016-4-26 下午4:37:08
 */
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
import zz.itcast.redbody7.utils.LogPrint;
import zz.itcast.redbody7.utils.MyUtils;
import zz.itcast.redbody7.utils.SecurityCode;
import zz.itcast.redbody7.view.TittleBarView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author ma
 * 
 * @date 2016-4-26 下午4:37:08
 */
public class Login2Activity extends Activity implements OnClickListener {

	private ImageView mSecurity;
	private TextView mUserName;
	private TextView mUserPwd;
	private TextView mSecurityCode;
	private Button mLogin;
	private String security;
	private TextView mFastRegist;
	private TextView mForgetPwd;
	private TittleBarView tbv_title;
	public HttpUtils httpUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login2);
		tbv_title = (TittleBarView) findViewById(R.id.tbv_title);
		tbv_title.setTittle("登录");
		tbv_title.setOperVisibility(false);
		tbv_title.setOnBackClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		initView();
		security = initSecurity();

	}

	/**
	 * 初始化控件
	 * 
	 * @date 2016-4-26 下午3:59:27
	 */
	private void initView() {
		mSecurity = (ImageView) findViewById(R.id.iv_security);
		mUserName = (TextView) findViewById(R.id.et_user_name);
		mUserPwd = (TextView) findViewById(R.id.et_user_pwd);
		mSecurityCode = (TextView) findViewById(R.id.et_security_code);
		mLogin = (Button) findViewById(R.id.btn_login);

		mFastRegist = (TextView) findViewById(R.id.tv_fast_regist);
		mFastRegist.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		mForgetPwd = (TextView) findViewById(R.id.tv_forget_pwd);
		mForgetPwd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		mSecurity.setOnClickListener(this);
		mLogin.setOnClickListener(this);
		mFastRegist.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_security:
			security = initSecurity();
			break;
		case R.id.btn_login:
			loginAccout();
			break;
		case R.id.tv_fast_regist:
			fastRegist();
			break;
		case R.id.tv_forget_pwd:
			forgetPwd();
			break;
		}
	}

	/**
	 * 快速注册
	 * 
	 * @date 2016-4-26 下午4:28:39
	 */
	private void fastRegist() {
		LogPrint.logI("lsj", "快速登录");
		startActivity(new Intent(this, RegistActivity.class));
		finish();
	}

	/**
	 * 忘记密码
	 * 
	 * @date 2016-4-26 下午4:29:12
	 */
	private void forgetPwd() {
		// TODO 跳转忘记密码
	}

	/**
	 * 登录账号
	 * 
	 * @date 2016-4-26 下午4:06:31
	 */
	private void loginAccout() {
		// 判断用户名是否为空,//判断密码是否为空,//判断验证码是否正确,//判断用户是否存在
		String userName = mUserName.getText().toString();
		String userPwd = mUserPwd.getText().toString();
		String securityCode = mSecurityCode.getText().toString();

		if (TextUtils.isEmpty(userName)) {
			Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		if (TextUtils.isEmpty(userPwd)) {
			Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		if (TextUtils.isEmpty(securityCode)) {
			Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
			return;
		}
		if (security.equalsIgnoreCase(securityCode)) {
			LogPrint.logI("lsj", "验证码正确");
		} else {
			Toast.makeText(this, "验证码不正确,请正确输入", Toast.LENGTH_SHORT).show();
			// 重新生成验证码
			initSecurity();
			mSecurityCode.setText("");
			return;
		}

		login(userName, userPwd);
	}

	/**
	 * 生成验证码
	 * 
	 * @date 2016-4-26 下午4:04:12
	 */
	private String initSecurity() {
		SecurityCode securityCode = SecurityCode.getInstance();
		DisplayMetrics disPaly = new DisplayMetrics();
		disPaly.scaledDensity = 5.0f;
		Bitmap bitmap = securityCode.createBitmap(140, 28, 22, this);
		mSecurity.setImageBitmap(bitmap);
		String code = securityCode.getCode();
		return code;
	}

	private void login(final String userName, String userPwd) {

		RequestParams params = new RequestParams();
		params.addBodyParameter("username", userName);
		params.addBodyParameter("password", userPwd);

		httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.POST, MyConstants.BASE_URL + "/login",
				params, new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						String responseJson = responseInfo.result;

						parseJson(responseJson, userName);
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

			} else {
				Toast.makeText(this, "密码不正确", Toast.LENGTH_SHORT).show();
				mUserPwd.setText("");

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
