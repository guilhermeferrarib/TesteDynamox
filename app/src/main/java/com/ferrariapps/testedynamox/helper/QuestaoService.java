package com.ferrariapps.testedynamox.helper;
import com.ferrariapps.testedynamox.model.Questao;
import com.ferrariapps.testedynamox.model.Resposta;
import com.ferrariapps.testedynamox.model.Result;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface QuestaoService {

    @GET("/")
    Call<Questao> buscarQuestoes();

    @POST("/answer")
    Call<Resposta> enviarResposta(@Query(value = "questionId") Long id, @Body Resposta resposta);
}
