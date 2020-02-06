package com.example.jhon.calculator.Utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jhon on 2020/1/28.
 */

public class Probability {

    public static String[] probCal(String input)
    {
        String temp1=input.replace("\n"," ");
        String temp2=temp1.replace(","," ");
        String[] datas=temp2.split(" ");

        List numList=new ArrayList<>();
        for (int i=0;i<datas.length;i++)
        {
            if (!TextUtils.isEmpty(datas[i]))
            {
                try {
                    numList.add(Float.parseFloat(datas[i]));
                }catch(NumberFormatException e) {
                }
            }
        }
        Float[] nums=new Float[numList.size()];
        numList.toArray(nums);

        /**
         * 均值
         */
        double sum1=0;
        for (float num:nums)
        {
            sum1+=num;
        }
        double average=sum1/(nums.length);

        /**
         * 方差
         */
        double sum2=0;
        for (float num:nums)
        {
            sum2+=(num-average)*(num-average);
        }
        double average2=sum2/(nums.length);

        /**
         * 样本标准差
         */
        double average3=Math.sqrt(sum2/(nums.length-1));

        String[] result=new String[3];
        result[0]=average+"";
        result[1]=average2+"";
        result[2]=average3+"";

        return result;
    }

}
