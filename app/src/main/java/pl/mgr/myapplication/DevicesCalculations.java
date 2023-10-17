package pl.mgr.myapplication;

public class DevicesCalculations {

    //Lista urządzeń
    DeviceInfo[] DevInfoList;
    //Przyjęta cena za kWh w złotówkach
    double kWhPriceInZl = 0.60;
    double kWhToCO2factor = 823.257/1000;

    public DevicesCalculations(DeviceInfo[] devInfoList) {
        DevInfoList = devInfoList;
    }

    public double GetMothlyCost()
    {
        double MothlyCostRetVal = 0.0;
        double MonthlykWh = GetMonthlyPowUsage();
        MothlyCostRetVal = MonthlykWh * kWhPriceInZl;
        return MothlyCostRetVal;
    }

    public String GetDevMaxPowUsageName()
    {
        String MaxPowUsageDevName = "";
        double MaxPoWUsageVal = 0;
        for(int i =0;i<DevInfoList.length;i++)
        {
            double DevPowUsage;
            double DevUsageTime;
            double UsageWeekTime;
            switch (DevInfoList[i].UseDayTimeValType)
            {
                case "0":
                    //sekundy na godziny
                    DevUsageTime = Double.parseDouble(DevInfoList[i].UseDayTimeVal)/3600;
                    break;
                case "1":
                    //minuty na godziny
                    DevUsageTime = Double.parseDouble(DevInfoList[i].UseDayTimeVal)/60;
                    break;
                case "2":
                    //godziny, jest ok, po prostu przypisanie
                    DevUsageTime = Double.parseDouble(DevInfoList[i].UseDayTimeVal);
                    break;
                default:
                    DevUsageTime = 0;
                    break;
            }
            UsageWeekTime = Double.parseDouble(DevInfoList[i].AvgUseTimeWeek)+1;
            //Dodajemy użycie kolejnego urządzenia - (Moc/1000)*(4*Ilość_dni_w_tygodniu*Czas_w_godzinach):
            // - Moc/1000 - Waty na KiloWaty
            // - 4 to przyjęta ilość tygodni w miesiącu,
            DevPowUsage=(Double.parseDouble(DevInfoList[i].Power)/1000)*(4*UsageWeekTime*DevUsageTime);
            if(MaxPoWUsageVal< DevPowUsage)//Integer.parseInt(DevInfoList[i].Power))
            {
                MaxPoWUsageVal =  DevPowUsage;//Integer.parseInt(DevInfoList[i].Power);
                MaxPowUsageDevName = DevInfoList[i].Name;
            }
        }
        return  MaxPowUsageDevName;
    }

    public double GetDailyPowUsage()
    {
        double DailykWhRetVal = 0.0;
        //Przyjęte, że miesiąc ma 28 dni/4 tygodnie
        DailykWhRetVal = GetMonthlyPowUsage()/28;
        return DailykWhRetVal;
    }

    public double GetMonthlyPowUsage()
    {
        double MonthlykWhRetVal = 0.0;
        double UsageTimeDayInHours = 0;
        double UsageWeekTime;
        for(int i =0;i<DevInfoList.length;i++)
        {
            switch (DevInfoList[i].UseDayTimeValType)
            {
                case "0":
                    //sekundy na godziny
                    UsageTimeDayInHours = Double.parseDouble(DevInfoList[i].UseDayTimeVal)/3600;
                    break;
                case "1":
                    //minuty na godziny
                    UsageTimeDayInHours = Double.parseDouble(DevInfoList[i].UseDayTimeVal)/60;
                    break;
                case "2":
                    //godziny, jest ok, po prostu przypisanie
                    UsageTimeDayInHours = Double.parseDouble(DevInfoList[i].UseDayTimeVal);
                    break;
                default:
                    UsageTimeDayInHours = 0;
                    break;
            }
            UsageWeekTime = Double.parseDouble(DevInfoList[i].AvgUseTimeWeek)+1;
            //Dodajemy użycie kolejnego urządzenia - (Moc/1000)*(4*Ilość_dni_w_tygodniu*Czas_w_godzinach):
            // - Moc/1000 - Waty na KiloWaty
            // - 4 to przyjęta ilość tygodni w miesiącu,
            MonthlykWhRetVal+=(Double.parseDouble(DevInfoList[i].Power)/1000)*(4*UsageWeekTime*UsageTimeDayInHours);

        }
        return MonthlykWhRetVal;
    }

    public double GetYearlyPowUsage()
    {
        double YearlykWhRetVal = 0.0;
        YearlykWhRetVal = GetMonthlyPowUsage()*12;
        return YearlykWhRetVal;
    }

    public double GetDailyCO2Usage()
    {
        double DailyCO2RetVal = 0.0;
        DailyCO2RetVal = GetDailyPowUsage()*kWhToCO2factor;
        return DailyCO2RetVal;
    }

    public double GetMonthlyCO2Usage()
    {
        double MonthlyCO2RetVal = 0.0;
        MonthlyCO2RetVal = GetMonthlyPowUsage()*kWhToCO2factor;
        return MonthlyCO2RetVal;
    }

    public double GetYearlyCO2Usage()
    {
        double YearlyCO2RetVal = 0.0;
        YearlyCO2RetVal = GetYearlyPowUsage()*kWhToCO2factor;
        return YearlyCO2RetVal;
    }
}
