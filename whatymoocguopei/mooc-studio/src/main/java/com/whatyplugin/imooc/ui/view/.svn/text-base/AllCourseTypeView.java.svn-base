package com.whatyplugin.imooc.ui.view;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.asyncimage.MCAsyncImageDefine.ImageType;
import com.whatyplugin.base.asyncimage.MCCacheManager;
import com.whatyplugin.base.asyncimage.MCImageHandleInterface;
import com.whatyplugin.base.asyncimage.MCImageView;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.base.network.MCNetworkDefine.MCNetworkStatus;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCCourseTypeModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackMapBlock;
import com.whatyplugin.imooc.logic.service_.MCCourseService;
import com.whatyplugin.imooc.logic.service_.MCCourseServiceInterface;
import com.whatyplugin.uikit.resolution.MCResolution;

public class AllCourseTypeView extends LinearLayout implements MCAnalyzeBackMapBlock {
   
	
	class AllCourseTypeAdapter extends ArrayAdapter {
        private Context context;
        private List list;
        
        public AllCourseTypeAdapter(AllCourseTypeView arg2, Context context, List arg4) {
        	super(context, 0, arg4);
             this.context = context;
             this.list = arg4;
        }

        public int getCount() {
            return this.list.size();
        }

        public MCCourseTypeModel getItem(int position) {
            return (MCCourseTypeModel)this.list.get(position);
        }

        public long getItemId(int position) {
            return ((long)position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder v10 = null;
            ViewGroup v7 = null;
            if(convertView == null) {
                convertView = LayoutInflater.from(this.context).inflate(R.layout.all_course_type_item, v7);
                v10 = new ViewHolder(AllCourseTypeView.this, v7);
                v10.imageView = (MCImageView) convertView.findViewById(R.id.course_type_image);
                v10.courseName = (TextView) convertView.findViewById(R.id.course_name);
                v10.courseNum = (TextView) convertView.findViewById(R.id.course_num);
                v10.selectedImg = (ImageView) convertView.findViewById(R.id.course_type_selected);
                convertView.setTag(v10);
            }
            else {
            	v10 = (ViewHolder) convertView.getTag();
            }

            MCCourseTypeModel typeModel = (MCCourseTypeModel) this.list.get(position);
            ViewGroup.LayoutParams v8 = v10.imageView.getLayoutParams();
            v8.width = MCResolution.getInstance(this.context).scaleWidth(Contants.COURSE_TYPE_IMAGE_WIDTH);
            v8.height = v8.width;
            if((MCNetwork.checkedNetwork(this.context)) || position != 0) {
                v10.imageView.setImageUrl(typeModel.getImgUrl(), MCCacheManager.getInstance().getImageLoader(), v8.width, v8.height, false, ImageType.CICLE_IMAGE, ((MCImageHandleInterface)v7));
            }
            else {
                v10.imageView.setDefaultImageResId(R.drawable.allcourse_default_bg);
                v10.imageView.setImageUrl(v7.toString(), MCCacheManager.getInstance().getImageLoader(), v8.width, v8.height, false, ImageType.CICLE_IMAGE, ((MCImageHandleInterface)v7));
            }

            v10.courseName.setText(typeModel.getName());
            v10.courseNum.setText(AllCourseTypeView.this.getResources().getString(R.string.talk_detail_replynum, new Object[]{Integer.valueOf(((MCCourseTypeModel)typeModel).getCourseNum())}));
            if(position == AllCourseTypeView.this.currentIndex) {
                v10.selectedImg.setVisibility(0);
            }
            else {
                v10.selectedImg.setVisibility(8);
            }

            return convertView;
        }
    }

    public interface IOnCourseTypeItemClick {
        void onCourseTypeItemClick(String arg1, MCCourseTypeModel arg2);
    }

    class ViewHolder {
        TextView courseName;
        TextView courseNum;
        MCImageView imageView;
        ImageView selectedImg;

        ViewHolder(AllCourseTypeView arg1, ViewGroup v7) {
        }

        private ViewHolder(AllCourseTypeView arg1) {
        }
    }

    private AllCourseTypeAdapter adapter;
    private Context context;
    private List courseTypeList;
    private int currentIndex;
    private MCGuidanceView guideView;
    private boolean isRegister;
    private ListView listView;
    private IOnCourseTypeItemClick listener;
    private MCCourseServiceInterface mCourseService;
    private BroadcastReceiver mNetworkListener;
    private AdapterView.OnItemClickListener onItemClickListener;
    private String[] types_keys;
    private String uid;

