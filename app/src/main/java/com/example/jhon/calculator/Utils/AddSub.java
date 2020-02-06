package com.example.jhon.calculator.Utils;

/**
 * Created by Jhon on 2020/2/1.
 */

public class AddSub {

    private static String datas1;
    private static String datas2;
    private static String dataInt1;
    private static String dataInt2;
    private static String dataFloat1;
    private static String dataFloat2;
    private static String resultInt;
    private static String resultFloat;
    private static boolean influence;
    private static boolean isNegative;
    private static int[] tempInt1;
    private static int[] tempInt2;
    private static int[] tempFloat1;
    private static int[] tempFloat2;
    private static int len1;
    private static int len2;

    private static void analyze()
    {
        dataInt1=datas1.split("[.]")[0];
        dataFloat1=datas1.split("[.]")[1];
        dataInt2=datas2.split("[.]")[0];
        dataFloat2=datas2.split("[.]")[1];
    }

    private static String addInt()
    {
        handleInt();
        int[] result=new int[len1+1];
        if(influence)
        {
            result[0]=1;
        }
        for(int i=0;i<len1;i++)
        {
            result[i]+=tempInt1[i]+tempInt2[i];
            if(result[i]>=10)
            {
                result[i]=result[i]%10;
                result[i+1]++;
            }
        }
        int i=len1;
        if(result[len1]==0)
        {
            i--;
        }
        resultInt=result[i]+"";
        for(i--;i>=0;i--)
        {
            resultInt+=result[i]+"";
        }
        return resultInt;
    }

    private static void addFloat()
    {
        handleFloat();
        influence=false;
        int[] result=new int[len2];
        for(int i=len2-1;i>=1;i--)
        {
            result[i]+=tempFloat1[i]+tempFloat2[i];
            if(result[i]>=10)
            {
                result[i]=result[i]%10;
                result[i-1]++;
            }
        }
        result[0]+=tempFloat1[0]+tempFloat2[0];
        if(result[0]>=10)
        {
            result[0]=result[0]%10;
            influence=true;
        }
        resultFloat=result[0]+"";
        for(int i=1;i<result.length;i++)
        {
            resultFloat+=result[i]+"";
        }
    }

    private static String subInt()
    {
        int[] result=new int[len1];
        if(influence)
        {
            tempInt1[0]--;
        }
        for(int i=0;i<len1-1;i++)
        {
            if(tempInt1[i]<tempInt2[i])
            {
                tempInt1[i]+=10;
                tempInt1[i+1]--;
            }
            result[i]=tempInt1[i]-tempInt2[i];
        }
        result[len1-1]=tempInt1[len1-1]-tempInt2[len1-1];
        int i=len1-1;
        if(result[len1-1]==0 && i>0)
        {
            i--;
        }
        if(isNegative)
        {
            resultInt="-"+result[i];
        }
        else
        {
            resultInt=result[i]+"";
        }
        for(i--;i>=0;i--)
        {
            resultInt+=result[i]+"";
        }
        return resultInt;
    }

    private static void subFloat()
    {
        influence=false;
        int[] result=new int[len2];
        for(int i=len2-1;i>=1;i--)
        {
            if(tempFloat1[i]<tempFloat2[i])
            {
                tempFloat1[i]+=10;
                tempFloat1[i-1]--;
            }
            result[i]=tempFloat1[i]-tempFloat2[i];
        }
        if(tempFloat1[0]<tempFloat2[0])
        {
            tempFloat1[0]+=10;
            influence=true;
        }
        result[0]=tempFloat1[0]-tempFloat2[0];
        resultFloat=result[0]+"";
        for(int i=1;i<result.length;i++)
        {
            resultFloat+=result[i]+"";
        }
    }

    private static void judge()
    {
        isNegative=false;
        int b=0;
        for(int i=len1-1;i>=0;i--)
        {
            if(tempInt1[i]==tempInt2[i])
            {
                continue;
            }
            else if(tempInt1[i]>tempInt2[i])
            {
                b=1;
                break;
            }
            else
            {
                b=-1;
                break;
            }
        }
        if(b==0)
        {
            for(int i=0;i<len2-1;i++)
            {
                if(tempFloat1[i]==tempFloat2[i])
                {
                    continue;
                }
                else if(tempFloat1[i]>tempFloat2[i])
                {
                    isNegative=false;
                }
                else
                {
                    isNegative=true;
                }
            }
        }
        else if(b==1)
        {
            isNegative=false;
        }
        else if(b==-1)
        {
            isNegative=true;
        }
        if(isNegative)
        {
            int[] temp=tempInt1;
            tempInt1=tempInt2;
            tempInt2=temp;
            temp=tempFloat1;
            tempFloat1=tempFloat2;
            tempFloat2=temp;
        }
    }

    private static void handleInt()
    {
        tempInt1=new int[len1];
        tempInt2=new int[len1];
        for(int i=dataInt1.length()-1;i>=0;i--)
        {
            tempInt1[dataInt1.length()-1-i]=dataInt1.charAt(i)-'0';
        }
        for(int i=dataInt2.length()-1;i>=0;i--)
        {
            tempInt2[dataInt2.length()-1-i]=dataInt2.charAt(i)-'0';
        }
    }

    private static void handleFloat()
    {
        tempFloat1=new int[len2];
        tempFloat2=new int[len2];
        for(int i=0;i<dataFloat1.length();i++)
        {
            tempFloat1[i]=dataFloat1.charAt(i)-'0';
        }
        for(int i=0;i<dataFloat2.length();i++)
        {
            tempFloat2[i]=dataFloat2.charAt(i)-'0';
        }
    }

    private static void setLen()
    {
        len1=Math.max(dataInt1.length(), dataInt2.length());
        len2=Math.max(dataFloat1.length(), dataFloat2.length());
    }

    public static String add(String datas1,String datas2)
    {
        AddSub.datas1=datas1;
        AddSub.datas2=datas2;
        analyze();
        setLen();
        handleInt();
        handleFloat();
        addFloat();
        addInt();
        return resultInt+"."+resultFloat;
    }

    public static String sub(String datas1,String datas2)
    {
        AddSub.datas1=datas1;
        AddSub.datas2=datas2;
        analyze();
        setLen();
        handleInt();
        handleFloat();
        judge();
        subFloat();
        subInt();
        return resultInt+"."+resultFloat;
    }
}
