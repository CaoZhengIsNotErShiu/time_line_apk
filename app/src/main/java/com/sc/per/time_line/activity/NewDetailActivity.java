package com.sc.per.time_line.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
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


    private void findViews() {
        ivTitle = findViewById( R.id.iv_title );
        ibMenu = findViewById( R.id.ib_menu );
        ibBack = findViewById( R.id.ib_back );
        ibFont = findViewById( R.id.ib_font );
        ibShare = findViewById( R.id.ib_share );
        webview = findViewById( R.id.webview );
        pbLoading = findViewById( R.id.pb_loading );
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
                + "<style>img{max-width: 100%; width:100%; height:auto;}*{margin:0px;}</style>"
                + "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}
