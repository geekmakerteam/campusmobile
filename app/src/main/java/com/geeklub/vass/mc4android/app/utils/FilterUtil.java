package com.geeklub.vass.mc4android.app.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by justhacker on 9/05/14.
 */
public class FilterUtil {

	public static String sizeConver(Long filesize)
	{
        double temp;
        boolean flag=false;

        if(filesize<1024)
        {
        	return String.valueOf(filesize)+"B";
        }
        else 
        {
        	temp=filesize/1024.00;

    		if(temp>=1024.00)
    		{
                temp=temp/1024.00;
                flag=true;
    		}
    	    DecimalFormat df = new DecimalFormat("#.00");
    		StringBuffer result=new StringBuffer(df.format(temp));
            if(flag)
            {
              result.append("M");
            }
            else
            {
            	result.append("K");
            }
            return result.toString();
		}
	}
	

	public static String getTime(long time)
	{
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      String date = sdf.format(new Date(1399647108*1000L));
	      return date;
	}

	public static void main(String args[])
	{
         System.out.println(sizeConver((long) 60928));
         System.out.println(getTime(1399647108));
	}
}
