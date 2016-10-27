package zz.itcast.redbody7.pager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.activity.MainActivity;
import zz.itcast.redbody7.activity.NewAdrressActivity;
import zz.itcast.redbody7.bean.CommodityListBean;
import zz.itcast.redbody7.bean.CommodityListBean.Commodity;
import zz.itcast.redbody7.bean.SortBean;
import zz.itcast.redbody7.bean.SortBean.Data;
import zz.itcast.redbody7.bean.SortBean.Data.Childs;
import zz.itcast.redbody7.fragment.BrandFragment;
import zz.itcast.redbody7.utils.LogPrint;
import zz.itcast.redbody7.view.TittleBarView;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 筛选的pager界面
 * @author wx
 *
 */
public class SortPager extends BasePager {
	private final class OnItemClickListenerImplementation implements
			OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			MainActivity mainActivity=(MainActivity) mContext;
			BrandFragment brandFragment = mainActivity.getBrandFragment();
			brandFragment.upDatePager(new CommodityListPager(null,null,mContext,null));
			
		}
	}

	/**
	 * 处理点击返回键
	 * @author wx
	 *
	 */
	private final class ClickListener implements
			OnClickListener {
		@Override
		public void onClick(View v) {
			MainActivity mainActivity=(MainActivity) mContext;
			BrandFragment brandFragment = mainActivity.getBrandFragment();
			BrandPager.isListBack=true;
			brandFragment.upDatePager(new CommodityListPager(null,null,mContext,null));
			
			
		}
	}

	/**
	 * 展示数据的ListView 对象
	 */
	private ListView lv;
	/**
	 * 头布局对象
	 */
	private TittleBarView tb;

	/**
	 * 解析json 的工具
	 */
	private Gson gson;
	/**
	 * 数据源
	 */
	private List<Data> productlist;
	private Myadpater myadpater;

	public SortPager(Context mContext) {
		super(mContext);
		gson = new Gson();
		
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
	
	
	@Override
	public void initData() {
		
		try {
			/*
			 * 服务器没有数据,就通过读取本地的json
			 */
			AssetManager assetManager = mContext.getAssets();
			InputStream open = assetManager.open("sort.txt");
			String json = getTestJson(open);
			SortBean listBean = gson.fromJson(json, SortBean.class);
			productlist = listBean.data;
			myadpater = new Myadpater();
			lv.setAdapter(myadpater);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 读取本地txt,获取json
	 */
	@Override
	public void initRegister() {
		/**
		 * 处理返回
		 */
		tb.setOnBackClick(new ClickListener());
		/**
		 * 处理条目点击
		 */
		lv.setOnItemClickListener(new OnItemClickListenerImplementation());
	}
	
	
	private class Myadpater extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return productlist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return productlist.get(position);
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
			holder.tv.setText(productlist.get(position).names);
			return convertView;
		}
		
	}
	
	/**
	 * 获得测试json字符串
	 * @param is
	 * @return
	 */
	private String getTestJson(InputStream is){
		String line=null;
		StringBuffer sb=null;
		try {
		InputStreamReader isr=new InputStreamReader(is);
		BufferedReader br=new BufferedReader(isr);
		sb=new StringBuffer();
			while ((line=br.readLine())!=null) {
				sb.append(line);
				
			}
			line=sb.toString().trim();
			//byte[] gbk = line.getBytes("gbk");
			//line=new String(gbk,"utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		LogPrint.logI("SortPager:TestJson:本地Json",line );
		return line;	
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
