package zz.itcast.redbody7.fragment;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.activity.AboutActivity;
import zz.itcast.redbody7.activity.ActivityBrowseData;
import zz.itcast.redbody7.activity.FeedbackActivity;
import zz.itcast.redbody7.activity.HelpActivity;
import zz.itcast.redbody7.activity.Login2Activity;
import zz.itcast.redbody7.activity.RegistActivity;
import zz.itcast.redbody7.activity.UserCenterActivity;
import zz.itcast.redbody7.utils.MyUtils;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 更多Fragment
 * 
 * @author zh
 * 
 */
public class MoreFragment extends BaseFragment implements OnClickListener {

	private TextView btn_call;
	private TextView btn_login;
	private TextView btn_regist;
	/**
	 * 头像
	 */
	private ImageView iv_header;
	/**
	 * 布局文件对象
	 */
	private View view;

	@Override
	public void onStart() {
		super.onStart();

		if (MyUtils.userId == 0) {

			btn_login.setVisibility(View.VISIBLE);
			btn_regist.setVisibility(View.VISIBLE);

			iv_header.setBackgroundResource(R.drawable.head_portrait);

		} else {

			btn_login.setVisibility(View.INVISIBLE);
			btn_regist.setVisibility(View.INVISIBLE);

			iv_header.setBackgroundResource(R.drawable.redchild);

		}

	}

	@Override
	public View initView() {

		initComm();

		if (MyUtils.userId == 0) {// 还未登录

			return first();

		} else {// 已经登录

			return second();
		}

	}

	private void initComm() {

		view = View.inflate(getActivity(), R.layout.more_fragment, null);

		btn_login = (TextView) view.findViewById(R.id.btn_login);
		btn_regist = (TextView) view.findViewById(R.id.btn_regist);
		iv_header = (ImageView) view.findViewById(R.id.iv_header);
		btn_call = (TextView) view.findViewById(R.id.btn_call);

		btn_regist.setOnClickListener(this);
		btn_login.setOnClickListener(this);

		RelativeLayout rl_record = (RelativeLayout) view
				.findViewById(R.id.rl_record);
		RelativeLayout rl_helper = (RelativeLayout) view
				.findViewById(R.id.rl_helper);
		RelativeLayout rl_fankui = (RelativeLayout) view
				.findViewById(R.id.rl_fankui);
		RelativeLayout rl_about = (RelativeLayout) view
				.findViewById(R.id.rl_about);

		rl_record.setOnClickListener(this);
		rl_helper.setOnClickListener(this);
		rl_about.setOnClickListener(this);
		rl_fankui.setOnClickListener(this);

		btn_call.setOnClickListener(this);

		iv_header.setOnClickListener(this);

	}

	public View first() {

		btn_login.setVisibility(View.VISIBLE);
		btn_regist.setVisibility(View.VISIBLE);

		iv_header.setBackgroundResource(R.drawable.head_portrait);

		return view;

	}

	private View second() {

		btn_login.setVisibility(View.GONE);
		btn_regist.setVisibility(View.GONE);

		iv_header.setBackgroundResource(R.drawable.redchild);

		return view;

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 101) {

			if (MyUtils.userId == 0) {

				btn_login.setVisibility(View.VISIBLE);
				btn_regist.setVisibility(View.VISIBLE);

				iv_header.setBackgroundResource(R.drawable.head_portrait);

			} else {

				btn_login.setVisibility(View.INVISIBLE);
				btn_regist.setVisibility(View.INVISIBLE);

				iv_header.setBackgroundResource(R.drawable.redchild);

			}

		}
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_login:

			Intent intent = new Intent(getActivity(), Login2Activity.class);
			startActivityForResult(intent, 101);

			break;
		case R.id.btn_regist:

			Intent intent1 = new Intent(getActivity(), RegistActivity.class);
			startActivity(intent1);

			break;
		case R.id.iv_header:

			if (MyUtils.userId == 0) {
				Toast.makeText(mContext, "请登录", Toast.LENGTH_SHORT).show();
				return;
			}

			Intent intent2 = new Intent(getActivity(), UserCenterActivity.class);
			startActivity(intent2);

			break;
		case R.id.rl_record:


			Intent browseDataIntent = new Intent(getActivity(),
					ActivityBrowseData.class);
			startActivity(browseDataIntent);

			

			break;
		case R.id.rl_helper:

			Intent intent3 = new Intent(getActivity(), HelpActivity.class);
			startActivity(intent3);
			break;
		case R.id.rl_fankui:

			Intent intent5 = new Intent(getActivity(), FeedbackActivity.class);
			startActivity(intent5);

			break;
		case R.id.rl_about:

			Intent intent4 = new Intent(getActivity(), AboutActivity.class);
			startActivity(intent4);
			break;
		case R.id.btn_call:

			call(v);
			break;

		}
	}

	public void call(View view) {

		String number = btn_call.getText().toString();
		String[] split = number.split(":");

		number = split[1];
		number = number.replace("-", "");
		// 打电话
		// 意图 想法 企图
		Intent intent = new Intent();
		// 你想干啥
		// 我想打电话
		intent.setAction(Intent.ACTION_CALL);
		// 我要打给谁
		intent.setData(Uri.parse("tel:" + number));

		// 让意图按照我的要求干活去吧
		startActivity(intent);

	}

}
