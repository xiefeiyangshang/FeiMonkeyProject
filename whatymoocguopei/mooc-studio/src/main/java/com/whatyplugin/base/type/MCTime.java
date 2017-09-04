package com.whatyplugin.base.type;



import java.io.Serializable;

import android.content.Context;
import android.content.res.Resources;
import cn.com.whatyplugin.mooc.R;


public class MCTime
  implements Serializable
{
  private long milliseconds;
  private TimeStruct stime;

  private MCTime(long paramLong)
  {
    this.milliseconds = paramLong;
    this.stime = parseTime();
  }

  private TimeStruct parseTime()
  {
    long l = this.milliseconds / 1000L;
    int i = (int)l / 3600;
    int j = (int)l / 60;
    if (i > 0)
      j = (int)(l - 60 * (i * 60)) / 60;
    return new TimeStruct(i, j, (int)l % 60);
  }

  public static MCTime timeWithMilliseconds(long paramLong)
  {
    return new MCTime(paramLong);
  }

  public static MCTime timeWithSeconds(int paramInt)
  {
    return new MCTime(paramInt * 1000);
  }

  public String ENAUTOHHMMSS()
  {
    if (this.stime.hour > 0)
    {
      Object[] arrayOfObject2 = new Object[3];
      arrayOfObject2[0] = Integer.valueOf(this.stime.hour);
      arrayOfObject2[1] = Integer.valueOf(this.stime.minute);
      arrayOfObject2[2] = Integer.valueOf(this.stime.second);
      return String.format("%d:%02d:%02d", arrayOfObject2);
    }
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = Integer.valueOf(this.stime.minute);
    arrayOfObject1[1] = Integer.valueOf(this.stime.second);
    return String.format("%02d:%02d", arrayOfObject1);
  }

  public String ENHHMM()
  {
    return "";
  }

  public String ENHHMMSS()
  {
    return "";
  }

  public String HOURS(Context paramContext)
  {
    Resources localResources = paramContext.getResources();
    int i = R.string.time_format;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(this.stime.hour);
    return localResources.getString(i, arrayOfObject);
  }

  public long milliseconds()
  {
    return this.milliseconds;
  }

  public long seconds()
  {
    return this.milliseconds / 1000L;
  }

  class TimeStruct
    implements Serializable
  {
    int hour;
    int minute;
    int second;

    public TimeStruct(int hour, int minute, int second)
    {
      this.hour = hour;
      this.minute = minute;
      this.second = second;
    }
  }
}
