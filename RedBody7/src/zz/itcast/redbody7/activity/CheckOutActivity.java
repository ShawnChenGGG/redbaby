/**
 * @date 2016-4-22 下午6:43:16
 */
package zz.itcast.redbody7.activity;

import java.util.List;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.bean.CartBean;
import zz.itcast.redbody7.bean.CheckOutBean;
import zz.itcast.redbody7.bean.CheckOutBean.DeliveryListBean;
import zz.itcast.redbody7.bean.CheckOutBean.PaymentListBean;
import zz.itcast.redbody7.bean.CheckOutBean.ProductListBean;
import zz.itcast.redbody7.dao.CartDao;
import zz.itcast.redbody7.utils.MyHttpUtils;
import zz.itcast.redbody7.utils.MyHttpUtils.OnPostRequestCallBack;
import zz.itcast.redbody7.utils.PreferenceUtils;
import zz.itcast.redbody7.view.GoodsInfoListView;
import zz.itcast.redbody7.view.LoadAnimationView;
import zz.itcast.redbody7.view.TextContentView;
import zz.itcast.redbody7.view.TittleBarView;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.http.RequestParams;

/**
 * @author ma
 * 
 * @date 2016-4-22 下午6:43:16
 */
public class CheckOutActivity extends BaseActivity implements OnClickListener {

	private TittleBarView mTbv;
	private TextContentView mAddressInfo;
	private List<PaymentListBean> paymentList;
	private TextContentView mPaymentType;
	private TextContentView mDeliveryTime;
	private LinearLayout mContains;
	private BitmapUtils bitmapUtils;
	private int addressId;
	private int paymenyTypeId;
	private int deliveryTimeId;
	private String addressDetail;
	private String name;
	private String phoneNumber;
	private TextContentView mInvoiceType;
	private LoadAnimationView mLoad;
	private LinearLayout mContains2;

	@Override
	public void initRegister() {
	}

