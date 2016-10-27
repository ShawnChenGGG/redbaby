/**
 * @date 2016-4-25 上午8:55:23
 */
package zz.itcast.redbody7.view;

import zz.itcast.redbody7.R;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * @author ma
 * 
 * @date 2016-4-25 上午8:55:23
 */
public class LoadingView extends RelativeLayout {

	public LoadingView(Context context) {
		super(context);
		initView();
	}

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	/**
	 * @date 2016-4-25 上午9:57:29
	 */
	private void initView() {
		View view = View.inflate(getContext(), R.layout.public_loading, null);
		ImageView mLoading = (ImageView) view.findViewById(R.id.iv_loding);
		AnimationDrawable animationDrawable = (AnimationDrawable) mLoading
				.getBackground();
		animationDrawable.start();
		addView(view);
	}

}
