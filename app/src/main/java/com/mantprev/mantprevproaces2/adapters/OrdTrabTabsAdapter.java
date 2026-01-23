package com.mantprev.mantprevproaces2.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.mantprev.mantprevproaces2.ui.ReportEjecOTs.FragmentTabVerOrdTrab;
import com.mantprev.mantprevproaces2.ui.listadoOTs.FragmentTabVerRepEje;

public class OrdTrabTabsAdapter extends FragmentStateAdapter {


    public OrdTrabTabsAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0){
            return new FragmentTabVerOrdTrab(); //

        } else { //Position es == 1
            return new FragmentTabVerRepEje();
        }
    }


    @Override
    public int getItemCount() {
        return 2;
    }



}
