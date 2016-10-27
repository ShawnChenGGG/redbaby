package zz.itcast.redbody7.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.activity.CheckOutActivity;
import zz.itcast.redbody7.activity.MainActivity;
import zz.itcast.redbody7.activity.ProductActivity;
import zz.itcast.redbody7.activity.ProductDetailActivity;
import zz.itcast.redbody7.adapter.DefaultBaseAdapter;
import zz.itcast.redbody7.bean.CartBean;
import zz.itcast.redbody7.bean.CartShoppingBean;
import zz.itcast.redbody7.bean.CartShoppingBean.CartBean.ProductBean;
import zz.itcast.redbody7.bean.CartShoppingBean.CartBean.ProductBean.property;
import zz.itcast.redbody7.dao.CartDao;
import zz.itcast.redbody7.utils.LogPrint;
import zz.itcast.redbody7.utils.MyUtils;
import zz.itcast.redbody7.view.LoadingView;
import zz.itcast.redbody7.view.TittleBarView;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 购物车Fragment
 * 
 * @author zh
 * 
 */
public class ShoppingFragment extends BaseFragment {

	/**
	 * 码洋的自定义标题头控件
	 */
	private TittleBarView tb_fragment_shopping_title;

	/**
	 * 商品列表
	 */
	private ListView lv_fragment_shopoing;

	/**
	 * 商品金额总计
	 */
	private int totalPrice;
	/**
	 * 商品积分总计
	 */
	private int totalPoint;
	/**
	 * 商品数量总计
	 */
	private int totalCount;
	/**
	 * 商品数量总计
	 */
	private TextView tv_fragment_shopping_count_num;

	/**
	 * 商品积分总计
	 */
	private TextView tv_fragment_shopping_integral_num;

	/**
	 * 商品金额总计
	 */
	private TextView tv_fragment_shopping_money_num;

	/**
	 * 总商品信息的线性布局
	 */
	private LinearLayout ll_shopping_total;

	/**
	 * 进度条
	 */
	private ProgressDialog pd;

	/**
	 * 从服务器获得的购物车列表json数据对应的javaBean对象
	 */
	private CartShoppingBean shoppingBean;

	private MyBaseAdapter adapter;

	private BitmapUtils bitmapUtils;

	/**
	 * 存放ListView每个条目左边按钮的id
	 */
	private ArrayList<String> positionList;

	/**
	 * 当无购物车列表时显示的布局
	 */
	private LinearLayout ll_shopping_null;

	/**
	 * 当无购物车列表时显示的布局中的点击按钮
	 */
	private TextView tv_shopping_nul;

	/**
	 * adapter的数据源，也是要传入结算中心的参数
	 */
	private ArrayList<CartShoppingBean.CartBean> cart;

	/**
	 * 商品列表中，每加一个条目，都会把商品id加入这里面
	 */
	private HashSet<String> existList;

	/**
	 * 进度条布局
	 */
	private LoadingView tb_fragment_shopping_Pd;

	private View view;

	/*
	 * 值天津唉一次脚步局
	 */
	boolean isFirst = false;

	@Override
	public View initView() {

		// if (view == null) {
		//
		view = View.inflate(mContext, R.layout.fragment_shopping, null);
		// }
		//
		// if (bitmapUtils == null) {
		//
		bitmapUtils = new BitmapUtils(mContext);
		// }
		// if (positionList == null) {
		//
		positionList = new ArrayList<String>();
		// }
		// if (existList == null) {
		//
		existList = new HashSet<String>();
		// }

		// 初始化标题栏
		initTitle(view);

		// 初始化总商品信息
		initTotalGoodsInfo(view);

		return view;
	}

	/**
	 * 初始化总商品信息
	 */
	private void initTotalGoodsInfo(View view) {

		tv_fragment_shopping_count_num = (TextView) view
				.findViewById(R.id.tv_fragment_shopping_count_num);
		tv_fragment_shopping_integral_num = (TextView) view
				.findViewById(R.id.tv_fragment_shopping_integral_num);
		tv_fragment_shopping_money_num = (TextView) view
				.findViewById(R.id.tv_fragment_shopping_money_num);

		ll_shopping_total = (LinearLayout) view
				.findViewById(R.id.ll_shopping_total);

		ll_shopping_total.setVisibility(View.INVISIBLE);

	}

