package zz.itcast.redbody7.pager;

import java.util.ArrayList;
import java.util.List;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.activity.MainActivity;
import zz.itcast.redbody7.bean.SortBean.Data.Childs;
import zz.itcast.redbody7.fragment.BrandFragment;

import zz.itcast.redbody7.view.TittleBarView;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 自己写的json 有问题
 * 就不再跳转二级菜单
 * @author wx
 *
 */
public class SortPager2 extends BasePager {
	/**
	 * 显示的数据集合
	 */
	private List<Childs> childs=new ArrayList<Childs>();
	
	private ListView lv;
	
	private TittleBarView tb;

	private Myadpater myadpater;
	

	public SortPager2(Context mContext, List<Childs> childs) {
		super(mContext);
		childs.clear();
		this.childs.addAll(childs);
	}

	@Override
	public void initRegister() {
		lv.setOnItemClickListener(new OnItemClickListenerImplementation());
		
		tb.setOnBackClick(new OnClickListenerImplementation());
	}

	@Override
	public void initData() {
		
		myadpater = new Myadpater();
		
		lv.setAdapter(myadpater);

	}
	
	

	@Override
	public View initView() {
		//初始化控件
				View view=View.inflate(mContext, R.layout.pager_sort, null);
				lv = (ListView) view.findViewById(R.id.lv_tb_pager_sort);
				tb = (TittleBarView) view.findViewById(R.id.tb_pager_sort);
				//初始化默认选项
				tb.setBackVisibility(true);
				tb.setOperVisibility(false);
				tb.setTittle("筛选");
				
				initData();
				initRegister();
				return view;
		
	}
	
	private final class OnClickListenerImplementation implements
			OnClickListener {
		@Override
		public void onClick(View v) {
			MainActivity mainActivity=(MainActivity) mContext;
			BrandFragment brandFragment = mainActivity.getBrandFragment();
			brandFragment.upDatePager(new SortPager(mContext));
			
		}
	}

	private final class OnItemClickListenerImplementation implements
			OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			MainActivity mainActivity=(MainActivity) mContext;
			BrandFragment brandFragment = mainActivity.getBrandFragment();
			//获得条目的url,但是这里不用这个,因为数据本身都是假的
			String link = childs.get(position).link;
			brandFragment.upDatePager(new CommodityListPager(null,null,mContext,null));
		}
	}

	private class Myadpater extends BaseAdapter{

		@Override
		public int getCount() {

			return childs.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return childs.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder=null;
			if (convertView==null) {
				holder=new ViewHolder();
				convertView=View.inflate(mContext, R.layout.item_sort_list, null);
				holder.tv=(TextView) convertView.findViewById(R.id.tv_item_sort);
				holder.iv=(ImageView) convertView.findViewById(R.id.iv_item_sort);
				convertView.setTag(holder);
			}else {
				holder=(ViewHolder) convertView.getTag();
			}
			holder.tv.setText(childs.get(position).name);
			holder.iv.setVisibility(View.GONE);
			return convertView;
		}
		
	}
	
	private class ViewHolder{
		/**
		 * 条目图片
		 */
		public ImageView iv;
		/**
		 * 条目的文字
		 */
		public TextView tv;
	}

}
