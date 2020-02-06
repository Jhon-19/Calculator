package com.example.jhon.calculator.Options;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jhon.calculator.History;
import com.example.jhon.calculator.R;
import com.example.jhon.calculator.Utils.Probability;
import com.example.jhon.calculator.db.HistoryData;

public class ProbActivity extends AppCompatActivity
            implements View.OnClickListener{

    private TextView resultText;
    private EditText inputText;
    private Button clearButton;
    private Button startButton;
    private Button historyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prob);
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
                String input=inputText.getText().toString();
                String[] result= Probability.probCal(input);
                String results="样本均值是  "+result[0]+"\n"
                        +"方差是  "+result[1]+"\n"
                        +"样本标准差是  "+result[2]+"\n";
                resultText.setText(results);

                HistoryData data=new HistoryData();
                data.setInput(input);
                data.setResult(results);
                data.save();
                break;
            case R.id.history_button:
                Intent intent=new Intent(this,History.class);
                startActivity(intent);
                break;
        }

    }

}
