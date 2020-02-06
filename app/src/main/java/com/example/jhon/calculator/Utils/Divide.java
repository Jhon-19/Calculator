package com.example.jhon.calculator.Utils;

/**
 * Created by Jhon on 2020/2/3.
 */

public class Divide {

    private static String datas1;
    private static String datas2;
    private static int[] data1;
    private static int[] data2;
    private static int[] temp;
    private static int[] result;
    private static int[] remain;
    private static int len1;
    private static int len2;
    private static int index;
    private static int start;
    private static String results;
    private static String remains;

    private static void analyze()
    {
        data1=new int[len1];
        data2=new int[len2];
        result=new int[len1-len2+1];
        remain=new int[len2];
        temp=new int[len2];
        for(int i=0;i<len1;i++)
        {
            data1[i]=datas1.charAt(i)-'0';
        }
        for(int i=0;i<len2;i++)
        {
            data2[i]=datas2.charAt(i)-'0';
        }
    }

    private static void setTemp()
    {
        for(int i=start;i<start+len2;i++)
        {
            temp[i-start]=data1[i];
        }
    }

    private static void setData1()
    {
        for(int i=start;i<start+len2;i++)
        {
            data1[i]=temp[i-start];
        }
    }

    private static void sub()
    {
        for(int i=len2-1;i>0;i--)
        {
            if(temp[i]<data2[i])
            {
                temp[i]+=10;
                temp[i-1]--;
            }
            temp[i]-=data2[i];
        }
        temp[0]-=data2[0];
        result[index]++;
    }

    private static boolean link()
    {
        setData1();
        start++;
        index++;
        if(index==result.length)
        {
            for(int i=start-1;i<len1;i++)
            {
                remain[i+1-start]=data1[i];
            }
            return false;
        }
        setTemp();
        return true;
    }

    private static int judge()
    {
        for(int i=0;i<len2;i++)
        {
            if(temp[i]==data2[i])
            {
                continue;
            }
            else if(temp[i]<data2[i])
            {
                return -1;
            }
            else
            {
                return 1;
            }
        }
        return 0;
    }

    private static void calculate()
    {
        setTemp();
        int judgement;
OUT:    while(true)
        {
            judgement=judge();
            if(judgement==-1)
            {
                while(judge()==-1)
                {
                    if(!link())
                    {
                        break OUT;
                    }
                    temp[0]+=data1[start-1]*10;
                    data1[start-1]=0;
                }
                sub();
            }
            else if(judgement==0)
            {
                sub();
                if(!link())
                {
                    break;
                }
            }
            else
            {
                sub();
            }
        }
    }

    private static void show()
    {
        results="";
        remains="+";
        int i=0;
        for(;i<result.length;i++)
        {
            if(result[i]!=0)
            {
                break;
            }
        }
        for(;i<result.length;i++)
        {
            results+=result[i]+"";
        }
        i=0;
        for(;i<remain.length-1;i++)
        {
            if(remain[i]!=0)
            {
                break;
            }
        }
        for(;i<remain.length;i++)
        {
            remains+=remain[i]+"";
        }
    }

    public static String divide(String datas1,String datas2)
    {
        index=0;
        start=0;
        len1=datas1.length();
        len2=datas2.length();
        Divide.datas1=datas1;
        Divide.datas2=datas2;
        analyze();
        if (len1<len2)
        {
            results="0";
            remains="+";
            for (int i=0;i<len1;i++)
            {
                remains+=data1[i];
            }
        }
        else
        {
            calculate();
            show();
        }
        return results+remains;
    }
}
