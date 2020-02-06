package com.example.jhon.calculator.Options;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jhon.calculator.History;
import com.example.jhon.calculator.R;
import com.example.jhon.calculator.Utils.Multiple;
import com.example.jhon.calculator.db.HistoryData;

public class PowActivity extends AppCompatActivity
            implements View.OnClickListener{

    private TextView resultText;
    private EditText inputText;
    private Button clearButton;
    private Button startButton;
    private Button historyButton;
    private String[] inputs;
    private long seat1;
    private long seat2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pow);
        resultText=(TextView)findViewById(R.id.result_text);
        inputText=(EditText)findViewById(R.id.input_text);
        clearButton=(Button)findViewById(R.id.clear_button);
        startButton=(Button)findViewById(R.id.start_button);
        historyButton=(Button)findViewById(R.id.history_button);
        clearButton.setOnClickListener(this);
        startButton.setOnClickListener(this);
        historyButton.setOnClickListener(this);
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
                String t=inputs0.replace("^","p");
                inputs=t.split("p");
                if (check()[0])
                {
                    String results="";
                    if (!check()[1] && !check()[2])
                    {
                        long temp=Long.parseLong(inputs[1]);
                        if (temp>=Integer.MAX_VALUE)
                        {
                            results="INFINITY";
                        }
                        else
                        {
                            int y=Integer.parseInt(inputs[1]);
                            String[] temps=new String[y+1];
                            temps[0]="1";
                            for (int i=0;i<y;i++)
                            {
                                temps[i+1]= Multiple.multiply(temps[i],inputs[0]);
                            }
                            results=temps[y];
                        }
                    }
                    else
                    {
                        double x=Double.parseDouble(inputs[0]);
                        double y=Double.parseDouble(inputs[1]);
                        double result=Math.pow(x,y);
                        results=result+"";
                    }
                    resultText.setText(results);

                    HistoryData data=new HistoryData();
                    data.setInput(inputs0);
                    data.setResult(results);
                    data.save();
                }
                else
                {
                    Toast.makeText(this, "输入有误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.history_button:
                Intent intent=new Intent(this,History.class);
                startActivity(intent);
                break;
        }
    }

    private boolean[] check() {

        boolean[] bs=new boolean[3];
        if (inputs.length!=2)
        {
            return bs;
        }
        for (String input:inputs)
        {
            if (TextUtils.isEmpty(input))
            {
                return bs;
            }
        }
        for (int i=0;i<2;i++)
        {
            int count=0;
            for (int j=0;j<inputs[i].length();j++)
            {
                if (inputs[i].charAt(j)=='.')
                {
                    count++;
                    bs[i+1]=true;
                }
                else if (inputs[i].charAt(j)>='0' && inputs[i].charAt(j)<='9')
                {
                    continue;
                }
                else
                {
                    return bs;
                }
            }
            if (count>1)
            {
                return bs;
            }
        }
        bs[0]=true;
        return bs;
    }
}
