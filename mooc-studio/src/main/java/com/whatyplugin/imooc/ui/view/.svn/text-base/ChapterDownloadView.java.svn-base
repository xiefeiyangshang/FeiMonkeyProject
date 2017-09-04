package com.whatyplugin.imooc.ui.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.define.MCBaseDefine.MCMediaType;
import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.base.download.MCDownloadNode;
import com.whatyplugin.base.download.MCDownloadQueue;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.base.network.MCNetworkDefine.MCNetworkStatus;
import com.whatyplugin.base.storage.MCUserDefaults;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCChapterAndSectionModel;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.logic.model.MCSectionModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCCourseService;
import com.whatyplugin.imooc.logic.utils.FileUtils;
import com.whatyplugin.uikit.dialog.MCCommonDialog;
import com.whatyplugin.uikit.pinnedlistview.PinnedSectionListView;
import com.whatyplugin.uikit.pinnedlistview.PinnedSectionListView.PinnedSectionListAdapter;
import com.whatyplugin.uikit.toast.MCToast;

public class ChapterDownloadView extends RelativeLayout implements View.OnClickListener, AdapterView.OnItemClickListener {
	private MCCreateDialog createDialog = new MCCreateDialog();
    private Handler mHandler = new Handler();
	public class MyBasePinnedAdapter extends BaseAdapter implements PinnedSectionListAdapter {
        class Holder {
            LinearLayout chapter_layout;
            TextView chapter_name;
            ImageView download_status;
            RelativeLayout section_layout;
            View section_line;
            TextView section_name;
            ImageView section_type;
            LinearLayout lesson_layout;
			TextView lesson_name;
            Holder(MyBasePinnedAdapter arg1) {
                super();
            }
        }

        private LayoutInflater inflater;
        private List<MCChapterAndSectionModel> list;
        private Context mContext;
        
        public MyBasePinnedAdapter(ChapterDownloadView view, Context context, List list) {
            super();
            this.inflater = null;
            this.mContext = context;
            this.list = list;
            this.inflater = LayoutInflater.from(this.mContext);
        }

        public int getCount() {
            return this.list.size();
        }

