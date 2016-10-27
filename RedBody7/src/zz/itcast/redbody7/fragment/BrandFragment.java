package zz.itcast.redbody7.fragment;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.MyConstant.VariableClass;
import zz.itcast.redbody7.bean.BrandMenu;
import zz.itcast.redbody7.pager.BasePager;
import zz.itcast.redbody7.pager.BrandPager;
import zz.itcast.redbody7.pager.CommodityListPager;
import zz.itcast.redbody7.utils.LogPrint;
import zz.itcast.redbody7.utils.MyUtils;
import zz.itcast.redbody7.utils.PreferenceUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * 品牌Fragment
 * 
 * @author zh
 * 
 */
public class BrandFragment extends BaseFragment {
	/**
	 * 请求分类菜单数据
	 * @author wx
	 *
	 */
	private final class MyRequestBrand extends
			RequestCallBack<String> {
		//联网成功
		@Override
		public void onSuccess(ResponseInfo<String> info) {
			String json = info.result;
			boolean isError = MyUtils.checkJson(json);
			LogPrint.logI("BrandFragment", "获得json数据:"+json);
			//解析json
			if (isError) {
				pareJson(json);
				//保存到本地
				PreferenceUtils.putString(mContext, MyConstants.BASE_URL+"/category", json);
			}else{
				Toast.makeText(mContext, "服务器异常", Toast.LENGTH_SHORT).show();
			}
			
			
			
		}
		
		//联网失败
		@Override
		public void onFailure(HttpException arg0, String arg1) {
			arg0.printStackTrace();
			LogPrint.logI("BrandFragment", "联网失败:"+arg1);
		}

		
	}

	/**
	 * 当前分类页面的fragment 的根布局
	 */
	private FrameLayout fl_brand;
	/**
	 * Xutils 的联网操作工具
	 */
	private HttpUtils httpUtils;
	/**
	 * 请求的类型
	 */
	private String response;
	/**
	 * 请求的版本号
	 */
	private String version;
	
	/**
	 * 初始化视图
	 */
	@Override
	public View initView() {
		//当前fragment展示的内容视图
		View view=View.inflate(mContext, R.layout.fragment_brand, null);
		fl_brand=(FrameLayout) view.findViewById(R.id.fl_brand);
		
		
		
		return view;
	}
	/**
	 * 初始化数据,没网络使用缓存数据,有网络使用网络数据
	 */
	@Override
	public void initData() {
		super.initData();
		//先获取缓存的json
		String caheJson = PreferenceUtils.getString(mContext, MyConstants.BASE_URL+"/category");
		if (!TextUtils.isEmpty(caheJson)) {
//			pareJson(caheJson);		
		}
		//联网查询最新的Json数据
		httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, MyConstants.BASE_URL+"/category", null, new MyRequestBrand());
		
	}
	/**
	 * 解析Json
	 * @param json 数据源
	 */
	private void pareJson(String json) {
		//每次进入之前都删除所有的子View
		fl_brand.removeAllViews();
		Gson gson=new Gson();
		//获取解析后的bean 对象
		BrandMenu brandMenu=gson.fromJson(json, BrandMenu.class);
		//得到服务器响应的类型
		response = brandMenu.response;
		//得到服务器返回的版本号
		version = brandMenu.version;
		if (isToCommodityList) {
			fl_brand.addView(new CommodityListPager(null, null, mContext, null).initView());
			isToCommodityList=false;
			CommodityListPager.isOthergoThere=true;	
		}else{
			//把子目录添加进去,并把数据集合传入子View
			fl_brand.addView(new BrandPager(mContext,brandMenu.category,null).initView());
		}
			
		
		
	}
	
	public void upDatePager(BasePager pager){
		if (fl_brand==null) {
			
			View view=View.inflate(VariableClass.mainActivity, R.layout.fragment_brand, null);
			fl_brand=(FrameLayout) view.findViewById(R.id.fl_brand);
			
		}
		fl_brand.removeAllViews();
		fl_brand.addView(pager.initView());
	}
	
	public static boolean isToCommodityList=false;
	
}
