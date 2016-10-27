package zz.itcast.redbody7.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.DbUtils.DaoConfig;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.bean.CommodityListBean;
import zz.itcast.redbody7.bean.CommodityListBean.Commodity;
import zz.itcast.redbody7.bean.DBBrowse;
import zz.itcast.redbody7.bean.ProductListBean;
import zz.itcast.redbody7.bean.ProductListBean.ProductDetail;
import zz.itcast.redbody7.dao.AddCartShopping;
import zz.itcast.redbody7.dao.AddFavorites;
import zz.itcast.redbody7.utils.LogPrint;
import zz.itcast.redbody7.utils.MyHttpUtils;
import zz.itcast.redbody7.utils.MyUtils;
import zz.itcast.redbody7.utils.MyHttpUtils.OnRequestCallBack;
import zz.itcast.redbody7.view.TittleBarView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.provider.Browser;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 商品详情界面
 * 
 * @author zh
 * 
 */
@SuppressWarnings("deprecation")
public class ProductDetailActivity extends BaseActivity {
	/**
	 * 该集合用来记录浏览记录,用浏览记录的Bean
	 * -wx
	 */
	public static ArrayList<DBBrowse> browseData=new ArrayList<DBBrowse>();
	private TittleBarView tbv;
	private int pid;
	private Gallery gallery;
	private ArrayList<String> pics;
	private TextView tvProductdesc;
	private TextView tvScj;
	private RatingBar rbScore;
	private TextView tvHyj;
	private Spinner spColor;
	private Spinner spSize;
	private TextView tvCount;
	private Button btnCat;
	private Button product__detail_sc;
	// 商品详情的布局
	private LinearLayout ll_detail;

