package zz.itcast.redbody7.pager;

import android.content.Context;
import android.view.View;

/**
 * 自定义pager，里面封装了initView（）和initData（）,initRegister()方法，同时也封装了上下文变量mContext。
 * 
 * @author 李胜杰
 * 
 */
public abstract class BasePager {

	/**
	 * 构造方法传递的上下文环境
	 */
	public Context mContext;

	/**
	 * initView()方法返回的对象
	 */
	public View view;

	public BasePager(Context mContext) {

		this.mContext = mContext;

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
	public abstract View initView();

}
