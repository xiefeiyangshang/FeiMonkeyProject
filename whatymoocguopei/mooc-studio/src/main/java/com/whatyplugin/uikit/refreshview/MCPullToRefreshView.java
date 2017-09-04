package com.whatyplugin.uikit.refreshview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.imooc.ui.view.MCGuidanceView;

public class MCPullToRefreshView extends LinearLayout {

	public interface OnFooterRefreshListener {
		void onFooterRefresh(MCPullToRefreshView arg1);
	}

	public interface OnHeaderRefreshListener {
		void onHeaderRefresh(MCPullToRefreshView arg1);
	}

	public interface OnTouchViewListener {
		void onTouchView(MCPullToRefreshView arg1);
	}

	private static final int DEFAULT_REFRESH = -1;
	private static final int PULL_DOWN_STATE = 1;
	private static final int PULL_TO_REFRESH = 2;
	private static final int PULL_UP_STATE = 0;
	private static final int REFRESHING = 4;
	private static final int RELEASE_TO_REFRESH = 3;
	private static final String TAG = "PullToRefreshView";
	private boolean allowFooterPull;
	private boolean allowHeaderPull;
	private boolean allowSwiped;
	// private AnimationDrawable animationDrawable;
	private boolean hasVerticalScrollbars;
	private boolean isSupportLoadMore;
	private AdapterView mAdapterView;
	private RotateAnimation mFlipAnimation;
	private LinearLayout mFooterLoadingView;
	private ProgressBar mFooterProgressBar;
	private int mFooterState;
	private TextView mFooterTextView;
	private View mFooterView;
	private int mFooterViewHeight;
	private ImageView mHeaderImageView;
	private ProgressBar mHeaderProgressBar;
	private TextView mHeaderTextView;
	private int mHeaderState;
	private View mHeaderView;
	private int mHeaderViewHeight;
	private LayoutInflater mInflater;
	private int mLastMotionY;
	private int mLastTotalItemCount;
	private boolean mLock;
	private OnFooterRefreshListener mOnFooterRefreshListener;
	private OnHeaderRefreshListener mOnHeaderRefreshListener;
	private AdapterView.OnItemClickListener mOnItemClickListener;
	private AdapterView.OnItemLongClickListener mOnItemLongClickListener;
	private AbsListView.OnScrollListener mOnScollListener;
	private OnTouchViewListener mOnTouchViewListener;
	private int mPullState;
	private RotateAnimation mReverseFlipAnimation;
	private ScrollView mScrollView;
	private int mSwipeOffsetLeft;
	private int rowCount;
	private onScollListenter monScollListenter;
	private Context mContext;
	private MCGuidanceView mGuidanceView;

