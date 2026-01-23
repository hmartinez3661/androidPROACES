package com.mantprev.mantprevproaces2.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.mantprev.mantprevproaces2.ui.Rutinas.FragmtTabRpteEjecRut;
import com.mantprev.mantprevproaces2.ui.Rutinas.FragmtTabVerRutina;

public class RutinasTabsAdapter extends FragmentStateAdapter {

    public RutinasTabsAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            return new FragmtTabVerRutina();

        } else { //Position es == 1
            return new FragmtTabRpteEjecRut();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }


}
