package zz.itcast.redbody7.activity;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.utils.MyHttpUtils;
import zz.itcast.redbody7.utils.MyHttpUtils.OnRequestCallBack;
import zz.itcast.redbody7.view.TittleBarView;
import android.view.View;
import android.view.View.OnClickListener;

public class SearchResultActivity2 extends BaseActivity {
	private TittleBarView search_result2_bar;
	

	@Override
	public void initRegister() {

	}

	@Override
	public void initData() {
		
	}

	@Override
	public void initView() {
		setContentView(R.layout.activity_search_result2);

		search_result2_bar = (TittleBarView) findViewById(R.id.search_result2_bar);
		search_result2_bar.setTittle("搜索结果");
		search_result2_bar.setOnBackClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		search_result2_bar.setBackVisibility(true);
		search_result2_bar.setOperVisibility(false);
		
	}

}
