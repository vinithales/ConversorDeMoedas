import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiRequision {

    private String endereco;



    Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            .create();

    public void fetchDataFromApi(Moeda moeda){

        try{
            endereco = "https://v6.exchangerate-api.com/v6/420964aa5e1021de99338466/pair/" +moeda.getMoeda()+ "/" +moeda.getMoedaConvertida()+ "/" + moeda.getValor();

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            MoedaApi moedaResult = gson.fromJson(json, MoedaApi.class);
            moeda.setMoedaConvertida(moedaResult.target_code());
            moeda.setMoeda(moedaResult.base_code());
            moeda.setValor(moedaResult.conversion_result());
            moeda.Resultado();



        } catch (Exception e) {
            System.out.println("Algo deu errado!: " + e.getMessage());
        }

    }



}
