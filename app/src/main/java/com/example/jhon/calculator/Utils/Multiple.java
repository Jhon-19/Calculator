package com.example.jhon.calculator.Utils;

/**
 * Created by Jhon on 2020/1/30.
 */

public class Multiple {

    private static int x;
    private static int y;
    private static int[] xValue;
    private static int[] yValue;
    private static int[] resultValue;
    private static int[][] processValue;
    private static String result;

    private static void analyze(String xS,String yS)
    {
        xValue=new int[xS.length()];
        yValue=new int[yS.length()];
        resultValue=new int[(xS.length()+yS.length())];
        processValue=new int[yS.length()][(xS.length()+yS.length())];
        for(int i=0;i<xS.length();i++)
        {
            xValue[i]=xS.charAt(xS.length()-1-i)-'0';
        }
        for(int i=0;i<yS.length();i++)
        {
            yValue[i]=yS.charAt(yS.length()-1-i)-'0';
        }

    }

    private static void cal()
    {
        for(int i=0;i<yValue.length;i++)
        {
            for(int j=0;j<xValue.length;j++)
            {
                processValue[i][j+i]+=xValue[j]*yValue[i];
                if(processValue[i][j+i]>=10)
                {
                    processValue[i][j+i+1]+=processValue[i][j+i]/10;
                    processValue[i][j+i]=processValue[i][j+i]%10;
                }
            }
        }

        for(int i=0;i<processValue[0].length;i++)
        {
            for(int j=0;j<processValue.length;j++)
            {
                resultValue[i]+=processValue[j][i];
            }

            String temp=resultValue[i]+"";
            for(int j=1;j<temp.length();j++)
            {
                resultValue[i+j]+=temp.charAt(temp.length()-1-j)-'0';
            }
            resultValue[i]=temp.charAt(temp.length()-1)-'0';
        }
    }


    private static void show()
    {
        int index=resultValue.length;

        for(int i=resultValue.length-1;i>=0;i--)
        {
            if(resultValue[i]>0)
            {
                index=i;
                break;
            }
        }
        StringBuilder builder=new StringBuilder();
        for(int i=index;i>=0;i--)
        {
            builder.append(resultValue[i]);
        }
        result=builder.toString();
    }

    public static String multiply(String xS,String yS)
    {
        analyze(xS,yS);
        cal();
        show();
        return result;
    }
}
