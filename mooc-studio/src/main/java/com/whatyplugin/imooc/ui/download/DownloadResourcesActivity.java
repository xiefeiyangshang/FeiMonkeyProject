package com.whatyplugin.imooc.ui.download;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.define.MCBaseDefine.MCDownloadNodeType;
import com.whatyplugin.base.download.MCDownloadListener;
import com.whatyplugin.base.download.MCDownloadNode;
import com.whatyplugin.base.download.MCDownloadQueue;
import com.whatyplugin.base.download.MCDownloadTask;
import com.whatyplugin.base.download.MCDownloadVideoNode;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.model.MCResourceDetailModel;
import com.whatyplugin.imooc.logic.model.MCResourceModel;
import com.whatyplugin.imooc.logic.model.MCSectionModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCCourseService;
import com.whatyplugin.imooc.logic.service_.MCCourseServiceInterface;
import com.whatyplugin.imooc.logic.service_.MCDownloadService;
import com.whatyplugin.imooc.logic.service_.MCDownloadServiceInterface;
import com.whatyplugin.imooc.logic.utils.FileUtils;
import com.whatyplugin.imooc.logic.utils.OpenFile;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.imooc.ui.view.MCGuidanceView;
import com.whatyplugin.uikit.toast.MCToast;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.whatyplugin.mooc.R;
public class DownloadResourcesActivity extends MCBaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, MCAnalyzeBackBlock {
    private static String TAG;
    private QuickAdapter adapter;
    private List<MCDownloadVideoNode> checkNodes;
    private MCSectionModel section;
    private boolean isAllSelect;
    private MCDownloadServiceInterface mDownloadService;
    private MCCourseServiceInterface mCourseService;
    private Handler mHandler;
    private ListView mListView;
    private TextView title;
    private TextView tipInfo;
    private MCGuidanceView mGuidanceView;
    private RelativeLayout loading_layout;
    private LinearLayout bottom_layout;
    private RelativeLayout foot_layout;
    private BaseTitleView titleView;
    static {
        DownloadResourcesActivity.TAG = DownloadResourcesActivity.class.getSimpleName();
    }
    public boolean isNodeInList(MCDownloadVideoNode node) {
        for (MCDownloadVideoNode inner : checkNodes) {
            if (inner.getSectionId().equals(inner.getSectionId())) {
                return true;
            }
        }
        return false;
    }

