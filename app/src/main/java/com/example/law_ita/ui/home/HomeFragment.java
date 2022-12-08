package com.example.law_ita.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.law_ita.R;
import com.example.law_ita.agendarActivity;
import com.example.law_ita.databinding.FragmentHomeBinding;
import com.example.law_ita.verCitaActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Button agendar, ver;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //HomeViewModel homeViewModel =
                //new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        agendar = (Button) root.findViewById(R.id.agendarBtn);
        ver = (Button) root.findViewById(R.id.citasBtn);

        agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), agendarActivity.class);
                getActivity().startActivity(intent);
            }
        });

        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), verCitaActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}