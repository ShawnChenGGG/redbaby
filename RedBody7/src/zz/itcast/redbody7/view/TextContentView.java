/**
 * @date 2016-4-22 下午5:33:00
 */
package zz.itcast.redbody7.view;

import zz.itcast.redbody7.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author ma
 * 
 * @date 2016-4-22 下午5:33:00
 */
public class TextContentView extends RelativeLayout {

	private TextView mDesc;
	private TextView mName;
	private TextView mNumber;
	private TextView mAddress;
	private ImageView mArrow;
	private View view;

	public TextContentView(Context context) {
		super(context);
		initView();
	}

	public TextContentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	/**
	 * @date 2016-4-22 下午5:33:33
	 */
	private void initView() {

		view = View.inflate(getContext(), R.layout.view_text_content, null);


		mDesc = (TextView) view.findViewById(R.id.tv_desc);
		mName = (TextView) view.findViewById(R.id.tv_name);
		mNumber = (TextView) view.findViewById(R.id.tv_number);
		mAddress = (TextView) view.findViewById(R.id.tv_address);
		mArrow = (ImageView) view.findViewById(R.id.iv_arrow);
		//添加到布局
		addView(view);
	}

	/**
	 * 是否显示联系人信息,包括,联系人姓名,联系电话,联系地址
	 * @param true 为显示,false为不显示
	 * @date 2016-4-23 上午11:45:53
	 */
	public void isShowConanctsInfo(boolean isShow){
		if(isShow){
			//为true时显示
			mName.setVisibility(View.VISIBLE);
			mNumber.setVisibility(View.VISIBLE);
			mAddress.setVisibility(View.VISIBLE);
		}else{
			//为false隐藏
			mName.setVisibility(View.GONE);
			mNumber.setVisibility(View.GONE);
			mAddress.setVisibility(View.GONE);
		}
	}
	/**
	 * 定义描述信息
	 * 
	 * @date 2016-4-22 下午6:24:54
	 */
	public void setDesc(String desc) {
		mDesc.setText(desc);
	}

	/**
	 * 设置收货人姓名
	 * 
	 * @date 2016-4-22 下午6:25:48
	 */
	public void setName(String name) {
		mName.setText(name);
	}

	/**
	 * 设置收货人联系电话
	 * 
	 * @date 2016-4-22 下午6:26:27
	 */
	public void setNumber(String number) {
		mNumber.setText(number);
	}

	/**
	 * 设置收获地址
	 * 
	 * @date 2016-4-22 下午6:27:02
	 */
	public void setAddress(String address) {
		mAddress.setText(address);
	}

	/**
	 * 箭头图标是否显示,true为显示,false为不显示
	 * 
	 * @date 2016-4-22 下午6:38:21
	 */
	public void setVisibility(boolean isShow) {
		if (isShow) {
			mArrow.setVisibility(View.VISIBLE);
		} else {
			mArrow.setVisibility(View.INVISIBLE);
		}
	}
	
	/**
	 * 设置image的图片
	 * @date 2016-4-22 下午9:39:55
	 */
	public void setImageResId(int resId){
		mArrow.setImageResource(resId);
	}
	
	/**
	 * 整个控件的点击事件
	 * @date 2016-4-22 下午9:41:39
	 */
	public void setViewOnClick(OnClickListener listener){
		if(listener != null){
			view.setOnClickListener(listener);
		}
	}
	/**
	 * 姓名显示
	 * @date 2016-4-24 下午7:16:11
	 */
	public void showName(){
		mName.setVisibility(View.VISIBLE);
	};
}

