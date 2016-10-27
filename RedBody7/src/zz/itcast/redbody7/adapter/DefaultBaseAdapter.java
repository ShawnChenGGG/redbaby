package zz.itcast.redbody7.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 封装baseAdapter的框架
 * 
 * @author 李胜杰
 * 
 * @param <T>
 *            List列表中的参数类型
 */
public abstract class DefaultBaseAdapter<T> extends BaseAdapter {

	/**
	 * 数据源
	 */
	public List<T> datas;

	public DefaultBaseAdapter(List<T> datas) {
		this.datas = datas;
	}


	@Override
	public int getCount() {
		if(datas != null){
			return datas.size();
		}
		
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public abstract View getView(int position, View convertView,
			ViewGroup parent);

}
