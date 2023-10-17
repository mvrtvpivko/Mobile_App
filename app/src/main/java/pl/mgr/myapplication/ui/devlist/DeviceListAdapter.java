package pl.mgr.myapplication.ui.devlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pl.mgr.myapplication.DeviceInfo;
import pl.mgr.myapplication.R;

public class DeviceListAdapter extends ArrayAdapter<DeviceInfo> {

    private String[] TypeStr;
    public DeviceListAdapter(Context context, ArrayList<DeviceInfo>DevInfo){
        super(context,0,DevInfo);
        //pobranie z pliku "strings.xml"
        TypeStr = context.getResources().getStringArray(R.array.sDevTypeItems);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DeviceInfo DevInfo = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.devicelist_adapter, parent, false);
        }
        TextView tvDevName = (TextView) convertView.findViewById(R.id.TvDevName);
        TextView tvDevType = (TextView) convertView.findViewById(R.id.TvDevType);
        TextView tvDevPower = (TextView) convertView.findViewById(R.id.TvDevPower);
        // Populate the data into the template view using the data object
        tvDevName.setText(DevInfo.Name);
        tvDevType.setText(TypeStr[Integer.parseInt(DevInfo.Type)]);
        tvDevPower.setText(DevInfo.Power+" W");
        // Return the completed view to render on screen
        return convertView;
    }


}
