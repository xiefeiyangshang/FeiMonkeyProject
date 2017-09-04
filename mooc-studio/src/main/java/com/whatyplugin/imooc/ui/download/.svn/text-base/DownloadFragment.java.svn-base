package com.whatyplugin.imooc.ui.download;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.asyncimage.MCAsyncImageDefine.ImageType;
import com.whatyplugin.base.asyncimage.MCCacheManager;
import com.whatyplugin.base.define.MCBaseDefine.MCDownloadNodeType;
import com.whatyplugin.base.download.MCDownloadQueue;
import com.whatyplugin.base.download.MCDownloadVideoNode;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.db.DBCommon.DownloadColumns;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCDownloadService;
import com.whatyplugin.imooc.logic.service_.MCDownloadServiceInterface;
import com.whatyplugin.imooc.logic.utils.FileUtils;
import com.whatyplugin.imooc.ui.base.MCBaseV4ListFragment;
import com.whatyplugin.uikit.resolution.MCResolution;

public class DownloadFragment extends MCBaseV4ListFragment {
	private static String TAG;
	private Handler handler;
	boolean isExists;
	private List list;
	private MCDownloadServiceInterface mDownloadService;
	private ContentObserver observer;
	private BroadcastReceiver receiver;
	private TextView sdcard_space;
	private ImageView used_img;
	static {
		DownloadFragment.TAG = DownloadFragment.class.getSimpleName();
	}

