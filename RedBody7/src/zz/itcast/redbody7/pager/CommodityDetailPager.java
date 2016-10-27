package zz.itcast.redbody7.pager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
/**
 * 商品详情页,在这里作废,由组长完成
 * @author wx
 *
 */
public class CommodityDetailPager extends BasePager {
	
	/**
	 * 当前商品的ID
	 */
	private int id;

	public CommodityDetailPager(Context mContext, int id) {
		super(mContext);
		this.id=id;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initRegister() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public View initView() {
		TextView tv=new TextView(mContext);
		tv.setText("商品详情页面");
		return tv;
	}

}
