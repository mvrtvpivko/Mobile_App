package pl.mgr.myapplication;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DevicesManager {

    private File devListFolder;
    private String fileName;
    private String filePath;
    private File devListFile;
    private FileWriter writer;
    private FileReader reader;
    private Context context;
    int DevCount = 0;
    int LastID = 0;

    String fileHeader = "ID;Name;Type;Power;UseDayTimeVal;UseDayTimeType;AvgUseTimeWeek\n";

    public DevicesManager(Context context)
    {
        //Environment.getExternalStorageDirectory()
        this.context = context;
        devListFolder = new File(context.getExternalFilesDir(null).getAbsolutePath()+ "/EnSaveApp");
        fileName = "DevListData.esa";
        filePath = devListFolder.toString()+"/"+fileName;
        devListFile = new File(filePath);
        Init();

    }

    private void Init()
    {
        if(!devListFolder.exists())
        {
            devListFolder.mkdir();
        }
        if(!devListFile.exists())
        {
            try {
                devListFile.createNewFile();
                writer = new FileWriter(filePath);
                writer.append(fileHeader);
                writer.flush();
                writer.close();

            } catch (IOException e) {

            }
        }
        GetDeviceList();
    }

    public void AddDevice(String name, String type, String Power, String UseDayTimeVal, String UseDayTimeType, String AvgUseTimeWeek)
    {
        String separator = ";";
        String endline = "\n";
        String id = String.valueOf(LastID +1);
        String Line = String.format(id + separator + name + separator + type + separator + Power + separator + UseDayTimeVal + separator + UseDayTimeType + separator + AvgUseTimeWeek + endline);
        try {
            writer = new FileWriter(filePath,true);
            writer.append(Line);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            
        }

    }

    public DeviceInfo[] GetDeviceList()
    {
        String[] lines = new String[2000];
        DevCount = 0;
        try {
            reader = new FileReader(filePath);
            Scanner sc = new Scanner(reader);

            while(sc.hasNextLine())
            {
                lines[DevCount] = sc.nextLine();
                DevCount++;

            }
            DevCount--;
        } catch (FileNotFoundException e) {

        }
        DeviceInfo[] DevInfoList = new DeviceInfo[DevCount];
        for (int i=1;i<DevInfoList.length+1;i++)
        {

            String[] parameters = lines[i].split(";");
            DevInfoList[i-1]=new DeviceInfo(parameters[0],parameters[1],parameters[2],parameters[3],parameters[4],parameters[5],parameters[6]);
            LastID = Integer.parseInt(parameters[0]);
            /*DevInfoList[i-1].ID = parameters[0];
            DevInfoList[i-1].Name = parameters[1];
            DevInfoList[i-1].Type = parameters[2];
            DevInfoList[i-1].Power = parameters[3];
            DevInfoList[i-1].UseDayTimeVal = parameters[4];
            DevInfoList[i-1].UseDayTimeValType = parameters[5];
            DevInfoList[i-1].AvgUseTimeWeek = parameters[6];*/
        }
        return DevInfoList;

    }

    public void ModifyDevice(String devID, String name, String type, String Power, String UseDayTimeVal, String UseDayTimeType, String AvgUseTimeWeek) {

        String NewContent = "";
        String separator = ";";
        String endline = "\n";
        try {
            reader = new FileReader(filePath);
            Scanner sc = new Scanner(reader);


            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parameters = line.split(";");
                if (parameters[0].equals(devID)) {
                   line = String.format(devID + separator + name + separator + type + separator + Power + separator + UseDayTimeVal + separator + UseDayTimeType + separator + AvgUseTimeWeek);
                }
                NewContent += line + endline;

            }
            reader.close();
            writer = new FileWriter(filePath);
            writer.write(NewContent);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

    }

    public void DeleteDevice(String devID) {

        String NewContent = "";
        String separator = ";";
        String endline = "\n";
        try {
            reader = new FileReader(filePath);
            Scanner sc = new Scanner(reader);


            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parameters = line.split(";");
                if (parameters[0].equals(devID)) {
                    //nie robimy zadnych akcji, nie dodajemy aktualnej linijki, przez co zostanie usunieta
                }
                else
                {
                    NewContent += line + endline;
                }


            }
            reader.close();
            writer = new FileWriter(filePath);
            writer.write(NewContent);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

    }

}

