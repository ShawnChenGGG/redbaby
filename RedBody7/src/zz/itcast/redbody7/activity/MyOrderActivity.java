package zz.itcast.redbody7.activity;

import java.util.List;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.bean.OrderListType1;
import zz.itcast.redbody7.bean.OrderListType1.OrderlistBean;
import zz.itcast.redbody7.utils.MyHttpUtils;
import zz.itcast.redbody7.utils.MyHttpUtils.OnPostRequestCallBack;
import zz.itcast.redbody7.view.OrderListInfoView;
import zz.itcast.redbody7.view.TittleBarView;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

/**
 * 我的订单页面
 * 
 * @author gjl
 * */
public class MyOrderActivity extends BaseActivity implements OnClickListener {

	private Button btn_one_month, btn_ago_month, btn_cancel;
	private TittleBarView tbv_order;
	private LinearLayout mContains;
	private String name;
	private String addressDetail;
	private String phoneNumber;

	@Override
	public void initRegister() {
		// TODO Auto-generated method stub
		// 服务器端返回的数据
		// {"orderinfo":{"orderid":"96","paymenttype":"货到付款","price":174},"response":"ordersumbit"}
	}

	@Override
	public void initData() {
		// 向服务器端提交的url http://192.168.12.253:8080/ECServicez8/orderlist
		// ?type=2&page=0&pageNum=10&userid=2
		
		mContains.removeAllViews();
		getNetData(1 + "");
		
		name = getIntent().getStringExtra("name");
		addressDetail = getIntent().getStringExtra("addressDetail");
		phoneNumber = getIntent().getStringExtra("phoneNumber");
	}

	/**
	 * @param type
	 * @date 2016-4-25 下午3:50:29
	 */
	private void getNetData(String type) {
		String url = MyConstants.BASE_URL + "/orderlist";
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", type);
		params.addBodyParameter("page", 0 + "");
		params.addBodyParameter("pageNum", 10 + "");
		params.addBodyParameter("userid", 2 + "");
		MyHttpUtils httpUtils = new MyHttpUtils(url, params,false);
		httpUtils.setPostRequestCallBack(new OnPostRequestCallBack() {

			@Override
			public void onPostSuccess(String result) {
				parserJson(result);
			}
		});
	}

	/**
	 * 解析近一个月的订单
	 * 
	 * @date 2016-4-25 下午3:31:20
	 */
	protected void parserJson(String result) {
		Gson gson = new Gson();
		OrderListType1 listType1 = gson.fromJson(result, OrderListType1.class);
		List<OrderlistBean> lists = listType1.orderlist;
		for (int i = 0; i < lists.size(); i++) {
			final OrderlistBean list = lists.get(i);
			OrderListInfoView listInfoView = new OrderListInfoView(mContext);
			listInfoView.setOrderNum(list.orderid + "");
			listInfoView.setOrderPrice(list.price + "");
			listInfoView.setOrderTime(list.time);
			listInfoView.setOnViewClick(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext,OrderDetailsActivity.class);
					intent.putExtra("orderId", list.orderid);
					intent.putExtra("name", name);
					intent.putExtra("addressDetail", addressDetail);
					intent.putExtra("phoneNumber", phoneNumber);
					
					startActivity(intent);
					
					finish();
				}
			});
			mContains.addView(listInfoView);
		}

	}

	/**
	 * 初始化view
	 * */
	@Override
	public void initView() {
		setContentView(R.layout.activity_my_order);
		btn_one_month = (Button) findViewById(R.id.btn_one_month);
		btn_ago_month = (Button) findViewById(R.id.btn_ago_month);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		mContains = (LinearLayout) findViewById(R.id.ll_contains);
		tbv_order = (TittleBarView) findViewById(R.id.tbv);

		tbv_order.setTittle("我的订单");
		tbv_order.setOperVisibility(false);
		
		btn_one_month.setSelected(true);
		btn_ago_month.setSelected(false);
		btn_cancel.setSelected(false);

		btn_one_month.setOnClickListener(this);
		btn_ago_month.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);

		// 订单的头部
		tbv_order = (TittleBarView) findViewById(R.id.tbv);
		tbv_order.setOnBackClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	@Override
	public void onClick(View v) {
		mContains.removeAllViews();
		switch (v.getId()) {
		case R.id.btn_one_month:
			btn_one_month.setSelected(true);
			btn_ago_month.setSelected(false);
			btn_cancel.setSelected(false);
			paserJsonType1();
			break;
		case R.id.btn_ago_month:
			btn_one_month.setSelected(false);
			btn_ago_month.setSelected(true);
			btn_cancel.setSelected(false);
			paserJsonType2();
			break;
		case R.id.btn_cancel:
			btn_one_month.setSelected(false);
			btn_ago_month.setSelected(false);
			btn_cancel.setSelected(true);
			paserJsonType3();
			break;
		}
	}

	/**
	 * @date 2016-4-25 下午4:21:39
	 */
	private void paserJsonType3() {
		getNetData(3 + "");
	}

	/**
	 * 获得一个月前的数据
	 * 
	 * @date 2016-4-25 下午4:07:33
	 */
	private void paserJsonType2() {
		getNetData(2 + "");
	}

	/**
	 * 解析近一个月的订单
	 * 
	 * @date 2016-4-25 下午3:49:00
	 */
	private void paserJsonType1() {
		getNetData(1 + "");
	}

}
