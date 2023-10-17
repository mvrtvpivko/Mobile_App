package pl.mgr.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class ModifyDeviceActivity extends AppCompatActivity {

    Spinner sDevType, sAvgTimeInfo, sAvgTime;
    Button btAddDev;
    EditText etDevName,etPowUsageVal, etAvgTimeVal;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        //pobranie parametrów klikniętego urządzenia (z intentu)
        Intent i = getIntent();
        DeviceInfo dev = (DeviceInfo)i.getSerializableExtra("DeviceInfo");
        //spinner Typu urządzenia
        sDevType = findViewById(R.id.sDevType);

        ArrayAdapter<CharSequence> sDevTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.sDevTypeItems, android.R.layout.simple_spinner_item);
        sDevTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sDevType.setAdapter(sDevTypeAdapter);

        //spinner średniego czasu działania
        sAvgTimeInfo = findViewById(R.id.sAvgTimeInfo);
        ArrayAdapter<CharSequence> sAvgTimeInfoAdapter = ArrayAdapter.createFromResource(this,
                R.array.sTimeTypeItems, android.R.layout.simple_spinner_item);
        sAvgTimeInfoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sAvgTimeInfo.setAdapter(sAvgTimeInfoAdapter);


        //spinner średniej ilości używania w tygodniu
        sAvgTime = findViewById(R.id.sAvgTime);

        ArrayAdapter<CharSequence> SAvgTimeAdapter = ArrayAdapter.createFromResource(this,
                R.array.sDayNumItems, android.R.layout.simple_spinner_item);
        SAvgTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sAvgTime.setAdapter(SAvgTimeAdapter);
        //kontrolki wartości wpisywalnych parametrów:
        etDevName = findViewById(R.id.etDevName);
        etPowUsageVal = findViewById(R.id.etPowUsageVal);
        etAvgTimeVal = findViewById(R.id.etAvgTimeVal);
        //przycisk dodania urządzenia
        btAddDev = findViewById(R.id.BtAddDevice);
        btAddDev.setText("Zatwierdź zmiany");


        //Ustawienie parametrów urządzenia względem faktycznych
        etDevName.setText(dev.Name);
        etPowUsageVal.setText(dev.Power);
        etAvgTimeVal.setText(dev.UseDayTimeVal);
        sAvgTimeInfo.setSelection(Integer.parseInt(dev.UseDayTimeValType));
        sDevType.setSelection(Integer.parseInt(dev.Type));
        sAvgTime.setSelection(Integer.parseInt(dev.AvgUseTimeWeek));

        btAddDev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ModifyDevice(dev.ID);

            }
        });


    }

    void ModifyDevice(String DevID)
    {
        String DevName = etDevName.getText().toString();
        String PowUsageVal = etPowUsageVal.getText().toString();
        String AvgDayTimeVal = etAvgTimeVal.getText().toString();
        String AvgDayTimeType = String.valueOf(sAvgTimeInfo.getSelectedItemPosition());
        String DevType = String.valueOf(sDevType.getSelectedItemPosition());
        String AvgWeekTime = String.valueOf(sAvgTime.getSelectedItemPosition());

        if(DevName.isEmpty()||PowUsageVal.isEmpty()|| AvgDayTimeVal.isEmpty())
        {
            Toast.makeText(this,"Proszę wypełnić wszystkie pola",Toast.LENGTH_SHORT).show();
        }
        else
        {
            DevicesManager manager = new DevicesManager(this);
            manager.ModifyDevice(DevID, DevName, DevType, PowUsageVal, AvgDayTimeVal, AvgDayTimeType, AvgWeekTime);
            finish();
        }
    }
}