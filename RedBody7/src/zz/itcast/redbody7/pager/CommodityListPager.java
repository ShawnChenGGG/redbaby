package zz.itcast.redbody7.pager;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.activity.MainActivity;
import zz.itcast.redbody7.activity.ProductDetailActivity;
import zz.itcast.redbody7.adapter.DefaultBaseAdapter;
import zz.itcast.redbody7.bean.BrandMenu.Category;
import zz.itcast.redbody7.bean.CommodityListBean;
import zz.itcast.redbody7.bean.CommodityListBean.Commodity;
import zz.itcast.redbody7.fragment.BrandFragment;
import zz.itcast.redbody7.utils.LogPrint;
import zz.itcast.redbody7.utils.MyCommoditySortUtils;
import zz.itcast.redbody7.utils.MyUtils;
import zz.itcast.redbody7.view.MyListView;
import zz.itcast.redbody7.view.MyListView.OnRefreshStateChangeListener;
import zz.itcast.redbody7.view.TittleBarView;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class CommodityListPager extends BasePager implements OnClickListener {

	/**
	 * 处理listVIew 的条目点击事件,跳转到该条目对应的商品详情中;
	 * 
	 * @author wx
	 * 
	 */
	private final class ItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			//角标越界异常，打补丁--lsj
			position=position-lv.getHeaderViewsCount();
			if (position == 9) {
				return;
			}
			Commodity commodity = productlist.get(position);
			int pid = Integer.valueOf(commodity.id);
			Intent intent = new Intent(mContext, ProductDetailActivity.class);
			intent.putExtra("PID", pid);
			mContext.startActivity(intent);

		}
	}

	private final class RefreshStateChangeListener implements
			OnRefreshStateChangeListener {
		/**
		 * 下拉刷新的操作,请求服务器,最新的数据当前第一页
		 */
		@Override
		public void refresh() {
			httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					lv.refreshFinish();
					Toast.makeText(mContext, "网络异常", 0).show();
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					lv.refreshFinish();
					String json = arg0.result;
					// 检验字符串
					boolean isError = MyUtils.checkJson(json);
					if (isError) {
						// 正确,开始解析
						CommodityListBean bean = gson.fromJson(arg0.result,
								CommodityListBean.class);
						productlist.addAll(bean.productlist);
						myAdapter.notifyDataSetChanged();
					} else {
						// 错误的话,提示服务器异常
						Toast.makeText(mContext, "服务器异常", Toast.LENGTH_SHORT)
								.show();

					}

				}
			});

		}

		/**
		 * 上拉加载的操作,请求服务器的下一个数据 ,但是这里服务器只有一页数据,
		 */
		@Override
		public void loading() {
			httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {

					Toast.makeText(mContext, "网络异常", 0).show();
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					lv.refreshFinish();
					String json = arg0.result;
					// 检验字符串
					boolean isError = MyUtils.checkJson(json);
					if (isError) {
						// 正确,开始解析
						CommodityListBean bean = gson.fromJson(arg0.result,
								CommodityListBean.class);
						productlist.addAll(bean.productlist);
						myAdapter.notifyDataSetChanged();
					} else {
						// 错误的话,提示服务器异常
						Toast.makeText(mContext, "服务器异常", Toast.LENGTH_SHORT)
								.show();

					}

				}
			});

		}
	}

	/**
	 * 主页跳转过来的
	 */
	public static boolean isOthergoThere = false;

	/**
	 * 点击返回,返回三级菜单
	 * 
	 * @author wx
	 * 
	 */

	private class RequestCallBackExtension extends RequestCallBack<String> {
		/**
		 * 联网成功
		 * 
		 * @param arg0
		 */
		@Override
		public void onSuccess(ResponseInfo<String> arg0) {
			// 获取json 字符串
			String json = arg0.result;
			// 检验字符串
			boolean isError = MyUtils.checkJson(json);
			if (isError) {
				// 正确,开始解析
				pareJson(json);
			} else {
				// 错误的话,提示服务器异常
				Toast.makeText(mContext, "服务器异常", Toast.LENGTH_SHORT).show();

			}

		}

		/**
		 * 联网失败
		 * 
		 * @param arg0
		 *            异常信息
		 * @param arg1
		 *            异常信息
		 */
		@Override
		public void onFailure(HttpException arg0, String msg) {
			arg0.printStackTrace();
			LogPrint.logI("CommodityListPager", msg);

		}
	}

	/**
	 * 打开数据的url;
	 */
	private String url;

	/**
	 * 标题栏视图
	 */
	private TittleBarView tb;
	/**
	 * 显示的商品列表
	 */
	private MyListView lv;
	/**
	 * 请求网络数据的工具
	 */
	private HttpUtils httpUtils;
	/**
	 * 解析json的工具
	 */
	private Gson gson;
	/**
	 * 获得当前列表的个数
	 */
	private int count;
	/**
	 * 获得响应头的类型
	 */
	private String response;
	/**
	 * 数据源,商品列表
	 */
	private ArrayList<Commodity> productlist;

	/**
	 * 用来处理集合排序的工具
	 */
	private MyCommoditySortUtils mUtils;
	/**
	 * 展示数据的适配器
	 */
	private MyAdapter myAdapter;
	/**
	 * 加载网络图片工具
	 */
	private BitmapUtils bitmapUtils;
	/**
	 * 为销量排序标记,true 为降序,false为升序
	 */
	private boolean isXiaoLiangDown = true;
	/**
	 * 为上架时间排序标记,true 为降序,false为升序
	 */
	private boolean isTimeDown = true;
	/**
	 * 为好评排序标记,true 为降序,false为升序
	 */
	private boolean isGoodsDown = true;
	/**
	 * 为价格(以会员价为准)排序标记,true 为降序,false为升序
	 */
	private boolean isPriceDown = true;
	/**
	 * 获得跳转到详情列表之前的菜单内容部分数据集合
	 */
	private static ArrayList<Category> printList;
	/**
	 * 三级菜单数据的总集合
	 */
	private static ArrayList<Category> printListCounts;
	/**
	 * 排序按钮
	 */
	private TextView tv_xiaoliang_sort, tv_Price_sort, tv_Goods_sort,
			tv_time_sort;

	public CommodityListPager(ArrayList<Category> childs,
			ArrayList<Category> totalCategory, Context mContext, String url) {
		super(mContext);
		this.url = MyConstants.CommodityList_URL;
		if (childs != null && totalCategory != null) {
			this.printList = childs;
			this.printListCounts = totalCategory;
		}
		httpUtils = new HttpUtils();
		gson = new Gson();
		mUtils = new MyCommoditySortUtils();
		bitmapUtils = new BitmapUtils(mContext);

	}

	@Override
	public View initView() {
		// 初始化控件
		View view = View.inflate(mContext, R.layout.pager_brand_commodity_list,
				null);

		tb = (TittleBarView) view.findViewById(R.id.tb_pager_commodity_list);
		lv = (MyListView) view.findViewById(R.id.lv_pager_commodity_list);
		tv_xiaoliang_sort = (TextView) view
				.findViewById(R.id.tv_xiaoliang_sort);
		tv_Price_sort = (TextView) view.findViewById(R.id.tv_price_sort);
		tv_Goods_sort = (TextView) view.findViewById(R.id.tv_goods_sort);
		tv_time_sort = (TextView) view.findViewById(R.id.tv_time_sort);
		// 设置默认选项
		tb.setTittle("商品列表");
		tb.setBackVisibility(true);
		tb.setOperVisibility(true);
		tb.setOperText("筛选");
		int dip2px = MyUtils.dip2px(mContext, 15);
		tb.setTextSize(dip2px);
		// 默认为销量排序
		// TODO 添加默认选项

		initData();
		initRegister();
		return view;
	}

	@Override
	public void initRegister() {

		/**
		 * 处理返回键
		 */
		tb.setOnBackClick(this);
		/**
		 * 处理筛选
		 */
		tb.setOnOperClick(this);
		/**
		 * 销量排序点击时间
		 */
		tv_xiaoliang_sort.setOnClickListener(this);
		/**
		 * 价格排序点击时间
		 */
		tv_Price_sort.setOnClickListener(this);
		/**
		 * 好评排序点击时间
		 */
		tv_Goods_sort.setOnClickListener(this);
		/**
		 * 上架时间排序点击时间
		 */
		tv_time_sort.setOnClickListener(this);
		/**
		 * 处理listView 的刷新状态
		 */
		lv.setOnRefreshStateChangeListener(new RefreshStateChangeListener());
		/**
		 * 处理listView的条目点击事件
		 * 
		 */
		lv.setOnItemClickListener(new ItemClickListener());
	}

	@Override
	public void initData() {
		// 请求网络,处理数据
		httpUtils.send(HttpMethod.GET, url, new RequestCallBackExtension());

	}

	/**
	 * 解析数据
	 * 
	 * @param json
	 */
	public void pareJson(String json) {
		// 解析得到对应的bean对象
		CommodityListBean listBean = gson.fromJson(json,
				CommodityListBean.class);
		// 获得bean 中的数据
		count = listBean.list_count;
		response = listBean.response;
		productlist = listBean.productlist;
		// 先将集合数据进行销量排序(默认为销量排序)
		productlist = (ArrayList<Commodity>) mUtils.getDownList(
				mUtils.XIAOLIANG_SORT, productlist);
		myAdapter = new MyAdapter(productlist);
		lv.setAdapter(myAdapter);
	}

	/**
	 * 展示商品列表
	 * 
	 * @author wx
	 * 
	 */
	private class MyAdapter extends DefaultBaseAdapter<Commodity> {

		public MyAdapter(List<Commodity> datas) {
			super(datas);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(mContext,
						R.layout.item_pager_commodity_list, null);
				holder = new ViewHolder();
				holder.iv = (ImageView) convertView
						.findViewById(R.id.iv_item_pager_commodity_list);
				holder.tv_info = (TextView) convertView
						.findViewById(R.id.tv_info);
				holder.tv_vip_price = (TextView) convertView
						.findViewById(R.id.tv_vip_price);
				holder.tv_old_price = (TextView) convertView
						.findViewById(R.id.tv_old_price);
				holder.tv_comment = (TextView) convertView
						.findViewById(R.id.tv_comment);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Commodity commodity = productlist.get(position);
			bitmapUtils
					.display(holder.iv, MyConstants.BASE_URL + commodity.pic);
			holder.tv_info.setText(commodity.name);
			holder.tv_vip_price.setText("￥:" + commodity.price);
			// 为TextView 设置中划线
			holder.tv_old_price.getPaint().setFlags(
					Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
			holder.tv_old_price.setText("￥:" + commodity.marketprice);
			holder.tv_comment.setText("已有" + commodity.commentcount + "人评价");

			return convertView;
		}

	}

	private class ViewHolder {
		public ImageView iv;

		public TextView tv_info, tv_vip_price, tv_old_price, tv_comment;
	}

	/**
	 * 所有按钮的点击事件
	 */
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tv_xiaoliang_sort:
			/**
			 * 处理销量排序
			 */
			// 先处理按钮显示
			tv_Goods_sort.setBackgroundResource(R.drawable.segment_normal_2_bg);
			tv_Price_sort.setBackgroundResource(R.drawable.segment_normal_2_bg);
			tv_time_sort.setBackgroundResource(R.drawable.segment_normal_3_bg);
			tv_xiaoliang_sort
					.setBackgroundResource(R.drawable.segment_selected_1_bg);
			// 处理数据显示
			if (isXiaoLiangDown) {
				productlist = (ArrayList<Commodity>) mUtils.getDownList(
						mUtils.XIAOLIANG_SORT, productlist);
			} else {
				productlist = (ArrayList<Commodity>) mUtils.getUpList(
						mUtils.XIAOLIANG_SORT, productlist);

			}
			isXiaoLiangDown = !isXiaoLiangDown;
			// 刷新数据
			myAdapter.notifyDataSetChanged();
			break;

		case R.id.tv_price_sort:
			/**
			 * 处理价格排序
			 */
			// 先处理按钮显示
			tv_Goods_sort.setBackgroundResource(R.drawable.segment_normal_2_bg);
			tv_Price_sort
					.setBackgroundResource(R.drawable.segment_selected_2_bg);
			tv_time_sort.setBackgroundResource(R.drawable.segment_normal_3_bg);
			tv_xiaoliang_sort
					.setBackgroundResource(R.drawable.segment_normal_1_bg);
			// 处理数据显示
			if (isPriceDown) {
				productlist = (ArrayList<Commodity>) mUtils.getDownList(
						mUtils.PRICE_SORT, productlist);
			} else {
				productlist = (ArrayList<Commodity>) mUtils.getUpList(
						mUtils.PRICE_SORT, productlist);

			}
			isPriceDown = !isPriceDown;
			// 刷新数据
			myAdapter.notifyDataSetChanged();
			break;

		case R.id.tv_goods_sort:
			/**
			 * 处理好评排序
			 */
			// 先处理按钮显示
			tv_Goods_sort
					.setBackgroundResource(R.drawable.segment_selected_2_bg);
			tv_Price_sort.setBackgroundResource(R.drawable.segment_normal_2_bg);
			tv_time_sort.setBackgroundResource(R.drawable.segment_normal_3_bg);
			tv_xiaoliang_sort
					.setBackgroundResource(R.drawable.segment_normal_1_bg);
			// 处理数据显示
			if (isGoodsDown) {
				productlist = (ArrayList<Commodity>) mUtils.getDownList(
						mUtils.GOODS_SORT, productlist);
			} else {
				productlist = (ArrayList<Commodity>) mUtils.getUpList(
						mUtils.GOODS_SORT, productlist);

			}
			isGoodsDown = !isGoodsDown;
			// 刷新数据
			myAdapter.notifyDataSetChanged();

			break;

		case R.id.tv_time_sort:

			/**
			 * 处理上架时间排序,服务器没有时间,选择器的排序方式为销量
			 */
			// 先处理按钮显示
			tv_Goods_sort.setBackgroundResource(R.drawable.segment_normal_2_bg);
			tv_Price_sort.setBackgroundResource(R.drawable.segment_normal_2_bg);
			tv_time_sort
					.setBackgroundResource(R.drawable.segment_selected_3_bg);
			tv_xiaoliang_sort
					.setBackgroundResource(R.drawable.segment_normal_1_bg);
			// 处理数据显示
			if (isTimeDown) {
				productlist = (ArrayList<Commodity>) mUtils.getDownList(
						mUtils.TIME_SORT, productlist);
			} else {
				productlist = (ArrayList<Commodity>) mUtils.getUpList(
						mUtils.TIME_SORT, productlist);

			}
			isTimeDown = !isTimeDown;
			// 刷新数据
			myAdapter.notifyDataSetChanged();
			break;
		case R.id.iv_back:
			// 点击返回
			MainActivity mainActivity = (MainActivity) mContext;
			if (isOthergoThere) {
				mainActivity.jumpPager(mainActivity.pagerId[0]);
				isOthergoThere = false;
			} else {
				BrandFragment brandFragment = mainActivity.getBrandFragment();
				BrandPager.isListBack = true;
				brandFragment.upDatePager(new BrandPager(mContext, printList,
						printListCounts));
			}
			break;

		case R.id.tv_oper:
			// 点击筛选
			MainActivity mainActivity2 = (MainActivity) mContext;
			BrandFragment brandFragment2 = mainActivity2.getBrandFragment();
			brandFragment2.upDatePager(new SortPager(mContext));
			break;
		default:
			break;
		}

	}

}
