package zz.itcast.redbody7.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * fragment基类
 * 
 * @author Administrator
 * 
 */
public abstract class BaseFragment extends Fragment {

	public Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**
		 * 得到MainActivity对象
		 */
		mContext = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return initView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
	}

	/**
	 * 初始化View
	 * 
	 * @return
	 */
	public abstract View initView();

	/**
	 * 初始化数据
	 */
	public void initData() {

	}

}
