package com.whaty.imooc.ui.index;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.whaty.imooc.logic.model.MCMyUserModel;
import com.whaty.imooc.ui.login.MCNewLoginActivity;
import com.whaty.imooc.utile.GPContants;
import com.whaty.imooc.utile.SharedClassInfo;
import com.whatyplugin.base.storage.MCUserDefaults;
import com.whatyplugin.base.utils.MCCourseConst;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCFullUserModel;
import com.whatyplugin.imooc.logic.proxy.MCLoginProxy;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.logic.utils.RequestUrl;
import com.whatyplugin.imooc.ui.mymooc.MoocApplication;
import com.whatyplugin.imooc.ui.note.MCNoteListActivity;
import com.whatyplugin.imooc.ui.question.MCQuestionMainActivity;

import java.util.Map;

public class GPInitInformation {

    public static String WEBTRN_TEST_LOCAL_URL = "http://gp.ahtvu.ah.cn";
    public static final String WEBTRN_TEST_LOCAL = "http://2015ahdd.learn.webtrn.cn";
    public static final String SET_WEBTRN_USER_Avatar = WEBTRN_TEST_LOCAL_URL + "/saveAvatarServlet";
    public static final String SET_WEBTRN_USER_Avatar_New = WEBTRN_TEST_LOCAL_URL + "/learning/appSave/avatar_saveUserAvatar.action";

    /**
     * 这里面初始化依赖库里面的信息
     */
    public static void initPluginParams(final Context context) {

        initConstParams(context);

        /**
         * 跳转到登陆的回调
         */
        MCLoginProxy.loginInstance().setLoginInterface(new MCLoginProxy.MCLoginInterface() {

            @Override
            public void loginInListener(Context context) {
                Intent intent = new Intent(context, MCNewLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }

            @Override
            public void jumpToMainListener(Context context) {
                context.startActivity(new Intent(context, MCMainActivity.class));
            }
        });
        /**
         * 保存自己的一些userinfo
         */
        MCSaveData.setOnSaveUserDataListener(new MCSaveData.MCSaveDataListener() {

            @Override
            public void onSaveUserData(MCFullUserModel model) {
                MCMyUserModel user = (MCMyUserModel) model;
                MCUserDefaults.getUserDefaults(MoocApplication.getInstance(), Contants.USERINFO_FILE).putString(Contants.NICKNAME, user.getNickname());
                MCUserDefaults.getUserDefaults(context, Contants.USERINFO_FILE).putString(Contants.ADDRESS, user.getLearSpaceAddress().replaceFirst("/$", ""));
                MCUserDefaults.getUserDefaults(context, Contants.USERINFO_FILE).putString(Contants.LEARLOGINTYPE, user.getLearLoginType());
            }
        });

        String learnSpace = MCSaveData.getUserInfo(Contants.ADDRESS, context).toString();
        Const.SITE_LOCAL_URL = learnSpace;
    }

    // 根据RUN_TYPE决定链接那个站点
    private static void initConstParams(final Context context) {

        // 站点信息
        Const.BASE_PATH = "/learnspace";
//		Const.SITECODE = "2015ahdd";
        Const.SITECODE = SharedClassInfo.getKeyValue(GPContants.CODESITE).equals("") ? "2015gkgp" : SharedClassInfo.getKeyValue(GPContants.CODESITE);
        // Const.SITECODE = "code161";
        // 默认账号密码
        Const.SHOW_USERNAME = true;
//        Const.USERNAME = "gkgpceshi001";
//        Const.PASSWORD = "gkgpceshi001";
        // Const.USERNAME = "ahddgp166";
        // Const.PASSWORD = "ahddgp166";

        Const.USERNAME = "2015mncs1";

        Const.USERNAME = "mystu2";
        Const.PASSWORD = "whaty123";
//        Const.USERNAME = "xngp018";
//        Const.PASSWORD = "xngp018";
//        Const.USERNAME = "xngp002";
//        Const.PASSWORD = "a1111111";
        Const.USERNAME = "16ggcs1";
        Const.PASSWORD = "16ggcs1";
        // 自动更新及邮件发送
        Const.DOWNURL = "http://software.webtrn.cn/cms/gpyddandroid/index.htm";
        Const.SENDMAIL = new String[]{"wangyabin@whaty.com"};

        // 是否可以注册
        Const.CAN_REGESIT = false;
        updateMyRequestUrl(context);
    }

    /**
     * 功能请求地址的变更 改变了Const里面的SITE_LOCAL_URL 调用下下面的2个方法更新路径 RequestUrl.initUrl();
     * MCInitInformation.updateMyRequestUrl();
     */
    private static void updateMyRequestUrl(Context context) {

        Object obj = MCSaveData.getUserInfo(Contants.ADDRESS, context);

        if (obj != null && !TextUtils.isEmpty(obj.toString())) {
            Const.SITE_LOCAL_URL = obj.toString();
        }

        // 重新初始化地址
        RequestUrl.initUrl();

        RequestUrl.getInstance().GET_USRINFO_URL = WEBTRN_TEST_LOCAL_URL + "/app/study/appUserInfo.action";

        RequestUrl.getInstance().SET_USRINFO_URL = WEBTRN_TEST_LOCAL_URL + "/app/study/appUserInfo_updateUserInfo.action";

        RequestUrl.getInstance().GET_COUSELIST_URL = WEBTRN_TEST_LOCAL_URL + "/app/study/appCourse.action";

        RequestUrl.getInstance().LOGIN_URL = WEBTRN_TEST_LOCAL_URL + "/app/appLogin.action";
        RequestUrl.getInstance().GET_FORUM_INFO = GPRequestUrl.getInstance().REPLY_BLOG_LIST;
        RequestUrl.getInstance().startUrl = "";


        popupMenuConut();
    }

    /**
     * 初始化课程内的弹出菜单个数
     */

    private static void popupMenuConut() {
        MCCourseConst.getInstance().count = 2;
        Map<Integer, Object[]> MapClass = MCCourseConst.getInstance().MapClass;
        MapClass.put(0, new Object[]{"共享笔记", MCNoteListActivity.class});
        MapClass.put(1, new Object[]{"大家疑问", MCQuestionMainActivity.class});
        // MapClass.put(2, new Object[] { "我的作业", MCHomeworkListActivity.class
        // });
    }

}