	@Override
	public void initData() {

		// pd = new ProgressDialog(mContext);
		// pd.setMessage("加载中...");
		// pd.show();

		// tb_fragment_shopping_Pd.setVisibility(View.VISIBLE);

		CartDao cDao = CartDao.getInstance(mContext);
		List<CartBean> findGoodsFromUerId = cDao
				.findGoodsFromUerId(MyUtils.userId);

		// 存放参数的集合
		ArrayList<String> paramsList = new ArrayList<String>();

		HashSet<String> skuList = new HashSet<String>();

		if (findGoodsFromUerId != null) {// 购物车中有数据

			StringBuilder sb = new StringBuilder();

			for (CartBean cartBean : findGoodsFromUerId) {

				int goodsId = cartBean.goodsId;
				int count = cartBean.count;
				String attrId = cartBean.attrId;

				String result = goodsId + ":" + count + ":" + attrId;

				sb.append(result + "|");

			}

			String sbString = sb.toString();
			LogPrint.logI("lsj", "sbString: " + sbString);
			// 联网请求数据
			requestData(sbString);

		} else {// 购物车中没有数据

			ll_shopping_null.setVisibility(View.VISIBLE);

			tv_shopping_nul.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO 随便逛逛 跳转到分类界面
					MainActivity mMainActivity = (MainActivity) mContext;
					mMainActivity.jumpPager(mMainActivity.pagerId[2]);

				}
			});

		}

	}

	/**
	 * 联网请求数据
	 */
	private void requestData(String sbString) {
		HttpUtils hu = new HttpUtils();
		String url = MyConstants.BASE_URL + "/cart";

		RequestParams params = new RequestParams();
		params.addBodyParameter("userid", MyUtils.userId + "");
		params.addBodyParameter("sku", sbString);
		LogPrint.logI("lsj", "ShoppingFragment--userId " + MyUtils.userId);

		hu.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

				LogPrint.logI("lsj", "/cart 联网失败");
				// pd.dismiss();
				// tb_fragment_shopping_Pd.setVisibility(View.GONE);

				// 显示联网失败对话框
				AlertDialog.Builder adb = new Builder(mContext);
				final AlertDialog ad = adb.create();
				ad.setMessage("联网失败");
				ad.setButton(-1, "确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						ad.dismiss();
					}
				});

				ad.show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {

				LogPrint.logI("lsj", "/cart 联网成功");

				String json = arg0.result;
				parseJson(json);
			}
		});
	}

	/**
	 * 初始化标题栏
	 */
	private void initTitle(View view) {

		tb_fragment_shopping_title = (TittleBarView) view
				.findViewById(R.id.tb_fragment_shopping_title);

		tb_fragment_shopping_title.setTittle("购物车");

		tb_fragment_shopping_title.setVisibility(View.VISIBLE);
		tb_fragment_shopping_title.setBackVisibility(false);
		tb_fragment_shopping_title.setTextSize(15);

		tb_fragment_shopping_title.setOnOperClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Toast.makeText(mContext, "去结算", Toast.LENGTH_SHORT).show();

				// TODO去结算 参数cart positionList
				// 应该把cart对象和positionList对象传递过去，
				// 根据positionList列表中存储的position去查找cart列表中的数据

				if (MyUtils.userId == 0) {

					Toast.makeText(mContext, "请登录", Toast.LENGTH_SHORT).show();
				}
				if (totalCount == 0) {
					Toast.makeText(mContext, "购物车为空", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				Intent intent = new Intent(mContext, CheckOutActivity.class);
				startActivity(intent);
			}
		});

		tb_fragment_shopping_title.setOperText("结算");

		lv_fragment_shopoing = (ListView) view
				.findViewById(R.id.lv_fragment_shopoing);

		ll_shopping_null = (LinearLayout) view
				.findViewById(R.id.ll_shopping_null);

		tv_shopping_nul = (TextView) view.findViewById(R.id.tv_shopping_nul);

		tb_fragment_shopping_Pd = (LoadingView) view
				.findViewById(R.id.tb_fragment_shopping_Pd);

	}

	/**
	 * 解析数据
	 * 
	 * @param json
	 */
	protected void parseJson(String json) {

		if (MyUtils.checkJson(json)) {

			LogPrint.logI("lsj", "/cart json解析成功 ");

			// 消失进度条
			// pd.dismiss();
			// tb_fragment_shopping_Pd.setVisibility(View.GONE);

			Gson gson = new Gson();
			shoppingBean = gson.fromJson(json, CartShoppingBean.class);

			totalCount = shoppingBean.totalCount;
			totalPoint = shoppingBean.totalPoint;
			totalPrice = shoppingBean.totalPrice;

			// 设置总商品信息
			setTotalGoodsInfo();

			cart = (ArrayList<zz.itcast.redbody7.bean.CartShoppingBean.CartBean>) shoppingBean.cart;

			// 设置适配器
			adapter = new MyBaseAdapter(cart);

			if (!isFirst) {

				lv_fragment_shopoing.addFooterView(View.inflate(mContext,
						R.layout.shopping_item, null));
				isFirst = true;
			}

			lv_fragment_shopoing.setAdapter(adapter);

			lv_fragment_shopoing
					.setOnItemClickListener(new OnMyItemClickListener());

		}
	}

	/**
	 * 设置总商品信息
	 */
	private void setTotalGoodsInfo() {

		ll_shopping_total.setVisibility(View.VISIBLE);
		tv_fragment_shopping_count_num.setText(totalCount + "");
		tv_fragment_shopping_integral_num.setText(totalPoint + "");
		tv_fragment_shopping_money_num.setText("￥" + totalPrice);

	}

	/**
	 * ListView的条目点击事件
	 * 
	 * @author 李胜杰
	 * 
	 */
	private final class OnMyItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			// TODO 跳转到商品详情页面
			// Toast.makeText(mContext, "跳转到商品详情界面", Toast.LENGTH_SHORT).show();

			// 脚步局不执行事件
			if (position == cart.size()) {
				return;
			}

			Intent intent = new Intent(mContext, ProductDetailActivity.class);
			int PID = cart.get(position).product.id;
			intent.putExtra("PID", PID);
			startActivity(intent);

		}
	}

	/**
	 * ListView的适配器
	 * 
	 * @author 李胜杰
	 * 
	 */
	private class MyBaseAdapter extends
			DefaultBaseAdapter<CartShoppingBean.CartBean> {

		public MyBaseAdapter(List<CartShoppingBean.CartBean> datas) {
			super(datas);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder vh = null;

			if (convertView == null) {

				vh = new ViewHolder();

				convertView = View.inflate(mContext, R.layout.view_goods_info,
						null);

				vh.tv_goods_color = (TextView) convertView
						.findViewById(R.id.tv_goods_color);
				vh.tv_goods_desc = (TextView) convertView
						.findViewById(R.id.tv_goods_desc);
				vh.tv_goods_total = (TextView) convertView
						.findViewById(R.id.tv_goods_total);
				vh.tv_goods_size = (TextView) convertView
						.findViewById(R.id.tv_goods_size);
				vh.tv_goods_price = (TextView) convertView
						.findViewById(R.id.tv_goods_price);
				vh.tv_goods_count = (TextView) convertView
						.findViewById(R.id.tv_goods_count);

				vh.iv_checkbox = (ImageView) convertView
						.findViewById(R.id.iv_checkbox);
				vh.iv_goods_pic = (ImageView) convertView
						.findViewById(R.id.iv_goods_pic);

				vh.et_goods_num = (EditText) convertView
						.findViewById(R.id.et_goods_num);

				convertView.setTag(vh);

			} else {
				vh = (ViewHolder) convertView.getTag();
			}

			List<property> propertyList = datas.get(position).product.product_property;

			ProductBean product = datas.get(position).product;

			vh.tv_goods_desc.setText(product.name);
			vh.tv_goods_total.setText("小计:￥"
					+ (product.price * datas.get(position).prodNum));

			vh.tv_goods_price.setText("价格:￥" + product.price + "");

			bitmapUtils.display(vh.iv_goods_pic, MyConstants.BASE_URL
					+ product.pic);

			vh.et_goods_num.setText(datas.get(position).prodNum + "");

			// 左边选择按钮的点击事件
			vh.iv_checkbox.setOnClickListener(new OnMyClickListener(position));

			// 修改数量先不做
			// vh.et_goods_num.addTextChangedListener(new TextWatcher()});

			if (positionList.contains(position + "")) {
				vh.iv_checkbox.setSelected(true);
			} else {
				vh.iv_checkbox.setSelected(false);
			}

			for (int i = 0; i < propertyList.size(); i++) {

				String k = propertyList.get(i).k;
				if (k != null) {

					if (i == 0) {
						vh.tv_goods_color
								.setText("颜色:" + propertyList.get(i).v);
					} else if (i == 1) {
						vh.tv_goods_color
								.setText("尺码:" + propertyList.get(i).v);
					}

				} else {

					if (i == 0) {
						vh.tv_goods_color.setText("颜色:" + "无");
					} else if (i == 1) {
						vh.tv_goods_size.setText("尺码:" + "无");
					}

				}

			}

			if (product.number == 0) {// 商品无货或者下架,商品信息文字颜色全部为灰色

				vh.tv_goods_desc.setTextColor(Color.GRAY);
				vh.tv_goods_total.setTextColor(Color.GRAY);
				vh.tv_goods_size.setTextColor(Color.GRAY);
				vh.tv_goods_price.setTextColor(Color.GRAY);
				vh.tv_goods_color.setTextColor(Color.GRAY);
				vh.tv_goods_count.setTextColor(Color.GRAY);

			} else {
				vh.tv_goods_desc.setTextColor(Color.BLACK);
				vh.tv_goods_total.setTextColor(Color.RED);
				vh.tv_goods_size.setTextColor(Color.BLACK);
				vh.tv_goods_price.setTextColor(Color.BLACK);
				vh.tv_goods_color.setTextColor(Color.BLACK);
				vh.tv_goods_count.setTextColor(Color.BLACK);
			}

			return convertView;
		}
	}

	private class ViewHolder {
		private TextView tv_goods_desc;
		private TextView tv_goods_total;
		private TextView tv_goods_size;
		private TextView tv_goods_price;
		private TextView tv_goods_color;
		private TextView tv_goods_count;
		private ImageView iv_checkbox;
		private ImageView iv_goods_pic;
		private EditText et_goods_num;

	}

	/**
	 * ListView每个条目左边按钮的点击事件
	 * 
	 * @author 李胜杰
	 * 
	 */
	private final class OnMyClickListener implements OnClickListener {

		private int position;

		public OnMyClickListener(int position) {

			this.position = position;
		}

		@Override
		public void onClick(View v) {

			if (positionList.contains(position + "")) {

				positionList.remove(position + "");
			} else {

				positionList.add(position + "");
			}

			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		System.out.println("----------");
		initData();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		isFirst = false;
	}

}
