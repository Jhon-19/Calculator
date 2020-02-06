package com.example.jhon.calculator.Utils;

/**
 * Created by Jhon on 2020/2/6.
 */

public class Analyze {

    public static String parse(String inputs)
    {
        String[] temps=inputs.split("[<>]");
        StringBuilder builder=new StringBuilder();
        for (int i=0;i<temps.length;i++)
        {
            if (i%2!=0)
            {
                temps[i]=temps[i].replace("^","#");
                String[] nums=temps[i].split("#");
                temps[i]="Math.pow("+nums[0]+","+nums[1]+")";
            }
            builder.append(temps[i]);
        }
        String temp=builder.toString();
        temp=temp.replace("exp","Math.exp");
        temp=temp.replace("ln","Math.log");
        temp=temp.replace("lg","Math.log10");
        temp=temp.replace("sin","Math.sin");
        temp=temp.replace("cos","Math.cos");
        temp=temp.replace("tan","Math.tan");
        temp=temp.replace("sh","Math.sinh");
        temp=temp.replace("ch","Math.cosh");
        temp=temp.replace("arcsin","Math.asin");
        temp=temp.replace("arccos","Math.acos");
        temp=temp.replace("arctan","Math.atan");
        temp=temp.replace("sqrt","Math.sqrt");
        temp=temp.replace("cbrt","Math.cbrt");
        temp=temp.replace("E","Math.E");
        temp=temp.replace("PI","Math.PI");
        String[] tempsAbs=temp.split("[|]");
        builder=new StringBuilder();
        for (int i=0;i<tempsAbs.length;i++)
        {
            if (i%2!=0)
            {
                tempsAbs[i]="Math.abs("+tempsAbs[i]+")";
            }
            builder.append(tempsAbs[i]);
        }
        temp=builder.toString();
        return temp;
    }
}
