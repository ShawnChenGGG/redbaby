package zz.itcast.redbody7.activity;

import android.view.View;
import android.view.View.OnClickListener;
import zz.itcast.redbody7.R;
import zz.itcast.redbody7.view.TittleBarView;

public class AboutActivity extends BaseActivity {
	private TittleBarView tb_about_title;

	@Override
	public void initRegister() {

	}

	@Override
	public void initData() {

	}

	@Override
	public void initView() {
		
		setContentView(R.layout.activity_about);
		tb_about_title = (TittleBarView) findViewById(R.id.tb_about_title);
		tb_about_title.setOperVisibility(false);
		tb_about_title.setOnBackClick(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tb_about_title.setTittle("关于");
		tb_about_title.setTextSize(15);
	}

}
