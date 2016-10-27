/**
 * @date 2016-4-23 下午4:22:16
 */
package zz.itcast.redbody7.view;

import zz.itcast.redbody7.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author ma
 * 
 * @date 2016-4-23 下午4:22:16
 */
public class GoodsInfoListView extends RelativeLayout {

	private ImageView mCheckBox;
	public ImageView mGoodsPic;
	private TextView mGoodsDesc;
	private TextView mGoodsColor;
	private TextView mGoodsSize;
	private TextView mGoodsPrice;
	private TextView mGoodsCount;
	private TextView mGoodsTotal;
	private EditText et_goods_num;
	
	public GoodsInfoListView(Context context) {
		super(context);
		initView();
	}

	public GoodsInfoListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	/**
	 * @date 2016-4-23 下午4:35:06
	 */
	private void initView() {
		View view = View.inflate(getContext(), R.layout.view_goods_info, null);
		mCheckBox = (ImageView) view.findViewById(R.id.iv_checkbox);
		mGoodsPic = (ImageView) view.findViewById(R.id.iv_goods_pic);
		mGoodsDesc = (TextView) view.findViewById(R.id.tv_goods_desc);
		mGoodsColor = (TextView) view.findViewById(R.id.tv_goods_color);
		mGoodsSize = (TextView) view.findViewById(R.id.tv_goods_size);
		mGoodsPrice = (TextView) view.findViewById(R.id.tv_goods_price);
		mGoodsCount = (TextView) view.findViewById(R.id.tv_goods_count);
		mGoodsTotal = (TextView) view.findViewById(R.id.tv_goods_total);
		et_goods_num = (EditText) view.findViewById(R.id.et_goods_num);

		addView(view);
	}
	
	/**
	 * 设置商品的单价
	 * @date 2016-4-25 下午12:42:21
	 */
	public void setGoodsPrice(String price){
		mGoodsPrice.setText(price);
	}

	/**
	 * 图片是否被勾选 true为勾选,false 为不勾选
	 * @date 2016-4-23 下午5:28:44
	 */
	public void setSelected(boolean isSelected) {
		mCheckBox.setSelected(isSelected);
	}
	
	/**
	 * 设置商品的图片
	 * @date 2016-4-23 下午5:32:19
	 */
	public void setGoodsPic(int resId){
		mGoodsPic.setImageResource(resId);
	}
	
	/**
	 * 设置商品的描述信息
	 * @date 2016-4-23 下午5:33:03
	 */
	public void setGoodsDesc(String desc){
		mGoodsDesc.setText(desc);
	}
	
	/**
	 * 设置商品属性颜色
	 * @date 2016-4-23 下午5:34:23
	 */
	public void setGoodsColor(int color){
		mGoodsColor.setTextColor(color);
	}
	
	/**
	 * 设置商品属性颜色
	 * @date 2016-4-25 下午12:37:28
	 */
	public void setGoodsAttrColor(String attrColor){
		mGoodsColor.setText(attrColor);
		
	}
	/**
	 * 设置商品的价格
	 * @date 2016-4-23 下午5:35:07
	 */
	public void setGoodsSize(String price){
		mGoodsSize.setText(price);
	}
	
	/**
	 * 设置商品的数量
	 * @date 2016-4-23 下午5:35:47
	 */
	public void setGoodsCount(String count){
//		mGoodsCount.setText(count);
		et_goods_num.setText(count);
		
	}
	 
	/**
	 * 设置商品小计价格
	 * @date 2016-4-23 下午5:36:45
	 */
	public void setGoodsTotalPrice(String price){
		mGoodsTotal.setText(price);
	}
	
	/**
	 * 设置是否显示mCheckBox
	 * @date 2016-4-24 下午9:48:42
	 */
	public void setIsShowmCheckBox(boolean isShow){
		if(isShow){
			mCheckBox.setVisibility(View.VISIBLE);
		}else{
			mCheckBox.setVisibility(View.GONE);
		}
	}
}
