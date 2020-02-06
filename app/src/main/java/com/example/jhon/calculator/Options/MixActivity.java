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

public class MixActivity extends AppCompatActivity
            implements View.OnClickListener{

    private TextView resultText;
    private EditText inputText;
    private Button clearButton;
    private Button startButton;
    private Button historyButton;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix);
        resultText=(TextView)findViewById(R.id.result_text);
        inputText=(EditText)findViewById(R.id.input_text);
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
                break;
            case R.id.start_button:
                String inputs=inputText.getText().toString();
                String temp= Analyze.parse(inputs);
                ScriptEngineManager manager=new ScriptEngineManager();
                ScriptEngine engine=manager.getEngineByName("js");
                double result;
                try {
                    result=(double)engine.eval(temp);
                } catch (Exception e) {
                    result=Double.NaN;
                }
                String results=result+"";
                resultText.setText(results);

                HistoryData data=new HistoryData();
                data.setInput(inputs);
                data.setResult(results);
                data.save();
                break;
            case R.id.history_button:
                Intent intent=new Intent(this,History.class);
                startActivity(intent);
                break;
            case R.id.fab:
                Intent intentIntro=new Intent(this, IntroActivity.class);
                startActivity(intentIntro);
                break;
        }
    }
}
