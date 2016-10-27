package zz.itcast.redbody7.activity;

import java.util.List;
import java.util.Random;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.bean.FollowInfoBean;
import zz.itcast.redbody7.bean.FollowInfoBean.FollowInfo;
import zz.itcast.redbody7.utils.MyHttpUtils;
import zz.itcast.redbody7.utils.MyUtils;
import zz.itcast.redbody7.utils.MyHttpUtils.OnPostRequestCallBack;
import zz.itcast.redbody7.view.TittleBarView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;
/**
 * @author gjl
 * 物流查询
 * */
public class ExpressQueryActivity extends BaseActivity{

	private TittleBarView tbv_express;
	private LinearLayout mContains;
	
	@Override
	public void initRegister() {

	}

	@Override
	public void initData() {
		tbv_express.setTittle("物流查询");
		tbv_express.setOperVisibility(false);
		
		getNetData();
	}

	/**
	 * @date 2016-4-26 下午12:02:58
	 */
	private void getNetData() {
		String url = MyConstants.BASE_URL+"/logistics";
		RequestParams params = new RequestParams();
		Random random = new Random();
		int dom = random.nextInt(100);
		params.addBodyParameter("orderId", dom+"");
		MyHttpUtils httpUtils = new MyHttpUtils(url, params ,false);
		httpUtils.setPostRequestCallBack(new OnPostRequestCallBack() {
			
			@Override
			public void onPostSuccess(String result) {
				parserJson(result);
			}
		});
	}

	/**
	 * @date 2016-4-26 下午12:09:53
	 */
	protected void parserJson(String result) {
		Gson gson = new Gson();
		FollowInfoBean followInfoBean = gson.fromJson(result, FollowInfoBean.class);
		List<FollowInfo> followInfo = followInfoBean.followInfo;
		for(int i = 0;i<followInfo.size();i++){
			FollowInfo info = followInfo.get(i);
			TextView textView = new TextView(mContext);
			int pading = MyUtils.px2dip(mContext, 10);
			textView.setPadding(pading, pading/2, pading/2, pading);
			textView.setText(info.followinfo);
			mContains.addView(textView);
		}
		
	}

	@Override
	public void initView() {
		setContentView(R.layout.activity_express_query);
		
		mContains = (LinearLayout) findViewById(R.id.ll_contains);
		tbv_express = (TittleBarView) findViewById(R.id.tbv);

		tbv_express.setOnBackClick(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				finish();
			}
		});
	}

}
