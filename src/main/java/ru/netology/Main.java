package ru.netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {
    public static final String REMOTE_SERVICE_URI = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    private static final Type factAboutCatListType = new TypeToken<List<FactAboutCat>>() {
    }.getType();

    public static void main(String[] args) {
        try (final CloseableHttpClient httpClient = createHttpClient()) {
            final HttpGet request = new HttpGet(REMOTE_SERVICE_URI);
            try (final CloseableHttpResponse response = httpClient.execute(request)) {
                final List<FactAboutCat> factAboutCatList = parseResponse(response);
                factAboutCatList.stream()
                        .filter(FactAboutCat::hasUpvotes)
                        .forEach(System.out::println);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static CloseableHttpClient createHttpClient() {
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5_000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30_000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
    }

    private static List<FactAboutCat> parseResponse(CloseableHttpResponse response) throws IOException {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        final String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
        return gson.fromJson(body, factAboutCatListType);
    }
}
