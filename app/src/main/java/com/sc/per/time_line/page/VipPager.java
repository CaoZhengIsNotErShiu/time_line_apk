package com.sc.per.time_line.page;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sc.per.time_line.R;
import com.sc.per.time_line.base.BasePager;
import com.sc.per.time_line.entity.ArticleDetail;
import com.sc.per.time_line.utils.Constants;
import com.sc.per.time_line.view.ScollListenWebView;
import com.sc.per.time_line.view.WebLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * vip
 */
public class VipPager extends BasePager {

    public VipPager(Context context) {
        super(context);
    }

    private WebSettings settings;
    private TextView ttTitle;
    private ImageView ivTitleHeader;

    WebLayout weblayout;
    ScollListenWebView webview;

    @Override
    public void initData() {
        super.initData();
        //1.设置标题
        tv_View.setText("vip页面");

        //2.获取数据
        View inflate = View.inflate(context, R.layout.activity_scroll_listen, null);
        webview = inflate.findViewById( R.id.webview );
        ttTitle = inflate.findViewById( R.id.tt_title );
        ivTitleHeader = inflate.findViewById( R.id.iv_icon );


        weblayout = inflate.findViewById( R.id.weblayout);

        //3.将子视图添加到BasePager的FrameLayout中
        frameLayout.addView(inflate);
        getData();
        setScollListener();

    }

    private void getData() {

        RequestParams params = new RequestParams(Constants.TIME_LINE_CLOUD_URL+"/editor/queryArticle");
        params.setConnectTimeout(5000);
        params.addBodyParameter("id","65");
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ArticleDetail article = gson.fromJson(result, ArticleDetail.class);
                ttTitle.setText(article.getData().getTitle());

                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.guide3);
                //设置bitmap.getWidth()可以获得圆形
                Bitmap bitmap1 = ClipSquareBitmap(bitmap,80,bitmap.getWidth());
                ivTitleHeader.setImageBitmap(bitmap1);
                //设置支持js
                settings = webview.getSettings();
                settings.setJavaScriptEnabled(true);
                settings.setTextZoom(100);
                //设置点击放大放小
                settings.setUseWideViewPort(true);
                //增加缩放按钮
                settings.setBuiltInZoomControls(true);
//                webview.setWebViewClient(new WebViewClient(){
//                    @Override
//                    public void onPageFinished(WebView view, String url) {
//                        super.onPageFinished(view, url);
//                        pbLoading.setVisibility(View.GONE);
//                    }
//                });
                webview.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        //处理重定向
                        return false;
                    }
                });

                webview.setOnScrollChangedCallback(new ScollListenWebView.OnScrollChangedCallback() {
                    @Override
                    public void onScroll(int dx, int dy) {
                        weblayout.scrollBy(0,dy);
                    }
                });
//                webview.loadUrl(article.getData().getData());
                //加载html数据
                webview.loadDataWithBaseURL(null,getHtmlData(article.getData().getData()),
                        "text/html" , "utf-8", null);


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }

    private void setScollListener() {
        webview.setOnScrollChangedCallback(new ScollListenWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int dx, int dy) {
                weblayout.scrollBy(0,dy);
            }
        });
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>/* table 样式 */\n" +
                "table {\n" +
                "  border-top: 1px solid #ccc;\n" +
                "  border-left: 1px solid #ccc;\n" +
                "}\n" +
                "table td,\n" +
                "table th {\n" +
                "  border-bottom: 1px solid #ccc;\n" +
                "  border-right: 1px solid #ccc;\n" +
                "  padding: 3px 5px;\n" +
                "}\n" +
                "table th {\n" +
                "  border-bottom: 2px solid #ccc;\n" +
                "  text-align: center;\n" +
                "}\n" +
                "\n" +
                "/* blockquote 样式 */\n" +
                "blockquote {\n" +
                "  display: block;\n" +
                "  border-left: 8px solid #d0e5f2;\n" +
                "  padding: 5px 10px;\n" +
                "  margin: 10px 0;\n" +
                "  line-height: 1.4;\n" +
                "  font-size: 100%;\n" +
                "  background-color: #f1f1f1;\n" +
                "}\n" +
                "\n" +
                "/* code 样式 */\n" +
                "code {\n" +
                "  display: inline-block;\n" +
                "  *display: inline;\n" +
                "  *zoom: 1;\n" +
                "  background-color: #f1f1f1;\n" +
                "  border-radius: 3px;\n" +
                "  padding: 3px 5px;\n" +
                "  margin: 0 3px;\n" +
                "}\n" +
                "pre code {\n" +
                "  display: block;\n" +
                "}\n" +
                "\n" +
                "/* ul ol 样式 */\n" +
                "ul, ol {\n" +
                "  margin: 10px 0 10px 20px;\n" +
                "}</style>"
                + "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    /**
     * 设置圆形头像
     * @param bmp
     * @param width
     * @param radius
     * @return
     */
    public static Bitmap ClipSquareBitmap(Bitmap bmp, int width, int radius) {
        if (bmp == null || width <= 0)
            return null;
        //如果图片比较小就没必要进行缩放了

        /**
         * 把图片进行缩放，以宽高最小的一边为准，缩放图片比例
         * */
        if (bmp.getWidth() > width && bmp.getHeight() > width) {
            if (bmp.getWidth() > bmp.getHeight()) {
                bmp = Bitmap.createScaledBitmap(bmp, (int) (((float) width) * bmp.getWidth() / bmp.getHeight()), width, false);
            } else {
                bmp = Bitmap.createScaledBitmap(bmp, width, (int) (((float) width) * bmp.getHeight() / bmp.getWidth()), false);
            }

        } else {
            width = bmp.getWidth() > bmp.getHeight() ? bmp.getHeight() : bmp.getWidth();
            Log.d("zeyu","宽" + width + ",w" + bmp.getWidth() + ",h" + bmp.getHeight());
            if (radius > width) {
                radius = width;
            }
        }
        Bitmap output = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        //设置画笔全透明
        canvas.drawARGB(0, 0, 0, 0);
        Paint paints = new Paint();
        paints.setColor(Color.WHITE);
        paints.setAntiAlias(true);//去锯齿
        paints.setFilterBitmap(true);
        //防抖动
        paints.setDither(true);

        //把图片圆形绘制矩形
        if (radius <= 0)
            canvas.drawRect(new Rect(0, 0, width, width), paints);
        else //绘制圆角
            canvas.drawRoundRect(new RectF(0, 0, width, width), radius, radius, paints);
        // 取两层绘制交集。显示前景色。
        paints.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Rect rect = new Rect();
        if (bmp.getWidth() >= bmp.getHeight()) {
            rect.set((bmp.getWidth() - width) / 2, 0, (bmp.getWidth() + width) / 2, width);
        } else {
            rect.set(0, (bmp.getHeight() - width) / 2, width, (bmp.getHeight() + width) / 2);
        }
        Rect rect2 = new Rect(0, 0, width, width);
        //第一个rect 针对bmp的绘制区域，rect2表示绘制到上面位置
        canvas.drawBitmap(bmp, rect, rect2, paints);
        bmp.recycle();
        return output;
    }




}
