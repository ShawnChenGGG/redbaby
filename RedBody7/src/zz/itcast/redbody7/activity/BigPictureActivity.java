package zz.itcast.redbody7.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import zz.itcast.redbody7.R;
import zz.itcast.redbody7.utils.MyUtils;
import zz.itcast.redbody7.view.TittleBarView;

/**
 * 大图查看
 * @author zh
 *
 */
public class BigPictureActivity extends BaseActivity {

	private TittleBarView big_title;
	private ViewPager big_Vp;
	private List<Integer> iList;
	private LinearLayout ll_ponit;
	private View red_point;
	
	@Override
	public void initView() {
		setContentView(R.layout.activity_big_picture);
		big_title = (TittleBarView) findViewById(R.id.big_picture_title);
		
		big_Vp = (ViewPager) findViewById(R.id.big_picture_vp);
		ll_ponit = (LinearLayout) findViewById(R.id.big_gray_point_ll);
		red_point = findViewById(R.id.big_red_point);
		TextView tv = (TextView) findViewById(R.id.yuan_jia);
		
		tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

	}

	@Override
	public void initData() {
		big_title.setTittle("大图浏览");
		big_title.setOperVisibility(false);
		
		big_title.setOnBackClick(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		iList = new ArrayList<Integer>();
		for (int i = 0; i < 5; i++) {
			
			iList.add(R.drawable.big_picture);
			
			View view = new View(mContext);

			view.setBackgroundResource(R.drawable.gary_point_ll);

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					MyUtils.dip2px(mContext, 5),
					MyUtils.dip2px(mContext, 5));

			if (i != 0) {
				// 设置边距
				params.leftMargin = MyUtils.dip2px(mContext, 5);
			}

			view.setLayoutParams(params);

			ll_ponit.addView(view);
		}
		
		ll_ponit.getViewTreeObserver().addOnGlobalLayoutListener(
				globalListener);
		
		big_Vp.setAdapter(new MyBigPictureAdapter());
		
		
		big_Vp.setOnPageChangeListener(onPageChangeListener);
	}


	@Override
	public void initRegister() {
		
	}
	
	
	class MyBigPictureAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return iList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView iv = new ImageView(mContext);
			iv.setImageResource(iList.get(position));
			iv.setScaleType(ScaleType.CENTER_CROP);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			iv.setLayoutParams(params);
			container.addView(iv);
			return iv;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}
	
	
	private int grayPointMargin;
	/**
	 * gray_point_ll线性布局监听
	 */
	OnGlobalLayoutListener globalListener = new OnGlobalLayoutListener() {


		@Override
		public void onGlobalLayout() {
			ll_ponit.getViewTreeObserver().removeGlobalOnLayoutListener(this);

			grayPointMargin = ll_ponit.getChildAt(1).getLeft()
					- ll_ponit.getChildAt(0).getLeft();
		}
	};
	
	
	/**
	 * 页面改变监听
	 */
	OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {

		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			// 得到偏移量
			int margin = (int) (grayPointMargin * positionOffset + position
					* grayPointMargin);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) red_point
					.getLayoutParams();
			params.leftMargin = margin;
			red_point.setLayoutParams(params);

		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}
	};
}
