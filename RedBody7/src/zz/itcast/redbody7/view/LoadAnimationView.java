package zz.itcast.redbody7.view;

import zz.itcast.redbody7.R;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
/**
 * 
 * @author ma
 *
 * @date 2016-4-26 下午12:54:46
 */
public class LoadAnimationView extends RelativeLayout {

	public LoadAnimationView(Context context) {
		super(context);
		initView();
	}

	public LoadAnimationView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	/**
	 * 
	 */
	private void initView() {
		View view = View.inflate(getContext(), R.layout.loading_animation, null);
		ImageView mLoading = (ImageView) view.findViewById(R.id.iv_loding);
		ImageView mLoadingText = (ImageView) view.findViewById(R.id.iv_loding_text);
		
		AnimationDrawable animationDrawable = (AnimationDrawable) mLoading.getBackground();
		animationDrawable.start();
		
		AnimationDrawable animationDrawable2 = (AnimationDrawable) mLoadingText.getBackground();
		animationDrawable2.start();
		
		addView(view);
	}

}
