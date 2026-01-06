package com.mantprev.mantprevproaces2.ui.ReportEjecOTs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.adapters.RepEjecOtTabsAdapter;


public class FragmentReportEjecTabs extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    RepEjecOtTabsAdapter repEjecOtTabsAdapter;

    public FragmentReportEjecTabs() {
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

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_report_ejec_tabs, container, false);

        tabLayout = (TabLayout) root.findViewById(R.id.tabLayoutId);
        viewPager = (ViewPager2) root.findViewById(R.id.viewPagerId);
        repEjecOtTabsAdapter = new RepEjecOtTabsAdapter(this);
        viewPager.setAdapter(repEjecOtTabsAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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