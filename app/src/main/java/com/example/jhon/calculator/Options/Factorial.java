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

public class Factorial extends AppCompatActivity
            implements View.OnClickListener {

    private TextView resultText;
    private EditText inputText;
    private Button clearButton;
    private Button startButton;
    private Button historyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factorial);
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
                String inputs=inputText.getText().toString();
                if(check(inputs))
                {
                    int num=Integer.parseInt(inputs);
                    String[] results=new String[num+1];
                    results[0]=1+"";
                    for (int i=1;i<=num;i++)
                    {
                        results[i]= Multiple.multiply(results[i-1],i+"");
                    }
                    resultText.setText(results[num]);

                    HistoryData data=new HistoryData();
                    data.setInput(inputs+" !");
                    data.setResult(results[num]);
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

    private boolean check(String inputs) {

        if (TextUtils.isEmpty(inputs))
        {
            return false;
        }
        for (int i=0;i<inputs.length();i++)
        {
            if (inputs.charAt(i)<'0' || inputs.charAt(i)>'9')
            {
                return false;
            }
        }
        return true;
    }
}
