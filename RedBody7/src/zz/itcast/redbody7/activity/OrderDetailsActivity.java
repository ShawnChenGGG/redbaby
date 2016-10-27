package zz.itcast.redbody7.activity;

import org.json.JSONException;
import org.json.JSONObject;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.bean.OrderDetailBean;
import zz.itcast.redbody7.utils.MyHttpUtils;
import zz.itcast.redbody7.utils.MyHttpUtils.OnPostRequestCallBack;
import zz.itcast.redbody7.view.GoodsInfoListView;
import zz.itcast.redbody7.view.TittleBarView;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lidroid.xutils.http.RequestParams;

public class OrderDetailsActivity extends BaseActivity implements
		OnClickListener {

	private TittleBarView tbv_details;

	private TextView tv_product_name;

	private RelativeLayout rl_logistics;

	private Button btn_cancel_order;

	private String name;

	private String addressDetail;

	private String phoneNumber;

	private int orderId;

	@Override
	public void initRegister() {

	}

	@Override
	public void initData() {
		tbv_details.setTittle("订单详情");
		tbv_details.setOperVisibility(false);

		// 收货人姓名
		TextView tv_name = (TextView) findViewById(R.id.tv_name);
		name = getIntent().getStringExtra("name");
		tv_name.setText(name);

		// 收货人电话
		TextView tv_phone = (TextView) findViewById(R.id.tv_phone);
		phoneNumber = getIntent().getStringExtra("phoneNumber");
		tv_phone.setText(phoneNumber);

		// 收货人详细信息
		TextView tv_address = (TextView) findViewById(R.id.tv_address);
		addressDetail = getIntent().getStringExtra("addressDetail");
		tv_address.setText(addressDetail);

		// 订单id
		TextView tv_order_num = (TextView) findViewById(R.id.tv_order_num);

		// 获得传来的订单id
		orderId = getIntent().getIntExtra("orderId", 0);
		tv_order_num.setText(orderId + "");
		getNetData(orderId);
	}

	/**
	 * 根据orderId获得网络数据
	 * 
	 * @date 2016-4-25 下午4:58:06
	 */
	private void getNetData(int orderId) {

		String url = MyConstants.BASE_URL + "/orderdetail";
		RequestParams params = new RequestParams();
		params.addBodyParameter("orderid", orderId + "");
		MyHttpUtils httpUtils = new MyHttpUtils(url, params, true);
		httpUtils.setPostRequestCallBack(new OnPostRequestCallBack() {

			@Override
			public void onPostSuccess(String result) {
				parserJson(result);
			}
		});
	}

	/**
	 * 解析从网络获取的字符串
	 * 
	 * @date 2016-4-25 下午5:21:34
	 */
	protected void parserJson(String result) {
		// 支付方式
		TextView tv_pay_way = (TextView) findViewById(R.id.tv_pay_way);
		// 送货方式
		TextView tv_send_request = (TextView) findViewById(R.id.tv_send_request);
		// 订单生成时间
		TextView tv_order_time = (TextView) findViewById(R.id.tv_order_time);
		// 商品价格
		TextView tv_price = (TextView) findViewById(R.id.tv_price);

		Gson gson = new Gson();
		OrderDetailBean detailBean = gson.fromJson(result,
				OrderDetailBean.class);
		// 送货方式
		String delivery_info = detailBean.order_info.delivery_info;
		String time = detailBean.order_info.time;
		int price = detailBean.order_info.price;
		tv_send_request.setText(delivery_info);
		tv_order_time.setText(time);
		tv_price.setText(price + "");

	}

	@Override
	public void initView() {
		setContentView(R.layout.activity_order_details);

		tbv_details = (TittleBarView) findViewById(R.id.tbv);

		tv_product_name = (TextView) findViewById(R.id.tv_product_name);

		rl_logistics = (RelativeLayout) findViewById(R.id.rl_logistics);

		btn_cancel_order = (Button) findViewById(R.id.btn_cancle_order_list);

		rl_logistics.setOnClickListener(this);

		tbv_details.setOnBackClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// 取消订单
		btn_cancel_order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//
				String url = MyConstants.BASE_URL + "/ordercancel";
				RequestParams params = new RequestParams();
				params.addBodyParameter("orderId", orderId + "");
				MyHttpUtils httpUtils = new MyHttpUtils(url, params,true);
				httpUtils.setPostRequestCallBack(new OnPostRequestCallBack() {

					@Override
					public void onPostSuccess(String result) {
						parserCancelJson(result);
					}

				});
				finish();
			}
		});

	}

	/**
	 * 取消订单网络json数据的获取
	 * 
	 * @date 2016-4-25 下午5:46:08
	 */
	private void parserCancelJson(String result) {
		// http://192.168.12.253:8080/ECServicez8//ordercancel?orderId=82

		// {"response":"ordercancel","result":true}
		try {
			JSONObject jsonObject = new JSONObject(result);
			String res = jsonObject.getString("result");
			if("true".equals(res)){
				Toast.makeText(mContext, "订单取消成功", 0).show();
			}
		} catch (JSONException e) {
		}
		
	}

	//物流查询
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, ExpressQueryActivity.class);
		startActivity(intent);
	}

}
