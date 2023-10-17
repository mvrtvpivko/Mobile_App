package pl.mgr.myapplication;

import java.io.Serializable;

public class DeviceInfo implements Serializable
{
    public DeviceInfo(String ID, String name, String type, String power, String useDayTimeVal, String useDayTimeValType, String avgUseTimeWeek) {
        this.ID = ID;
        Name = name;
        Type = type;
        Power = power;
        UseDayTimeVal = useDayTimeVal;
        UseDayTimeValType = useDayTimeValType;
        AvgUseTimeWeek = avgUseTimeWeek;
    }

    public String ID;
    public String Name;
    public String Type;
    public String Power;
    public String UseDayTimeVal;
    public String UseDayTimeValType;
    public String AvgUseTimeWeek;



}