	@Override
	/**
	 * 初始化View
	 */
	public void initView() {
		setContentView(R.layout.activity_check_out);
		//给头标题设置一些属性
		mTbv = (TittleBarView) findViewById(R.id.tbv);
		mLoad = (LoadAnimationView) findViewById(R.id.load_animation);
		mContains2 = (LinearLayout) findViewById(R.id.ll_contains2);
		mContains2.setVisibility(View.INVISIBLE);
		mLoad.setVisibility(View.VISIBLE);
		mTbv.setOnBackClick(this);
		mTbv.setTittle("结算中心");
		mTbv.setOperText("提交");
		mTbv.setTextSize(15);
		mTbv.setOnOperClick(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(TextUtils.isEmpty(name)){
					Toast.makeText(mContext, "收货地址不能为空", 0).show();
					return;
				}
				//将地址信息传递到启动的界面
				Intent intent = new Intent(CheckOutActivity.this, MyOrderActivity.class);
				intent.putExtra("name", name);
				intent.putExtra("addressDetail", addressDetail);
				intent.putExtra("phoneNumber", phoneNumber);
				startActivity(intent);
			}
		});
		
		// 处理收货人信息
		dealAddressInfo();
		// 处理支付方式
		dealPaymentType();
		//处理送货时间
		dealDeliveryTime();
		//处理发票类型
		dealIncoiceType();
		
	}

	/**
	 * 处理收货人信息
	 * 
	 * @date 2016-4-23 下午1:28:36
	 */
	private void dealAddressInfo() {
		mAddressInfo = (TextContentView) findViewById(R.id.tcv_address_info);
		mAddressInfo.isShowConanctsInfo(false);
		mAddressInfo.setDesc("收货人信息");
		/**
		 * 整个控件的点击事件
		 */
		mAddressInfo.setViewOnClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, AddressListActivity.class);
				startActivityForResult(intent, 100);
				
			}
		});
	}

	/**
	 * 处理支付方式信息
	 * 
	 * @date 2016-4-23 下午1:30:56
	 */
	private void dealPaymentType() {
		mPaymentType = (TextContentView) findViewById(R.id.tcv_payment_type);
		mPaymentType.setDesc("支付方式");
		mPaymentType.isShowConanctsInfo(false);
		mPaymentType.setViewOnClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//List<PaymentListBean> paymentList = outBean.paymentList;
				Intent intent = new Intent(mContext, PaymentTypeActivity.class);
				startActivityForResult(intent, 100);
			}
		});
	}
	
	/**
	 * 
	 * @date 2016-4-24 上午10:14:45
	 */
	private void dealIncoiceType() {
		
		mInvoiceType = (TextContentView) findViewById(R.id.tcv_invoice_type);
		mInvoiceType.setDesc("索取发票");
		mInvoiceType.isShowConanctsInfo(false);
		mInvoiceType.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, IncoiceTypeActivity.class);
				startActivityForResult(intent, 100);
			}
		});
	}

	/**
	 * 处理送货时间
	 * 
	 * @date 2016-4-23 下午2:05:26
	 */
	private void dealDeliveryTime() {
		mDeliveryTime = (TextContentView) findViewById(R.id.tcv_delivery_type);
		mDeliveryTime.setDesc("送货时间");
		mDeliveryTime.isShowConanctsInfo(false);
		mDeliveryTime.setViewOnClick(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 跳转到送货时间的界面
				Intent intent = new Intent(mContext, DeliveryTimeActivity.class);
				
				startActivityForResult(intent, 100);
			}
		});
		
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//判断intent数据是否为空
		if (data != null) {
			if (requestCode == 100) {
				switch (resultCode) {
				case 1:
					addressId = data.getIntExtra("id", 0);
					
					addressDetail = data.getStringExtra("addressDetail");
					name = data.getStringExtra("name");
					phoneNumber = data.getStringExtra("phoneNumber");
					
					mAddressInfo.isShowConanctsInfo(true);
					mAddressInfo.setDesc("收货人信息:");
					mAddressInfo.setName(name);
					mAddressInfo.setNumber(phoneNumber);
					mAddressInfo.setAddress(addressDetail);
					break;
				case 2:
					paymenyTypeId = data.getIntExtra("type", 0);
					String paymentDec = data.getStringExtra("des");
					mPaymentType.setDesc("支付方式: " + paymentDec);
					break;
				case 3:
					deliveryTimeId = data.getIntExtra("type", 0);
					String deliveryTimeDesc = data.getStringExtra("des");
					mDeliveryTime.setDesc("送货方式");
					mDeliveryTime.setName("送货时间:"+deliveryTimeDesc);
					mDeliveryTime.showName();
					break;
				case 4:
					String title = data.getStringExtra("title");
					String content = data.getStringExtra("content");
					mInvoiceType.setDesc("索取发票:" + title+"/"+content);
				}
			}
		}
	}
	@Override
	/**
	 * 初始化数据
	 */
	public void initData() {
		//1.通过查询本地数据库获得得到商品信息的sku
		CartDao dao = CartDao.getInstance(mContext);
		List<CartBean> infos = dao.findGoodsInfo();
		//从本地数据库获得sku
		String sku = AppendInfos(infos);
		//2.通过网络请求获得数据
		String url = MyConstants.BASE_URL+"/checkout";
		RequestParams params = new RequestParams();
		params.addBodyParameter("sku", sku);
		MyHttpUtils httpUtils = new MyHttpUtils(url, params,true);
		httpUtils.setPostRequestCallBack(new OnPostRequestCallBack() {
			@Override
			public void onPostSuccess(String result) {
				SystemClock.sleep(1000);
				PreferenceUtils.putString(mContext, MyConstants.KEY_CHECK_OUT_JSON, result);
				//解析json字符串
				if(!TextUtils.isEmpty(result)){
					parseJson(result);
				}
			}
		});
	}

	/**
	 * @param infos 
	 * @date 2016-4-24 下午12:50:09
	 */
	private String AppendInfos(List<CartBean> infos) {
		StringBuilder sb = new StringBuilder();

		if(infos != null && infos.size()>0){
			for (CartBean cartBean : infos) {

				int goodsId = cartBean.goodsId;
				int count = cartBean.count;
				String attrId = cartBean.attrId;
				sb.append(goodsId + ":" + count + ":" + attrId + "|");
			}
			
			return sb.deleteCharAt(sb.length()-1).toString();
		}
		return null;
		
		
	}

	/**
	 * 解析json字符串
	 * @date 2016-4-24 上午11:33:52
	 */
	protected void parseJson(String result) {
		Gson gson = new Gson();
		CheckOutBean outBean = gson.fromJson(result, CheckOutBean.class);
		paymentList = outBean.paymentList;
		//添加之前先清空集合
		MyConstants.paymenType.clear();
		MyConstants.paymenType.addAll(paymentList);
		//送货方式集合
		List<DeliveryListBean> deliveryList = outBean.deliveryList;
		//给deliveryType集合赋值
		//添加之前先清空集合
		MyConstants.deliveryType.clear();
		MyConstants.deliveryType.addAll(deliveryList);
		
		mLoad.setVisibility(View.GONE);
		mContains2.setVisibility(View.VISIBLE);
		//商品信息集合
		List<ProductListBean> productList = outBean.productList; 
		
		/**
		 * 处理商品信息
		 */
		dealGoodsInfo(productList);
		initGoodsInfo(productList);
	}

	/**
	 * @param productList 
	 * @date 2016-4-25 上午1:29:23
	 */
	private void initGoodsInfo(List<ProductListBean> productList) {
		TextView mNumTotal = (TextView) findViewById(R.id.tv_num_total);
		TextView mOriginalPrice = (TextView) findViewById(R.id.tv_original_price);
		TextView mYunFei = (TextView) findViewById(R.id.tv_yunfei);
		TextView mPreferentialPrice = (TextView) findViewById(R.id.tv_preferential_price);
		TextView mGoodsPrice= (TextView) findViewById(R.id.tv_goods);
		
		mNumTotal.setText(productList.size()*2+"件");
		int originalPrice = 0;
		for (int i = 0; i < productList.size(); i++) {
			ProductListBean bean = productList.get(i);
			originalPrice+=bean.price;
		}
		mOriginalPrice.setText("$"+originalPrice*2);
		mYunFei.setText("$10");
		mPreferentialPrice.setText("$30");
		originalPrice = originalPrice*2-30+10;
		mGoodsPrice.setText("$"+originalPrice);
		
	}
	
	/**
	 * @param productList 
	 * @date 2016-4-24 下午9:43:25
	 */
	private void dealGoodsInfo(List<ProductListBean> productList) {
		//获得bitmapUtils 
		bitmapUtils = new BitmapUtils(mContext);
		mContains = (LinearLayout) findViewById(R.id.ll_contains);
		for(int i = 0;i<productList.size();i++){
			GoodsInfoListView goodsInfo = new GoodsInfoListView(mContext);
			goodsInfo.setIsShowmCheckBox(false);
			ProductListBean infos = productList.get(i);
			//
			//设置商品图片,用btiMapUtils做三级缓存
			bitmapUtils.display(goodsInfo.mGoodsPic, infos.pic.picUrl);
			goodsInfo.setGoodsDesc(infos.name);
			if(i == 0){
				goodsInfo.setGoodsAttrColor("颜色:均色");
			}else if(i == 1){
				goodsInfo.setGoodsAttrColor("颜色:红色");
			}
			goodsInfo.setGoodsCount(2+"");
			goodsInfo.setGoodsSize("尺码:均码");
			goodsInfo.setGoodsPrice("$"+infos.price);
			goodsInfo.setGoodsTotalPrice("$"+infos.price*2);
			
			
			mContains.addView(goodsInfo);
		}
	}
	
	public void address(View view) {
		Intent intent = new Intent(this, AddressManageActivity.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		finish();
	}
	
	/**
	 * 提交
	 * @date 2016-4-27 下午1:16:26
	 */
	public void commit(View view){
		if(TextUtils.isEmpty(name)){
			Toast.makeText(mContext, "收货地址不能为空", 0).show();
			return;
		}
		//将地址信息传递到启动的界面
		Intent intent = new Intent(CheckOutActivity.this, MyOrderActivity.class);
		intent.putExtra("name", name);
		intent.putExtra("addressDetail", addressDetail);
		intent.putExtra("phoneNumber", phoneNumber);
		startActivity(intent);
	}
}
