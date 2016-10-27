package zz.itcast.redbody7.activity;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.bean.AddressListBean;
import zz.itcast.redbody7.utils.PreferenceUtils;
import zz.itcast.redbody7.view.TittleBarView;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 修改地址页面
 * 
 * @author ChengQin
 * 
 */
public class UpdateAddressActivity extends BaseActivity implements
		OnClickListener {

	private TittleBarView tbv;
	private EditText et_name;
	private EditText et_phoneNumber;
	private EditText et_city;
	private EditText et_address;
	private int id;

	/**
	 * 默认的tag值
	 */
	private int isDefault = 0;
	// 默认和删除按钮
	private Button btn_default;
	private Button btn_delete;

	@Override
	public void initRegister() {
		// 返回按钮 跳回地址管理页面
		tbv.setOnBackClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// 保存按钮
		tbv.setOnOperClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// 保存地址数据
				saveDate();
				finish();
			}

		});
		btn_default.setOnClickListener(this);
		btn_delete.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_default:
			setDefault(v);

			break;
		case R.id.btn_delete:
			deleteAddress(v);
			break;

	
		}
	}

	@Override
	/**
	 * 初始化数据
	 */
	public void initData() {
		// 获取地址管理传过来的数据
		Intent intent = getIntent();
		id = intent.getIntExtra("id", 0);
		String name = intent.getStringExtra("name");
		String phoneNumber = intent.getStringExtra("phoneNumber");
		String addressDetail = intent.getStringExtra("addressDetail");

		et_name.setText(name);
		et_phoneNumber.setText(phoneNumber);
		String province = addressDetail.substring(0, 6);
		String address = addressDetail.substring(6);
		et_city.setText(province);
		et_address.setText(address);

	}

	@Override
	/**
	 * 初始化控件
	 */
	public void initView() {
		setContentView(R.layout.activity_updateaddress);

		tbv = (TittleBarView) findViewById(R.id.tbv);
		tbv.setTittle("修改地址");
		tbv.setOperText("保存");

		et_name = (EditText) findViewById(R.id.et_name);
		et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
		et_city = (EditText) findViewById(R.id.et_city);
		et_address = (EditText) findViewById(R.id.et_address);

		// 默认和删除
		btn_default = (Button) findViewById(R.id.btn_default);
		btn_delete = (Button) findViewById(R.id.btn_delete);

	}

	/**
	 * 保存地址
	 */
	private void saveDate() {
		final String name = et_name.getText().toString();
		final String phoneNumber = et_phoneNumber.getText().toString();
		String city = et_city.getText().toString();
		final String addressDetail = et_address.getText().toString();

		HttpUtils httpUtils = new HttpUtils();
		// http://192.168.12.253:8080/ECServicez8/addresssave?name=aaa&phoneNumber=222212&
		// city=1&province=1&area=1&addressDetail=fdadf&zipCode=100195&fixedtel=13
		final String addressDetail1 = city + addressDetail;
		RequestParams params = new RequestParams();
		params.addBodyParameter("id", id + "");
		params.addBodyParameter("name", name);
		params.addBodyParameter("phoneNumber", phoneNumber);
		params.addBodyParameter("city", "1");
		params.addBodyParameter("province", "1");
		params.addBodyParameter("area", "1");
		params.addBodyParameter("addressDetail", addressDetail1);
		params.addBodyParameter("zipCode", "450000");
		params.addBodyParameter("fixedtel", "1");
		params.addBodyParameter("isDefault", isDefault + "");
		httpUtils.send(HttpMethod.POST,
				MyConstants.BASE_URL + "/addressupdate", params,
				new RequestCallBack<String>() {

					public void onSuccess(ResponseInfo<String> responseInfo) {
						Toast.makeText(getApplicationContext(), "保存成功",
								Toast.LENGTH_SHORT).show();

						parseJson(responseInfo.result);
						//将数据存到sp中
						if (isDefault==1) { //等于1 的时候存储
							
							PreferenceUtils.putString(mContext, "name", name);
							PreferenceUtils.putString(mContext, "phoneNumber", phoneNumber);
							PreferenceUtils.putString(mContext, "addressDetail", addressDetail1);
						}
						
					}

					public void onFailure(HttpException error, String msg) {
						Log.i("qc", "保存失败:" + msg);
						System.out.println(error);
					}

				});

	}

	/**
	 * 解析数据
	 * 
	 * @param result
	 */
	protected void parseJson(String json) {
		Gson gson = new Gson();
		AddressListBean addressBean = gson
				.fromJson(json, AddressListBean.class);

	}

	/**
	 * 删除地址
	 * 
	 * @param v
	 *            /addressdelete
	 */
	public void deleteAddress(View v) {
		HttpUtils httpUtils = new HttpUtils();

		RequestParams params = new RequestParams();
		params.addBodyParameter("id", id + "");
		httpUtils.send(HttpMethod.POST,
				MyConstants.BASE_URL + "/addressdelete", params,
				new RequestCallBack<String>() {

					@Override
					/**
					 * 成功
					 */
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Toast.makeText(getApplicationContext(), "删除成功",
								Toast.LENGTH_SHORT).show();
						parseJson(responseInfo.result);
						Intent data = new Intent();
						data.putExtra("id", id);
						setResult(116, data);
						finish();
					}

					@Override
					/**
					 * 失败
					 */
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(getApplicationContext(), "删除失败",
								Toast.LENGTH_SHORT).show();
						Log.i("UpdateAddressActivity", msg);
					}

				});
	}

	/**
	 * 设置为默认地址
	 * 
	 * @param v
	 */
	public void setDefault(View v) {
		Toast.makeText(getApplicationContext(), "设置成功,请保存数据", Toast.LENGTH_SHORT)
				.show();
		// 将default设置为1
		isDefault = 1;
		
	}
}
