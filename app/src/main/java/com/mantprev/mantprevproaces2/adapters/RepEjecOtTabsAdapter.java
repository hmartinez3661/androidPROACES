package com.mantprev.mantprevproaces2.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.mantprev.mantprevproaces2.ui.ReportEjecOTs.FragmentTabRepEjecOT;
import com.mantprev.mantprevproaces2.ui.ReportEjecOTs.FragmentTabVerOrdTrab;

public class RepEjecOtTabsAdapter extends FragmentStateAdapter {


    public RepEjecOtTabsAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0){
            return new FragmentTabVerOrdTrab();

        } else { //Position es == 1
            return new FragmentTabRepEjecOT();
        }
    }


    @Override
    public int getItemCount() {
        return 2;
    }


}
