package com.example.jhon.calculator.Utils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.jhon.calculator.R;

public class IntroActivity extends AppCompatActivity {

    private TextView introduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        introduction=(TextView)findViewById(R.id.introduction);
        String text="表达式支持 " +"\n"+
                "（1）+、-、*、/,乘号不可省略"+"\n" +
                "（2）指数<x^y>,e为底的指数<E^x>或exp(x)" +"\n"+
                "（3）自然对数ln(x),常用对数lg(x)"+"\n" +
                "（4）三角函数sin(x),cos(x),tan(x)"+"\n" +
                "（5）双曲三角函数sh(x),ch(x)"+"\n" +
                "（6）反三角函数arcsin(x),arccos(x),arctan(x)"+"\n" +
                "（7）平方根sqrt(x),立方根cbrt(x)"+"\n" +
                "（8）绝对值|x|"+"\n" +
                "注意事项"+"\n" +
                "（1）使用英文输入法下的符号"+"\n" +
                "（2）指数输入时需使用<>防止歧义，不能嵌套自身"+"\n" +
                "（3）使用无理数e,Π时用E,PI代替"+"\n" +
                "（4）为方便输出，所有结果转化为小数形式"+"\n" +
                "（5）三角函数采用弧度制"+"\n" +
                "（6）绝对值不能嵌套自身" ;
        introduction.setText(text);
    }
}
