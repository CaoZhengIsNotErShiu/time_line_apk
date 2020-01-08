package com.sc.per.time_line.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sc.per.time_line.R;
import com.sc.per.time_line.entity.ArticleDetail;
import com.sc.per.time_line.utils.Constants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class NewDetailActivity extends Activity implements View.OnClickListener{

    private TextView ivTitle;
    private ImageButton ibMenu;
    private ImageButton ibBack;
    private ImageButton ibFont;
    private ImageButton ibShare;
    private WebView webview;
    private ProgressBar pbLoading;
    private String url;
    private WebSettings settings;
    private TextView ttTitle;
    private ImageView ivTitleHeader;


    private void findViews() {
        ivTitle = findViewById( R.id.iv_title );
        ibMenu = findViewById( R.id.ib_menu );
        ibBack = findViewById( R.id.ib_back );
        ibFont = findViewById( R.id.ib_font );
        ibShare = findViewById( R.id.ib_share );
        webview = findViewById( R.id.webview );
        pbLoading = findViewById( R.id.pb_loading );
        ttTitle = findViewById( R.id.tt_title );
        ivTitleHeader = findViewById( R.id.iv_icon );
        //隐藏，显示
        ivTitle.setVisibility(View.GONE);
        ibMenu.setVisibility(View.GONE);
        ibBack.setVisibility(View.VISIBLE);
        ibFont.setVisibility(View.VISIBLE);
        ibShare.setVisibility(View.VISIBLE);


        ibBack.setOnClickListener( this );
        ibFont.setOnClickListener( this );
        ibShare.setOnClickListener( this );
    }


    @Override
    public void onClick(View v) {
        if ( v == ibBack ) {
            // Handle clicks for ibBack
            finish();
        } else if ( v == ibFont ) {
            // Handle clicks for ibFont
            updateTextSize();
        } else if ( v == ibShare ) {
            // Handle clicks for ibShare
            Toast.makeText(this,"分享" , Toast.LENGTH_SHORT).show();
        }
    }

    private int tempSize = 2;
    private int realSize = tempSize;

    /**
     * 修改字体大小
     */
    private void updateTextSize() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置字体大小");
        String[] items = {"超大","大","正常","小","超小"};
        builder.setSingleChoiceItems(items, realSize, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tempSize = which;
            }
        });
        builder.setNegativeButton("取消",null );
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                realSize = tempSize;
                changeTextSize(realSize);
            }
        });
        builder.show();
    }

    private void changeTextSize(int realSize) {
        switch (realSize){
            case 0:
                settings.setTextZoom(200);
                break;
            case 1:
                settings.setTextZoom(150);
                break;
            case 2:
                settings.setTextZoom(100);
                break;
            case 3:
                settings.setTextZoom(75);
                break;
            case 4:
                settings.setTextZoom(50);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dateil);
        findViews();
        getData();
    }

    private void getData() {

        url = getIntent().getStringExtra("art_id");
        RequestParams params = new RequestParams(Constants.TIME_LINE_CLOUD_URL+"/editor/queryArticle");
        params.setConnectTimeout(5000);
        params.addBodyParameter("id",url);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ArticleDetail article = gson.fromJson(result, ArticleDetail.class);
                System.out.println(article.getData().getData());
                ttTitle.setText(article.getData().getTitle());
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guide3);
                //设置bitmap.getWidth()可以获得圆形
                Bitmap bitmap1 = ClipSquareBitmap(bitmap,100,bitmap.getWidth());
                ivTitleHeader.setImageBitmap(bitmap1);
                //设置支持js
                settings = webview.getSettings();
                settings.setJavaScriptEnabled(true);
                settings.setTextZoom(100);
                //设置点击放大放小
                settings.setUseWideViewPort(true);
                //增加缩放按钮
                settings.setBuiltInZoomControls(true);
                webview.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        pbLoading.setVisibility(View.GONE);
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
