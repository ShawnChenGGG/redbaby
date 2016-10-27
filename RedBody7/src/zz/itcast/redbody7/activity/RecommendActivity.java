package zz.itcast.redbody7.activity;

import java.util.List;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.MyConstant.VariableClass;
import zz.itcast.redbody7.bean.RecommendBean;
import zz.itcast.redbody7.bean.RecommendBean.Recommend;
import zz.itcast.redbody7.utils.LogPrint;
import zz.itcast.redbody7.utils.MyHttpUtils;
import zz.itcast.redbody7.utils.MyHttpUtils.OnRequestCallBack;
import zz.itcast.redbody7.view.LoadAnimationView;
import zz.itcast.redbody7.view.RecommendGrideView;
import zz.itcast.redbody7.view.TittleBarView;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.google.gson.Gson;

/**
 * 推荐品牌页面
 * 
 * @author Administrator
 * 
 */
public class RecommendActivity extends BaseActivity {

	private TittleBarView tbv;
	private LinearLayout fl;

	@Override
	public void initRegister() {

	}

	@Override
	public void initView() {
		setContentView(R.layout.recommend_activity);

		tbv = (TittleBarView) findViewById(R.id.recommend_tbv);

		fl = (LinearLayout) findViewById(R.id.recommend_fl);
		lav = (LoadAnimationView) findViewById(R.id.recommend_loading);
	}

	@Override
	public void initData() {

		tbv.setTittle("推荐品牌");
		tbv.setOperVisibility(false);

		tbv.setOnBackClick(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// 请求数据
		sendRequest(MyConstants.BASE_URL + "/brand");
	}

	/**
	 * 请求数据
	 * 
	 * @param string
	 */

	private void sendRequest(String url) {
		MyHttpUtils myHttpUtils = new MyHttpUtils(url,true);
		myHttpUtils.setOnRequestCallBack(new OnRequestCallBack() {

			@Override
			public void onSuccess(String result) {
				String json = result;
				// System.out.println("ReCommend:" + json);
				parseJson(json);
			}

		});

	}

	// 解析数据
	private void parseJson(String json) {
		Gson gson = new Gson();
		RecommendBean recommendBean = gson.fromJson(json, RecommendBean.class);

		List<Recommend> brand = recommendBean.brand;
		
		lav.setVisibility(View.GONE);
		fl.setVisibility(View.VISIBLE);
		

		for (int i = 0; i < brand.size(); i++) {

			RecommendGrideView rgv = new RecommendGrideView(mContext);
			fl.addView(rgv);

			rgv.setAdpaterData(brand.get(i));
			rgv.setGVOnItemClickListener(listenerGV);
		}

	}

	OnItemClickListener listenerGV = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

			LogPrint.logI("zh", "你已抢购《七度空间》和《超人内裤》");
			MainActivity ma = VariableClass.mainActivity;
			ma.isToBrandFragment=true;
			finish();
			
		}
	};
	private LoadAnimationView lav;

}