        public MCChapterAndSectionModel getItem(int position) {
            return this.list.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public int getItemViewType(int position) {
            return this.getItem(position).getWhichType();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
        	Holder holder = null;
            if(convertView == null) {
            	holder = new Holder(this);
            	convertView = this.inflater.inflate(R.layout.chapter_download_item_layout, null);
                holder.section_name = (TextView) convertView.findViewById(R.id.section_name);
                holder.chapter_name = (TextView) convertView.findViewById(R.id.chapter_name);
                holder.download_status = (ImageView) convertView.findViewById(R.id.download_checked);
                holder.chapter_layout = (LinearLayout) convertView.findViewById(R.id.chapter_layout);
                holder.section_layout = (RelativeLayout) convertView.findViewById(R.id.section_layout);
                holder.section_line = convertView.findViewById(R.id.section_line);
                holder.section_type = (ImageView) convertView.findViewById(R.id.section_type);
                
            	holder.lesson_name =  (TextView) convertView.findViewById(R.id.lesson_name);
				holder.lesson_layout = (LinearLayout) convertView.findViewById(R.id.lesson_layout); 
				
                convertView.setTag(holder);
            }
            else {
                holder = (Holder) convertView.getTag();
            }

            MCChapterAndSectionModel model = this.getItem(position);
			
            if (model.getType() == 1) {
				holder.chapter_layout.setVisibility(View.VISIBLE);
				holder.chapter_name.setText(model.getChapter().getName());
				holder.section_layout.setVisibility(View.GONE);
				holder.lesson_layout.setVisibility(View.GONE);
			} else if(model.getType() == 2){
				holder.lesson_layout.setVisibility(View.VISIBLE);
				holder.lesson_name.setText(model.getChapter().getName());
				holder.section_layout.setVisibility(View.GONE);
				holder.chapter_layout.setVisibility(View.GONE);
			} else{
            	MCSectionModel sectionModel = model.getSection();

            	//设置图片
            	int num = sectionModel.getType().toNumber();
            	holder.section_type.getDrawable().setLevel(num);
                holder.section_layout.setVisibility(View.VISIBLE);
                holder.section_name.setText(sectionModel.getName());
                holder.chapter_layout.setVisibility(View.GONE);
                holder.download_status.setVisibility(View.VISIBLE);
				holder.lesson_layout.setVisibility(View.GONE);
                //最后一个不显示下划线
                if(position == this.getCount() - 1) {
                    holder.section_line.setVisibility(View.VISIBLE);
                }
                else {
                    holder.section_line.setVisibility(View.INVISIBLE);
                }

                if(ChapterDownloadView.this.checkedSectiones.contains(model) || checkedSFPSections.contains(model) ) {
                    holder.download_status.getBackground().setLevel(1);
                }
                else {
                    holder.download_status.getBackground().setLevel(0);
                }
                
                if(sectionModel.getType() == MCMediaType.MC_VIDEO_TYPE) {
                    if(MCDownloadQueue.mDownloadTasks != null && MCDownloadQueue.mDownloadTasks.get(sectionModel.getId()) != null) {
                        if((MCDownloadQueue.mDownloadTasks.get(sectionModel.getId()).getNode().isDownloadOver()) && !FileUtils.isFileExistsInPhoneOrSdcard(MCDownloadQueue.mDownloadTasks.get(sectionModel.getId()).getNode().getFilename(), this.mContext)) {
                            holder.download_status.setVisibility(View.VISIBLE);
                        }else{
                        	holder.download_status.setVisibility(View.INVISIBLE);
                        }
                    }else{
                    	holder.download_status.setVisibility(View.VISIBLE);
                    }
                    return convertView;
                }
                
                if(sectionModel.getType() == MCMediaType.MC_COURSEWARE_TYPE) {  	
                	if(MCDownloadQueue.mDownloadTasks.get(sectionModel.getId() + Contants.SFP_SUFFIX) != null) {
                		holder.download_status.setVisibility(View.INVISIBLE);
                	}else{
                		holder.download_status.setVisibility(View.VISIBLE);
                	}
                	return convertView;
                }

                holder.download_status.setVisibility(View.INVISIBLE);
            }

            return convertView;
        }

        public int getViewTypeCount() {
            return 2;
        }

        public boolean isItemViewTypePinned(int viewType) {
            boolean pinned = true;
            if(viewType != 1) {
                pinned = false;
            }

            return pinned;
        }
    }
    public static final String TAG = "ChapterDownloadView";	
    private MyBasePinnedAdapter adapter;
    private ImageView back;
    private TextView checkedAll_tv;
    private List<MCChapterAndSectionModel> checkedSectiones;
    private List<MCChapterAndSectionModel> checkedSFPSections;
    private TextView download_tv;
    private LayoutInflater inflater;
    private List list;
    private Context mContext;
    private MCCourseModel mCourse;
    private PinnedSectionListView mPinnedListView;
    private PopupWindow pop;
    private int totalSections;
    private Dialog dialog;
    public ChapterDownloadView(Context context, List arg8, MCCourseModel mCourse) {
        super(context);
        this.checkedSectiones = new ArrayList<MCChapterAndSectionModel>();
        this.checkedSFPSections = new ArrayList<MCChapterAndSectionModel>();
        this.totalSections = 0;
        this.mContext = context;
        this.list = arg8;
        this.mCourse = mCourse;
        this.inflater = LayoutInflater.from(context);
        View v1 = this.inflater.inflate(R.layout.chapter_download_layout, null);
        this.back = (ImageView)v1.findViewById(R.id.back);
        this.back.setOnClickListener(((View.OnClickListener)this));
        this.checkedAll_tv = (TextView)v1.findViewById(R.id.checkedAll_tv);
        this.checkedAll_tv.setOnClickListener(((View.OnClickListener)this));
        this.download_tv = (TextView)v1.findViewById(R.id.download_tv);
        this.download_tv.setOnClickListener(((View.OnClickListener)this));
        this.mPinnedListView = (PinnedSectionListView)v1.findViewById(R.id.pinnedListview);
        this.adapter = new MyBasePinnedAdapter(this, this.mContext, this.list);
        this.mPinnedListView.setAdapter(this.adapter);
        this.mPinnedListView.setShadowVisible(false);
        this.mPinnedListView.setOnItemClickListener(((AdapterView.OnItemClickListener)this));
        Iterator iterator = this.list.iterator();
        while(iterator.hasNext()) {
        	MCChapterAndSectionModel model = (MCChapterAndSectionModel) iterator.next();
            if(model.getSection() == null) {
                continue;
            }

            if(model.getSection().getType() != MCMediaType.MC_VIDEO_TYPE) {
                continue;
            }

            ++this.totalSections;
        }

        this.addView(v1);
    }