	@Override
	public void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_product_detail);

		tbv = (TittleBarView) findViewById(R.id.product__detail_title);
		gallery = (Gallery) findViewById(R.id.product__detail_gallery);
		tvProductdesc = (TextView) findViewById(R.id.product__detail_productdesc);
		tvScj = (TextView) findViewById(R.id.product__detail_scj);
		tvScj.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		rbScore = (RatingBar) findViewById(R.id.product__detail_score);
		tvHyj = (TextView) findViewById(R.id.product__detail_hyj);
		spColor = (Spinner) findViewById(R.id.product__detail_color);
		spSize = (Spinner) findViewById(R.id.product__detail_size);
		tvCount = (TextView) findViewById(R.id.product__detail_count);
		btnCat = (Button) findViewById(R.id.product__detail_cat);
		product__detail_sc = (Button) findViewById(R.id.product__detail_sc);
		ll_detail = (LinearLayout) findViewById(R.id.ll_detail);
		pid = getIntent().getIntExtra("PID", -1);
		ll_comment = (RelativeLayout) findViewById(R.id.ll_comment);
		

	}

	@Override
	public void initRegister() {
		/**
		 * 设置商品信息布局点击事件
		 */
		ll_detail.setOnClickListener(new LL_Detail_ClickListener());
		/**
		 * 设置评论点击
		 */
		ll_comment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,ActivityComment.class);
				intent.putExtra("pId", productDetail.id);
				startActivity(intent);
				
			}
		});
		
		
		
	}

	// http://192.168.12.253:8080/ECServicez8/product?pId=2

	@Override
	public void initData() {
		tbv.setTittle("商品详情");
		tbv.setOperVisibility(false);

		tbv.setOnBackClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		ivList = new ArrayList<Integer>();
		for (int i = 0; i < 5; i++) {
			ivList.add(R.drawable.product_detail_img);
		}

		getServerData();
	}

	/**
	 * 获取网络数据
	 */
	private void getServerData() {

		MyHttpUtils mUtils = new MyHttpUtils(MyConstants.BASE_URL + "/product?pId=" + pid,false);

		mUtils.setOnRequestCallBack(new OnRequestCallBack() {

			@Override
			public void onSuccess(String result) {

				LogPrint.logI("zh----" + pid, result);

				parserJson(result);
			}
		});

	}

	/*
	 * 解析json
	 */
	protected void parserJson(String result) {

		Gson gson = new Gson();
		ProductListBean fromJson = gson.fromJson(result, ProductListBean.class);

		pics = fromJson.product.get(0).pics;

		gallery.setAdapter(new MyGralleryAdapter());


		gallery.setOnItemClickListener(listenerGallery);
		



		if (fromJson.product.size() >= 2) {
			productDetail = fromJson.product.get(1);
			if (productDetail != null) {
				// 商品信息描述
				tvProductdesc.setText(productDetail.productdesc);
				// 市场价和商品评分
				tvScj.setText("￥:" + productDetail.marketprice);
				rbScore.setMax(productDetail.score);
				rbScore.setProgress(productDetail.sales);
				// 会员价
				tvHyj.setText("￥:" + productDetail.price);

				// 颜色和尺码
				String[] strsColor = { "红", "蓝", "黑", "白", "紫", "粉" };
				SpinnerAdapter adapterColor = new ArrayAdapter<String>(
						mContext, android.R.layout.simple_list_item_1,
						strsColor);

				spColor.setAdapter(adapterColor);

				String[] strsSize = { "特大", "大", "中", "小", "特小" };
				SpinnerAdapter adapterSize = new ArrayAdapter<String>(mContext,
						android.R.layout.simple_list_item_1, strsSize);

				spSize.setAdapter(adapterSize);

				tvCount.setText(productDetail.commentcount + "");

				// 添加购物车监听
				btnCat.setOnClickListener(addCatListener);
				product__detail_sc.setOnClickListener(addCatListener);
			}
		}

	}

	OnClickListener addCatListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.product__detail_sc:// 加入收藏夹

				if (MyUtils.userId == 0) {
					// 调到更多
					Toast.makeText(mContext, "请登录", Toast.LENGTH_SHORT).show();

				} else {

					AddFavorites addFavorites = new AddFavorites(pid,
							MyUtils.userId);

					addFavorites.addFavoritesImpl();

					Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
				}

				break;

			case R.id.product__detail_cat:// 加入购物车

				if (productDetail != null) {
					if (productDetail.number == 0) {
						productDetail.number = 1;
					}
					if (MyUtils.userId == 0) {
						// 调到更多
						Toast.makeText(mContext, "请登录", Toast.LENGTH_SHORT)
								.show();
					} else {

						AddCartShopping aShopping = new AddCartShopping(
								mContext, pid, 1, 1 + "");

						aShopping.AddCartShoppingImpl();

						Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT)
								.show();
					}
				}

				break;

			default:
				break;
			}

		}
	};

	private ProductDetail productDetail;
	private List<Integer> ivList;


	/**
	 * 商品信息的布局点击事件,进入商品描述
	 * 
	 * @author wx
	 * 
	 */
	private final class LL_Detail_ClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO 完成商品介绍跳转
			if (productDetail!=null) {
				Intent intent=new Intent(ProductDetailActivity.this,ActivityProductContent.class);
				intent.putExtra("name", productDetail.name);
				intent.putExtra("id", pid);
				startActivity(intent);
			}

		}
	}


	class MyGralleryAdapter extends BaseAdapter {

		private BitmapUtils bUtils;

		public MyGralleryAdapter() {
			bUtils = new BitmapUtils(mContext);
		}

		@Override
		public int getCount() {
			if (pics != null && pics.size() > 0) {
				if (pics.size() < 3) {
					String path = pics.get(0);
					for (int i = 0; i < 8; ++i) {
						pics.add(path);
					}
				}
				return pics.size();
			} else {

				return ivList.size();
			}
		}

		@Override
		public String getItem(int position) {
			if (pics != null && pics.size() > 0) {
				return pics.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (pics != null && pics.size() > 0) {
				ImageView iv = new ImageView(mContext);

				String item = getItem(position);

				bUtils.display(iv, MyConstants.BASE_URL + item);
				return iv;

			} else {

				View view = View.inflate(mContext,
						R.layout.product_detail_item, null);

				ImageView iv = (ImageView) view
						.findViewById(R.id.product_detail_item_iv);

				int integer = ivList.get(position);
				iv.setImageResource(integer);

				return view;
			}

		}

	}

	/*
	 * gallery点击监听
	 */
	OnItemClickListener listenerGallery = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			startActivity(new Intent(ProductDetailActivity.this,
					BigPictureActivity.class));
		}
	};
	
	/**
	 * 在可视的时候进行添加数据
	 * id onResume();
	 */
	//TODO  -wx
	public static int id;
	private RelativeLayout ll_comment;
	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
//		
//		SharedPreferences sp = getSharedPreferences(MyConstants.ID_XML_NAME, MODE_PRIVATE);
//		id = sp.getInt("id", 0);
//		
		/**
		 * 在获取ID之后,添加到浏览记录集合,
		 * 只记录20个,
		 */
		//TODO 有可能会出错,不出错在删除这个TODO
		
		DBBrowse browse=new DBBrowse();
		LogPrint.logI("ProductDetailActivity", "browse : "+browse+" productDetail : "+productDetail);
		//当前商品ID;
		browse.browse_id=pid;
		//当前商品名字
		browse.name=productDetail.name;
		
		//当前商品简介
		browse.detail=productDetail.productdesc;
		//当前商品图片url
		browse.pic=productDetail.pic;
		//当前商品价格
		browse.price=productDetail.price;
		
		
		if (browseData.size()<20) {
			//TODO,在mainActivity 退出的时候,将集合中的数据存储数据库
			browseData.add(0,browse);
		
		}else {
			//当超出20个时候,删除最后一个
		
			browseData.remove(browseData.size()-1);
		
		}
	};

}
