package pl.mgr.myapplication.ui.devlist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import pl.mgr.myapplication.DeviceInfo;
import pl.mgr.myapplication.DevicesManager;
import pl.mgr.myapplication.MainActivity;
import pl.mgr.myapplication.ModifyDeviceActivity;
import pl.mgr.myapplication.R;

public class DevListFragment extends Fragment {

    private DevListViewModel devListViewModel;
    ListView lvDevList;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        devListViewModel =
                new ViewModelProvider(this).get(DevListViewModel.class);
        devListViewModel.passContext(this.getContext());
        View root = inflater.inflate(R.layout.fragment_devices, container, false);

        lvDevList = root.findViewById(R.id.lvDevList);
        InitDevList();

        lvDevList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long l) {

                //pobranie parametrów kliknietego urzadzenia
                DeviceInfo SelectedDev = (DeviceInfo) lvDevList.getItemAtPosition(position);
                //utworzenie intentu
                Intent intent = new Intent(getActivity(), ModifyDeviceActivity.class);
                intent.putExtra("DeviceInfo", SelectedDev);
                //rozpoczecie aktywnosci
                startActivity(intent);
            }
        });

        lvDevList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                //pobranie parametrów kliknietego urzadzenia
                DeviceInfo SelectedDev = (DeviceInfo) lvDevList.getItemAtPosition(position);
                // Zbudowanie dialogu pytającego czy na pewno usunąć urządzenie
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Czy na pewno chcesz usunąć urządzenie?")
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                devListViewModel.DeleteDevice(SelectedDev.ID);
                                InitDevList();
                            }
                        })
                        .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                builder.show();

                return true;
            }




        });
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.)


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        InitDevList();
    }

    private void InitDevList()
    {
        ArrayList<DeviceInfo> arrayOfDevices= new ArrayList<DeviceInfo>();
        DeviceInfo[] DevInfos = devListViewModel.GetDeviceList();

        DeviceListAdapter adapter = new DeviceListAdapter(this.getContext(),arrayOfDevices);
        lvDevList.setAdapter(adapter);
        for(int i = 0;i<DevInfos.length;i++)
        {
            adapter.add(DevInfos[i]);
        }
    }



}