	public MCPullToRefreshView(Context context) {
		super(context);
		this.mContext = context;
		this.mLastTotalItemCount = 0;
		this.mHeaderState = -1;
		this.mFooterState = -1;
		this.mSwipeOffsetLeft = 0;
		this.rowCount = 1;
		this.allowHeaderPull = false;
		this.allowFooterPull = false;
		this.allowSwiped = false;
		this.isSupportLoadMore = false;
		this.hasVerticalScrollbars = false;
		this.mOnScollListener = new AbsListView.OnScrollListener() {
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (monScollListenter != null)
					monScollListenter.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
				if (!MCPullToRefreshView.this.isRefresh() && (MCPullToRefreshView.this.isSupportLoadMore)
						&& firstVisibleItem + visibleItemCount == totalItemCount && MCPullToRefreshView.this.mFooterView != null
						&& MCPullToRefreshView.this.mFooterView.getVisibility() == 0) {
					View childView = view.getChildAt(0);
					if (childView != null) {
						int v1 = childView.getHeight() * totalItemCount;
						System.out.println(MCPullToRefreshView.this.mLastTotalItemCount+"   "+totalItemCount+"    "+v1+"   "+view.getMeasuredHeight());
						if (MCPullToRefreshView.this.mLastTotalItemCount != totalItemCount && v1 > view.getMeasuredHeight()) {
							MCPullToRefreshView.this.footerRefreshing(true);
							MCPullToRefreshView.this.mLastTotalItemCount = totalItemCount;
						}
					}
				}
			}

			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (monScollListenter != null)
					monScollListenter.onScrollStateChanged(view, scrollState);
			}
		};
		this.init();
	}

	public static abstract interface onScollListenter {
		public abstract void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);

		public abstract void onScrollStateChanged(AbsListView view, int scrollState);

	}

	// listview 滚动事件回调
	public void setOnScollListenter(onScollListenter onScollListenter) {
		this.monScollListenter = onScollListenter;
	}

	public MCPullToRefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		this.mLastTotalItemCount = 0;
		this.mHeaderState = -1;
		this.mFooterState = -1;
		this.mSwipeOffsetLeft = 0;
		this.rowCount = 1;
		this.allowHeaderPull = false;
		this.allowFooterPull = false;
		this.allowSwiped = false;
		this.isSupportLoadMore = false;
		this.hasVerticalScrollbars = false;
		this.mOnScollListener = new AbsListView.OnScrollListener() {
			int last = 0;

			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (monScollListenter != null)
					monScollListenter.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
				if (!MCPullToRefreshView.this.isRefresh() && (MCPullToRefreshView.this.isSupportLoadMore)
						&& firstVisibleItem + visibleItemCount == totalItemCount && MCPullToRefreshView.this.mFooterView != null
						&& MCPullToRefreshView.this.mFooterView.getVisibility() == 0) {
					View childView = view.getChildAt(0);
					if (childView != null) {
						int v1 = childView.getHeight() * totalItemCount;
						if (MCPullToRefreshView.this.mLastTotalItemCount != totalItemCount && v1 > view.getMeasuredHeight()) {
							if (firstVisibleItem >= last&&allowFooterPull) {// 从上往下滑动 的时候才会加载更多
								MCPullToRefreshView.this.footerRefreshing(true);
								MCPullToRefreshView.this.mLastTotalItemCount = totalItemCount;
							}
						}
					}
				}

				last = firstVisibleItem;
			}

			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (monScollListenter != null)
					monScollListenter.onScrollStateChanged(view, scrollState);
			}
		};
		TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.com_mooc_uikit_refreshview_MCPullToRefreshView);
		this.rowCount = typeArray.getInteger(R.styleable.com_mooc_uikit_refreshview_MCPullToRefreshView_rowCount, 1);
		this.allowHeaderPull = typeArray.getBoolean(R.styleable.com_mooc_uikit_refreshview_MCPullToRefreshView_allowHeaderPull, true);
		this.allowFooterPull = typeArray.getBoolean(R.styleable.com_mooc_uikit_refreshview_MCPullToRefreshView_allowFooterPull, true);
		this.allowSwiped = typeArray.getBoolean(R.styleable.com_mooc_uikit_refreshview_MCPullToRefreshView_allowSwipe, false);
		this.mSwipeOffsetLeft = typeArray.getDimensionPixelSize(R.styleable.com_mooc_uikit_refreshview_MCPullToRefreshView_swipelistOffsetLeft, 0);
		this.isSupportLoadMore = typeArray.getBoolean(R.styleable.com_mooc_uikit_refreshview_MCPullToRefreshView_supportLoadMore, true);
		this.hasVerticalScrollbars = typeArray.getBoolean(R.styleable.com_mooc_uikit_refreshview_MCPullToRefreshView_hasVerticalScrollbars, false);
		this.init();
	}

	public void addAdapterHeaderView(View v) {
		if (this.rowCount == 1) {
			((ListView) this.mAdapterView).addHeaderView(v);
		}
	}

	/**
	 * 返回当前顶部可见Items
	 * 
	 * @return
	 */
	public int getFirstVisiblePosition() {
		return ((ListView) this.mAdapterView).getFirstVisiblePosition();
	}

	private void addFooterView() {
		this.mFooterView = this.mInflater.inflate(R.layout.refresh_footer, ((ViewGroup) this), false);
		this.mFooterLoadingView = (LinearLayout) this.mFooterView.findViewById(R.id.loading_data);
		this.mFooterTextView = (TextView) this.mFooterView.findViewById(R.id.pull_to_load_text);
		this.mFooterProgressBar = (ProgressBar) this.mFooterView.findViewById(R.id.pull_to_load_progress);
		this.measureView(this.mFooterView);
		this.mFooterViewHeight = this.mFooterView.getMeasuredHeight();
		this.addView(this.mFooterView, new LinearLayout.LayoutParams(-1, this.mFooterViewHeight));
	}

	private void addHeaderView() {
		// this.mHeaderView = this.mInflater.inflate(R.layout.refresh_header,
		// ((ViewGroup)this), false);
		this.mHeaderView = this.mInflater.inflate(R.layout.my_refresh_header, ((ViewGroup) this), false);
		this.mHeaderImageView = (ImageView) this.mHeaderView.findViewById(R.id.head_image);
		this.mHeaderProgressBar = (ProgressBar) this.mHeaderView.findViewById(R.id.pb_refresh);
		mHeaderTextView = (TextView) this.mHeaderView.findViewById(R.id.tv_refresh);
		this.measureView(this.mHeaderView);
		this.mHeaderViewHeight = this.mHeaderView.getMeasuredHeight();
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, this.mHeaderViewHeight);
		layoutParams.topMargin = -this.mHeaderViewHeight;
		this.addView(this.mHeaderView, ((ViewGroup.LayoutParams) layoutParams));
	}

	private int changingHeaderViewTopMargin(int deltaY) {
		int result;
		ViewGroup.LayoutParams params = this.mHeaderView.getLayoutParams();
		float margin = (((float) ((LinearLayout.LayoutParams) params).topMargin)) + (((float) deltaY)) * 0.3f;
		if (deltaY <= 0 || this.mPullState != 0 || Math.abs(((LinearLayout.LayoutParams) params).topMargin) > this.mHeaderViewHeight) {
			if (deltaY < 0 && this.mPullState == 1) {
				Math.abs(((LinearLayout.LayoutParams) params).topMargin);
			}

			Log.i("PullToRefreshView", "newTopMargin:" + margin + "  mHeaderViewHeight:" + this.mHeaderViewHeight);
			((LinearLayout.LayoutParams) params).topMargin = ((int) margin);
			this.mHeaderView.setLayoutParams(params);
			this.invalidate();
			result = ((LinearLayout.LayoutParams) params).topMargin;
		} else {
			result = ((LinearLayout.LayoutParams) params).topMargin;
		}

		return result;
	}

	private void footerPrepareToRefresh(int deltaY) {
		int footState = 3;
		if (this.allowFooterPull) {
			int delta = this.changingHeaderViewTopMargin(deltaY);
			if (Math.abs(delta) >= this.mHeaderViewHeight + this.mFooterViewHeight && this.mFooterState != footState) {
				this.mFooterTextView.setText("");
				this.mFooterState = 3;
			} else if (Math.abs(delta) < this.mHeaderViewHeight + this.mFooterViewHeight) {
				this.mFooterTextView.setText("");
				this.mFooterState = 2;
			}

			if (this.isSupportLoadMore) {
				return;
			}

			this.mFooterProgressBar.setVisibility(View.GONE);
		}
	}

	private void footerRefreshing(boolean showProgressBar) {
		if (canFootRequestData() && this.mFooterLoadingView.getVisibility() == 0) {
			this.mFooterState = 4;
			this.setHeaderTopMargin(-(this.mHeaderViewHeight + this.mFooterViewHeight));
			if (showProgressBar) {
				this.mFooterProgressBar.setVisibility(View.VISIBLE);
				this.mFooterTextView.setVisibility(View.VISIBLE);
				this.mFooterTextView.setText(R.string.pull_to_refresh_loading_more);
				if (this.mOnFooterRefreshListener != null) {
					this.mOnFooterRefreshListener.onFooterRefresh(this);
				}
			} else {
				this.onFooterRefreshComplete();
			}
		} else {
			this.setHeaderTopMargin(-this.mHeaderViewHeight);
		}
	}

	private int getHeaderTopMargin() {
		// 这种临时的解决办法会引起一些其他问题，很难查找，所以尽量原来的逻辑一点都不要变。
		return ((LinearLayout.LayoutParams) (this.mHeaderView.getLayoutParams())).topMargin;
	}

	private void headerPrepareToRefresh(int deltaY) {
		int v3 = 3;
		if (this.allowHeaderPull) {
			int delta = this.changingHeaderViewTopMargin(deltaY);
			if (delta >= 0 && this.mHeaderState != v3) {
				this.mHeaderProgressBar.setVisibility(View.GONE);
				this.mHeaderImageView.setVisibility(View.VISIBLE);
				// this.setBackground(R.drawable.head_image_default);
				this.setBackground(R.drawable.refresh_up);
				this.mHeaderTextView.setText(getResources().getString(R.string.pull_to_refresh_release_label));
				this.mHeaderState = v3;
				return;
			}

			if (delta >= 0) {
				return;
			}

			if (delta <= -this.mHeaderViewHeight) {
				return;
			}
			this.mHeaderProgressBar.setVisibility(View.GONE);
			this.mHeaderImageView.setVisibility(View.VISIBLE);
			this.mHeaderTextView.setText(getResources().getString(R.string.pull_to_refresh_pull_label));
			// this.setBackground(R.drawable.head_image_default);
			this.setBackground(R.drawable.refresh_down);
			this.mHeaderState = 2;
		}
	}

	public void headerRefreshing() {
		this.mHeaderState = 4;
		this.setHeaderTopMargin(0);
		// this.mHeaderImageView.setVisibility(View.VISIBLE);
		this.mHeaderImageView.setVisibility(View.GONE);
		this.mHeaderProgressBar.setVisibility(View.VISIBLE);
		this.mHeaderTextView.setText(getResources().getString(R.string.pull_to_refresh_refreshing_label));
		// 红色的加载进度条
		// this.setBackground(R.drawable.head_image_background);
		/*
		 * this.animationDrawable = (AnimationDrawable)
		 * this.mHeaderImageView.getBackground(); if(this.animationDrawable !=
		 * null && (this.animationDrawable.isRunning())) {
		 * this.animationDrawable.stop(); }
		 * 
		 * this.animationDrawable.start();
		 */
		if (this.mOnHeaderRefreshListener != null) {
			this.mOnHeaderRefreshListener.onHeaderRefresh(this);
		}
	}

	private void init() {
		this.setOrientation(1);
		this.mFlipAnimation = new RotateAnimation(0f, -180f, 1, 0.5f, 1, 0.5f);
		this.mFlipAnimation.setInterpolator(new LinearInterpolator());
		this.mFlipAnimation.setDuration(250);
		this.mFlipAnimation.setFillAfter(true);
		this.mReverseFlipAnimation = new RotateAnimation(-180f, 0f, 1, 0.5f, 1, 0.5f);
		this.mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
		this.mReverseFlipAnimation.setDuration(250);
		this.mReverseFlipAnimation.setFillAfter(true);
		this.mInflater = LayoutInflater.from(this.getContext());

		this.mGuidanceView = new MCGuidanceView(mContext);
		this.addHeaderView();
	}

	public void initAdapterView() {
		ViewGroup viewGroup = null;
		int v7 = -1;
		FrameLayout refreshView = (FrameLayout) LayoutInflater.from(this.getContext()).inflate(R.layout.refresh_framelayout, viewGroup);
		if (this.rowCount == 1) {
			ListView listView = (ListView) LayoutInflater.from(this.getContext()).inflate(R.layout.listview, viewGroup);
			if (this.hasVerticalScrollbars) {
				(listView).setVerticalScrollBarEnabled(true);
			} else {
				(listView).setVerticalScrollBarEnabled(false);
			}

			(listView).setOnItemClickListener(this.mOnItemClickListener);
			(listView).setOnItemLongClickListener(this.mOnItemLongClickListener);
			(listView).setOnScrollListener(this.mOnScollListener);
			((FrameLayout) refreshView).addView(listView, 0, new FrameLayout.LayoutParams(v7, v7));
		} else {
			GridView gridView = (GridView) LayoutInflater.from(this.getContext()).inflate(R.layout.gridview, viewGroup);
			(gridView).setOnItemClickListener(this.mOnItemClickListener);
			(gridView).setOnItemLongClickListener(this.mOnItemLongClickListener);
			FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(v7, v7);
			((GridView) gridView).setNumColumns(this.rowCount);
			(refreshView).addView(gridView, 0, ((ViewGroup.LayoutParams) layoutParams));
		}

		this.addView(refreshView, 1, new LinearLayout.LayoutParams(v7, v7));
	}

	private void initContentAdapterView() {
		if (this.getChildCount() < 3) {
			throw new IllegalArgumentException("this layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
		}

		View v2 = ((ViewGroup) this.getChildAt(1)).getChildAt(0);
		if ((v2 instanceof AdapterView)) {
			this.mAdapterView = ((AdapterView) v2);
		}

		if (this.mAdapterView == null && this.mScrollView == null) {
			throw new IllegalArgumentException("must contain a AdapterView or ScrollView in this layout!");
		}
	}

	public boolean isFooterRefresh() {
		boolean footRefresh = this.mFooterState != -1 ? true : false;
		return footRefresh;
	}

	public boolean isHeaderRefresh() {
		boolean headerRefresh = this.mHeaderState != -1 ? true : false;
		return headerRefresh;
	}

	private boolean isRefresh() {
		boolean refresh = (this.isHeaderRefresh()) || (this.isFooterRefresh()) ? true : false;
		return refresh;
	}

	private boolean isRefreshViewScroll(int deltaY) {
		View childView;
		int v8 = 4;
		boolean flag = false;
		if (this.mHeaderState != v8 && this.mFooterState != v8) {
			View v1 = this.getChildAt(1);
			if (this.mAdapterView != null && ((ViewGroup) v1).getChildAt(0).getVisibility() == 0) {
				if (deltaY > 0) {
					childView = this.mAdapterView.getChildAt(0);
					if (childView != null) {
						if (this.mAdapterView.getFirstVisiblePosition() == 0 && childView.getTop() == 0) {
							this.mPullState = 1;
							return true;
						}

						int top = childView.getTop();
						int paddingTop = this.mAdapterView.getPaddingTop();
						if (this.mAdapterView.getFirstVisiblePosition() != 0) {
							return flag;
						}

						if (Math.abs(top - paddingTop) > 20) {
							return flag;
						}

						this.mPullState = 1;
						flag = true;
					} else {
					}
				} else {
					if (deltaY >= 0) {
						return flag;
					}

					View lastView = this.mAdapterView.getChildAt(this.mAdapterView.getChildCount() - 1);
					if (lastView == null) {
						return flag;
					}

					if (lastView.getBottom() > this.getHeight()) {
						return flag;
					}

					if (this.mAdapterView.getLastVisiblePosition() != this.mAdapterView.getCount() - 1) {
						return flag;
					}

					this.mPullState = 0;
					flag = true;
				}

				return flag;
			}

			if (this.mScrollView != null && ((FrameLayout) v1).getChildAt(1).getVisibility() == 0) {
				childView = this.mScrollView.getChildAt(0);
				if (deltaY > 0 && this.mScrollView.getScrollY() == 0) {
					this.mPullState = 1;
					return true;
				}

				if (deltaY >= 0) {
					return flag;
				}

				if (childView.getMeasuredHeight() > this.getHeight() + this.mScrollView.getScrollY()) {
					return flag;
				}

				this.mPullState = 0;
				return true;
			}

			if (this.getChildAt(1) == null) {
				return flag;
			}

			if (deltaY > 0) {
				this.mPullState = 1;
				return true;
			}

			this.mPullState = 0;
			flag = true;
		}

		return flag;
	}

	private void lock() {
		this.mLock = true;
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
		if (layoutParams == null) {
			layoutParams = new ViewGroup.LayoutParams(-1, -2);
		}

		int spec = ViewGroup.getChildMeasureSpec(0, 0, layoutParams.width);
		int height = layoutParams.height;
		int spec2 = height > 0 ? View.MeasureSpec.makeMeasureSpec(height, 1073741824) : View.MeasureSpec.makeMeasureSpec(0, 0);
		child.measure(spec, spec2);
	}

	protected void onFinishInflate() {
		super.onFinishInflate();
		this.initAdapterView();
		this.addFooterView();
		this.initContentAdapterView();
	}

	public void onFooterRefreshComplete() {
		this.setHeaderTopMargin(-this.mHeaderViewHeight);
		this.mFooterTextView.setText("");
		this.mFooterProgressBar.setVisibility(View.GONE);
		this.mFooterState = -1;
	}

	public void onHeaderRefreshComplete() {
		// 这里结束进度条
		this.setHeaderTopMargin(-this.mHeaderViewHeight);
		/*
		 * if(this.animationDrawable != null &&
		 * (this.animationDrawable.isRunning())) {
		 * this.animationDrawable.stop(); }
		 */

		this.setBackground(R.drawable.refresh_down);
		this.mHeaderImageView.setVisibility(View.GONE);
		this.mHeaderState = -1;
		if (this.mFooterLoadingView.getVisibility() != 0) {
			this.mFooterLoadingView.setVisibility(View.VISIBLE);
		}

		this.mFooterView.setVisibility(View.VISIBLE);
		this.mFooterProgressBar.setVisibility(View.VISIBLE);
		this.mFooterTextView.setVisibility(View.VISIBLE);
		this.mLastTotalItemCount = 0;
	}

	public void onHeaderRefreshComplete(CharSequence lastUpdated) {
		this.setLastUpdated(lastUpdated);
		this.onHeaderRefreshComplete();
	}

	public boolean onInterceptTouchEvent(MotionEvent e) {
		boolean bool = false;
		switch (e.getAction()) {
		case 0: {
			this.mLastMotionY = ((int) e.getRawY());
			if (this.mOnTouchViewListener == null) {
				return bool;
			}

			this.mOnTouchViewListener.onTouchView(this);
			break;
		}
		case 2: {
			int y = (((int) e.getRawY())) - this.mLastMotionY;
			Log.i("PullToRefreshView", "onInterceptTouchEvent deltaY:" + y + "  e.getRawY():" + e.getRawY() + " mLastMotionY:" + this.mLastMotionY);
			if (Math.abs(y) < 10) {
				return bool;
			}

			if (this.isRefreshViewScroll(y)) {
				Log.i("PullToRefreshView", "true");
				return true;
			}

			Log.i("PullToRefreshView", "false");
			break;
		}
		}

		return bool;
	}

	public boolean onTouchEvent(MotionEvent event) {
		boolean touched = true;
		if (!this.mLock) {
			Log.i("PullToRefreshView", "onTouchEvent getAction:" + event.getAction());
			int rawY = ((int) event.getRawY());
			switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE: {
				int y = rawY - this.mLastMotionY;
				Log.i("PullToRefreshView", "onTouchEvent deltaY:" + y);
				if (this.mPullState == 1) {
					Log.i("PullToRefreshView", " pull down!parent view move!");
					this.headerPrepareToRefresh(y);
				} else if (this.mPullState == 0) {
					Log.i("PullToRefreshView", "pull up!parent view move!");
					this.footerPrepareToRefresh(y);
				}

				this.mLastMotionY = rawY;
				break;
			}
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL: {
				int topMargin = this.getHeaderTopMargin();
				if (this.mPullState == 1) {
					if (topMargin >= 0) {
						this.headerRefreshing();
						return super.onTouchEvent(event);
					}

					this.setHeaderTopMargin(-this.mHeaderViewHeight);
					return super.onTouchEvent(event);
				}

				if (this.mPullState != 0) {
					return super.onTouchEvent(event);
				}

				if (this.mFooterState != 2 && this.mFooterState != 3) {
					return super.onTouchEvent(event);
				}
				System.out.println("=====   "+Math.abs(topMargin) +"     "+this.mHeaderViewHeight + this.mFooterViewHeight);
				if (Math.abs(topMargin) >= this.mHeaderViewHeight + this.mFooterViewHeight) {
					this.footerRefreshing(true);// 这里改成true就有下拉效果了，原来是false
					return super.onTouchEvent(event);
				}

				this.setHeaderTopMargin(-this.mHeaderViewHeight);
				break;
			}
			}
			touched = super.onTouchEvent(event);
		}

		return touched;
	}

	public void setAdapterViewHorizontalSpacing(int horizontalSpacing) {
		if (this.rowCount > 1) {
			((GridView) this.mAdapterView).setHorizontalSpacing(horizontalSpacing);
		}
	}

	public void setAdapterViewPadding(int left, int top, int right, int bottom) {
		this.mAdapterView.setPadding(left, top, right, bottom);
	}

	public void setAdapterViewRecyclerListener(AbsListView.RecyclerListener mRecyclerListener) {
		if (this.rowCount == 1) {
			((AbsListView) this.mAdapterView).setRecyclerListener(mRecyclerListener);
		} else {
			((AbsListView) this.mAdapterView).setRecyclerListener(mRecyclerListener);
		}
	}

	public void setAdapterViewVerticalSpacing(int verticalSpacing) {
		if (this.rowCount > 1) {
			((GridView) this.mAdapterView).setVerticalSpacing(verticalSpacing);
		}
	}

	public void setAdapterViewWhenHasData() {
		View v1 = this.getChildAt(1);
		try {
			((FrameLayout) v1).getChildAt(1).setVisibility(View.GONE);
		} catch (Exception v2) {
		}

		try {
			View childView = ((FrameLayout) v1).getChildAt(0);
			if (childView == null) {
				this.initAdapterView();
				return;
			}

			childView.setVisibility(View.VISIBLE);
		} catch (Exception v2) {
		}
	}

	public void setAllowFooterPull(boolean allowFooterPull) {
		this.allowFooterPull = allowFooterPull;
	}

	public void setAllowHeaderPull(boolean allowHeaderPull) {
		this.allowHeaderPull = allowHeaderPull;
	}

	public void setAllowSwipe(boolean allowSwipe) {
		this.allowSwiped = allowSwipe;
	}

	@SuppressLint("NewApi")
	private void setBackground(int drawableId) {
		this.mHeaderImageView.setImageResource(drawableId);
		/*
		 * if(Build.VERSION.SDK_INT < 16) {
		 * this.mHeaderImageView.setBackgroundDrawable
		 * (this.getResources().getDrawable(drawableId)); } else {
		 * this.mHeaderImageView
		 * .setBackground(this.getResources().getDrawable(drawableId)); }
		 */
	}

	public void setDataAdapter(BaseAdapter adapter) {
		if (this.rowCount == 1) {
			this.mAdapterView.setAdapter(((ListAdapter) adapter));
		} else {
			this.mAdapterView.setAdapter(((ListAdapter) adapter));
		}
	}

	public void setGuidanceViewWhenNoDatasssss(View view) {
		FrameLayout layOut = (FrameLayout) this.getChildAt(1);
		try {
			layOut.getChildAt(0).setVisibility(View.GONE);
		} catch (Exception v3) {
			v3.printStackTrace();
		}

		try {
			if (layOut.getChildAt(1) == null) {
				layOut.addView(view, 1, new FrameLayout.LayoutParams(-1, -1));
				return;
			}

			layOut.getChildAt(1).setVisibility(View.VISIBLE);
		} catch (Exception v3) {
			v3.printStackTrace();
		}
	}

	public void setGuidanceViewWhenNoData() {
		FrameLayout layOut = (FrameLayout) this.getChildAt(1);
		try {
			layOut.getChildAt(0).setVisibility(View.GONE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (layOut.getChildAt(1) == null) {
				layOut.addView(this.mGuidanceView, 1, new FrameLayout.LayoutParams(-1, -1));
				return;
			}

			layOut.getChildAt(1).setVisibility(View.VISIBLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setHeaderTopMargin(int topMargin) {
		ViewGroup.LayoutParams layoutParams = this.mHeaderView.getLayoutParams();
		((LinearLayout.LayoutParams) layoutParams).topMargin = topMargin;
		this.mHeaderView.setLayoutParams(layoutParams);
		this.invalidate();
	}

	public void setLastUpdated(CharSequence lastUpdated) {
	}

	public void setNoContentVisibility() {
		this.mFooterView.setVisibility(View.GONE);
	}

	public void resetContentVisibility() {
		this.mFooterView.setVisibility(View.VISIBLE);
	}

	public boolean canFootRequestData() {
		return this.mFooterView.getVisibility() == View.VISIBLE;
	}

	public void setOnFooterRefreshListener(OnFooterRefreshListener footerRefreshListener) {
		this.mOnFooterRefreshListener = footerRefreshListener;
	}

	public void setOnHeaderRefreshListener(OnHeaderRefreshListener headerRefreshListener) {
		this.mOnHeaderRefreshListener = headerRefreshListener;
	}

	public void setOnItemClickListener(AdapterView.OnItemClickListener arg3) {
		this.mOnItemClickListener = arg3;
		this.mAdapterView.setOnItemClickListener(this.mOnItemClickListener);
	}

	public void setOnItemLongLitener(AdapterView.OnItemLongClickListener arg3) {
		this.mOnItemLongClickListener = arg3;
		this.mAdapterView.setOnItemLongClickListener(this.mOnItemLongClickListener);
	}

	public void setOnTouchViewListener(OnTouchViewListener touchViewListener) {
		this.mOnTouchViewListener = touchViewListener;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public void setSelection(int position) {
		if (this.mAdapterView != null && ((this.mAdapterView instanceof ListView))) {
			this.mAdapterView.setSelection(position);
		}
	}

	public void setTranscriptMode(int mode) {
		if (this.mAdapterView != null && ((this.mAdapterView instanceof ListView))) {
			((AbsListView) this.mAdapterView).setTranscriptMode(mode);
		}
	}
	public boolean isAllowFooterPull(){
		return allowFooterPull;
	}

	/**
	 * 添加没有网络提示
	 * 
	 * @param view
	 */
	public void setGuidanceViewWhenNoNet() {
		this.mGuidanceView.setGuidanceBitmap(R.drawable.no_network_icon);
		this.mGuidanceView.setGuidanceText(R.string.no_network_alert_label);
		this.mGuidanceView.setLayoutMarginTop(this.getResources().getDimensionPixelSize(R.dimen.mooc_110_dp));
		this.setGuidanceViewWhenNoData();
	}

	/**
	 * 添加没有数据提示
	 * 
	 * @param icon
	 * @param info
	 *            字符串的提示信息
	 */
	public void setGuidanceViewWhenNoData(int icon, String info) {
		this.mGuidanceView.setGuidanceBitmap(icon);
		this.mGuidanceView.setGuidText(info);
		this.mGuidanceView.setLayoutMarginTop(this.getResources().getDimensionPixelSize(R.dimen.mooc_110_dp));
		this.setGuidanceViewWhenNoData();
	}

	/**
	 * 添加文字的
	 * 
	 */
	public void setGuidanceViewWhenNoDataWith(String info) {
		this.mGuidanceView.setGuidText(info);
		this.mGuidanceView.setLayoutMarginTop(this.getResources().getDimensionPixelSize(R.dimen.mooc_110_dp));
		this.setGuidanceViewWhenNoData();
	}

	/**
	 * 添加没有数据提示
	 * 
	 * @param icon
	 * @param info
	 *            资源里字符串的提示信息ID
	 */
	public void setGuidanceViewWhenNoData(int icon, int info) {
		this.mGuidanceView.setGuidanceBitmap(icon);
		this.mGuidanceView.setGuidanceText(info);
		this.mGuidanceView.setLayoutMarginTop(this.getResources().getDimensionPixelSize(R.dimen.mooc_110_dp));
		this.setGuidanceViewWhenNoData();
	}
}
