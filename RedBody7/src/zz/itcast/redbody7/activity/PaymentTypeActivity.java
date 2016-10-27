/**
 * @date 2016-4-24 上午11:36:14
 */
package zz.itcast.redbody7.activity;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.bitmap.PauseOnScrollListener;

import zz.itcast.redbody7.R;
import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.bean.CheckOutBean.PaymentListBean;
import zz.itcast.redbody7.view.TittleBarView;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author ma
 *
 * @date 2016-4-24 上午11:36:14
 */
public class PaymentTypeActivity extends Activity{
	
	private String[] types;
	private String[] dess;
	private List<PaymentListBean> paymentype;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TODO
		setContentView(R.layout.activity_aymenttype);
		ListView mListView = (ListView) findViewById(R.id.listview);
		initTittle();
		paymentype = MyConstants.paymenType;
		
		types = new String[paymentype.size()];
		dess = new String[paymentype.size()];
		
		for (int i = 0; i < paymentype.size(); i++) {
			PaymentListBean datas = paymentype.get(i);
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
				setResult(2, data);
				finish();
				
			}
		});
		mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dess));
	}

	/**
	 * @date 2016-4-24 下午6:22:28
	 */
	private void initTittle() {
		TittleBarView mTbv = (TittleBarView) findViewById(R.id.tbv);
		mTbv.setTittle("支付方式");
		mTbv.setOperVisibility(false);
		mTbv.setOnBackClick(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
