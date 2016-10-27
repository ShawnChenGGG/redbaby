/**
 * @date 2016-4-22 下午6:54:05
 */
package zz.itcast.redbody7.view;

import zz.itcast.redbody7.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 自定义标题控件
 * @author ma
 * 
 * @date 2016-4-22 下午6:54:05
 */
public class TittleBarView extends RelativeLayout {

	

	private ImageView mBack;
	private TextView mTittle;
	private TextView mOper;

	public TittleBarView(Context context) {
		super(context);
		initView();
	}

	public TittleBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}
	
	public TittleBarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	/**
	 *初始化View
	 * @date 2016-4-22 下午6:54:53
	 */
	public void initView() {
		
		View view = View.inflate(getContext(), R.layout.view_tittle_bar, null);
		mBack = (ImageView) view.findViewById(R.id.iv_back);
		mTittle = (TextView) view.findViewById(R.id.tv_tittle);
		mOper = (TextView) view.findViewById(R.id.tv_oper);
		
		addView(view);
	}
	
	
	/**
	 * 点击返回按钮
	 * @date 2016-4-22 下午7:47:02
	 */
	public void setOnBackClick(OnClickListener listener){
		if(listener != null){
			mBack.setOnClickListener(listener);
		}
	}
	
	/**
	 * 点击操作按钮
	 * @date 2016-4-22 下午7:52:23
	 */
	public  void setOnOperClick(OnClickListener listener){
		if(listener != null){
			mOper.setOnClickListener(listener);
		}
	}
	
	/**
	 * 设置标题文字
	 * @date 2016-4-22 下午8:41:33
	 */
	public void setTittle(String tittle){
		mTittle.setText(tittle);
	}
	
	/**
	 * 设置操作的文字
	 * @date 2016-4-22 下午8:42:15
	 */
	public void setOperText(String text){
		mOper.setText(text);
	}
	
	/**
	 * 返回控件是否显示
	 * @date 2016-4-22 下午9:32:39
	 */
	public void setBackVisibility(boolean isShow){
		if(isShow){
			mBack.setVisibility(View.VISIBLE);
		}else{
			mBack.setVisibility(View.INVISIBLE);
		}
	}
	
	/**
	 * 操作控件是否显示 
	 * @param true 为显示 false 为隐藏
	 * @date 2016-4-22 下午9:36:18
	 */
	public void setOperVisibility(boolean isShow){
		if(isShow){
			mOper.setVisibility(View.VISIBLE);
		}else{
			mOper.setVisibility(View.INVISIBLE);
		}
	}
	
	/**
	 * 设置字体操作的字体大小
	 * @date 2016-4-22 下午9:38:07
	 */
	public void setTextSize(float size){
		mOper.setTextSize(size);
	}
	
}
