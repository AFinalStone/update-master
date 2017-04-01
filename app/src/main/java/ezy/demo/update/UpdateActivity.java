package ezy.demo.update;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ezy.boost.update.UpdateAgent;
import ezy.boost.update.UpdateInfo;
import ezy.boost.update.UpdateManager;

public class UpdateActivity extends AppCompatActivity {


    String mCheckUrl = "http://client.waimai.baidu.com/message/updatetag";
    ApkUpdateInfoBean infoBean;

    //    apkMd5
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        UpdateManager.setDebuggable(true);
        UpdateManager.setWifiOnly(false);
        UpdateManager.setUrl(mCheckUrl, "yyb");
        UpdateManager.check(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApkUpdateInfoLoadBiz loadBiz = new ApkUpdateInfoLoadBizImp();
                infoBean = loadBiz.apkUpdateInfoLoad();
                LogUtil.LogShitou(infoBean.toString());
                SystemClock.sleep(5000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        check(false, true, false, false, true, 998);
                    }
                });


            }
        }).start();


    }


    void check(boolean isManual, final boolean hasUpdate, final boolean isForce, final boolean isSilent, final boolean isIgnorable, final int notifyId) {
        UpdateManager.create(this).setUrl(mCheckUrl).setManual(isManual).setNotifyId(notifyId).setParser(new UpdateAgent.InfoParser() {
            @Override
            public UpdateInfo parse(String source) throws Exception {
                UpdateInfo info = new UpdateInfo();
                info.hasUpdate = hasUpdate;
//              info.updateContent = "• 支持文字、贴纸、背景音乐，尽情展现欢乐气氛；\n• 两人视频通话支持实时滤镜，丰富滤镜，多彩心情；\n• 图片编辑新增艺术滤镜，一键打造文艺画风；\n• 资料卡新增点赞排行榜，看好友里谁是魅力之王。";
                info.updateContent = infoBean.getNewFeature();
                info.versionCode = infoBean.getVersionCode();
                info.versionName = infoBean.getVersionName();
                info.url = infoBean.getApkUrl();
                info.md5 = infoBean.getApkMd5();
                info.size = infoBean.getFileSize().desc;
                info.isForce = isForce;
                info.isIgnorable = isIgnorable;
                info.isSilent = isSilent;
                return info;
            }
        }).check();
    }


}