    public void onClick(View v) {
    	MCChapterAndSectionModel sectionModel;
        int v11 = R.string.chapter_download_uncheckedall;  // R.string.chapter_download_uncheckedall
        int id = v.getId();
		if (id == R.id.back) {
			this.pop.setFocusable(false);
			this.pop.dismiss();
			return;
		} else if (id == R.id.checkedAll_tv) {
			this.checkedSectiones.clear();
			if(this.checkedAll_tv.getText().equals(this.mContext.getResources().getString(v11))) {
			      this.adapter.notifyDataSetChanged();
			      this.checkedAll_tv.setText(this.mContext.getResources().getString(R.string.chapter_download_checkedall));
			      this.adapter.notifyDataSetChanged();
			      return;
			  }
			Iterator<MCChapterAndSectionModel> v8 = this.list.iterator();
			while(v8.hasNext()) {
			      sectionModel = v8.next();
			      if(sectionModel.getSection() == null) {
			          continue;
			      }

			      if(sectionModel.getSection().getType() != MCMediaType.MC_VIDEO_TYPE) {
			          continue;
			      }

			      try {
			          if(MCDownloadQueue.mDownloadTasks.get(sectionModel.getSection().getId()) == null) {
			              this.checkedSectiones.add(sectionModel);
			              continue;
			          }

			          MCDownloadNode v5 = MCDownloadQueue.mDownloadTasks.get(sectionModel.getSection().getId()).getNode();
			          if(!v5.isDownloadOver()) {
			              continue;
			          }

			          if(FileUtils.isFileExistsInPhoneOrSdcard(v5.getFilename(), this.mContext)) {
			              continue;
			          }

			          this.checkedSectiones.add(sectionModel);
			          continue;
			      }
			      catch(Exception e) {
			    		e.printStackTrace();
			      		continue;
			      }
			  }
			if(this.checkedSectiones.size() <= 0) {
			  }
			  else {
			      this.checkedAll_tv.setText(this.mContext.getResources().getString(v11));
			      this.adapter.notifyDataSetChanged();
			      return;
			  }
			this.adapter.notifyDataSetChanged();
			return;
		} else if (id == R.id.download_tv) {
			if (MCNetwork.currentNetwork(this.mContext) != MCNetworkStatus.MC_NETWORK_STATUS_WWAN) {
				downloadSections();
				return;
			 }
			if (MCUserDefaults.getUserDefaults(this.mContext, Contants.NETWORK).getBoolean(Contants.NETWORK_SETTING)) {
				final MCCommonDialog dialogView = new MCCommonDialog(this.mContext.getString(R.string.download_network_title),
						this.mContext.getString(R.string.nowifi_download_label), R.layout.network_dialog_layout);
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						dialogView.setCommitListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialogView.dismiss();
								downloadSections();
							}
						});
					}
				}, 200);
				dialogView.show(createDialog.getFragmentTransaction((Activity) this.mContext), "删除");
			} else {
				MCToast.show(mContext, "现在是非WiFi环境不允许下载！！如有需要请在设置中设置");
			}
		}
		return;

	}

    /**
     * 将选择的节点添加到下载队列
     */
    private void downloadSections() {
        long sdsize;
        final MCCommonAlertDialog mCommonDialog ;

        if (FileUtils.isTwoSdcard(mContext)) {
            sdsize = FileUtils.getAvailaleSDSize(MCSaveData.getDownloadSDcardPath(mContext));
        } else {
            sdsize = FileUtils.getAvailaleSDSize(Environment.getExternalStorageDirectory().getPath());
        }
        if(sdsize < 500 * 1024 * 1024){//判断手机使用内存
            new MCCreateDialog().createOkDialog((Activity)mContext, "您的手机剩余内存空间不足");
        }else {
            MCCourseService courseService = new MCCourseService();
            String msg = null;
            if (checkedSFPSections.size() > 0) {
                msg = "缓存的视频中有电子课件类型，\n操作时间可能会稍长，请稍后~";
            }
            else {
                msg = "正在添加到缓存队列，请稍后~";
            }
            final MCCommonDialog loading_dialog = createDialog.createLoadingDialog((Activity) mContext, msg, 0);
            loading_dialog.setCancelable(false);
            courseService.addCheckSFPToDownloadQueue(mCourse, checkedSectiones, checkedSFPSections, new MCAnalyzeBackBlock() {
                public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
                    loading_dialog.dismiss();
                    MCToast.show(mContext, result.getResultDesc());
                }
            }, mContext);
            this.pop.setFocusable(false);
            this.pop.dismiss();
        }
        return;
    }

    public void onItemClick(AdapterView arg7, View view, int position, long id) {
        int v5 = R.string.chapter_download_uncheckedall;  // R.string.chapter_download_uncheckedall
        int v4 = R.string.chapter_download_checkedall;  // R.string.chapter_download_checkedall
        MCChapterAndSectionModel model = (MCChapterAndSectionModel) arg7.getAdapter().getItem(position);
        if(model.getSection() != null && model.getSection().getType() == MCMediaType.MC_VIDEO_TYPE) {
            if(MCDownloadQueue.mDownloadTasks.get(model.getSection().getId()) == null) {
                if(this.checkedSectiones.contains(model)) {
                    this.checkedSectiones.remove(model);
                }
                else {
                    this.checkedSectiones.add(model);
                }

                if(this.checkedSectiones.size() == this.totalSections) {
                    this.checkedAll_tv.setText(this.getResources().getString(v5));
                }
                else {
                    this.checkedAll_tv.setText(this.getResources().getString(v4));
                }

                this.adapter.notifyDataSetChanged();
            }
            else {
                if(FileUtils.isFileExistsInPhoneOrSdcard(MCDownloadQueue.mDownloadTasks.get(model.getSection().getId()).getNode().getFilename(), this.mContext)) {
                    return;
                }

                if(this.checkedSectiones.contains(model)) {
                    this.checkedSectiones.remove(model);
                }
                else {
                    this.checkedSectiones.add(model);
                }

                if(this.checkedSectiones.size() == this.totalSections) {
                    this.checkedAll_tv.setText(this.getResources().getString(v5));
                }
                else {
                    this.checkedAll_tv.setText(this.getResources().getString(v4));
                }

                this.adapter.notifyDataSetChanged();
            }
        }else if(model.getSection() != null && model.getSection().getType() == MCMediaType.MC_COURSEWARE_TYPE) {
        	  if(this.checkedSFPSections.contains(model)) {
                  this.checkedSFPSections.remove(model);
              } else {
                  this.checkedSFPSections.add(model);
              }
        	  this.adapter.notifyDataSetChanged();
        }
    }

    public void setPop(PopupWindow pop) {
        this.pop = pop;
    }
}

