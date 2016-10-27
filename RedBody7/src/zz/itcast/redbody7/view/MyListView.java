package zz.itcast.redbody7.view;

import zz.itcast.redbody7.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
/**
 * 处理下拉刷新和加载更多的listView
 * @author wx 
 *
 */
public class MyListView extends ListView {
	private final class ScrollListener implements
			OnScrollListener {
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (getLastVisiblePosition()==getAdapter().getCount()-1&&scrollState==OnScrollListener.SCROLL_STATE_IDLE) {
				// TODO 当显示的是最后一个条目的时候,需要加载更多
				//加载完成后隐藏
				if (!isLoading) {
					isLoading=true;
					if (listener!=null) {
						listener.loading();
					}
				}
				
				//refreshFinish();
				//因为加载数据是联网操作;异步的,只有在数据加载完成后,执行刷新完成的方法;
				
			}
			
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
		
			
		}
	}

	//头布局的根布局
	@ViewInject(R.id.ll_refresh_header_root)
	private LinearLayout ll_header_root;
	//刷新布局的跟布局
	@ViewInject(R.id.ll_refresh_header_view)
	private LinearLayout ll_header_view;
	
	@ViewInject(R.id.pb_refresh_header)
	private ProgressBar pb;

	@ViewInject(R.id.iv_refresh_header_arrow)
	private ImageView iv;
	
	@ViewInject(R.id.tv_refresh_header_text)
	private TextView tv_state;
	
	
	
	private Animation down_to_up,up_to_down;
	private int downY;
	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public MyListView(Context context) {
		super(context);
		initView();
	}
	
	/**
	 * 定义刷新的状态;
	 * complete_state 完成刷新
	 * refresh_state 释放刷新
	 * down_state   下拉刷新
	 * current_state  当前状态
	 */
	public static final	int complete_state=0;
	public static final int refresh_state=1;
	public static final int down_state=2;
	private int current_state=down_state;
	private int measuredHeight;
	private int footerHeight;
	//不用View pager
	//private View rollView;
	
	
	public void initView(){
		View view=View.inflate(getContext(),  R.layout.refresh_header, null);
		ViewUtils.inject(this, view);
		addHeaderView(view);
		
		//隐藏刷新头部
		//通知去测量
		ll_header_view.measure(0, 0);
		measuredHeight = ll_header_view.getMeasuredHeight();
		//设置负的帕丁值,隐藏起来
		ll_header_view.setPadding(0, -measuredHeight, 0, 0);
		
		 footerView = View.inflate(getContext(), R.layout.refresh_footer, null);
		 footerView.measure(0, 0);
		 footerHeight = footerView.getMeasuredHeight();
		 addFooterView(footerView);
		 
		 setOnScrollListener(new ScrollListener());
		 
		initAnimation();
		
	}
	
	public void addRollPhoto(View roll){
		//this.rollView=roll;
		ll_header_root.addView(roll);
	}
	
	private void initAnimation(){
		down_to_up=new RotateAnimation
	(0	, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		down_to_up.setDuration(400);
		down_to_up.setFillAfter(true);
		
		up_to_down=new RotateAnimation
				(-180, -360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		up_to_down.setDuration(400);
		up_to_down.setFillAfter(true);
		
		
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		//获得listView 的Y坐标和rollView 的Y坐标
//		int[] location=new int[2];
//		this.getLocationInWindow(location);
//		listViewY = location[1];
//		Log.i("listViewY", listViewY+"");
//		rollView.getLocationInWindow(location);
//		rollY = location[1];
//		Log.i("rollY", rollY+"");
		return super.dispatchTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				
				break;
				
			case MotionEvent.ACTION_MOVE:
				
				if (rollY>=listViewY) {
					if (downY==0) {
						downY = (int) ev.getY();
						break;
					}
					int moveY=(int) ev.getY();
					if (moveY>downY) {
						int disY=moveY-downY;
						Log.i("disY", disY+"");
						int padingtop=-measuredHeight+disY;
						ll_header_view.setPadding(0, padingtop, 0, 0);
						//下拉刷新
						if (current_state==down_state&&padingtop>=0) {
							current_state=refresh_state;
							switchState(current_state);
						}
						
						if (current_state==refresh_state) {
							if (padingtop<0) {
								current_state=down_state;
								switchState(current_state);
							}
							return true;
						}
					}
				}
					
				
				
				break;
				
			case MotionEvent.ACTION_UP:
				downY=0;
				if (current_state==down_state) {
					ll_header_view.setPadding(0, -measuredHeight, 0	, 0);
				}
				if (current_state==refresh_state) {
					current_state=complete_state;
					switchState(current_state);
				}
				break;

			default:
				break;
			}
		
		return super.onTouchEvent(ev);
	}
	
	private void switchState(int state){
		switch (state) {
		case down_state:
			iv.startAnimation(up_to_down);
			tv_state.setText("下拉刷新");
			break;
		case refresh_state:
			iv.startAnimation(down_to_up);
			tv_state.setText("释放刷新");
			break;
			
		case complete_state:
			iv.clearAnimation();
			iv.setVisibility(View.GONE);
			pb.setVisibility(View.VISIBLE);
			tv_state.setText("正在刷新");
			ll_header_view.setPadding(0, 0, 0, 0);
			
			//handler.sendEmptyMessageDelayed(0, 2000);
			//正在刷新时候,获取网络数据
			if (listener!=null) {
				listener.refresh();
			}
			
			break;
		default:
			break;
		}
	}

//	private Handler handler=new Handler(){
//		public void handleMessage(android.os.Message msg) {
//			if (msg.what==0) {
//				isLoading=false;
//				footerView.setPadding(0, 0, 0, 0);
//			}
//		}
//	};
	private int listViewY;
	private int rollY;
	private OnRefreshStateChangeListener listener;
	
	public boolean isLoading=false;
	private View footerView;
	
	public void refreshFinish(){
		//完成之后  恢复初始
		if (isLoading) {
			//上拉加载更多
			footerView.setPadding(0, -footerHeight, 0, 0);
			isLoading=false;
			footerView.setPadding(0, 0, 0, 0);
			//handler.sendEmptyMessageDelayed(0, 500);
			
		}else {
			//下拉刷新
			pb.setVisibility(View.INVISIBLE);
			iv.setVisibility(View.VISIBLE);
			tv_state.setText("下拉刷新");
			ll_header_view.setPadding(0, -measuredHeight, 0, 0);
			current_state=down_state;
	
		}
	}
	
	public void setOnRefreshStateChangeListener(OnRefreshStateChangeListener Listener){
		this.listener=Listener;
	}
	
	//添加刷新状态的监听
	public interface OnRefreshStateChangeListener{

		public void loading();

		public void refresh();
		
		
				
		
	}
	
	/**
	 * 隐藏脚布局
	 */
	public void inVisibleFooter(){
		footerView.setPadding(0, -footerHeight, 0, 0);
		}

}
