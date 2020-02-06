package com.example.jhon.calculator.Options;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jhon.calculator.History;
import com.example.jhon.calculator.R;
import com.example.jhon.calculator.Utils.Multiple;
import com.example.jhon.calculator.db.HistoryData;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MulActivity extends AppCompatActivity
            implements View.OnClickListener{

    public final int MODE_INT=0;
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
    private int mode;
    private int level;
    private String temp;
    private String[] inputs;
    private String[] results;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mul);
        resultText=(TextView)findViewById(R.id.result_text);
        inputText=(EditText)findViewById(R.id.input_text);
        clearButton=(Button)findViewById(R.id.clear_button);
        startButton=(Button)findViewById(R.id.start_button);
        historyButton=(Button)findViewById(R.id.history_button);
        modeButton=(Button)findViewById(R.id.mode_button);
        levelButton=(Button)findViewById(R.id.level_button);
        clearButton.setOnClickListener(this);
        startButton.setOnClickListener(this);
        historyButton.setOnClickListener(this);
        modeButton.setOnClickListener(this);
        levelButton.setOnClickListener(this);
        mode=MODE_INT;
        level=LEVEL_LOW;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.clear_button:
                inputText.setText("");
                break;
            case R.id.start_button:
                String input=inputText.getText().toString();
                temp=input.replace("*","x");
                inputs=temp.split("x");

                if (mode==MODE_INT)
                {
                    if (checkInt()) {
                        calculate();
                    }
                }
                else if (mode==MODE_FLOAT)
                {
                    if (checkFloat())
                    {
                        calculate();
                    }
                }
                break;
            case R.id.history_button:
                Intent intent=new Intent(this,History.class);
                startActivity(intent);
                break;
            case R.id.mode_button:
                if (mode==MODE_INT)
                {
                    modeButton.setText("模式：小数");
                    mode=MODE_FLOAT;
                }
                else
                {
                    modeButton.setText("模式：整数");
                    mode=MODE_INT;
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
                }
                else if (level==LEVEL_HIGH)
                {
                    level=LEVEL_LOW;
                    levelButton.setText("级别：低");
                }
                break;
        }
    }

    public void handleLow()
    {
        if (mode==MODE_INT)
        {
            long[] datas=new long[inputs.length];
            long result=1;
            for (int i=0;i<datas.length;i++)
            {
                datas[i]=Long.parseLong(inputs[i]);
                result*=datas[i];
            }
            results=new String[1];
            results[0]=result+"";
        }
        else if (mode==MODE_FLOAT)
        {
            double[] datas=new double[inputs.length];
            double result=1;
            for (int i=0;i<datas.length;i++)
            {
                datas[i]=Double.parseDouble(inputs[i]);
                result*=datas[i];
            }
            results=new String[1];
            results[0]=result+"";
        }
    }

    public void handleMiddle()
    {
        if (mode==MODE_INT)
        {
            BigInteger[] datas=new BigInteger[inputs.length];
            BigInteger[] result=new BigInteger[inputs.length+1];
            result[0]=new BigInteger("1");
            for (int i=0;i<datas.length;i++)
            {
                datas[i]=new BigInteger(inputs[i]);
                result[i+1]=result[i].multiply(datas[i]);
            }
            results=new String[1];
            results[0]=result[result.length-1].toString();
        }
        else if (mode==MODE_FLOAT)
        {
            BigDecimal[] datas=new BigDecimal[inputs.length];
            BigDecimal[] result=new BigDecimal[inputs.length+1];
            result[0]=new BigDecimal("1");
            for (int i=0;i<datas.length;i++)
            {
                datas[i]=new BigDecimal(inputs[i]);
                result[i+1]=result[i].multiply(datas[i]);
            }
            results=new String[1];
            results[0]=result[result.length-1].toString();
        }
    }

    public void handleHigh()
    {
        if (mode==MODE_INT)
        {
            results=new String[inputs.length+1];
            results[0]= "1";
            for(int i=0;i<inputs.length;i++)
            {
                results[i+1]= Multiple.multiply(results[i],inputs[i]);
            }
        }
        else if (mode==MODE_FLOAT)
        {
            String[] inputsNex=new String[inputs.length];
            int[] indexs=new int[inputs.length];
            for (int i=0;i<inputs.length;i++)
            {
                for (int j=0;j<inputs[0].length();j++)
                {
                    if (inputs[i].charAt(j)=='.')
                    {
                        indexs[i]=inputs[i].length()-j-1;
                    }
                }
                inputsNex[i]=inputs[i].replace(".","");
            }
            int resultIndex=0;
            for (int index:indexs)
            {
                resultIndex+=index;
            }
            results=new String[inputs.length+1];
            results[0]= "1";
            for(int i=0;i<inputs.length;i++)
            {
                results[i+1]= Multiple.multiply(results[i],inputsNex[i]);
            }
            int floatIndex=results[results.length-1].length()-resultIndex;
            String resultNex1=results[results.length-1].substring(0,floatIndex);
            String resultNex2=results[results.length-1].substring(floatIndex,floatIndex+resultIndex);
            results[results.length-1]=resultNex1+"."+resultNex2;
        }
    }

    private boolean checkInt()
    {
        if (inputs.length<=1)
        {
            return false;
        }

        for (String input:inputs)
        {
           for (int i=0;i<input.length();i++)
           {
               if (input.charAt(i)-'0'>9 || input.charAt(i)-'0'<0)
               {
                   Toast.makeText(this, "输入有误", Toast.LENGTH_SHORT).show();
                   return false;
               }
           }
        }
        return true;
    }

    private boolean checkFloat()
    {
        if (inputs.length<=1)
        {
            return false;
        }

        for (String input:inputs)
        {
            int count=0;
            for (int i=0;i<input.length();i++)
            {
                if (input.charAt(i)=='.')
                {
                    count++;
                    continue;
                }
                else if (input.charAt(i)-'0'>9 || input.charAt(i)-'0'<0)
                {
                    Toast.makeText(this, "输入有误", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            if (count>1)
            {
                return false;
            }
            else if (count==0)
            {
                input=input+".0";
            }
        }
        return true;
    }

    private void calculate()
    {
        if (level == LEVEL_LOW) {
            handleLow();
        } else if (level == LEVEL_MIDDLE) {
            handleMiddle();
        } else if (level == LEVEL_HIGH) {
            handleHigh();
        }
        resultText.setText(results[results.length - 1]);

        HistoryData data = new HistoryData();
        data.setInput(temp);
        data.setResult(results[results.length - 1]);
        data.save();
    }
}