    public AllCourseTypeView(Context context) {
        super(context);
        this.uid = Contants.DEFAULT_UID;
        this.types_keys = new String[]{"categories"};
        this.currentIndex = 0;
        this.isRegister = false;
        this.mNetworkListener = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                int v1 = intent.getExtras().getInt("previous_network");
                if(MCNetwork.currentNetwork(context) != MCNetworkStatus.MC_NETWORK_STATUS_NONE && v1 == MCNetworkStatus.MC_NETWORK_STATUS_NONE.value()) {
                    AllCourseTypeView.this.mCourseService.getCourseType(AllCourseTypeView.this.uid, AllCourseTypeView.this, context);
                }
            }
        };
        this.onItemClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg4, View view, int position, long id) {
                if(AllCourseTypeView.this.courseTypeList != null && AllCourseTypeView.this.courseTypeList.size() > 0 && AllCourseTypeView.this.listener != null) {
                    AllCourseTypeView.this.listener.onCourseTypeItemClick(AllCourseTypeView.this.types_keys[0], (MCCourseTypeModel) AllCourseTypeView.this.courseTypeList.get(position));
                    AllCourseTypeView.this.currentIndex = position;
                    if(AllCourseTypeView.this.adapter != null) {
                        AllCourseTypeView.this.adapter.notifyDataSetInvalidated();
                    }
                }
            }
        };
        this.context = context;
        this.init(context);
    }

    public void OnAnalyzeBackBlock(MCServiceResult result, Map map) {
        int v6 = R.dimen.mooc_110_dp;  // R.dimen.mooc_110_dp
        int v5 = R.string.no_network_label;  // R.string.no_network_label
        int v4 = R.drawable.no_network_icon;  // R.drawable.no_network_icon
        
        //设置模拟数据
        result.setResultCode(MCResultCode.MC_RESULT_CODE_SUCCESS);
        List<MCCourseTypeModel> list = new ArrayList<MCCourseTypeModel>();
        MCCourseTypeModel model = new MCCourseTypeModel();
        model.setName("体育类");
        model.setId("1");
        model.setCourseNum(12);
        model.setImgUrl("http://pic22.nipic.com/20120715/4442934_200235184103_2.jpg");
        MCCourseTypeModel model1 = new MCCourseTypeModel();
        model1.setName("金融类");
        model1.setId("2");
        model1.setImgUrl("http://img1.imgtn.bdimg.com/it/u=3058495989,3952572650&fm=21&gp=0.jpg");
        model1.setCourseNum(3);
        MCCourseTypeModel model2 = new MCCourseTypeModel();
        model2.setName("理工类");
        model2.setId("3");
        model2.setImgUrl("http://img0.imgtn.bdimg.com/it/u=3180799282,1501306968&fm=21&gp=0.jpg");
        model2.setCourseNum(15);
        list.add(model);
        list.add(model1);
        list.add(model2);
        map.put("categories", list);
        
        if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
            this.guideView.setVisibility(8);
            String[] v3 = this.types_keys;
            v4 = v3.length;
            int v2;
            for(v2 = 0; v2 < v4; ++v2) {
                this.courseTypeList = (List) map.get(v3[v2]);
                if(this.courseTypeList != null && this.courseTypeList.size() > 0) {
                    this.adapter = new AllCourseTypeAdapter(this, this.context, this.courseTypeList);
                    this.listView.setAdapter(this.adapter);
                }
            }
        }
        else {
            if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_EMPTY) {
                return;
            }

            if(result.isExposedToUser()) {
                this.adapter.clear();
                this.adapter.notifyDataSetChanged();
                return;
            }

            if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
                this.guideView.setVisibility(0);
                this.guideView.setGuidanceBitmap(v4);
                this.guideView.setGuidanceText(v5);
                this.guideView.setLayoutMarginTop(this.getResources().getDimensionPixelSize(v6));
                return;
            }

            this.guideView.setVisibility(0);
            this.guideView.setGuidanceBitmap(v4);
            this.guideView.setGuidanceText(v5);
            this.guideView.setLayoutMarginTop(this.getResources().getDimensionPixelSize(v6));
        }
    }

    static MCCourseServiceInterface access$0(AllCourseTypeView arg1) {
        return arg1.mCourseService;
    }

    static String access$1(AllCourseTypeView arg1) {
        return arg1.uid;
    }

    static List access$2(AllCourseTypeView arg1) {
        return arg1.courseTypeList;
    }

    static IOnCourseTypeItemClick access$3(AllCourseTypeView arg1) {
        return arg1.listener;
    }

    static String[] access$4(AllCourseTypeView arg1) {
        return arg1.types_keys;
    }

    static void access$5(AllCourseTypeView arg0, int arg1) {
        arg0.currentIndex = arg1;
    }

    static AllCourseTypeAdapter access$6(AllCourseTypeView arg1) {
        return arg1.adapter;
    }

    static int access$7(AllCourseTypeView arg1) {
        return arg1.currentIndex;
    }

    public int getCourseTypeListSize() {
        int size = this.courseTypeList != null ? this.courseTypeList.size() : 0;
        return size;
    }

    private void init(Context context) {
        this.mCourseService = new MCCourseService();
        View v1 = LayoutInflater.from(context).inflate(R.layout.all_course_type_layout, null);
        this.guideView = (MCGuidanceView) v1.findViewById(R.id.no_network);
        this.listView = (ListView) v1.findViewById(R.id.course_type_listview);
        if(Build.VERSION.SDK_INT >= 9) {
            this.listView.setOverScrollMode(2);
        }

        this.listView.setOnItemClickListener(this.onItemClickListener);
        this.addView(v1);
        try {
            this.uid = MCSaveData.getUserInfo(Contants.UID, context).toString();
        }
        catch(Exception v2) {
        }

        this.queryCourseType();
        if(!this.isRegister) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Contants.NETWORK_CHANGED_ACTION);
            context.registerReceiver(this.mNetworkListener, filter);
            this.isRegister = true;
        }
    }

    public void queryCourseType() {
        if(this.mCourseService != null) {
            this.mCourseService.getCourseType(this.uid, ((MCAnalyzeBackMapBlock)this), this.context);
        }
    }

    public void setOnCourseTypeItemClick(IOnCourseTypeItemClick listener) {
        this.listener = listener;
    }

    public void unRegisterReceiver() {
        if((this.isRegister) && this.context != null) {
            this.context.unregisterReceiver(this.mNetworkListener);
            this.isRegister = false;
        }
    }
}

