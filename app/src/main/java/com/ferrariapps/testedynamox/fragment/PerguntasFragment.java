package com.ferrariapps.testedynamox.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ferrariapps.testedynamox.R;
import com.ferrariapps.testedynamox.helper.QuestaoService;
import com.ferrariapps.testedynamox.config.RetrofitConfig;
import com.ferrariapps.testedynamox.model.Questao;
import com.ferrariapps.testedynamox.model.Resposta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PerguntasFragment extends Fragment {

    private LinearLayout linearLayout;
    private Button enviar;
    private Retrofit retrofit;
    private List<String> listaQuestoes = new ArrayList<>();
    private Questao questao;
    private QuestaoService questaoService;
    private RadioGroup rg;
    private TextView pergunta;
    private String resposta;
    private String result;
    private int contador = 0;
    private int nota = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perguntas, container, false);
        retrofit = RetrofitConfig.getRetrofit();
        buscarQuestoes(view);
        inicializarComponentes(view);
        return view;
    }

    private void inicializarComponentes(View view) {
        linearLayout = view.findViewById(R.id.linearLayout);
        enviar = view.findViewById(R.id.buttonEnviar);
    }

    private void buscarQuestoes(View view) {
        questaoService = retrofit.create(QuestaoService.class);
        Call<Questao> call = questaoService.buscarQuestoes();
        call.enqueue(new Callback<Questao>() {
            @Override
            public void onResponse(Call<Questao> call, Response<Questao> response) {
                if (response.isSuccessful()) {
                    questao = response.body();
                    assert questao != null;
                    listaQuestoes = questao.getOptions();
                    createRadioButton(listaQuestoes, view);
                }
            }

            @Override
            public void onFailure(Call<Questao> call, Throwable t) {

            }
        });
    }

    private void createRadioButton(List<String> lista, View view) {
        final RadioButton[] rb = new RadioButton[lista.size()];
        pergunta = new TextView(view.getContext());
        pergunta.setPadding(16, 16, 16, 16);
        rg = new RadioGroup(view.getContext()); //create the RadioGroup
        rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
        rg.setPadding(16, 16, 16, 16);
        for (int i = 0; i < lista.size(); i++) {
            rb[i] = new RadioButton(view.getContext());
            rg.addView(rb[i]); //the RadioButtons are added to the radioGroup instead of the layout
            rb[i].setText(lista.get(i));
        }
        pergunta.setText(questao.getStatement());
        linearLayout.addView(pergunta);
        linearLayout.addView(rg);//you add the whole RadioGroup to the layout
        //linearLayout.addView(enviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                linearLayout.removeView(rg);
                for (int j = 0; j < lista.size(); j++) {
                    if (rb[j].isChecked()) {
                        resposta = rb[j].getText().toString();
                    }
                }
                for (int i = 0; i < lista.size(); i++) {
                    rg.removeView(rb[i]);//now the RadioButtons are in the RadioGroup
                }
                linearLayout.removeView(pergunta);
                linearLayout.removeView(rg);
                contarResultado(resposta, view);
                contador++;
            }
        });
    }

    private void contarResultado(String answer, View view) {

        Resposta resposta = new Resposta();
        resposta.setAnswer(answer);
        questaoService = retrofit.create(QuestaoService.class);
        Call<Resposta> call = questaoService.enviarResposta(questao.getId(), resposta);
        call.enqueue(new Callback<Resposta>() {
            @Override
            public void onResponse(Call<Resposta> call, Response<Resposta> response) {
                if (response.isSuccessful()) {
                    Resposta resposta1 = response.body();
                    assert resposta1 != null;
                    result = resposta1.getValue();
                    if (result.equals("true")) {
                        if (contador == 10) {
                            mostrarResultado("Você acertou, terminamos o teste", view);
                        } else {
                            mostrarResultado("Você acertou, vamos para a próxima pergunta!", view);
                        }
                        nota = nota + 1;
                    } else {
                        if (contador == 10) {
                            mostrarResultado("Você errou, terminamos o teste", view);
                        } else {
                            mostrarResultado("Você errou, vamos para a próxima pergunta!", view);
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<Resposta> call, Throwable t) {

            }
        });
    }

    private void mostrarResultado(String resultado, View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());

        alertDialog.setTitle("Resultado");
        alertDialog.setMessage(resultado);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", (dialog, which) -> {
            if (contador < 10) {
                buscarQuestoes(view);
            }
            if (contador == 10) {
                Fragment fragment = new ResultadoFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("nota", nota);
                fragment.setArguments(bundle);
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, fragment);
                ft.commit();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }
}
