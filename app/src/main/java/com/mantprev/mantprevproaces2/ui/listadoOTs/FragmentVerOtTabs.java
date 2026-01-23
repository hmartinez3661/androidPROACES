package com.mantprev.mantprevproaces2.ui.listadoOTs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.adapters.OrdTrabTabsAdapter;
import com.mantprev.mantprevproaces2.utilities.MetodosStaticos;


public class FragmentVerOtTabs extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    public static String fechaInicListOTs;
    public static String fechaFinalListOTs;


    public FragmentVerOtTabs() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_ver_ot_tabs, container, false);

        tabLayout = (TabLayout) root.findViewById(R.id.tabLayoutId);
        viewPager = (ViewPager2) root.findViewById(R.id.viewPagerId);
        OrdTrabTabsAdapter verOrdTrabTabsAdapter = new OrdTrabTabsAdapter(this);

        viewPager.setAdapter(verOrdTrabTabsAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (MetodosStaticos.idRepteEjecOT == 0){
                    Toast.makeText(getContext(), getResources().getString(R.string.msgNoRepteEjec), Toast.LENGTH_LONG).show();
                } else {
                    viewPager.setCurrentItem(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        return root;
    }



}