package com.whatyplugin.imooc.ui.live;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.imooc.logic.model.MCLiveOnLineModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;

public class MCLiveOnLineActivity extends MCBaseActivity {
    private TextView tv_time;
    private TextView tv_liveName;
    private TextView tv_liveNote;
    private Button bt_comeIn;
    private MCStudyService studyService;
    private String itemsId;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liveonlineactivity);
        studyService = new MCStudyService();
        itemsId = getIntent().getStringExtra("itemsId");
        initView();
        requestDate();

    }

    // 请求地址返回
    private void requestDate() {
        studyService.getLiveOnLine(itemsId, new MCAnalyzeBackBlock() {

            @Override
            public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
                if (resultList != null & resultList.size() > 0) {
                    MCLiveOnLineModel lineModel = (MCLiveOnLineModel) resultList.get(0);
                    tv_liveName.setText(lineModel.getTitle());
                    tv_liveNote.setText(lineModel.getNote());
                    bt_comeIn.setText(lineModel.getButtonText());
                    tv_time.setText(lineModel.showTime());
                    bt_comeIn.setEnabled(lineModel.getStatus() == 0 ? false : true);
                    bt_comeIn.setBackgroundResource(lineModel.getStatus() == 0 ? R.drawable.live_come_in_btn_off : R.drawable.live_come_in_btn_on);
                    url = lineModel.getLiveLink();
                }

            }
        }, this);
    }

    private void initView() {
        tv_time = (TextView) findViewById(R.id.live_time);
        tv_liveName = (TextView) findViewById(R.id.live_name);
        tv_liveNote = (TextView) findViewById(R.id.live_note);
        bt_comeIn = (Button) findViewById(R.id.come_in_live);
        bt_comeIn.setBackgroundResource(R.drawable.live_come_in_btn_off);
        bt_comeIn.setEnabled(false);
        bt_comeIn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 可以跳转
                Intent intent = new Intent(MCLiveOnLineActivity.this, MCLiveHomeActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);

            }
        });

    }

}
