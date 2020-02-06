package com.example.jhon.calculator.Options;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jhon.calculator.History;
import com.example.jhon.calculator.R;
import com.example.jhon.calculator.Utils.Divide;
import com.example.jhon.calculator.db.HistoryData;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DivActivity extends AppCompatActivity
            implements View.OnClickListener{

    public final int MODE_REMAIN=0;
    public final int MODE_FLOAT=1;
    public final int LEVEL_LOW=0;
    public final int LEVEL_MIDDLE=1;
    public final int LEVEL_HIGH=2;

    private TextView resultText;
    private EditText inputText;
    private Button clearButton;
    private Button modeButton;
    private Button startButton;
    private Button levelButton;
    private Button historyButton;
    private FloatingActionButton fab;
    private EditText floatNumber;
    private int mode;
    private int level;
    private String[] inputs;
    private int floatNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_div);
        resultText=(TextView)findViewById(R.id.result_text);
        inputText=(EditText)findViewById(R.id.input_text);
        clearButton=(Button)findViewById(R.id.clear_button);
        startButton=(Button)findViewById(R.id.start_button);
        historyButton=(Button)findViewById(R.id.history_button);
        modeButton=(Button)findViewById(R.id.mode_button);
        levelButton=(Button)findViewById(R.id.level_button);
        fab=(FloatingActionButton)findViewById(R.id.fab) ;
        floatNumber=(EditText)findViewById(R.id.float_number);
        clearButton.setOnClickListener(this);
        startButton.setOnClickListener(this);
        historyButton.setOnClickListener(this);
        modeButton.setOnClickListener(this);
        levelButton.setOnClickListener(this);
        fab.setOnClickListener(this);
        mode=MODE_REMAIN;
        level=LEVEL_LOW;
        floatNum=1;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.clear_button:
                inputText.setText("");
                break;
            case R.id.start_button:
                String inputs0=inputText.getText().toString();
                inputs=inputs0.split("/");
                if (inputs.length!=2 || TextUtils.isEmpty(inputs[0]) || TextUtils.isEmpty(inputs[1]))
                {
                    Toast.makeText(this, "输入错误", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (mode==MODE_REMAIN)
                    {
                        if(checkRemain())
                        {
                            String result="";
                            String remain="";
                            if (level==LEVEL_LOW)
                            {
                                long data1=Long.parseLong(inputs[0]);
                                long data2=Long.parseLong(inputs[1]);
                                result=(data1/data2)+"";
                                remain=(data1%data2)+"";
                            }
                            else if (level==LEVEL_MIDDLE)
                            {
                                BigInteger data1=new BigInteger(inputs[0]);
                                BigInteger data2=new BigInteger(inputs[1]);
                                result=data1.divideAndRemainder(data2)[0].toString();
                                remain=data1.divideAndRemainder(data2)[1].toString();
                            }
                            else if (level==LEVEL_HIGH)
                            {
                                result=Divide.divide(inputs[0],inputs[1]).split("[+]")[0];
                                remain=Divide.divide(inputs[0],inputs[1]).split("[+]")[1];
                            }
                            String results=result+"\n"+"……"+remain;
                            resultText.setText(results);

                            HistoryData data=new HistoryData();
                            data.setInput(inputs0);
                            data.setResult(results);
                            data.save();
                        }
                        else
                        {
                            Toast.makeText(this, "输入错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        if (checkFloat())
                        {
                            String results="";
                            if (level==LEVEL_LOW)
                            {
                                float data1=Float.parseFloat(inputs[0]);
                                float data2=Float.parseFloat(inputs[1]);
                                results=data1/data2+"";
                            }
                            else if (level==LEVEL_MIDDLE)
                            {
                                double data1=Double.parseDouble(inputs[0]);
                                double data2=Double.parseDouble(inputs[1]);
                                results=data1/data2+"";
                            }
                            else if (level==LEVEL_HIGH)
                            {
                                BigDecimal data1=new BigDecimal(inputs[0]);
                                BigDecimal data2=new BigDecimal(inputs[1]);
                                results=data1.divide(data2,floatNum,BigDecimal.ROUND_HALF_DOWN).toString();
                            }
                            resultText.setText(results);

                            HistoryData data=new HistoryData();
                            data.setInput(inputs0);
                            data.setResult(results);
                            data.save();
                        }
                        else
                        {
                            Toast.makeText(this, "输入错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case R.id.history_button:
                Intent intent=new Intent(this,History.class);
                startActivity(intent);
                break;
            case R.id.mode_button:
                if (mode==MODE_REMAIN)
                {
                    modeButton.setText("模式：小数");
                    mode=MODE_FLOAT;
                }
                else
                {
                    modeButton.setText("模式：余数");
                    mode=MODE_REMAIN;
                }
                break;
            case R.id.level_button:
                if (level==LEVEL_LOW)
                {
                    level=LEVEL_MIDDLE;
                    levelButton.setText("级别：中");
                }
                else if(level==LEVEL_MIDDLE)
                {
                    level=LEVEL_HIGH;
                    levelButton.setText("级别：高");
                    if (mode==MODE_FLOAT)
                    {
                        fab.setVisibility(View.VISIBLE);
                    }
                }
                else if (level==LEVEL_HIGH)
                {
                    level=LEVEL_LOW;
                    levelButton.setText("级别：低");
                }
                break;
            case R.id.fab:
                if (mode!=MODE_FLOAT || level!=LEVEL_HIGH)
                {
                    fab.setVisibility(View.INVISIBLE);
                }
                else
                {
                    if (floatNumber.getVisibility()==View.VISIBLE)
                    {
                        String temp=floatNumber.getText().toString();
                        try{
                            floatNum=Integer.parseInt(temp);
                        }catch (NumberFormatException e){

                        }
                        if (floatNum>0)
                        {
                            floatNumber.setVisibility(View.GONE);
                        }
                    }
                }
                break;
        }
    }
//
//    private String handleHighFloat() {
//
//        int[] seat=new int[2];
//        for (int i=0;i<2;i++)
//        {
//            for (int j=0;j<inputs[i].length();j++)
//            {
//                if (inputs[i].charAt(j)=='.')
//                {
//                    seat[i]=inputs[i].length()-1-j;
//                }
//            }
//        }
//        if (seat[0]>seat[1])
//        {
//            inputs[0]=inputs[0].replace(".","");
//            String temp=inputs[1].replace(".","");
//            for (int i=0;i<seat[0]-seat[1];i++)
//            {
//                temp+="0";
//            }
//            inputs[1]=temp;
//        }
//        else
//        {
//            inputs[1]=inputs[1].replace(".","");
//            String temp=inputs[0].replace(".","");
//            for (int i=0;i<seat[1]-seat[0];i++)
//            {
//                temp+="0";
//            }
//            inputs[0]=temp;
//        }
//        String tempResult=Divide.divide(inputs[0],inputs[1]);
//        String[] tempResults=tempResult.split("[+]");
//        StringBuilder builder=new StringBuilder(tempResults[1]);
//        for (int i=0;i<floatNum;i++)
//        {
//            builder.append("0");
//        }
//        String[] temps=Divide.divide(builder.toString(),inputs[1]).split("[+]");
//        if (checkIncrese(temps[1]))
//        {
//            temps[0]= AddSub.add(temps[0]+".0","1.0").split("[.]")[0];
//        }
//        for (int i=0;i<inputs[1].length()-tempResults[1].length();i++)
//        {
//            temps[0]="0"+temps[0];
//        }
//        return tempResults[0]+"."+temps[0];
//    }
//
//    private boolean checkIncrese(String temp) {
//
//        String[] halfValues=Divide.divide(inputs[1],"2").split("[+]");
//        int len=Math.min(halfValues[0].length(),temp.length());
//        for (int i=0;i<len;i++)
//        {
//            if (temp.charAt(i)==halfValues[0].charAt(i))
//            {
//                continue;
//            }
//            else if (temp.charAt(i)<halfValues[0].charAt(i))
//            {
//                return false;
//            }
//            else if (temp.charAt(i)>halfValues[0].charAt(i))
//            {
//                return true;
//            }
//        }
//        if (temp.length()==halfValues[0].length())
//        {
//            if (halfValues[1].charAt(0)>'0')
//            {
//                return false;
//            }
//            else
//            {
//                return true;
//            }
//        }
//        if (temp.length()==len)
//        {
//            return false;
//        }
//        else
//        {
//            return true;
//        }
//    }

    private boolean checkFloat() {

        for (int i=0;i<2;i++)
        {
            int count=0;
            for (int j=0;j<inputs[i].length();j++)
            {
                if (inputs[i].charAt(j)=='.')
                {
                    count++;
                }
                else if (inputs[i].charAt(j)>'9' || inputs[i].charAt(j)<'0')
                {
                    return false;
                }
            }
            if (count==0)
            {
                inputs[i]+=".0";
            }
            else if (count>1)
            {
                return false;
            }
        }
        return true;
    }

    private boolean checkRemain() {

        for (String input:inputs)
        {
            for (int i=0;i<input.length();i++)
            {
                if (input.charAt(i)>'9' || input.charAt(i)<'0')
                {
                    return false;
                }
            }
        }
        return true;
    }
}
