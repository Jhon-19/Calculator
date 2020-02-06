package com.example.jhon.calculator.Options;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jhon.calculator.History;
import com.example.jhon.calculator.R;
import com.example.jhon.calculator.Utils.Analyze;
import com.example.jhon.calculator.Utils.IntroActivity;
import com.example.jhon.calculator.db.HistoryData;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class InteActivity extends AppCompatActivity
            implements View.OnClickListener{

    private TextView resultText;
    private EditText inputText;
    private EditText downLimitationText;
    private EditText upLimitationText;
    private EditText levelText;
    private Button clearButton;
    private Button startButton;
    private Button historyButton;
    private FloatingActionButton fab;
    private double downLimitation;
    private double upLimitation;
    private int level;
    private String inputs;
    private boolean isNegative;
    private String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inte);
        resultText=(TextView)findViewById(R.id.result_text);
        inputText=(EditText)findViewById(R.id.input_text);
        downLimitationText=(EditText)findViewById(R.id.down_limitation_text);
        upLimitationText=(EditText)findViewById(R.id.up_limitation_text);
        levelText=(EditText)findViewById(R.id.level_text);
        clearButton=(Button)findViewById(R.id.clear_button);
        startButton=(Button)findViewById(R.id.start_button);
        historyButton=(Button)findViewById(R.id.history_button);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        clearButton.setOnClickListener(this);
        startButton.setOnClickListener(this);
        historyButton.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.clear_button:
                inputText.setText("");
                upLimitationText.setText("");
                downLimitationText.setText("");
                levelText.setText("");
                break;
            case R.id.start_button:
                init();
                temp= Analyze.parse(inputs);
                String results;
                try{
                    results=calculate()+"";
                }catch(Exception e){
                    results=Double.NaN+"";
                }
                if (isNegative)
                {
                    if (results.charAt(0)=='-')
                    {
                        results=results.substring(1);
                    }
                    else
                    {
                        results="-"+results;
                    }
                }
                resultText.setText(results);

                HistoryData data=new HistoryData();
                data.setInput("∫ ["+downLimitation+"->"+upLimitation+"]  "+inputs+"  dx");
                data.setResult(results);
                data.save();
                break;
            case R.id.history_button:
                Intent intent=new Intent(v.getContext(),History.class);
                startActivity(intent);
                break;
            case R.id.fab:
                Intent intentIntro=new Intent(v.getContext(), IntroActivity.class);
                startActivity(intentIntro);
                break;
        }
    }

    private double fun(double xi) throws ScriptException,NumberFormatException {

        ScriptEngineManager manager=new ScriptEngineManager();
        ScriptEngine engine=manager.getEngineByName("js");
        engine.put("x",xi);
        double result=(double)engine.eval(temp);
        return result;
    }

    private double calculate() throws ScriptException,NumberFormatException{

        int n=level;
        double a=downLimitation;
        double b=upLimitation;
        double h=(upLimitation-downLimitation)/(2*n);
        double total=fun(a)+fun(b);
        double xi;
        for (int i=1;i<2*n;i++)
        {
            xi=a+h*i;
            if (i%2==0)
            {
                total+=2*fun(xi);
            }
            else
            {
                total+=4*fun(xi);
            }
        }
        return total*h/3;
    }

    private void init() {

        inputs=inputText.getText().toString();
        String temp=levelText.getText().toString();
        try {
            level = Integer.parseInt(temp);
        }catch(NumberFormatException e)
        {
            level=100;
        }
        String temp1=downLimitationText.getText().toString();
        String temp2=upLimitationText.getText().toString();
        try{
            downLimitation=Double.parseDouble(temp1);
            upLimitation=Double.parseDouble(temp2);
        }catch(NumberFormatException e)
        {
            downLimitation=0;
            upLimitation=0;
        }
        if (downLimitation>upLimitation)
        {
            double t=downLimitation;
            downLimitation=upLimitation;
            upLimitation=t;
            isNegative=true;
        }
        else
        {
            isNegative=false;
        }
    }
}
