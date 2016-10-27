package zz.itcast.redbody7.activity;

import java.util.List;
import java.util.Random;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.activity.SnatchActivity.MyPanicItemBaseAdapter;
import zz.itcast.redbody7.adapter.DefaultBaseAdapter;
import zz.itcast.redbody7.bean.FavoriteBean;
import zz.itcast.redbody7.bean.FavoriteBean.Favorite;
import zz.itcast.redbody7.bean.PanicBean.Trade;
import zz.itcast.redbody7.utils.MyHttpUtils;
import zz.itcast.redbody7.utils.MyHttpUtils.OnRequestCallBack;
import zz.itcast.redbody7.utils.MyUtils;
import zz.itcast.redbody7.view.TittleBarView;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;

public class FavoriteActivity extends BaseActivity {

	private ListView favorite_listview;
	public List<Favorite> productlist;
	private TittleBarView tbv;
	private FavoriteItemBaseAdapter adapter;

	@Override
	public void initRegister() {

	}

	@Override
	public void initData() {

		tbv.setTittle("收藏夹");
		tbv.setOperVisibility(false);

		tbv.setOnBackClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// 请求数据

		// http://192.168.12.253:8080/ECServicez8/favorites?page=1&pageNum=1&userId=2

		if(MyUtils.userId==0){
			return;
		}
		sendRequest(MyConstants.BASE_URL
				+ "/favorites?page=1&pageNum=1&userId="+MyUtils.userId);
	}

	private void sendRequest(String url) {
		MyHttpUtils myHttpUtils = new MyHttpUtils(url, false);
		myHttpUtils.setOnRequestCallBack(new OnRequestCallBack() {

			@Override
			public void onSuccess(String result) {
				String json = result;
				// 解析
				parseJson(json);
			}

		});
	}

	private void parseJson(String json) {
		Gson gson = new Gson();
		FavoriteBean favoriteBean = gson.fromJson(json, FavoriteBean.class);
		productlist = favoriteBean.productlist;

		adapter = new FavoriteItemBaseAdapter(productlist);

		favorite_listview.setAdapter(adapter);

	}

	class FavoriteItemBaseAdapter extends DefaultBaseAdapter<Favorite> {
		private BitmapUtils bUtils;

		public FavoriteItemBaseAdapter(List<Favorite> datas) {
			super(datas);
			bUtils = new BitmapUtils(mContext);
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			MyFavoriteHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(FavoriteActivity.this,
						R.layout.listview_item_favorite, null);
				holder = new MyFavoriteHolder();

				holder.iv_item_img = (ImageView) convertView
						.findViewById(R.id.iv_list_item_productlist);

				holder.tv_item_name = (TextView) convertView
						.findViewById(R.id.tv_list_item_name_productlist);

				holder.tv_item_price = (TextView) convertView
						.findViewById(R.id.tv_list_item_price_productlist);

				holder.tv_item_prices = (TextView) convertView
						.findViewById(R.id.tv_list_item_marketprice_productlist);

				holder.tv_list_item_comment_productlist = (TextView) convertView
						.findViewById(R.id.tv_list_item_comment_productlist);

				convertView.setTag(holder);
			} else {
				holder = (MyFavoriteHolder) convertView.getTag();
			}
			// 填充数据

			bUtils.display(holder.iv_item_img,
					MyConstants.BASE_URL + datas.get(position).pic);

			holder.tv_item_name.setText(datas.get(position).name);
			holder.tv_item_price.setText("¥："
					+ (datas.get(position).marketprice + ""));
			holder.tv_item_prices.setText("¥："
					+ (datas.get(position).price + ""));
			holder.tv_list_item_comment_productlist.setText("已有886人评价");
			holder.tv_item_prices.getPaint().setFlags(
					Paint.STRIKE_THRU_TEXT_FLAG);
			return convertView;
		}
	}

	class MyFavoriteHolder {

		public TextView tv_list_item_comment_productlist;
		public TextView tv_item_prices;
		public TextView tv_item_price;
		public TextView tv_item_name;
		public ImageView iv_item_img;

	}

	@Override
	public void initView() {

		setContentView(R.layout.favorite_activity);

		tbv = (TittleBarView) findViewById(R.id.favorite);

		favorite_listview = (ListView) findViewById(R.id.favorite_listview);

	}

}
