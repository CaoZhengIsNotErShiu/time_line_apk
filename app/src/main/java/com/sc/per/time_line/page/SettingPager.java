package com.sc.per.time_line.page;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.sc.per.time_line.R;
import com.sc.per.time_line.base.BasePager;
import com.sc.per.time_line.utils.Constants;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 主页
 */
public class SettingPager extends BasePager {

    public SettingPager(Context context) {
        super(context);
    }

    private Button signin;
    private Button enroll;
    private Button btn_login;
    private EditText account_input;
    private EditText password_input;
    private View view;
    private ImageButton ib_login_back;

    @Override
    public void initData() {
        super.initData();

        view = View.inflate(context, R.layout.setting_pager, null);

        //登录按钮
        signin = view.findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                view = View.inflate(context, R.layout.login, null);
                tv_View.setText("登录");
                rlTitle.setVisibility(View.GONE);

                //返回按钮
                ib_login_back = view.findViewById(R.id.ib_login_back);
                ib_login_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        frameLayout.removeView(view);
                    }
                });

                //失去焦点后，隐藏键盘
                view.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        return false;
                    }
                });
                //账号密码
                account_input = view.findViewById(R.id.account_input);
                password_input = view.findViewById(R.id.password_input);
                //登录
                btn_login = view.findViewById(R.id.btn_login);
                btn_login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userName = account_input.getText().toString();
                        String pwd = password_input.getText().toString();
                        Toast.makeText(context,userName +" = "+pwd ,Toast.LENGTH_SHORT ).show();
                        //登录
                        doLogin(userName,pwd);
                    }
                });
                frameLayout.addView(view);
            }
        });

        //注册按钮
        enroll = view.findViewById(R.id.enroll);
        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "注册按钮",Toast.LENGTH_SHORT ).show();
                view = View.inflate(context, R.layout.enroll, null);
                rlTitle.setVisibility(View.GONE);
                frameLayout.addView(view);
            }
        });
        rlTitle.setVisibility(View.GONE);
        tv_View.setTextColor(Color.parseColor("#00BE8E"));
        //3.将子视图添加到BasePager的FrameLayout中
        frameLayout.addView(view);


    }

    /**
     * 登录
     * @param userName
     * @param pwd
     */
    public void doLogin(String userName,String pwd){
        OkHttpClient client = new OkHttpClient();
        String url = Constants.TIME_LINE_CLOUD_URL + "/user/dologin";
        FormBody body = new FormBody.Builder()
                .add("phone",userName)
                .add("password",pwd)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //...
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String result = response.body().string();
                    //处理UI需要切换到UI线程处理
                    System.out.println(result.toString());
                    frameLayout.removeView(view);
                    view = View.inflate(context,R.layout.mine ,null );
                    frameLayout.addView(view);
                }
            }
        });
    }

}
