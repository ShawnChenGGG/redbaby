package zz.itcast.redbody7.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.DbException;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.MyConstant.VariableClass;
import zz.itcast.redbody7.bean.CommodityListBean.Commodity;
import zz.itcast.redbody7.bean.DBBrowse;

import zz.itcast.redbody7.utils.LogPrint;
import zz.itcast.redbody7.view.MyListView;
import zz.itcast.redbody7.view.TittleBarView;
import android.content.Intent;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import android.widget.TextView;

/**
 * 浏览记录
 * @author wx
 *
 */
public class ActivityBrowseData extends BaseActivity {
	/**
	 * 商品列表排序模块和隐藏的模块,隐藏的模块,在没有记录时候显示
	 */
	private LinearLayout ll_sort,ll_browse;
	private ListView lv;
	private TittleBarView tb;
	/**
	 * 第一次计入的时候
	 */
	private static boolean isFirstLogin=true;
	/**
	 * 浏览记录记录的Id集合;
	 */
	public static ArrayList<DBBrowse> browseData=new ArrayList<DBBrowse>();
	private TextView tv_list;
	private BitmapUtils bitmapUtils;
	private MyAdapater myAdapater;
	
	@Override
	public void initRegister() {
		/**
		 * 条目点击事件
		 */
		lv.setOnItemClickListener(new ItemClickListener());
		/**
		 * 返回点击事件
		 */
		tb.setOnBackClick(new ClickListener());
		
		tv_list.setOnClickListener(new Tv_list_OnClickListener());
	}

	@Override
	public void initData() {
		//TODO 获取数据,其实获取数据库的数据
		//获取浏览记录集合的数据  假如为1
		ArrayList<DBBrowse> data = ProductDetailActivity.browseData;
		if (isFirstLogin) {
			try {
				//如果第一次进入,从数据库取出数据  2
				List<DBBrowse> dbAll = MainActivity.dbUtils.findAll(DBBrowse.class);
				if (data.size()!=0) {
					//先添加内存的集合,添加到展示的集合内
					browseData.addAll(data);
				}
				//再添加数据库的集合  3
				browseData.addAll(dbAll);
				//让内存中的浏览集合把数据添加所有记录
				ProductDetailActivity.browseData.clear();
				ProductDetailActivity.browseData.addAll(browseData);
			} catch (DbException e) {
				e.printStackTrace();
				LogPrint.logI("ActivityBrowseData", "第一次获取浏览记录数据失败");
			}
			isFirstLogin=false;
		}else {
			//如果第二次进入,第一次有数据这里会出问题,
			browseData.clear();
			browseData.addAll(data);
		
		}
		/*
		 * 需要判断集合,当集合为0,lv不展示数据,展示隐藏布局
		 */
		if (browseData.size()==0) {
			lv.setVisibility(View.GONE);
			ll_browse.setVisibility(View.VISIBLE);
		}else {
			
			lv.setVisibility(View.VISIBLE);
			ll_browse.setVisibility(View.GONE);
			setData();
		}
		
	}
	
	/**
	 * 为listView 填充数据
	 */
	private void setData() {
		if (myAdapater==null) {
			myAdapater = new MyAdapater();
		}
		lv.setAdapter(myAdapater);
		
	}

	@Override
	public void initView() {
		//初始化控件
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pager_brand_commodity_list);
		tb = (TittleBarView) findViewById(R.id.tb_pager_commodity_list);
		ll_sort=(LinearLayout) findViewById(R.id.ll_pager_commodity_list);
		ll_browse=(LinearLayout) findViewById(R.id.ll_browse);
		tv_list = (TextView) findViewById(R.id.tv_go_list);
		lv = (ListView) findViewById(R.id.lv_browse);
		MyListView myListView=(MyListView) findViewById(R.id.lv_pager_commodity_list);
		bitmapUtils = new BitmapUtils(mContext);
		//初始化默认值
		ll_sort.setVisibility(View.GONE);
		//浏览的listView 显示
		lv.setVisibility(View.VISIBLE);
		myListView.setVisibility(View.GONE);
		tb.setOperVisibility(false);
		tb.setTittle("浏览记录");
		tb.setBackVisibility(true);
		
	}
	
	/**
	 * 隐藏布局按钮,当没有数据,点击跳转某某界面
	 * @author wx
	 *
	 */
	private final class Tv_list_OnClickListener implements
			OnClickListener {
		@Override
		public void onClick(View v) {

			MainActivity ma = VariableClass.mainActivity;
			ma.isToBrandFragment=true;
			finish();
			
		}
	}



	/**
	 * 处理返回点击
	 * @author wx
	 *
	 */
	private final class ClickListener implements
			OnClickListener {
		@Override
		public void onClick(View v) {
			finish();
		}
	}



	/**
	 * 跳转到详情页
	 * @author wx
	 *
	 */
	private final class ItemClickListener implements
			OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			LogPrint.logI("ActivityBrowseData", browseData.get(position).pic);
			Intent intent=new Intent(mContext,ProductDetailActivity.class);
			intent.putExtra("PID", browseData.get(position).browse_id);
			startActivity(intent);
			
		}
	}
	
	

	private class MyAdapater extends BaseAdapter{

		@Override
		public int getCount() {
			return browseData.size();
		}

		@Override
		public Object getItem(int position) {

			return browseData.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder=null;
			if (convertView==null) {
				convertView=View.inflate(mContext, R.layout.item_pager_commodity_list, null);
				holder=new ViewHolder();
				holder.iv=(ImageView) convertView.findViewById(R.id.iv_item_pager_commodity_list);
				holder.tv_info=(TextView) convertView.findViewById(R.id.tv_info);
				holder.tv_vip_price=(TextView) convertView.findViewById(R.id.tv_vip_price);
				holder.tv_old_price=(TextView) convertView.findViewById(R.id.tv_old_price);
				holder.tv_comment=(TextView) convertView.findViewById(R.id.tv_comment);
				convertView.setTag(holder);
			}else {
				holder=(ViewHolder) convertView.getTag();
			}
			DBBrowse commodity = browseData.get(position);
			holder.tv_old_price.setVisibility(View.GONE);
			
			holder.tv_info.setText(commodity.name);
			holder.tv_comment.setText(commodity.detail);
			bitmapUtils.display(holder.iv, MyConstants.BASE_URL+commodity.pic);
			holder.tv_vip_price.setText("$"+commodity.price);
			return convertView;
		}
		
	}
	
	private class ViewHolder{
		public ImageView iv;
		
		public TextView tv_info,tv_vip_price,tv_old_price,tv_comment;
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		LogPrint.logI("ActivityBrowseData", "onStart");
		if (myAdapater!=null) {
			ArrayList<DBBrowse> data = ProductDetailActivity.browseData;
			browseData.clear();
			browseData.addAll(data);
			myAdapater.notifyDataSetChanged();
		}
		
	}

}
