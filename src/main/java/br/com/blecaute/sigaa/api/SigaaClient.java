package br.com.blecaute.sigaa.api;

import br.com.blecaute.sigaa.api.model.Bulletin;
import br.com.blecaute.sigaa.api.model.discipline.Disciplines;
import br.com.blecaute.sigaa.api.model.User;
import br.com.blecaute.sigaa.api.parser.ParserMap;
import br.com.blecaute.sigaa.api.response.BulletinResponse;
import br.com.blecaute.sigaa.api.response.DisciplinesResponse;
import br.com.blecaute.sigaa.api.response.ResponseType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import okhttp3.OkHttpClient;

import java.util.concurrent.CompletableFuture;

@Data
public class SigaaClient {

    private final OkHttpClient httpClient;
    private final String cookie;

    @Setter(AccessLevel.PROTECTED)
    private User user;

    public CompletableFuture<Bulletin> getBulletin() {
        return CompletableFuture.supplyAsync(() -> {
            BulletinResponse response = ResponseType.BULLETIN.getResponse();
            return ParserMap.parse(Bulletin.class, response.getBulletin(httpClient, cookie));
        });
    }

    public CompletableFuture<Disciplines> getDisciplines() {
        return CompletableFuture.supplyAsync(() -> {
            DisciplinesResponse response = ResponseType.DISCIPLINES.getResponse();
            return ParserMap.parse(Disciplines.class, response.getDisciplines(httpClient, cookie));
        });
    }

    public CompletableFuture<User> refreshUser() {
        return null;
    }

}