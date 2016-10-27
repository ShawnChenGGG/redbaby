package zz.itcast.redbody7.activity;

import java.util.ArrayList;
import java.util.List;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.bean.UserInfoBean;
import zz.itcast.redbody7.bean.UserInfoBean.User;
import zz.itcast.redbody7.utils.LogPrint;
import zz.itcast.redbody7.utils.MyHttpUtils;
import zz.itcast.redbody7.utils.MyHttpUtils.OnRequestCallBack;
import zz.itcast.redbody7.utils.MyUtils;
import zz.itcast.redbody7.view.TittleBarView;
import android.content.Intent;
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

import com.google.gson.Gson;

public class UserCenterActivity extends BaseActivity {

	private TextView tv_username, tv_dingdan, tv_address, tv_youhuiquan,
			tv_shoucangjia;

	private TittleBarView tbv_title;

	@Override
	public void initRegister() {
		user_center.setOnItemClickListener(new OnMyItemClickListener());
	}

	private List<String> userinfos = new ArrayList<String>();
	private String bonusString;
	private String level;
	private int orderCount;
	private int favoritesCount;
	private int userId = MyUtils.userId;
	private TextView userName;
	private TextView dengJi;
	private TextView jiFen;

	private ListView user_center;

	@Override
	public void initView() {

		setContentView(R.layout.activity_user_center);

		user_center = (ListView) findViewById(R.id.user_center);

		tbv_title = (TittleBarView) findViewById(R.id.tbv_title);

		tv_dingdan = (TextView) findViewById(R.id.tv_dingdan);
		tv_address = (TextView) findViewById(R.id.tv_address);
		// tv_youhuiquan = (TextView) findViewById(R.id.tv_youhuiquan);
		// tv_shoucangjia = (TextView) findViewById(R.id.tv_shoucangjia);

		userName = (TextView) findViewById(R.id.tv_user);
		dengJi = (TextView) findViewById(R.id.tv_dengji);
		jiFen = (TextView) findViewById(R.id.tv_jifen);

	}

	@Override
	public void initData() {

		tbv_title.setTittle("用户中心");
		tbv_title.setOnBackClick(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();

			}
		});

		tbv_title.setOperVisibility(false);
		tbv_title.setOperText("注销");
		tbv_title.setOperVisibility(true);
		tbv_title.setTextSize(15);
		tbv_title.setOnOperClick(new OnClickListener() {

			@Override
			public void onClick(View v) {

				MyUtils.userId = 0;
				MyUtils.userName = null;
				finish();
			}
		});

		sendRequest(MyConstants.BASE_URL + "/userinfo?userid=" + MyUtils.userId);
		LogPrint.logI("lsj", "UserCenterActivity ： " + MyUtils.userId);

		adapter = new MyUserCenterAdapter();
		user_center.setAdapter(adapter);

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

	protected void parseJson(String json) {

		if (MyUtils.checkJson(json)) {

			Gson gson = new Gson();
			UserInfoBean userInfoBean = gson.fromJson(json, UserInfoBean.class);
			User userinfo = userInfoBean.userinfo;

			int bonus = userinfo.bonus;
			int favoritescount2 = userinfo.favoritescount;
			String level2 = userinfo.level;
			int ordercount2 = userinfo.ordercount;
			String usersession = userinfo.usersession;

			userName.setText(MyUtils.userName);
			if (level2 == null || level2.equals("")) {

				dengJi.setText("普通用户");
			} else {

				dengJi.setText(level2);
			}
			jiFen.setText(bonus + "");

		} else {
			Toast.makeText(mContext, "服务器出错！", Toast.LENGTH_SHORT).show();
		}

	}

	private String[] userInfo = { "我的订单", "地址管理", "优惠券/礼品卡", "收藏夹", };
	private MyUserCenterAdapter adapter;

	private final class OnMyItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			/**
			 * 点击跳转页面
			 * 
			 */
			switch (position) {
			case 0:
				startActivity(new Intent(UserCenterActivity.this,
						MyOrderActivity.class));
				finish();
				break;

			case 1:
				startActivity(new Intent(UserCenterActivity.this,
						AddressManageActivity.class));
				finish();
				break;
			case 2:
				Intent intent = new Intent(UserCenterActivity.this,
						MyCouponActivity.class);
				startActivity(intent);
				finish();
				break;
			case 3:
				Intent favorite = new Intent(mContext, FavoriteActivity.class);
				startActivity(favorite);
				finish();
				break;

			}

		}
	}

	class MyUserCenterAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return userInfo.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MyUserInfoHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(mContext,
						R.layout.user_info_list_item, null);
				holder = new MyUserInfoHolder();

				holder.tv_dingdan = (TextView) convertView
						.findViewById(R.id.tv_dingdan);
				holder.iv_boult = (ImageView) convertView
						.findViewById(R.id.iv_boult);
				convertView.setTag(holder);
			} else {
				holder = (MyUserInfoHolder) convertView.getTag();
			}
			holder.tv_dingdan.setText(userInfo[position]);

			return convertView;
		}

	}

	class MyUserInfoHolder {

		public ImageView iv_boult;
		public TextView tv_dingdan;

	}

}
