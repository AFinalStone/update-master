package ezy.demo.update;

/**
 * Created by SHI on 2017/4/1 14:34
 */

public interface ApkUpdateInfoLoadBiz {

    String url_webHtml = "http://a.app.qq.com/o/simple.jsp?pkgname=com.xianglixiangqin.xianglixiangqin";

    String flagBegin = "{\"appId\":";
    String flagEnd = ",\"isNew\"";


    public ApkUpdateInfoBean apkUpdateInfoLoad();
}
