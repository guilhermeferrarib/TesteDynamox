package com.ferrariapps.testedynamox.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ferrariapps.testedynamox.R;
import com.ferrariapps.testedynamox.helper.UsuarioDAO;
import com.ferrariapps.testedynamox.model.Usuario;

import java.util.Objects;

public class ResultadoFragment extends Fragment {

    private TextView resultadoFinal;
    private Button buttonReiniciar;
    private int notaFinal;
    private SharedPreferences prefs;
    private String nome;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_resultado, container, false);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        notaFinal = bundle.getInt("nota", 0);
        inicializarComponentes(view);
        return view;
    }

    private void inicializarComponentes(View view) {
        resultadoFinal = view.findViewById(R.id.textNota);
        buttonReiniciar = view.findViewById(R.id.buttonReiniciar);
        resultadoFinal.setText(String.valueOf(notaFinal));
        prefs = requireActivity().getSharedPreferences(
                "User", Context.MODE_PRIVATE);
        nome = prefs.getString("user", null);
        UsuarioDAO usuarioDAO = new UsuarioDAO(view.getContext());
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setNota(notaFinal);
        usuarioDAO.salvar(usuario);
        if (notaFinal>=7){
            resultadoFinal.setTextColor(Color.parseColor("#008F39"));
        }else{
            resultadoFinal.setTextColor(Color.parseColor("#FF0000"));
        }
        buttonReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, new PrincipalFragment());
                ft.commit();
            }
        });
    }
}