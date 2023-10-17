package pl.mgr.myapplication.ui.devlist;

import android.app.Application;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import pl.mgr.myapplication.DeviceInfo;
import pl.mgr.myapplication.DevicesManager;

public class DevListViewModel extends ViewModel {

    private DevListViewModel devListViewModel;
    DevicesManager manager;
    Context context;
    public DevListViewModel() {



    }
    public void passContext(Context context)
    {
     this.context = context;
     manager =  new DevicesManager(context);
    }

    public DeviceInfo[] GetDeviceList()
    {
        //ArrayAdapter<String,String,String> adapter = new ArrayAdapter<String,String,String>();
        
        DeviceInfo[] DevInfo = manager.GetDeviceList();
        return DevInfo;
    }

    public void DeleteDevice(String ID)
    {
        manager.DeleteDevice(ID);
    }


}