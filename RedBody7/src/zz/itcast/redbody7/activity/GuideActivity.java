package zz.itcast.redbody7.activity;

import java.util.ArrayList;
import java.util.List;

import zz.itcast.redbody7.R;
import android.R.integer;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 引导界面
 * 
 * 介绍当前应用新功能
 * 
 * 点击按钮:跳转主界面
 * 
 */
public class GuideActivity extends BaseActivity {

	private List<Integer> imageResIds = new ArrayList<Integer>();
	private Button button;
	private MyPagerAdapter adapter;
	private ViewPager view_pager;
	private ImageView jian, jiana;

	@Override
	public void initRegister() {

	}

	@Override
	public void initView() {

		setContentView(R.layout.activity_guide);
		view_pager = (ViewPager) findViewById(R.id.view_pager);
		button = (Button) findViewById(R.id.button);

		jian = (ImageView) findViewById(R.id.jian);
		jiana = (ImageView) findViewById(R.id.jiana);
		imageResIds.add(R.drawable.guid_page_iv);
		imageResIds.add(R.drawable.guid_page_iv2);
		imageResIds.add(R.drawable.guid_page_iv3);
		adapter = new MyPagerAdapter();
		view_pager.setAdapter(adapter);
		// view_pager添加界面改变监听
		view_pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// 如果是最后一个界面,需要显示按钮
				if (position == imageResIds.size() - 1) {
					button.setVisibility(View.VISIBLE);
					Animation a = new AlphaAnimation(0.0f, 1.0f);
					a.setDuration(1500);
					a.setRepeatCount(0);
					a.setRepeatMode(Animation.REVERSE);
					button.startAnimation(a);
					jian.setVisibility(View.GONE);
					jiana.setVisibility(View.VISIBLE);
					button.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent = new Intent(GuideActivity.this,
									MainActivity.class);
							startActivity(intent);
							finish();
						}
					});
				} else {
					// 隐藏button
					button.setVisibility(View.GONE);
					jiana.setVisibility(View.GONE);
					jian.setVisibility(View.VISIBLE);
				}

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub

			}
		});

	}

	class MyPagerAdapter extends PagerAdapter {
		/**
		 * viewpager有几个界面
		 */
		@Override
		public int getCount() {
			return imageResIds.size();
		}

		/**
		 * 初始化item界面 1.根据当前的position,获取对应的view,并且将view添加到container 2.将view返回
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = new ImageView(GuideActivity.this);
			imageView.setBackgroundResource(imageResIds.get(position));
			container.addView(imageView);
			return imageView;
		}

		/**
		 * 销毁item界面 将object从container中移除
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// super.destroyItem(container, position, object);父类抛出异常
			container.removeView((View) object);
		}

		/**
		 * view和object是否是同样的一个对象
		 */
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

	@Override
	public void initData() {

	}

}
