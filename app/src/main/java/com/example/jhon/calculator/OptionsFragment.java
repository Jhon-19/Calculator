package com.example.jhon.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jhon.calculator.Options.AddSubActivity;
import com.example.jhon.calculator.Options.DivActivity;
import com.example.jhon.calculator.Options.Factorial;
import com.example.jhon.calculator.Options.InteActivity;
import com.example.jhon.calculator.Options.MixActivity;
import com.example.jhon.calculator.Options.MulActivity;
import com.example.jhon.calculator.Options.PowActivity;
import com.example.jhon.calculator.Options.ProbActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jhon on 2020/1/29.
 */

public class OptionsFragment extends Fragment {

    private ListView optionsList;
    private List<String> options;
    private ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.options,container,false);
        optionsList=(ListView)view.findViewById(R.id.options_list);
        options=new ArrayList<>();
        addOptions();
        adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,options);
        optionsList.setAdapter(adapter);
        optionsList.setSelection(0);
        return view;
    }

    private void addOptions() {

        options.clear();
        String option1="样本均值标准差";
        String option2="乘法";
        String option3="加法减法";
        String option4="阶乘";
        String option5="乘方";
        String option6="除法";
        String option7="混合运算";
        String option8="定积分";
        options.add(option1);
        options.add(option2);
        options.add(option3);
        options.add(option4);
        options.add(option5);
        options.add(option6);
        options.add(option7);
        options.add(option8);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        optionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch(position)
                {
                    case 0:
                        Intent intent0=new Intent(getContext(), ProbActivity.class);
                        startActivity(intent0);
                        getActivity().finish();
                        break;
                    case 1:
                        Intent intent1=new Intent(getContext(), MulActivity.class);
                        startActivity(intent1);
                        getActivity().finish();
                        break;
                    case 2:
                        Intent intent2=new Intent(getContext(), AddSubActivity.class);
                        startActivity(intent2);
                        getActivity().finish();
                        break;
                    case 3:
                        Intent intent3=new Intent(getContext(), Factorial.class);
                        startActivity(intent3);
                        getActivity().finish();
                        break;
                    case 4:
                        Intent intent4=new Intent(getContext(), PowActivity.class);
                        startActivity(intent4);
                        getActivity().finish();
                        break;
                    case 5:
                        Intent intent5=new Intent(getContext(), DivActivity.class);
                        startActivity(intent5);
                        getActivity().finish();
                        break;
                    case 6:
                        Intent intent6=new Intent(getContext(), MixActivity.class);
                        startActivity(intent6);
                        getActivity().finish();
                        break;
                    case 7:
                        Intent intent7=new Intent(getContext(), InteActivity.class);
                        startActivity(intent7);
                        getActivity().finish();
                        break;
                }

            }
        });
    }
}