    public DownloadResourcesActivity() {
        super();
        this.mDownloadService = null;
        this.mHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    adapter.notifyDataSetChanged();
                    super.handleMessage(msg);
                    return;
                }
                MCDownloadVideoNode videoNode = (MCDownloadVideoNode) msg.obj;
                ProgressBar seekBar = (ProgressBar) mListView.findViewWithTag("mSeekbar_" + videoNode.getSectionId());
                TextView status = (TextView) mListView.findViewWithTag("statusText_" + videoNode.getSectionId());
                ImageView statusImg = (ImageView) mListView.findViewWithTag("statusImg_" + videoNode.getSectionId());
                switch (msg.what) {
                    // updateProcess 更新进度
                    case 1: {
                        if (isNodeInList(videoNode)) {//不能用对象判断，因为对象每次进来都是会变的
                            if (seekBar != null) {
                                seekBar.setVisibility(View.VISIBLE);
                                int process = videoNode.getDownloadProgress();
                                seekBar.setProgress(process);
                            }
                            if (status != null) {
                                status.setText("开始下载……");
                            }
                            if (statusImg != null) {
                                statusImg.setVisibility(View.VISIBLE);
                                statusImg.getBackground().setLevel(0);
                            }
                        }
                        break;
                    }
                }
                super.handleMessage(msg);
            }
        };
    }

    public void OnAnalyzeBackBlock(MCServiceResult result, List retList) {
        this.adapter.clear();
        this.loading_layout.setVisibility(View.INVISIBLE);
        final Map<String, MCDownloadVideoNode> map = new LinkedHashMap<String, MCDownloadVideoNode>();
        List<MCResourceDetailModel> detailModel = null;
        if (retList != null && retList.size() > 0) {
            MCResourceModel resModel = (MCResourceModel) retList.get(0);
            detailModel = resModel.getDetailModelList();
        }
        if (detailModel == null && result.getResultCode() != MCResultCode.MC_RESULT_CODE_FAILURE) {
            result.setResultCode(MCResultCode.MC_RESULT_CODE_EMPTY);
        }
        if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
            this.bottom_layout.setVisibility(View.VISIBLE);
            for (int j = 0; j < detailModel.size(); j++) {
                MCResourceDetailModel model = detailModel.get(j);
                if (model.getResourcesType() == null) {
                    continue;
                }
                MCDownloadVideoNode videoNode = new MCDownloadVideoNode(this);
                videoNode.setSectionName(model.getName());
                videoNode.fileSize = model.getSize();
                videoNode.downloadUrl = model.getResourceLink();
                videoNode.setSectionId(model.getResourceId());
                videoNode.setNodeType(MCDownloadNodeType.MC_RESOURCE_TYPE);// 设置为资源类型下载的标记
                videoNode.setResourceSection(section.getId());// 记录是哪个节点下的
                videoNode.setResourcesType(model.getResourcesType());
                map.put(videoNode.getSectionId(), videoNode);
            }
            // 返回的跟本地的对比进行处理
            this.mDownloadService.getResourceBySectionId(section.getId(), new MCAnalyzeBackBlock() {
                @Override
                public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
                    List<MCDownloadVideoNode> dbResource = resultList;
                    // 数据库里已经存在的下载资料
                    for (MCDownloadVideoNode node : dbResource) {
                        MCDownloadVideoNode retNode = map.get(node.getSectionId());
                        if (retNode != null) {
                            node.filePath = FileUtils.getFilePathByName(node.getFilename(), DownloadResourcesActivity.this);
                            //node.isInDB = true;
                            node.setResourcesType(retNode.getResourcesType());
                            map.put(node.getSectionId(), node);// 替换成数据库里存的
                        }
                    }
                    // 构建集合
                    List<MCDownloadVideoNode> allList = new ArrayList<MCDownloadVideoNode>();
                    Set<String> keySet = map.keySet();
                    Iterator<String> iter = keySet.iterator();
                    while (iter.hasNext()) {
                        String key = iter.next();
                        allList.add(map.get(key));
                    }
                    //把刚一进来从全局变量里取的集合替换成新的对象。不然用contains判断就不准确了。
                    if (DownloadResourcesActivity.this.checkNodes.size() > 0) {
                        for (MCDownloadVideoNode node : allList) {
                            int index = indexOfCheckNodes(node);
                            if (index >= 0) {
                                DownloadResourcesActivity.this.checkNodes.remove(index);
                                DownloadResourcesActivity.this.checkNodes.add(node);
                            }
                        }
                    }
                    DownloadResourcesActivity.this.adapter.addAll(allList);
                }
            }, ((Context) this));
            checkAllButtonStatus();
        } else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_EMPTY) {
            this.mGuidanceView.setGuidanceBitmap(R.drawable.no_download_icon);
            this.mGuidanceView.setGuidanceText(R.string.no_download_res_label);
            this.mGuidanceView.setLayoutMarginTop(this.getResources().getDimensionPixelSize(R.dimen.mooc_180_dp));
            this.foot_layout.addView(this.mGuidanceView, 1, new FrameLayout.LayoutParams(-1, -1));
            return;
        }
    }

    public void initView() {
        this.titleView = (BaseTitleView) this.findViewById(R.id.rl_titile);
        this.mListView = (ListView) this.findViewById(R.id.mListView);
        this.mListView.setOnItemClickListener(this);
        this.tipInfo = (TextView) this.findViewById(R.id.tip_info);
        this.loading_layout = (RelativeLayout) this.findViewById(R.id.loading_layout);
        this.bottom_layout = (LinearLayout) this.findViewById(R.id.bottom_layout);
        this.foot_layout = (RelativeLayout) this.findViewById(R.id.foot_layout);
        this.findViewById(R.id.download_all).setOnClickListener(this);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.download_all) {
            if (!isAllSelect) {
                List<MCDownloadVideoNode> list = getAllCanChoicList();
                int count = 0;
                for (MCDownloadVideoNode node : list) {
                    if (!this.checkNodes.contains(node)) {
                        downloadNode(node, false);
                        count++;
                    }
                }
                MCDownloadQueue.getInstance().notifyChanged();//队列更新
                if (count > 0) {
                    this.adapter.notifyDataSetChanged();
                    MCToast.show(this, count + "个任务加入到下载队列！");
                } else {
                    MCToast.show(this, "没有需要下载的任务！");
                }
                isAllSelect = true;
                this.tipInfo.setText(getResources().getString(R.string.cancel_all));
            } else {
                List<MCDownloadVideoNode> currentList = new ArrayList<MCDownloadVideoNode>();
                currentList.addAll(this.checkNodes);
                int count = 0;
                for (MCDownloadVideoNode node : currentList) {
                    if (!node.isDownloadOver()) {
                        cancelDownLoadNode(node, false);//取消所有的 文件
                        count++;
                    }
                }
                MCDownloadQueue.getInstance().notifyChanged();//队列更新
                if (count > 0) {
                    this.mHandler.sendEmptyMessageDelayed(0, 100);//延迟一会再更新ui
                    MCToast.show(this, count + "个任务被取消！");
                } else {
                    MCToast.show(this, "没有需要取消的任务！");
                }
                isAllSelect = false;
                this.tipInfo.setText(getResources().getString(R.string.download_all));
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.download_resource_layout);
        isAllSelect = false;
        section = (MCSectionModel) this.getIntent().getExtras().getSerializable("section");
        // 如果全局变量里有就从全局变量里取，否则新建
        this.checkNodes = MCDownloadQueue.getDownloadMap().get(this.section.getId());
        if (this.checkNodes != null) {
            for (MCDownloadVideoNode node : this.checkNodes) {
                MCLog.d(TAG, "队列中有此任务：" + node.getSectionName());
                MCDownloadTask task = MCDownloadQueue.getInstance().mDownloadTasks.get(node.getSectionId());
                initTaskListener(task);// 原来正在进行的任务重新初始化一下listener
            }
        } else {
            this.checkNodes = new ArrayList<MCDownloadVideoNode>();
        }
        this.initView();
        this.adapter = new QuickAdapter(this, R.layout.download_resource_item_layout) {
            protected void convert(BaseAdapterHelper helper, final MCDownloadVideoNode item) {
                MCDownloadVideoNode videoNode = item;
                TextView statusView = (TextView) helper.getView(R.id.statusText);
                Button operateView = (Button) helper.getView(R.id.transfer);
                ProgressBar progressBar = (ProgressBar) helper.getView(R.id.mSeekbar);
                ImageView startView = (ImageView) helper.getView(R.id.downloadStart);
                ImageView statusImg = (ImageView) helper.getView(R.id.statusView);
                statusView.setTag("statusText_" + videoNode.getSectionId());
                operateView.setTag("transfer_" + videoNode.getSectionId());
                progressBar.setTag("mSeekbar_" + videoNode.getSectionId());
                statusImg.setTag("statusImg_" + videoNode.getSectionId());
                helper.getView(R.id.icon).getBackground().setLevel(videoNode.getResourcesType().toNumber());
                // 设置名字
                helper.setText(R.id.txt, videoNode.getSectionName());
                // 设置大小
                helper.setText(R.id.detail, FileUtils.FormetFileSizeSmall(videoNode.getFileSize()));
                // 下载完成的
                if (item.isDownloadOver()) {
                    startView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    operateView.setVisibility(View.VISIBLE);
                    statusView.setText("已下载");
                    operateView.setText("查看");
                    statusImg.setVisibility(View.VISIBLE);
                    statusImg.getBackground().setLevel(1);
                } else if (checkNodes.contains(item)) {//通过id判断是否在集合中
                    if (item.isDownloading) {
                        progressBar.setVisibility(View.VISIBLE);
                        statusView.setText("开始下载……");
                        statusImg.setVisibility(View.VISIBLE);
                        statusImg.getBackground().setLevel(0);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        statusView.setText("等待下载……");
                        statusImg.setVisibility(View.INVISIBLE);
                    }
                    operateView.setVisibility(View.VISIBLE);
                    startView.setVisibility(View.GONE);
                    operateView.setText("取消");
                } else {
                    startView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    operateView.setVisibility(View.GONE);
                    statusImg.setVisibility(View.INVISIBLE);
                    statusView.setText("");
                    operateView.setText("");
                }
                //点击条目和点击按钮做同样的事情，根据状态做出处理
                operateView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        operateItemByStatus(item);
                    }
                });
            }

            protected void convert(BaseAdapterHelper helper, Object node) {
                this.convert(helper, ((MCDownloadVideoNode) node));
            }
        };
        this.titleView.findViewById(R.id.left_img).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadResourcesActivity.this.setResult(-1);
                DownloadResourcesActivity.this.finish();
            }
        });
        this.mListView.setAdapter(this.adapter);
        this.mDownloadService = new MCDownloadService();
        this.mCourseService = new MCCourseService();
        //查询数据
        this.mCourseService.getDownloadResouce(section.getId(), ((MCAnalyzeBackBlock) this), this);
        this.mGuidanceView = new MCGuidanceView(this);
        this.mGuidanceView.setLayoutMarginTop(this.getResources().getDimensionPixelSize(R.dimen.mooc_192_dp));
        this.loading_layout.setVisibility(View.VISIBLE);
    }

    private int indexOfCheckNodes(MCDownloadVideoNode item) {
        MCDownloadVideoNode nowItem = null;
        for (int i = 0; i < checkNodes.size(); i++) {
            nowItem = checkNodes.get(i);
            if (nowItem.getSectionId().equals(item.getSectionId())) {
                item.isDownloading = nowItem.isDownloading;//替换一下是否正在下载的状态
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        MCDownloadVideoNode item = (MCDownloadVideoNode) adapter.getAdapter().getItem(position);
        operateItemByStatus(item);
    }

    private void operateItemByStatus(MCDownloadVideoNode item) {
        if (!MCDownloadQueue.getInstance().isDownloadNodeInQueue(item)) {
            downloadNode(item, true);
            this.adapter.notifyDataSetChanged();
        } else if (item.isDownloadOver()) {
            String filePath = item.filePath;
            if (!TextUtils.isEmpty(filePath)) {
                OpenFile.openFile(new File(filePath), DownloadResourcesActivity.this);
            } else {
                MCToast.show(DownloadResourcesActivity.this, "文件不存在呢……");
            }
        } else {
            cancelDownLoadNode(item, true);
            MCToast.show(DownloadResourcesActivity.this, "已取消" + item.getSectionName() + "的下载");
            mHandler.sendEmptyMessageDelayed(0, 300);
        }
        checkAllButtonStatus();
    }

    // 判断是否是已经全部下载了，是的话自动变成全部取消
    private void checkAllButtonStatus() {
        List<MCDownloadVideoNode> list = getAllCanChoicList();
        boolean isAllChecked = true;
        for (MCDownloadVideoNode node : list) {
            if (!this.checkNodes.contains(node)) {
                isAllChecked = false;
                break;
            }
        }
        if (isAllChecked) {
            isAllSelect = true;
            this.tipInfo.setText(getResources().getString(R.string.cancel_all));
        } else {
            isAllSelect = false;
            this.tipInfo.setText(getResources().getString(R.string.download_all));
        }
    }

    // 单个文档的下载,注册回调
    private void downloadNode(MCDownloadVideoNode node, boolean update) {
        checkNodes.add(node);
        MCDownloadTask task = new MCDownloadTask(node);
        initTaskListener(task);
        MCDownloadQueue.getInstance().addTask(task);
        MCDownloadHelper.saveVideoNodeToDB(node, DownloadResourcesActivity.this);
        if (update) {
            MCDownloadQueue.getInstance().notifyChanged();
        }
    }

    private void initTaskListener(MCDownloadTask task) {
        if (task == null) {
            return;
        }
        MCLog.d(TAG, "给task设置监听：" + task.getNode().getSectionName());
        task.setListener(new MCDownloadListener() {
            public void errorDownload(MCDownloadNode node, String error) {
                mHandler.sendEmptyMessage(0);
            }

            public void finishDownload(MCDownloadNode node) {
                checkNodes.remove(node);
                mHandler.sendEmptyMessage(0);
            }

            public void preDownload(MCDownloadNode node) {
                mHandler.sendEmptyMessage(0);
            }

            public void updateProcess(MCDownloadNode node) {
                Message message = new Message();
                message.what = 1;
                message.obj = node;
                message.arg1 = (int) node.getDownloadSize();
                message.arg2 = (int) node.fileSize;
                DownloadResourcesActivity.this.mHandler.sendMessage(message);
            }
        });
    }

    // 单个文档的取消下载
    private void cancelDownLoadNode(MCDownloadVideoNode item, boolean update) {
        checkNodes.remove(item);
        MCDownloadQueue.getInstance().deleteTaskBySectionId(item.getSectionId());
        if (update) {
            MCDownloadQueue.getInstance().notifyChanged();
        }
    }

    /**
     * 获取所有能选择的任务
     *
     * @return
     */
    private List<MCDownloadVideoNode> getAllCanChoicList() {
        List<MCDownloadVideoNode> list = this.adapter.getAdapterList();
        List<MCDownloadVideoNode> retList = new ArrayList<MCDownloadVideoNode>();
        for (MCDownloadVideoNode node : list) {
            if (!node.isDownloadOver()) {
                retList.add(node);
            }
        }
        return retList;
    }

    @Override
    protected void onDestroy() {
        if (MCDownloadQueue.getDownloadMap() != null) {
            MCDownloadQueue.getDownloadMap().put(this.section.getId(), this.checkNodes);
        }
        super.onDestroy();
    }
}
