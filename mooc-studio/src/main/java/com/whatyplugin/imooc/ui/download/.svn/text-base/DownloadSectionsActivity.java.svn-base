package com.whatyplugin.imooc.ui.download;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.define.MCBaseDefine.MCDownloadNodeType;
import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.base.download.MCDownloadListener;
import com.whatyplugin.base.download.MCDownloadNode;
import com.whatyplugin.base.download.MCDownloadQueue;
import com.whatyplugin.base.download.MCDownloadTask;
import com.whatyplugin.base.download.MCDownloadVideoNode;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCDownloadService;
import com.whatyplugin.imooc.logic.service_.MCDownloadServiceInterface;
import com.whatyplugin.imooc.logic.utils.FileUtils;
import com.whatyplugin.imooc.ui.SFPscreen.MCSFPScreenActivity;
import com.whatyplugin.imooc.ui.SFPscreen.MCSFPScreenNoThumbActivity;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.imooc.ui.view.BaseTitleView.RightClickListener;
import com.whatyplugin.uikit.dialog.MCCommonDialog;
import com.whatyplugin.uikit.toast.MCToast;

public class DownloadSectionsActivity extends MCBaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, MCAnalyzeBackBlock {
    private static String TAG;
    private QuickAdapter adapter;
    private LinearLayout bottom_layout;
    private List<MCDownloadVideoNode> checkeNodes;
    private TextView checkedAll_tv;
    private String courseId;
    private String courseName;
    private TextView delete_tv;
    private boolean isEditing;
    private MCDownloadServiceInterface mDownloadService;
    private Handler mHandler;
    private ListView mListView;
    private BroadcastReceiver receiver;
    private Dialog dialog;
	private MCDownloadQueue queue;
	private BaseTitleView titleView;
	private MCCreateDialog  creatDialog = new MCCreateDialog();
    static {
        DownloadSectionsActivity.TAG = DownloadSectionsActivity.class.getSimpleName();
    }
    
    public MCDownloadVideoNode getItemBySectionId(String sectionId) {
        for(Object obj:this.adapter.getAdapterList()){
        	MCDownloadVideoNode node = (MCDownloadVideoNode)obj;
        	if(node!=null&&sectionId.equals(node.getSectionId())){
        		return node;
        	}
        }
        return null;
    }
    
