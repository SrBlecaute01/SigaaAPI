package br.com.blecaute.sigaa.api.response;

import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;

public interface VirtualClassResponse extends ClientResponse{

    Document getVirtualClass(@NotNull OkHttpClient client, @NotNull String cookie, @NotNull String id);

}