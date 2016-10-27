package zz.itcast.redbody7.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自己封装的pagerAdapter的简单框架
 * 
 * @author 李胜杰
 * 
 * @param <T>
 *            list列表中的参数类型
 */
public abstract class DefaultPagerAdapter<T> extends PagerAdapter {

	/**
	 * 数据源
	 */
	private List<T> datas;

	public DefaultPagerAdapter(List<T> datas) {
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public abstract Object instantiateItem(ViewGroup container, int position);

}
