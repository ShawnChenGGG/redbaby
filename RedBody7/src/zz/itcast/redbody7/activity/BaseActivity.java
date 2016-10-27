package zz.itcast.redbody7.activity;

import android.app.Activity;

import android.content.Context;
import android.os.Bundle;

/**
 * 封装的activity基类，其中的三个方法要重写
 * 
 * @author 李胜杰
 * 
 */
public abstract class BaseActivity extends Activity {

	/**
	 * activity的上下文环境
	 */
	public Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;

		initView();

		initData();

		initRegister();
	}

	/**
	 * 初始化监听，要注册的监听写在这个方法里
	 */
	public abstract void initRegister();

	/**
	 * 初始化数据
	 */
	public abstract void initData();

	/**
	 * 初始化界面
	 */
	public abstract void initView();

}
