package com.mantprev.mantprevproaces2.ui.Rutinas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.adapters.RutinasTabsAdapter;


public class FragmentRutinasTabs extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    RutinasTabsAdapter rutinasTabsAdapter;


    public FragmentRutinasTabs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_rutinas_tabs, container, false);

        tabLayout = (TabLayout) root.findViewById(R.id.tabLayoutId);
        viewPager = (ViewPager2) root.findViewById(R.id.viewPagerId);

        rutinasTabsAdapter = new RutinasTabsAdapter(this);  //   getChildFragmentManager(), getLifecycle()
        viewPager.setAdapter(rutinasTabsAdapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());  //
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