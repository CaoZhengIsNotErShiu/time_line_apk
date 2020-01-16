package com.sc.per.time_line.page;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.sc.per.time_line.R;
import com.sc.per.time_line.activity.MainActivity;
import com.sc.per.time_line.base.BasePager;

import org.xutils.x;

/**
 * 主页
 */
public class SettingPager extends BasePager {

    public SettingPager(Context context) {
        super(context);
    }

    private Button signin;
    private Button enroll;

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
                view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect r = new Rect();
                        view.getWindowVisibleDisplayFrame(r);
                        int screenHeight = view.getRootView()
                                .getHeight();
                        int heightDifference = screenHeight - (r.bottom);
                        if (heightDifference > 200) {
                            //软键盘显示
                            // changeKeyboardHeight(heightDifference);
                        } else {
                            //软键盘隐藏
                            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            //imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }
                });

                //返回按钮
                ib_login_back = view.findViewById(R.id.ib_login_back);
                ib_login_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        frameLayout.removeView(view);
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





}
