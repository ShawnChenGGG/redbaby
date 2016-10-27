package zz.itcast.redbody7.activity;

import zz.itcast.redbody7.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

/*
 * 闪屏
 * 1.延迟跳转:handler发送延迟消息
 * 2. 第一次进入程序

 闪屏 - 引导界面

 第二次进入程序:

 闪屏 - 主界面

 将一个标志,保存到本地,sp
 */
public class SplashActivity extends BaseActivity {
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				// 跳转 (获取sp中的标志,如果是第一次,引导.如果是第二次,主界面)
				boolean isFirst = sp.getBoolean("isFirst", true);
				if (isFirst) {
					// 如果是第一次,引导
					Intent intent = new Intent(SplashActivity.this,
							GuideActivity.class);
					startActivity(intent);
				} else {
					// 如果是第二次,主界面
					Intent intent = new Intent(SplashActivity.this,
							MainActivity.class);
					startActivity(intent);

				}
				finish();
				break;

			default:
				break;
			}
		};
	};
	private SharedPreferences sp;

	@Override
	public void initRegister() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initView() {
		setContentView(R.layout.activity_splash);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		// 发送延迟消息 2000
		handler.sendEmptyMessageDelayed(0, 2000);

	}

}
