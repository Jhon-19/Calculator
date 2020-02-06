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
import com.example.jhon.calculator.Utils.AddSub;
import com.example.jhon.calculator.db.HistoryData;

import java.util.ArrayList;
import java.util.List;

public class AddSubActivity extends AppCompatActivity
            implements View.OnClickListener{

    private TextView resultText;
    private EditText inputText;
    private Button clearButton;
    private Button startButton;
    private Button historyButton;
    private int sign=1;
    private List<Integer> modes;
    private String inputs[];
    private String[] results;

    public final int MODE_ADD=1;
    public final int MODE_SUB=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);
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
                if (inputs0.charAt(0)=='-')
                {
                    sign=-1;
                    String inputs1=inputs0.substring(1);
                    setMode(inputs1);
                    calculate(inputs1);
                }
                else if (inputs0.charAt(0)<='9' && inputs0.charAt(0)>='0')
                {
                    sign=1;
                    setMode(inputs0);
                    calculate(inputs0);
                }
                else
                {
                    Toast.makeText(this, "输入有误", Toast.LENGTH_SHORT).show();
                }
                resultText.setText(results[results.length-1]);

                HistoryData data=new HistoryData();
                data.setInput(inputs0);
                data.setResult(results[results.length-1]);
                data.save();
                break;
            case R.id.history_button:
                Intent intent=new Intent(this, History.class);
                startActivity(intent);
        }
    }

    private void calculate(String inputs0) {

        inputs=inputs0.split("[+-]");
        results=new String[inputs.length+1];
        results[0]=0+".0";
        if (check())
        {
            for (int i=0;i<inputs.length;i++)
            {
                if (modes.get(i).intValue()==MODE_ADD)
                {
                    results[i+1]= AddSub.add(results[i],inputs[i]);
                }
                else if (modes.get(i).intValue()==MODE_SUB)
                {
                    results[i+1]=AddSub.sub(results[i],inputs[i]);
                }
            }
            if (sign==-1)
            {
                if (results[results.length-1].charAt(0)!='-')
                {
                    results[results.length-1]="-"+results[results.length-1];
                }
                else
                {
                    results[results.length-1]=results[results.length-1].substring(1);
                }
            }
        }
        else
        {
            Toast.makeText(this, "输入有误", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean check() {
        
        boolean b=false;
        for (int i=0;i<inputs.length;i++)
        {
            int count=0;
            for (int j=0;j<inputs[i].length();j++)
            {
                if (inputs[i].charAt(j)=='.')
                {
                    count++;
                }
            }
            if (count==0)
            {
                inputs[i]+=".0";
                b=true;
            }
            else if (count==1)
            {
                b=true;
            }
            else
            {
                b=false;
            }
        }
        if (inputs.length<=1)
        {
            b=false;
        }
        return b;
    }

    private void setMode(String inputs0) {

        modes=new ArrayList<>();
        modes.add(1);
        for (int i=0;i< inputs0.length();i++)
        {
            if (inputs0.charAt(i)=='+')
            {
                modes.add(sign*1);
            }
            else if (inputs0.charAt(i)=='-')
            {
                modes.add(sign*(-1));
            }
            else
            {
                continue;
            }
        }
    }
}
