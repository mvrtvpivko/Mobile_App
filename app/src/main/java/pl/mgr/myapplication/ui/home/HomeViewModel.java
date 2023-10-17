package pl.mgr.myapplication.ui.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import pl.mgr.myapplication.DeviceInfo;
import pl.mgr.myapplication.DevicesCalculations;
import pl.mgr.myapplication.DevicesManager;

public class HomeViewModel extends ViewModel {

    DevicesManager manager;
    Context context;
    DeviceInfo[] DevInfo;
    DevicesCalculations DevCalcs;

    public HomeViewModel() {


    }

    public void passContext(Context context)
    {
        this.context = context;
        manager =  new DevicesManager(context);
        DevInfo = manager.GetDeviceList();
        DevCalcs = new DevicesCalculations(DevInfo);
    }

    public DeviceInfo[] GetDeviceList()
    {
        return DevInfo;
    }

    public String GetMothlyCostStr()
    {
        return String.format("%.2f",DevCalcs.GetMothlyCost());
    }

    public String GetDevMaxPowUsageName()
    {
        return DevCalcs.GetDevMaxPowUsageName();
    }

    public String GetDailyPowUsage()
    {
        return String.format("%.2f",DevCalcs.GetDailyPowUsage());
    }

    public String GetMonthlyPowUsage()
    {
        return String.format("%.2f",DevCalcs.GetMonthlyPowUsage());
    }

    public String GetYearlyPowUsage()
    {
        return String.format("%.2f",DevCalcs.GetYearlyPowUsage());
    }

    public String GetDailyCO2Usage()
    {
        return String.format("%.2f",DevCalcs.GetDailyCO2Usage());
    }

    public String GetMonthlyCO2Usage()
    {
        return String.format("%.2f",DevCalcs.GetMonthlyCO2Usage());
    }

    public double GetMonthlyCO2UsageNum()
    {
        return DevCalcs.GetMonthlyCO2Usage();
    }

    public String GetYearlyCO2Usage()
    {
        return String.format("%.2f",DevCalcs.GetYearlyCO2Usage());
    }


}