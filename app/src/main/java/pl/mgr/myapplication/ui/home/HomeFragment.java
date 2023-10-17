package pl.mgr.myapplication.ui.home;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import pl.mgr.myapplication.R;

public class HomeFragment extends Fragment {

    final double EMISSION_GOOD_MIN =  0;
    final double EMISSION_NORMAL_MIN =  30.0;
    final double EMISSION_BAD_MIN =  60.0;
    final double EMISSION_BAD_MAX = 90.0;
    private HomeViewModel homeViewModel;
    Spinner SPeriod;
    TextView lBillVal, lDevVal, lkWhVal,lCO2Val;
    ImageView ivCO2UsePointer, ivColorBar;
    enum CO2EmissionEnum
    {
        good,
        normal,
        bad
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        SPeriod = root.findViewById(R.id.SPeriod);
        lBillVal = root.findViewById(R.id.LBillVal);
        lDevVal = root.findViewById(R.id.LDevVal);
        lkWhVal = root.findViewById(R.id.LkWhVal);
        lCO2Val = root.findViewById(R.id.LCO2Val);

        ivCO2UsePointer = root.findViewById(R.id.ivCO2UsePointer);
        ivColorBar = root.findViewById(R.id.ivColorBar);









        ArrayAdapter<CharSequence> SPeriodAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sPeriodItems, android.R.layout.simple_spinner_item);
        SPeriodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPeriod.setAdapter(SPeriodAdapter);
        InitView();
        SPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch(position)
                {

                    case 0:
                        //wybrano "dziennie"
                        lkWhVal.setText(homeViewModel.GetDailyPowUsage());
                        lCO2Val.setText(homeViewModel.GetDailyCO2Usage());
                        break;
                    case 1:
                        //wybrano "miesiecznie"
                        lkWhVal.setText(homeViewModel.GetMonthlyPowUsage());
                        lCO2Val.setText(homeViewModel.GetMonthlyCO2Usage());
                        break;
                    case 2:
                        //wybrano "rocznie"
                        lkWhVal.setText(homeViewModel.GetYearlyPowUsage());
                        lCO2Val.setText(homeViewModel.GetYearlyCO2Usage());
                        break;
                    default:
                        //Nie można wybrać innego elementu
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //przy wybraniu niczego nic się nie dzieje
            }

        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        InitView();

        int ColorBarSize =ivColorBar.getMeasuredWidth()-40;// Resources.getSystem().getDisplayMetrics().widthPixels/Resources.getSystem().getDisplayMetrics().density;
        double CO2Val = homeViewModel.GetMonthlyCO2UsageNum();
        CO2EmissionEnum ValToImageEnum;
        if(CO2Val>EMISSION_BAD_MAX)
        {
            CO2Val = EMISSION_BAD_MAX;
            ValToImageEnum = CO2EmissionEnum.bad;
            ivCO2UsePointer.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.unhappy));
        }
        else if(CO2Val>=EMISSION_BAD_MIN)
        {
            ValToImageEnum = CO2EmissionEnum.bad;
            ivCO2UsePointer.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.unhappy));
        }
        else if (CO2Val>=EMISSION_NORMAL_MIN)
        {
            ValToImageEnum = CO2EmissionEnum.normal;
            ivCO2UsePointer.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.normal));
        }
        //można dodawać kolejne stopnie i kolejne "emotki"
        else
        {
            ValToImageEnum = CO2EmissionEnum.good;
            ivCO2UsePointer.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.happy));
        }
        double LeftMarginVal = (ColorBarSize * CO2Val)/EMISSION_BAD_MAX;
        ConstraintLayout.LayoutParams lp =(ConstraintLayout.LayoutParams) ivCO2UsePointer.getLayoutParams();
        lp.leftMargin = (int)LeftMarginVal;
        ivCO2UsePointer.setLayoutParams(lp);

    }

    public void InitView()
    {
        homeViewModel.passContext(this.getContext());
        SPeriod.setSelection(0);
        lBillVal.setText(homeViewModel.GetMothlyCostStr());
        lDevVal.setText(homeViewModel.GetDevMaxPowUsageName());
        lkWhVal.setText(homeViewModel.GetDailyPowUsage());
        lCO2Val.setText(homeViewModel.GetDailyCO2Usage());
    }

}