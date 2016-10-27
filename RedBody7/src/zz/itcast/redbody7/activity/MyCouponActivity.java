package zz.itcast.redbody7.activity;

import java.util.ArrayList;
import java.util.List;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.view.TittleBarView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 我的优惠券
 * @author zh
 *
 */
public class MyCouponActivity extends BaseActivity {


	private ListView lv;
	private TittleBarView coupon_title;

	@Override
	public void initView() {
		setContentView(R.layout.activity_my_coupon);
		lv = (ListView) findViewById(R.id.coupon_lv);
		coupon_title = (TittleBarView) findViewById(R.id.coupon_title);
		
	}


	@Override
	public void initData() {

		coupon_title.setTittle("我的优惠券");
		coupon_title.setOperVisibility(false);
		
		coupon_title.setOnBackClick(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		List<String> objects = new ArrayList<String>();
		
		objects.add("九月份惊喜50元礼券");
		objects.add("国庆节80元礼券");
		objects.add("圣诞节大放送80元礼券");
		
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, 
				android.R.id.text1, objects));
		
		
		
	}
	

	@Override
	public void initRegister() {

	}
}
