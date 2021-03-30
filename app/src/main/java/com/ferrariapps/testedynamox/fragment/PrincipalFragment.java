package com.ferrariapps.testedynamox.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ferrariapps.testedynamox.R;
import com.ferrariapps.testedynamox.activity.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;


public class PrincipalFragment extends Fragment {

    private Button button;
    private SharedPreferences prefs;
    private TextInputEditText textNome;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal, container, false);
        textNome = view.findViewById(R.id.textNome);

        button = view.findViewById(R.id.buttonComeçar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Objects.requireNonNull(textNome.getText()).toString().isEmpty() && !textNome.getText().toString().equals("")) {
                    prefs = requireActivity().getSharedPreferences(
                            "User", Context.MODE_PRIVATE);
                    prefs.edit().putString("user", Objects.requireNonNull(textNome.getText()).toString()).apply();

                            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.frameLayout, new PerguntasFragment());
                            ft.commit();
                    } else {
                        Toast.makeText(view.getContext(),
                                "Por favor digite seu nome para começar!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        return view;

        }

    }