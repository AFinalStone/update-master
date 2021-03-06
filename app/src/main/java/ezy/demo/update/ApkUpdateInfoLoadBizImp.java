package ezy.demo.update;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by SHI on 2017/4/1 14:34
 */

public class ApkUpdateInfoLoadBizImp implements ApkUpdateInfoLoadBiz {

    public ApkUpdateInfoBean apkUpdateInfoLoad() {
        ApkUpdateInfoBean infoBean = null;
        try {
            // 1.创建一个URL对象,打开一个http类型的连接
            URL url = new URL(url_webHtml);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 2.给连接设置请求参数
            conn.setRequestMethod("GET"); // 默认就是GET方式
            conn.setConnectTimeout(5000); // 设置连接的超时时间
            //conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");
            int code = conn.getResponseCode();
            if (code == 200) {
                // 3.得到服务器端返回的响应码是否为200,应该接收服务器端返回的二进制输入流
                //接收服务器端返回的二进制输入流
                String html = convertStreamToString(conn.getInputStream());
                int positionStart = html.indexOf(flagBegin);
                int positionEnd = html.indexOf(flagEnd);
                String strInfo = html.substring(positionStart, positionEnd);
                Gson gson = new Gson();
                infoBean = gson.fromJson(strInfo, ApkUpdateInfoBean.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return infoBean;
    }

    /**
     * 输入流转化为String
     * @author Administrator
     * @time 2017/4/1 14:48
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
