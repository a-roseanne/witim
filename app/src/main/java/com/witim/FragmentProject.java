package com.witim;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProject extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    View view;

    public FragmentProject() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_project, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tablayout_project);
        viewPager = view.findViewById(R.id.viewpager_project);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        //Add Fragment
        adapter.addFragment(new fragment_project_ongoing(), "On-Progress");
        adapter.addFragment(new fragment_project_finished(), "Finished");

        //Adapter SetUp
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}