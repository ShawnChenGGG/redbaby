package zz.itcast.redbody7.pager;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.BitmapUtils;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.MyConstant.VariableClass;
import zz.itcast.redbody7.activity.MainActivity;
import zz.itcast.redbody7.adapter.DefaultBaseAdapter;
import zz.itcast.redbody7.bean.BrandMenu.Category;
import zz.itcast.redbody7.fragment.BrandFragment;
import zz.itcast.redbody7.utils.LogPrint;
import zz.itcast.redbody7.view.TittleBarView;
import android.R.array;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BrandPager extends BasePager {
	
	
	public static boolean isSortBack=false;
	
	/**
	 * 商品列表返回来的标记
	 */
	public static boolean isListBack=false;
	/**
	 * 所有条目的集合
	 */
	private ArrayList<Category> totalCategory;
	/**
	 * 每个级别菜单对应的数据源
	 */
	private ArrayList<Category> childs = new ArrayList<Category>();
	/**
	 * 显示内容的lv;
	 */
	private ListView lv;

	/**
	 * 标题
	 */
	private TittleBarView title;
	/**
	 * 网络加载图片的工具类
	 */
	private BitmapUtils bitmapUtils;
	/**
	 * 填充listView 的适配器
	 */
	private MyAdapter myAdapter;
	/**
	 * 用来记录之前的集合
	 */
	private List<Category> lastList = new ArrayList<Category>();
	/**
	 * 点返回键记录的
	 */
	private List<Category> backTemporarys= new ArrayList<Category>();
	
	/**
	 * 
	 * @param mContext 上下文对象
	 * @param category 商品列表返回传输的子类部分数据集合 
	 * @param printListCounts 
	 * @param printCounts  总集合
	 */
	public BrandPager(Context mContext, ArrayList<Category> category, ArrayList<Category> printListCounts) {
		super(mContext);
		LogPrint.logI("BrandPager", String.valueOf(isListBack));
		
		if (printListCounts==null) {
			totalCategory=category;	
		}else {
			childs=category;
			this.totalCategory=printListCounts;
		}	
		this.mContext = mContext;
		bitmapUtils = new BitmapUtils(mContext);
		
		//initView();

	}

	@Override
	public void initRegister() {
		// 点击返回操作
		title.setOnBackClick(new ClickListener());

		// listView 的条目点击
		lv.setOnItemClickListener(new ItemClickListener());
	}

	/**
	 * 初始化视图
	 */
	@Override
	public View initView() {
		// 创建填充的视图
		View view = View.inflate(mContext, R.layout.pager_brand_menu, null);
		// 初始化控件
		lv = (ListView) view.findViewById(R.id.lv_brand_menu);
		title = (TittleBarView) view
				.findViewById(R.id.tv_base_pager_title_bar_title);
		// 初始化标题
		title.setTittle("分类浏览");
		title.setOperVisibility(false);
		// 控制按钮显示
		if (isListBack) {
			title.setBackVisibility(true);
		}else {
			title.setBackVisibility(false);
		}
		

		initData();
		initRegister();
		return view;
	}

	/**
	 * 初始化数据
	 */
	@Override
	public void initData() {
		// 因为缓存的原因,每次都要先把集合清空
		// totalCategory.clear();
		LogPrint.logI("BrandPager: initdata", String.valueOf(isListBack));
		if (!isListBack) {
			//如果是商品列表返回的,就不清除子数据
			childs.clear();
			//如果不是列表返回,就先获得1级菜单
			getLevel1(); 
		}
		//商品列表返回标记回复
		isListBack=false;
		if (myAdapter == null) {
			myAdapter = new MyAdapter();
		}
		lv.setAdapter(myAdapter);
		
			
		

	}
	/**
	 * 获得一级菜单
	 */
	private void getLevel1() {
		// 处理标题和按钮显示
		title.setTittle("分类浏览");
		title.setBackVisibility(false);
		// 当复用的时候也需要清空
		childs.clear();
		for (Category categorys : totalCategory) {
			// 根据level 值来决定为几级菜单的数据源
			if (categorys.level == 1) {
				// 当为1级的时候代表数据为一级菜单的数据
				childs.add(categorys);
			}
		}

	}

	/**
	 * 获得子条目的数据源
	 * 
	 * @param id
	 * @return
	 */
	private ArrayList<Category> classify(String id) {
		//先清空过去的集合
		lastList.clear();
		lastList.addAll(childs);
		childs.clear();
		int mId = Integer.valueOf(id);
		for (Category categorys : totalCategory) {
			if (mId == categorys.parentid) {
				childs.add(categorys);
			}
		}
		return childs;
	}

	/**
	 * listView的条目点击事件
	 * 
	 * @author wx
	 * 
	 */
	private class ItemClickListener implements OnItemClickListener {
		private BrandFragment brandFragment;

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// 获得当前条目对象
			Category category2 = childs.get(position);
			
			if (category2.isleafnode){
				// 如果为true,直接显示对应的商品列表
				LogPrint.logI("BrandPager:显示列表", "显示条目对应的商品列表");
				// TODO 跳转到下一个pager,显示商品列表
				/**
				 * 获取当前activity对象,然后获取分类的的fragment对象;
				 */
				MainActivity mainActivity=(MainActivity) mContext;
				if (brandFragment==null) {
					brandFragment = mainActivity.getBrandFragment();
				}
				/**
				 * 在这里可以通过 pager 的构造传递对应的数据和url
				 * 但是在服务器那边并没有对每个条目封装url,这里自己造一个
				 * 应该cid 就是当前条目的id;排序也没做,自己做
				 */
				brandFragment.upDatePager(new CommodityListPager(childs,totalCategory,mContext,MyConstants.CommodityList_URL));
				return;
			}
			
			LogPrint.logI("BrandPager:分类菜单", "显示条目对应菜单内容");

			childs = classify(category2.id);
			// 需要判断集合内容的大小有可能为0,如果为0,提示没有分类,并恢复子集合的内容
			// 不为0刷新数据
			if (childs.size() > 0) {
				myAdapter.notifyDataSetChanged();
				title.setTittle(category2.name);
				title.setBackVisibility(true);
				
			} else {
				childs.addAll(lastList);
				Toast.makeText(mContext, "没有分类了", Toast.LENGTH_SHORT).show();
				if (category2.level==1) {
					title.setTittle("分类浏览");	
				}else {
					title.setTittle(category2.name);
					title.setBackVisibility(true);
				}
				 
			}

		}
	}

	/**
	 * 返回键的点击事件
	 * 
	 * @author wx
	 * 
	 */
	private class ClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			backItemData();
			myAdapter.notifyDataSetChanged();
		}

	}

	/**
	 * 用来解决返回键时显示的列表内容
	 */
	private void backItemData() {
		//m每次返回 返回的集合也需要清空
		backTemporarys.clear();
		// 清空集合,因为又要展示新的数据了
		backTemporarys.addAll(childs);
		childs.clear();
		// 随便获得当前集合的一个对象
		Category categorys = backTemporarys.get(0);
		// 记录 父类对象的id
		int id = 0;
		if (categorys.level == 2) {
			/*
			 * 如果是二级菜单,必然是返回一级菜单 那么就让子集合内容更新为1级菜单内容界面
			 */
			getLevel1();
		} else if (categorys.level == 3) {
			/*
			 * 如果是三级菜单,先获得他父类的id, 遍历总集合,获得父类对象,获得父类对象的pid, 判断集合所有的pid=是否和父类pid
			 * 相等,相等就添加到子集合 显示按钮
			 */
			int pid = categorys.parentid;
			// 获取父类的pid
			for (Category categoryss : totalCategory) {
				if (pid == Integer.valueOf(categoryss.id)) {
					// 寻找他父类对象,获得他父类对象的pid
					id = categoryss.parentid;

				}
			}

			for (Category categorysss : totalCategory) {
				if (id == categorysss.parentid) {
					// 集合所有内容包含这个pid添加到子集合
					childs.add(categorysss);
				}
				if (id == Integer.valueOf(categorysss.id)) {
					// 处理标题
					title.setTittle(categorysss.name);
				}

			}

			title.setBackVisibility(true);

		}

	}

	/**
	 * 适配器
	 * 
	 * @author wx
	 * 
	 */
	public class MyAdapter extends BaseAdapter {
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LogPrint.logI("BrandPager: MyAdapter: getView", "1111111111");
			
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(mContext,
						R.layout.item_pager_brand_menu, null);
				holder.iv = (ImageView) convertView
						.findViewById(R.id.iv_item_pager_brand_menu);
				holder.tv_title = (TextView) convertView
						.findViewById(R.id.tv_item_title_pager_brand_menu);
				holder.tv_info = (TextView) convertView
						.findViewById(R.id.tv_item_info_pager_brand_menu);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Category categorys = childs.get(position);
			// 菜单图片的url
			String pic = categorys.pic;
			// 菜单条目的标题
			String title = categorys.name;
			// 菜单条目对应的 简介
			String info = categorys.t;

			/**
			 * 如果图片存在,就展示,没有就将控件取消
			 */
			if (TextUtils.isEmpty(pic)) {
				holder.iv.setVisibility(View.GONE);
			} else {
				holder.iv.setVisibility(View.VISIBLE);
				bitmapUtils.display(holder.iv, MyConstants.BASE_URL + pic);
			}

			/**
			 * 如果简介内容存在,就展示,没有就将控件取消
			 */
			if (TextUtils.isEmpty(title)) {
				holder.tv_info.setVisibility(View.GONE);
			} else {
				holder.tv_info.setVisibility(View.VISIBLE);
				holder.tv_info.setText(info);
			}

			holder.tv_title.setText(title);
			return convertView;
		}

		@Override
		public int getCount() {
			return childs.size();
		}

		@Override
		public Object getItem(int position) {
			return childs.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

	}

	/**
	 * 用来优化listView的findViewById次数
	 * 
	 * @author wx
	 * 
	 */
	private class ViewHolder {
		/**
		 * 菜单条目图片
		 */
		public ImageView iv;
		/**
		 * 菜单条目标题
		 */
		public TextView tv_title;
		/**
		 * 菜单条目简介
		 */
		public TextView tv_info;
	}

}