	public DownloadFragment() {
		super();
		this.list = new ArrayList();
		this.mDownloadService = null;
		this.isExists = false;
		this.handler = new Handler() {
			public void handleMessage(Message msg) {
				long time = 5000;
				if (msg.what == 0) {
					DownloadFragment.this.adapter.notifyDataSetChanged();
					if (MCDownloadQueue.getInstance().getDownloadingSize(MCDownloadNodeType.MC_SFP_AND_VIDEO_TYPE) > 0) {
						this.sendEmptyMessageDelayed(0, time);
					} else {
						this.removeMessages(0);
					}
				} else if (msg.what == 1) {
					DownloadFragment.this.showSdCardSpace();
					DownloadFragment.this.mDownloadService.getAllDownloadCourse(MCDownloadNodeType.MC_SFP_AND_VIDEO_TYPE, DownloadFragment.this,
							DownloadFragment.this.getActivity());
					this.sendEmptyMessageDelayed(1, time);
				}

				super.handleMessage(msg);
			}
		};

		// Android中内容观察者的使用观察
		// (捕捉)特定Uri引起的数据库的变化，继而做一些相应的处理，它类似于数据库技术中的触发器(Trigger)
		this.observer = new ContentObserver(handler) {
			public void onChange(boolean selfChange) {
				if (DownloadFragment.this.getUserVisibleHint()) {
					DownloadFragment.this.mDownloadService.getAllDownloadCourse(MCDownloadNodeType.MC_SFP_AND_VIDEO_TYPE, DownloadFragment.this,
							DownloadFragment.this.getActivity());
					DownloadFragment.this.showSdCardSpace();
				}

				super.onChange(selfChange);
			}
		};
		this.receiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(Contants.SDCARD_STATUS_CHANGED)) {
					DownloadFragment.this.mDownloadService.getAllDownloadCourse(MCDownloadNodeType.MC_SFP_AND_VIDEO_TYPE, DownloadFragment.this,
							DownloadFragment.this.getActivity());

				}
			}
		};
	}

	public void OnAnalyzeBackBlock(MCServiceResult result, List retList) {
		this.adapter.clear();

		removeLoading();
		if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
			this.mListView.setAdapterViewWhenHasData();
			List<MCDownloadVideoNode> nodeList = new ArrayList<MCDownloadVideoNode>();
			Iterator<MCDownloadVideoNode> nodeIter = retList.iterator();
			while (nodeIter.hasNext()) {
				MCDownloadVideoNode videoNode = (MCDownloadVideoNode) nodeIter.next();
				this.isExists = false;
				// 通过课程获得其所有节点的数据
				this.mDownloadService.getAllDownloadSectionByCourseId(MCDownloadNodeType.MC_SFP_AND_VIDEO_TYPE, videoNode.getCourseId(),
						new MCAnalyzeBackBlock() {
							public void OnAnalyzeBackBlock(MCServiceResult result, List arg7) {
								if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
									Iterator<MCDownloadVideoNode> v1 = arg7.iterator();
									while (v1.hasNext()) {
										MCDownloadVideoNode item = v1.next();
										if (item.isDownloadOver() && item.nodeType != MCDownloadNodeType.MC_SFP_TYPE) {
											if (!FileUtils.isFileExistsInPhoneOrSdcard(item.getFilename(), DownloadFragment.this.getActivity())) {
												continue;
											}

											DownloadFragment.this.isExists = true;
										} else {
											DownloadFragment.this.isExists = true;
										}
									}
								}
							}
						}, this.getActivity());
				if (!this.isExists) {
					continue;
				}

				nodeList.add(videoNode);
			}

			this.adapter.addAll(nodeList);
		} else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_EMPTY) {
			this.mListView.setGuidanceViewWhenNoData(R.drawable.no_download_icon, R.string.no_download_label);
		}
		
	}

	/**
	 * 原来用跟课程列表同一个layout，现在单独用自己的layout，相互修改起来不会有影响
	 */
	public void onActivityCreated(Bundle savedInstanceState) {
		this.mDownloadService = new MCDownloadService();
		this.used_img = (ImageView) this.getActivity().findViewById(R.id.used_img);
		this.sdcard_space = (TextView) this.getActivity().findViewById(R.id.sdcard_space);
		this.showSdCardSpace();
		this.getActivity().getContentResolver().registerContentObserver(DownloadColumns.CONTENT_URI_DOWNLOADINFO, true, this.observer);
		this.handler.sendEmptyMessageDelayed(0, 5000);
		this.handler.sendEmptyMessageDelayed(1, 5000);
		IntentFilter filter = new IntentFilter();
		filter.addAction(Contants.SDCARD_STATUS_CHANGED);
		this.getActivity().registerReceiver(this.receiver, filter);

		super.onActivityCreated(savedInstanceState);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 10 && resultCode == -1) {
			this.mDownloadService.getAllDownloadCourse(MCDownloadNodeType.MC_SFP_AND_VIDEO_TYPE, ((MCAnalyzeBackBlock) this), this.getActivity());
			this.showSdCardSpace();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	public void onDestroy() {
		this.getActivity().getContentResolver().unregisterContentObserver(this.observer);
		this.handler.removeMessages(0);
		this.handler.removeMessages(1);
		super.onDestroy();
	}

	public void onItemClick(AdapterView arg6, View view, int position, long id) {
		MCDownloadVideoNode viewNode = (MCDownloadVideoNode) arg6.getAdapter().getItem(position);
		Intent intent = new Intent(this.getActivity(), DownloadSectionsActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("courseId", viewNode.getCourseId());
		bundle.putString("courseName", viewNode.getCourseName());
		intent.putExtras(bundle);
		this.startActivityForResult(intent, 10);
	}

	/**
	 * 最下边的sd卡空间情况
	 */
	private void showSdCardSpace() {
		long sdsize;
		long allsize;
		if (FileUtils.isTwoSdcard(this.getActivity())) {
			allsize = FileUtils.getAllSDSize(MCSaveData.getDownloadSDcardPath(this.getActivity()));
			sdsize = FileUtils.getAvailaleSDSize(MCSaveData.getDownloadSDcardPath(this.getActivity()));
		} else {
			allsize = FileUtils.getAllSDSize(Environment.getExternalStorageDirectory().getPath());
			sdsize = FileUtils.getAvailaleSDSize(Environment.getExternalStorageDirectory().getPath());
		}

		try {
			this.sdcard_space.setText(this.getActivity().getString(R.string.space_label,
			new Object[] { FileUtils.FormetFileSize(allsize - sdsize), FileUtils.FormetFileSize(sdsize) }));
			ViewGroup.LayoutParams layoutParams = this.used_img.getLayoutParams();
			layoutParams.width = ((int) ((((long) MCResolution.getInstance(this.getActivity()).getDevWidth(this.getActivity()))) * (allsize - sdsize) / allsize));
			this.used_img.setLayoutParams(layoutParams);
		} catch (Exception v2) {
			v2.printStackTrace();
		}
	}

	@Override
	public void requestData() {
		DownloadFragment.this.mDownloadService.getAllDownloadCourse(MCDownloadNodeType.MC_SFP_AND_VIDEO_TYPE, DownloadFragment.this,
				DownloadFragment.this.getActivity());
	}

	@Override
	public void doAfterItemClick(Object obj) {
		MCDownloadVideoNode viewNode = (MCDownloadVideoNode) obj;
		Intent intent = new Intent(this.getActivity(), DownloadSectionsActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("courseId", viewNode.getCourseId());
		bundle.putString("courseName", viewNode.getCourseName());
		intent.putExtras(bundle);
		this.startActivityForResult(intent, 10);
	}

	@Override
	public void initAdapter() {
		this.adapter = new QuickAdapter(this.getActivity(), R.layout.download_course_item_layout) {
			protected void convert(final BaseAdapterHelper helper, MCDownloadVideoNode item) {
				int downloadingImg = R.id.downloading_img; // R.id.downloading_img
				int contentLayout = R.id.content_layout; // R.id.content_layout
				int image = R.id.image; // R.id.image
				helper.setText(R.id.name, item.getCourseName());
				helper.setText(R.id.desc, item.getCourseName());
				helper.setAlpha(R.id.desc, 0f);
				helper.setImageResource(R.id.left, R.drawable.download_count_icon);
				helper.setImageResource(R.id.right, R.drawable.download_size_icon);
				if (MCDownloadQueue.getInstance().isCourseDownloading(item.getCourseId())) {
					helper.getView(downloadingImg).setVisibility(View.VISIBLE);
				} else {
					helper.getView(downloadingImg).setVisibility(View.INVISIBLE);
				}

				// 下载的节点的数量
				DownloadFragment.this.mDownloadService.getSectionCountByCourseId(MCDownloadNodeType.MC_SFP_AND_VIDEO_TYPE, item.getCourseId(),
						new MCAnalyzeBackBlock() {
							public void OnAnalyzeBackBlock(MCServiceResult result, List objs) {
								helper.setText(R.id.learnedcount_tv, objs.get(0).toString());
							}
						}, DownloadFragment.this.getActivity());

				// 下载的节点所占用的空间
				DownloadFragment.this.mDownloadService.getTotalSizeByCourseId(MCDownloadNodeType.MC_SFP_AND_VIDEO_TYPE, item.getCourseId(),
						new MCAnalyzeBackBlock() {
							public void OnAnalyzeBackBlock(MCServiceResult result, List objs) {
								long tmp = Long.valueOf(objs.get(0).toString());
								helper.setText(R.id.learnedtime_tv, FileUtils.FormetFileSize(tmp));
							}
						}, DownloadFragment.this.getActivity());
				ViewGroup.LayoutParams v10 = helper.getView(image).getLayoutParams();
				v10.width = MCResolution.getInstance(DownloadFragment.this.getActivity()).scaleWidth(Contants.COURSE_IMAGE_WIDTH);
				v10.height = MCResolution.getInstance(DownloadFragment.this.getActivity()).scaleHeightByScaleWidth(v10.width);
				helper.getView(image).setLayoutParams(v10);
				ViewGroup.LayoutParams v9 = helper.getView(contentLayout).getLayoutParams();
				v9.width = v10.width;
				v9.height = v10.height;
				helper.getView(contentLayout).setLayoutParams(v9);
				helper.setImageUrl(image, item.getCourseImageUrl(), MCCacheManager.getInstance().getImageLoader(), v10.width, v10.height, false,
						ImageType.CICLE_IMAGE, null);
			}

			protected void convert(BaseAdapterHelper arg1, Object arg2) {
				this.convert(arg1, ((MCDownloadVideoNode) arg2));
			}
		};
	}

	/**
	 * 子类有自己布局的时候重写此方法
	 * 
	 * @param resultList
	 */
	public int getRootViewId() {
		return R.layout.download_layout;
	}
}
