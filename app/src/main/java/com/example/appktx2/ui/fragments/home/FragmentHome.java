package com.example.appktx2.ui.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.appktx2.R;
import com.example.appktx2.databinding.FragmentHomeBinding;
import com.example.appktx2.databinding.FragmentProfileBinding;
import com.example.appktx2.databinding.ViewMyListViewBinding;

public class FragmentHome extends Fragment {
    FragmentHomeBinding binding;

    public FragmentHome() {
        // Required empty public constructor
    }
    public static FragmentHome newInstance() {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        setControl();
        setEvent();

        return view;


    }
    private void setEvent() {

    }

    private void setControl() {

    }

}
