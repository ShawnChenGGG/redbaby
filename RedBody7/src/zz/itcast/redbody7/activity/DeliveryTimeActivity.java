/**
 * @date 2016-4-24 下午5:14:27
 */
package zz.itcast.redbody7.activity;

import java.util.List;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.bean.CheckOutBean.DeliveryListBean;
import zz.itcast.redbody7.bean.CheckOutBean.PaymentListBean;
import zz.itcast.redbody7.view.TittleBarView;

/**
 * @author ma
 * 
 * @date 2016-4-24 下午5:14:27
 */
public class DeliveryTimeActivity extends BaseActivity {

	private String[] types;
	private String[] dess;
	private List<DeliveryListBean> deliverytype;
	private ListView mListView;

	@Override
	public void initRegister() {
	}

	@Override
	public void initView() {
		setContentView(R.layout.activity_delivery_time);
		initTittle();
		mListView = (ListView) findViewById(R.id.listview);
	}

	@Override
	public void initData() {
		deliverytype = MyConstants.deliveryType;
		types = new String[deliverytype.size()];
		dess = new String[deliverytype.size()];

		for (int i = 0; i < deliverytype.size(); i++) {
			DeliveryListBean datas = deliverytype.get(i);
			types[i] = datas.type;
			dess[i] = datas.des;
		}
		
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String type = types[position];
				String des = dess[position];
				Intent data = new Intent();
				data.putExtra("type", type);
				data.putExtra("des", des);
				setResult(3, data);
				finish();
			}
		});
		mListView.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, dess));
	}

	/**
	 * @date 2016-4-24 下午6:44:05
	 */
	private void initTittle() {
		TittleBarView mTbv = (TittleBarView) findViewById(R.id.tbv);
		mTbv.setTittle("送货时间");
		mTbv.setOperVisibility(false);
		mTbv.setOnBackClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
