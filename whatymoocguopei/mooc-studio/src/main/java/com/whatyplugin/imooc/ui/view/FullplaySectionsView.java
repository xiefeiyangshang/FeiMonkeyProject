package com.whatyplugin.imooc.ui.view;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.imooc.logic.model.MCChapterAndSectionModel;
import com.whatyplugin.imooc.logic.model.MCSectionModel;

public class FullplaySectionsView extends LinearLayout implements AdapterView.OnItemClickListener {
    public interface OnSectionListClickListener {
        void onSectionListClick(MCSectionModel arg1);
    }

    private View currentView;
    private OnSectionListClickListener listener;
    private Context mContext;
    private ListView mListView;
    private List mSectionList;

    public FullplaySectionsView(Context context, List arg14, MCSectionModel currentSection) {
    	super(context);
        ViewGroup v11 = null;
        this.mSectionList = new ArrayList();
        this.mContext = context;
        int v8 = 0;
        int v7;
        for(v7 = 0; v7 < arg14.size(); ++v7) {
            Object v6 = arg14.get(v7);
            if(((MCChapterAndSectionModel)v6).getSection() != null && (((MCChapterAndSectionModel)v6).getSection().getChapterSeq() == currentSection.getChapterSeq() || ((MCChapterAndSectionModel)v6).getSection().getChapterId() == currentSection.getChapterId())) {
                if(((MCChapterAndSectionModel)v6).getSection().getId() == currentSection.getId()) {
                    v8 = v7;
                }

                this.mSectionList.add(((MCChapterAndSectionModel)v6).getSection());
            }
        }

        View v9 = LayoutInflater.from(this.mContext).inflate(R.layout.fullplay_section_layout, v11);
        this.mListView = (ListView) v9.findViewById(R.id.listview);
        this.mListView.setOnItemClickListener(((AdapterView.OnItemClickListener)this));
        this.mListView.setAdapter(new QuickAdapter() {
            protected void convert(BaseAdapterHelper helper, MCSectionModel item) {
             //   if(this.val.currentSection.getId() == item.getId()) {
                    helper.getView().setBackgroundColor(FullplaySectionsView.this.mContext.getResources().getColor(R.color.fullplay_listview_selected_bg));
                    FullplaySectionsView.this.currentView = helper.getView();
              //  }
              //  else {
                 //   helper.getView().setBackgroundColor(FullplaySectionsView.this.mContext.getResources().getColor(17170445));
            //    }

                helper.setText(R.id.name, item.getName());
            }

            protected void convert(BaseAdapterHelper arg1, Object arg2) {
                this.convert(arg1, ((MCSectionModel)arg2));
            }
        });
        this.currentView = this.mListView.getAdapter().getView(v8, ((View)v11), this.mListView);
        this.currentView.setBackgroundColor(this.mContext.getResources().getColor(R.color.fullplay_listview_selected_bg));
        this.addView(v9);
    }

    static Context access$0(FullplaySectionsView arg1) {
        return arg1.mContext;
    }

    static void access$1(FullplaySectionsView arg0, View arg1) {
        arg0.currentView = arg1;
    }

    public List getmSectionList() {
        return this.mSectionList;
    }

    public void onItemClick(AdapterView arg5, View view, int position, long id) {
        this.listener.onSectionListClick((MCSectionModel) arg5.getAdapter().getItem(position));
        this.currentView.setBackgroundColor(this.mContext.getResources().getColor(17170445));
        view.setBackgroundColor(this.mContext.getResources().getColor(R.color.fullplay_listview_selected_bg));
        this.currentView = view;
    }

    public void setOnSectionListClickListener(OnSectionListClickListener listener) {
        this.listener = listener;
    }
}

