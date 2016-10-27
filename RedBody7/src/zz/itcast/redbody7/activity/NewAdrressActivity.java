package zz.itcast.redbody7.activity;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.bean.AddressListBean;
import zz.itcast.redbody7.view.TextContentView;
import zz.itcast.redbody7.view.TittleBarView;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
 * 新增地址页面
 * 
 * @author ChengQin
 * 
 */
public class NewAdrressActivity extends BaseActivity {

	/**
	 * 地址详细
	 */
	private TextContentView mTcv;
	/**
	 * 标题栏 自定义控件
	 */
	private TittleBarView mTbv;
	// 新增地址
	private EditText et_name;
	private EditText et_city;
	private EditText et_phoneNumber;
	private EditText et_address;
	//获取到数据
	private String name;
	private String addressDetail;
	private String city;
	private String phoneNumber;
	
	@Override
	public void initRegister() {
		
		/**
		 * 返回按钮
		 */
		mTbv.setOnBackClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
				finish();
			}
		});
		/**
		 * 保存按钮
		 */
		mTbv.setOnOperClick(new OnClickListener() {

		
			@Override
			public void onClick(View v) {
				name = et_name.getText().toString().trim();
				addressDetail = et_address.getText().toString().trim();
				city = et_city.getText().toString().trim();
				phoneNumber = et_phoneNumber.getText().toString().trim();
				
				if (TextUtils.isEmpty(name)) {
					Toast.makeText(getApplicationContext(), "请输入收货人姓名", Toast.LENGTH_SHORT).show();
					return;
				}
				if (TextUtils.isEmpty(phoneNumber)) {
					Toast.makeText(getApplicationContext(), "请输入收货人电话号码", Toast.LENGTH_SHORT).show();
					return;
				}
				if (TextUtils.isEmpty(city)) {
					Toast.makeText(getApplicationContext(), "请输入省市区", Toast.LENGTH_SHORT).show();
					return;
				}
				if (TextUtils.isEmpty(addressDetail)) {
					Toast.makeText(getApplicationContext(), "请输入详细地址", Toast.LENGTH_SHORT).show();
					return;
				}
				saveAddress();
				//发送数据给地址列表，直接添加到集合中，显示数据
				Intent data = new Intent();
				data.putExtra("name", name);
				data.putExtra("phoneNumber",phoneNumber );
				data.putExtra("addressDetail", city+addressDetail);
				setResult(110, data);
				
				
				finish();
			}
		});
}
	
	@Override
	public void initData() {
		
	}

	private void saveAddress() {
		HttpUtils httpUtils = new HttpUtils();
		//http://192.168.12.253:8080/ECServicez8/addresssave?name=aaa&phoneNumber=222212&
		//city=1&province=1&area=1&addressDetail=fdadf&zipCode=100195&fixedtel=13
		addressDetail = city+addressDetail;
		RequestParams params= new RequestParams();
		params.addBodyParameter("name", name);
		params.addBodyParameter("phoneNumber", phoneNumber);
		params.addBodyParameter("city", "1");
		params.addBodyParameter("province", "1");
		params.addBodyParameter("area", "1");
		params.addBodyParameter("addressDetail", addressDetail);
		params.addBodyParameter("zipCode", "450000");
		params.addBodyParameter("fixedtel", "1");
		
		httpUtils.send(HttpMethod.POST, MyConstants.BASE_URL+"/addresssave", params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				Toast.makeText(getApplicationContext(), 
						"保存成功", Toast.LENGTH_SHORT).show();
				
				
				parseJson(responseInfo.result);
			}
			@Override
			public void onFailure(HttpException error, String msg) {
				Log.i("qc", "保存失败:"+msg );
				System.out.println(error);
			}

		});
	}
	/**
	 * 解析数据
	 * @param result
	 */
	protected void parseJson(String json) {
		Gson gson = new Gson();
		AddressListBean addressBean = gson.fromJson(json, AddressListBean.class);
		
		System.out.println(addressBean.toString());
	}

	@Override
	public void initView() {
		setContentView(R.layout.activity_newaddress);
		// 标题栏设置
		mTbv = (TittleBarView) findViewById(R.id.tbv);
		mTbv.setTittle("新增地址");
		mTbv.setOperText("保存");
		mTbv.setTextSize(15);
		et_name = (EditText) findViewById(R.id.et_name);
		et_city = (EditText) findViewById(R.id.et_city);
		et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
		et_address = (EditText) findViewById(R.id.et_address);
	}

}
