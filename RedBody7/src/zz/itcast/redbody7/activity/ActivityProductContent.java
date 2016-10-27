package zz.itcast.redbody7.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.activity.ActivityProductContent.ProductContent.ContentME;
import zz.itcast.redbody7.pager.CommodityDetailPager;
import zz.itcast.redbody7.utils.LogPrint;
import zz.itcast.redbody7.utils.MyUtils;
import zz.itcast.redbody7.view.TittleBarView;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 商品内容简介的activity
 * @author wx
 *
 */
public class ActivityProductContent extends BaseActivity {
	
	/**
	 * 请求数据
	 * @author wx
	 *
	 */
	private final class RequestProductContent extends
			RequestCallBack<String> {
		@Override
		public void onFailure(HttpException arg0, String arg1) {
			arg0.printStackTrace();
			LogPrint.logI("ActivityProductContent:httpUtils 联网失败", arg1);
			
		}

		@Override
		public void onSuccess(ResponseInfo<String> arg0) {
			String json=arg0.result;
			LogPrint.logI("ActivityProductContent:httpUtils 联网成功", json);
			boolean checkJson = MyUtils.checkJson(json);
			if (checkJson) {
				pareJson(json);
			}else {
				Toast.makeText(ActivityProductContent.this, "服务器异常", 0).show();
			}
			
		}
	}

	private TittleBarView tb_title;
	
	private TextView tv_title,tv_content;
	/**
	 * 商品 Id
	 */
	private int id;
	/**
	 * 联网工具
	 */
	private HttpUtils httpUtils;

	private String productdesc;
	
	@Override
	public void initRegister() {
		
		
		/**
		 * 返回商品列表
		 */
		
		tb_title.setOnBackClick(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Intent intent=new Intent(ActivityProductContent.this,ProductDetailActivity.class);
//				intent.putExtra("PID", id);
//				startActivity(intent);
				finish();
				
			}
		});
	}
	
	/**
	 * 解析数据
	 * @param json
	 */
	public void pareJson(String json) {
		Gson gson=new Gson();
		ProductContent fromJson = gson.fromJson(json, ProductContent.class);
		ArrayList<ContentME> contentList= (ArrayList<ContentME>) fromJson.productdesc;
		ContentME contentME = contentList.get(0);
		productdesc = contentME.productdesc;
		LogPrint.logI("ActivityProductContent", productdesc+"========");
		tv_content.setText(productdesc);
	}

	@Override
	public void initData() {
		LogPrint.logI("ActivityProductContent", MyConstants.BASE_URL+"/product/description?pId="+id);
		httpUtils.send(HttpMethod.GET, MyConstants.BASE_URL+"/product/description?pId="+id, new RequestProductContent());
		
	}

	@Override
	public void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_product_content);
		id = getIntent().getExtras().getInt("id");
		String name=getIntent().getExtras().getString("name");
		tb_title=(TittleBarView) findViewById(R.id.tb_title);
		tv_title=(TextView) findViewById(R.id.tv_title);
		tv_content=(TextView) findViewById(R.id.tv_content);
		
		//设置默认选项
		tv_title.setText(name);
		tb_title.setOperVisibility(false);
		tb_title.setTittle("商品详情	");
	
		
		httpUtils = new HttpUtils();
		
	}
	
	public class ProductContent{
		public String response;
		
		public List<ContentME> productdesc;
		
		public class ContentME{
			/**
			 * 内容信息
			 */
			public String productdesc;
		}
	}

}
