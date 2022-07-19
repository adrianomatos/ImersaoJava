import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        // Conexão http trazendo top 250 filmes
        String url = "https://alura-filmes.herokuapp.com/conteudos";
        var endereco = URI.create(url); // URI endereco
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        // System.out.println(body);

        // Extrair dados
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        // System.out.println(listaDeFilmes.size());
        // System.out.println(listaDeFilmes.get(0));

        String red = "\u001b[37m \u001b[41m";
        String gren = "\u001b[37m \u001b[42m";
        String blue = "\u001b[37m \u001b[44m";

        // Filtrar informações baixadas (nome, img e arredondaNota) - FOREACH
        for (Map<String, String> filme : listaDeFilmes) {
            System.out.println(red + "Filme:\u001b[m " + filme.get("title"));
            System.out.println(gren + "Capa do filme:\u001b[m " + filme.get("image"));

            float converteStringEmFloat = Float.valueOf(filme.get("imDbRating")).floatValue();
            int arredondaNota = Math.round(converteStringEmFloat);

            if (arredondaNota >= 9) {
                System.out.println(blue + "Avaliação:\u001b[m ⭐⭐⭐⭐⭐\n");
            } else if (arredondaNota >= 8) {
                System.out.println("Avaliação: ⭐⭐⭐⭐\n");
            } else if (arredondaNota >= 6) {
                System.out.println("Avaliação: ⭐⭐⭐\n");
            } else if (arredondaNota >= 3) {
                System.out.println("Avaliação: ⭐⭐\n");
            } else {
                System.out.println("Avaliação: ⭐\n");
            }
        }
    }
}