    public DownloadSectionsActivity() {
        super();
        this.isEditing = false;
        this.checkeNodes = new ArrayList<MCDownloadVideoNode>();
        this.mHandler = new Handler() {
            public void handleMessage(Message msg) {
                MCDownloadVideoNode videoNode = (MCDownloadVideoNode) msg.obj;
                SeekBar seekBar = null;
                ImageView status = null;
                TextView fileSize = null;
                MCDownloadVideoNode node = null;
                
                if(videoNode!=null){
	                seekBar = (SeekBar) DownloadSectionsActivity.this.mListView.findViewWithTag("mSeekbar_" + videoNode.getSectionId());
	                status = (ImageView) DownloadSectionsActivity.this.mListView.findViewWithTag("download_controll_" + videoNode.getSectionId());
	                fileSize = (TextView) DownloadSectionsActivity.this.mListView.findViewWithTag("media_size_" + videoNode.getSectionId());
		           	node = DownloadSectionsActivity.this.getItemBySectionId(videoNode.getSectionId());
                }
            
	           	//每次都更新进度信息
	            if(node!=null){
	             	node.downloadSize = videoNode.downloadSize;
	             	node.fileSize = videoNode.fileSize;
	            }
               
                switch(msg.what) {
                	//preDownload  准备下载
                    case 0: {
                        if(status != null) {
                        	status.getBackground().setLevel(2);
                        }
                        break;
                    }
                    //updateProcess  更新进度
                    case 1: {
                        if(seekBar != null) {
                            seekBar.setProgress(videoNode.getDownloadProgress());
                        }

                        if(fileSize != null) {
                        	fileSize.setText(String.valueOf(FileUtils.FormetFileSize(((long)msg.arg1))) + "/" + FileUtils.FormetFileSize(((long)msg.arg2)));
                        }
                        break;
                    }
                    //finishDownload 完成下载
                    case 2: {

                        if(seekBar != null) {
                            seekBar.setVisibility(View.GONE);
                        }

                        if(status != null) {
                            status.setVisibility(View.GONE);
                        }

                        if(fileSize != null) {
                        	fileSize.setText(FileUtils.FormetFileSize(((long)msg.arg2)));
                        }
                        break;
                    }
                    //errorDownload 下载出错
                    case 3: {
                        if(msg.getData().getString("error") != null) {
                        	System.out.println(msg.getData().getString("error"));
                            queue.addToFailedList(new MCDownloadTask(videoNode));
                        }

                        if(status != null) {
                        	 status.getBackground().setLevel(1);
                        }
                       
                        break;
                    }
                    case 4:{
                    	adapter.notifyDataSetChanged();
                    	break;
                    }
                }

                super.handleMessage(msg);
            }
        };
        this.receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent arg1) {
                String action = arg1.getAction();
                MCLog.e(DownloadSectionsActivity.TAG, "DownloadSectionsActivity action:" + action);
                if(action.equals(Contants.RELOAD_CHANGED_ACTION)) {
                    DownloadSectionsActivity.this.adapter.notifyDataSetChanged();
                }
                else if(action.equals(Contants.SDCARD_STATUS_CHANGED)) {
                    DownloadSectionsActivity.this.mDownloadService.getAllDownloadSectionByCourseId(MCDownloadNodeType.MC_SFP_AND_VIDEO_TYPE, DownloadSectionsActivity.this.courseId, DownloadSectionsActivity.this, context);
                }
            }
        };
    }

    public void OnAnalyzeBackBlock(MCServiceResult result, List retList) {
        this.adapter.clear();
        List<MCDownloadVideoNode> nodeList = new ArrayList<MCDownloadVideoNode>();
        if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {

			for (MCDownloadVideoNode node : (List<MCDownloadVideoNode>) retList) {
				if (node.nodeType == MCDownloadNodeType.MC_SFP_TYPE) {
					if (node.isParent()) {
						nodeList.add(node);
					}
				} else {
					if (node.isDownloadOver() && !FileUtils.isFileExistsInPhoneOrSdcard(node.getFilename(), this)) {
						continue;
					}
					nodeList.add(node);
				}
			}

            Collections.sort(nodeList, new Comparator() {
                public int compare(MCDownloadVideoNode lhs, MCDownloadVideoNode rhs) {
                    int result = 1;
                    int flag = -1;
                    if(lhs.getChapter_seq() <= rhs.getChapter_seq()) {
                        if(lhs.getChapter_seq() < rhs.getChapter_seq()) {
                            result = flag;
                        }
                        else if(lhs.getSection_seq() <= rhs.getSection_seq()) {
                            result = lhs.getSection_seq() < rhs.getSection_seq() ? flag : 0;
                        }
                    }

                    return result;
                }

                public int compare(Object arg2, Object arg3) {
                    return this.compare(((MCDownloadVideoNode)arg2), ((MCDownloadVideoNode)arg3));
                }
            });
            this.adapter.addAll(nodeList);
        }
    }
    
    private void checkedAllSectiones() {
        this.checkeNodes.clear();
        this.checkeNodes.addAll(this.adapter.getAdapterList());
    }

    public void initView() {
        this.titleView = (BaseTitleView) findViewById(R.id.rl_titile);
    	

        this.mListView = (ListView)this.findViewById(R.id.mListView);
        this.mListView.setOnItemClickListener(((AdapterView.OnItemClickListener)this));
        this.bottom_layout = (LinearLayout)this.findViewById(R.id.bottom_layout);
        this.checkedAll_tv = (TextView)this.findViewById(R.id.checkedAll_tv);
        this.delete_tv = (TextView)this.findViewById(R.id.delete_tv);
        if(this.courseName != null && this.courseName != "") {
            this.titleView.setTitle(String.valueOf(this.courseName) + " ");
        }
        this.titleView.findViewById(R.id.left_img).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DownloadSectionsActivity.this.setResult(-1);
				DownloadSectionsActivity.this.finish();
			}
		});
    }

	private void initEvent() {
		this.titleView.setRigTextListener(new RightClickListener() {
			
			@Override
			public void onRightViewClick() {
				int checkCount = R.string.chapter_download_checkedall;
				if(isEditing) {
				    isEditing = false;
				    bottom_layout.setVisibility(View.GONE);
				    titleView.setRightText(DownloadSectionsActivity.this.getString(R.string.download_edit_label));
				    checkedAll_tv.setText(getResources().getString(checkCount));
				    checkeNodes.clear();
				}
				else {
				    isEditing = true;
				    bottom_layout.setVisibility(View.VISIBLE);
				    titleView.setRightText(DownloadSectionsActivity.this.getString(R.string.cancel));
				}
				adapter.notifyDataSetChanged();
			}
		});
        this.checkedAll_tv.setOnClickListener(((View.OnClickListener)this));
        this.delete_tv.setOnClickListener(((View.OnClickListener)this));
	}

    public void onClick(View v) {
        int unCheckCount = R.string.chapter_download_uncheckedall;  
        int checkCount = R.string.chapter_download_checkedall;  
        int id = v.getId();
		if (id == R.id.checkedAll_tv) {
			if(this.checkedAll_tv.getText().equals(this.getResources().getString(unCheckCount))) {
			    this.checkeNodes.clear();
			    this.adapter.notifyDataSetChanged();
			    this.checkedAll_tv.setText(this.getResources().getString(checkCount));
			    return;
			}
			this.checkedAllSectiones();
			this.adapter.notifyDataSetChanged();
			this.checkedAll_tv.setText(this.getResources().getString(unCheckCount));
		} else if (id == R.id.delete_tv) {
			if(this.checkeNodes.size() > 0) {
			    final MCCommonDialog dialogView = new MCCommonDialog(this.getString(R.string.download_network_title), this.getString(R.string.delete_download_label, new Object[]{Integer.valueOf(this.checkeNodes.size())}), R.layout.network_dialog_layout);
			    mHandler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						dialogView.setCommitListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								Map map = new HashMap();

					            Iterator iterator = DownloadSectionsActivity.this.checkeNodes.iterator();
					            while(iterator.hasNext()) {
					            	MCDownloadVideoNode videoNode = (MCDownloadVideoNode) iterator.next();
					                queue.deleteTaskBySectionId(videoNode.getSectionId());
					            }
					            adapter.removeAll(DownloadSectionsActivity.this.checkeNodes);
					            checkeNodes.clear();
					            isEditing = false;
					            bottom_layout.setVisibility(View.GONE);
					            titleView.setRightText(DownloadSectionsActivity.this.getString(R.string.download_edit_label));
					            dialogView.dismiss();
							}
						});
					}
				}, 200);
			    dialogView.show(creatDialog.getFragmentTransaction(this), "删除");
			    return;
			}
			this.isEditing = false;
			titleView.setRightText(this.getString(R.string.download_edit_label));
			this.bottom_layout.setVisibility(View.GONE);
		}
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.download_section_layout);
        this.courseId = (String) this.getIntent().getExtras().getSerializable("courseId");
        this.courseName = this.getIntent().getExtras().getString("courseName");
        this.initView();
        this.initEvent();
        this.queue = MCDownloadQueue.getInstance();
        this.adapter = new QuickAdapter(this,R.layout.download_sections_item_layout) {

            protected void convert(BaseAdapterHelper helper, MCDownloadVideoNode item) {
            	MCDownloadTask downloadTask;
                MCDownloadVideoNode videoNode = item;
                int name = R.id.name;  // R.id.name
                try {
                    helper.setText(name, videoNode.getSectionName());

                    downloadTask = MCDownloadQueue.mDownloadTasks.get(videoNode.getSectionId());
                    helper.getView(R.id.download_controll).setTag("download_controll_" + videoNode.getSectionId());
                    helper.getView(R.id.media_size).setTag("media_size_" + videoNode.getSectionId());
                    helper.getView(R.id.mSeekbar).setTag("mSeekbar_" + videoNode.getSectionId());
                    if(downloadTask == null) {
                        helper.getView(R.id.download_controll).getBackground().setLevel(1);
                    }
                    else {
                        if(queue.isTaskDownloading(downloadTask)) {
                            helper.getView(R.id.download_controll).getBackground().setLevel(2);
                        }
                        else if(queue.isTaskWaiting(downloadTask)) {
                            helper.getView(R.id.download_controll).getBackground().setLevel(0);
                        }
                        else {
                            helper.getView(R.id.download_controll).getBackground().setLevel(1);
                        }
                    }

                    if(videoNode.isDownloadOver()) {
                        helper.getView(R.id.mSeekbar).setVisibility(View.INVISIBLE);
                        helper.getView(R.id.download_controll).setVisibility(View.INVISIBLE);
                        helper.setText(R.id.media_size, FileUtils.FormetFileSize(videoNode.getFileSize()));
                    }else{
	                    helper.getView(R.id.mSeekbar).setVisibility(View.VISIBLE);
	                    helper.getView(R.id.download_controll).setVisibility(View.VISIBLE);
	                    helper.setText(R.id.media_size, String.valueOf(FileUtils.FormetFileSize(videoNode.getDownloadSize())) + "/" + FileUtils.FormetFileSize(videoNode.getFileSize()));
	                    //设置下载进度
	                    name = R.id.mSeekbar;  
	                    // 这里面获得的进度没有及时更新
	                    helper.setProgress(name, videoNode.getDownloadProgress());
                    }
                
                    if(DownloadSectionsActivity.this.isEditing) {
                        helper.getView(R.id.download_controll).setVisibility(View.GONE);
                        helper.getView(R.id.check).setVisibility(View.VISIBLE);
                        if(DownloadSectionsActivity.this.checkeNodes.contains(item)) {
                            helper.getView(R.id.check).getBackground().setLevel(1);
                        }
                        else {
                        	 name = R.id.check;
                        	 helper.getView(name).getBackground().setLevel(0);
                        }
                    }
                    else {
                    	 if(videoNode.isDownloadOver()) {
                             helper.getView(R.id.download_controll).setVisibility(View.INVISIBLE);
                         }
                         else {
                             helper.getView(R.id.download_controll).setVisibility(View.VISIBLE);
                         }

                         helper.getView(R.id.check).setVisibility(View.GONE);
                    }
                    if(downloadTask != null) {
                        downloadTask.setListener(new MCDownloadListener() {
                            public void errorDownload(MCDownloadNode node, String error) {
                                Message msg = new Message();
                                msg.what = 3;
                                msg.obj = node;
                                Bundle bundle = new Bundle();
                                bundle.putString("error", error);
                                msg.setData(bundle);
                                DownloadSectionsActivity.this.mHandler.sendMessage(msg);
                            }

                            public void finishDownload(MCDownloadNode node) {
                                MCLog.e(DownloadSectionsActivity.TAG, "finishDownload");
                                Message msg = new Message();
                                msg.what = 2;
                                msg.obj = node;
                                msg.arg1 = ((int)node.getDownloadSize());
                                msg.arg2 = ((int)node.fileSize);
                                DownloadSectionsActivity.this.mHandler.sendMessage(msg);
                            }

                            public void preDownload(MCDownloadNode node) {
                                Message msg = new Message();
                                msg.what = 0;
                                msg.obj = node;
                                DownloadSectionsActivity.this.mHandler.sendMessage(msg);
                            }

                            public void updateProcess(MCDownloadNode node) {
                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = node;
                                msg.arg1 = ((int)node.getDownloadSize());
                                msg.arg2 = ((int)node.fileSize);
                                DownloadSectionsActivity.this.mHandler.sendMessage(msg);
                            }
                        });
                    }

                    return;
                }
                catch(Exception e) {
                    return;
                }
            }

            protected void convert(BaseAdapterHelper helper, Object node) {
                this.convert(helper, ((MCDownloadVideoNode)node));
            }
        };
        this.mListView.setAdapter(this.adapter);
        this.mDownloadService = new MCDownloadService();
        this.mDownloadService.getAllDownloadSectionByCourseId(MCDownloadNodeType.MC_SFP_AND_VIDEO_TYPE, this.courseId, ((MCAnalyzeBackBlock)this), ((Context)this));
        IntentFilter filter = new IntentFilter();
        filter.addAction(Contants.NETWORK_CHANGED_ACTION);
        filter.addAction(Contants.RELOAD_CHANGED_ACTION);
        filter.addAction(Contants.SDCARD_STATUS_CHANGED);
        this.registerReceiver(this.receiver, filter);
    }

    protected void onDestroy() {
        this.unregisterReceiver(this.receiver);
        super.onDestroy();
    }

    public void onItemClick(AdapterView adapterView, final View view, int position, long id) {
    	//listview中videoNode的downloadsize没有及时更行的原因
        final MCDownloadVideoNode videoNode = (MCDownloadVideoNode) adapterView.getAdapter().getItem(position);
        MCDownloadTask itemTask = null;
        final MCDownloadVideoNode node = null;
        //编辑模式
        if(this.isEditing) {
            if(this.checkeNodes.contains(videoNode)) {
                this.checkeNodes.remove(videoNode);
                //setLevel(0)空心的圆圈，表示没有选中
                view.findViewById(R.id.check).getBackground().setLevel(0);
            }
            else {
                this.checkeNodes.add(videoNode);
              //setLevel(1)实心的圆圈，表示选中
                view.findViewById(R.id.check).getBackground().setLevel(1);
            }

            //全选和取消全选的切换
            if(this.checkeNodes.size() == this.adapter.getAdapterList().size()) {
                this.checkedAll_tv.setText(this.getResources().getString(R.string.chapter_download_uncheckedall));
            } else {
            	this.checkedAll_tv.setText(this.getResources().getString(R.string.chapter_download_checkedall));
            }
            return;
        }
        
        itemTask = MCDownloadQueue.mDownloadTasks.get(videoNode.getSectionId());
        
        //如果该节点已经下载完成就打开该节点
        if(itemTask!=null && itemTask.getNode().isDownloadOver()) {
        	if(itemTask.getNode().nodeType==MCDownloadNodeType.MC_SFP_TYPE){
				Intent intent = null;
				if (videoNode.getSectionName().contains("FLASH")) {
					intent = new Intent(this, MCSFPScreenNoThumbActivity.class);
				} else {
					intent = new Intent(this, MCSFPScreenActivity.class);
				}
				intent.putExtra("courseId", videoNode.getCourseId());
				intent.putExtra("isLocal", true);
				intent.putExtra("sectionId", videoNode.getResourceSection());
				this.startActivity(intent);
        	}else if(itemTask.getNode().nodeType==MCDownloadNodeType.MC_VIDEO_TYPE){
        		Intent intent = new Intent(this, OfflinePlayerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("filename",videoNode.getFilename());
                bundle.putString("sectionName", videoNode.getSectionName());
                intent.putExtras(bundle);
                intent.putExtra("userid", Contants.USERID);
                intent.putExtra("courseid", videoNode.getCourseId());
                //                intent.putExtra("id", videoNode.get);
                intent.putExtra("sectionseq", videoNode.getSection_seq());
                intent.putExtra("sectionid", videoNode.getSectionId());
                intent.putExtra("chapterseq", videoNode.getChapter_seq());
                this.startActivityForResult(intent, 11);
        	}else{
        		MCToast.show(this, "暂未支持的离线缓存播放类型……");
        	}

            return;
        }
        if(itemTask!=null && itemTask.getNode()!=null){
	        //很重要的一句话，更新视图中数据的已下载值。
	        videoNode.downloadSize = itemTask.getNode().downloadSize;
        }
        //根据该节点的下载状态做不同的处理
        int level = view.findViewById(R.id.download_controll).getBackground().getLevel();
        String sectionId = videoNode.getSectionId();
        switch(level) {
        	//当前是等待，切换为下载
            case 0: {
            	 queue.insertTaskFromWaitingToDownloading(sectionId);
                 break;
            }
            //当前是暂停
            case 1: {
            	 queue.insertTaskFromPauseToDownloading(sectionId);
                 break;
            }
            //当前是下载状态，切换为暂停
            case 2: {
                 queue.insertTaskFromDownloadingToPause(sectionId);
                 break;
            }
        }
        this.mHandler.sendEmptyMessageDelayed(4, 300);
        return;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == 4) {
            this.setResult(-1);
            this.finish();
        }
        return super.onKeyUp(keyCode, event);
    }

}

