/**
 * @date 2016-4-25 下午2:58:27
 */
package zz.itcast.redbody7.view;

import zz.itcast.redbody7.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author ma
 * 
 * @date 2016-4-25 下午2:58:27
 */
public class OrderListInfoView extends RelativeLayout {

	private TextView mOrderNum;
	private TextView mOrderPrice;
	private TextView mOrderTime;
	private RelativeLayout mOrderView;

	public OrderListInfoView(Context context) {
		super(context);
		initView();
	}

	public OrderListInfoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	/**
	 * @date 2016-4-25 下午2:59:51
	 */
	private void initView() {
		View view = View.inflate(getContext(), R.layout.view_order_list, null);
		
		mOrderView = (RelativeLayout) view.findViewById(R.id.rl_my_order);
		
		mOrderNum = (TextView) view.findViewById(R.id.tv_order_num);
		mOrderPrice = (TextView) view.findViewById(R.id.tv_order_price);
		mOrderTime = (TextView) view.findViewById(R.id.tv_order_time);

		for(int i = 0;i<getChildCount();i++){
			if(listener != null){
				listener.OnItemClickListener(i);
			}
		}
		
		addView(view);
	}

	/**
	 * 对整个布局的点击事件
	 * @date 2016-4-25 下午4:35:52
	 */
	public void setOnViewClick(OnClickListener listener){
		if(listener != null){
			mOrderView.setOnClickListener(listener);
		}
	}
	
	/**
	 * 设置订单编号
	 * 
	 * @date 2016-4-25 下午3:09:05
	 */
	public void setOrderNum(String orderNum) {
		mOrderNum.setText(orderNum);
	}

	/**
	 * 设置订单金额
	 * 
	 * @date 2016-4-25 下午3:10:51
	 */
	public void setOrderPrice(String orderPrice) {
		mOrderPrice.setText(orderPrice);
	}

	/**
	 * 设置订单时间
	 * @date 2016-4-25 下午3:11:27
	 */
	public void setOrderTime(String orderTime) {
		mOrderTime.setText(orderTime);
	}
	
	
	
	/**
	 * 获得商品的id
	 * @date 2016-4-25 下午4:37:18
	 */
	public String getOrderNum(){
		return mOrderNum.getText().toString();
	}
	
	
	private OnItemClickListener listener;
	public void setOnItemClickListener(OnItemClickListener listener){
		this.listener = listener;
	}
	
	public interface OnItemClickListener{
		void OnItemClickListener(int position);
	}
}
