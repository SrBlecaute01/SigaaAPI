package br.com.blecaute.sigaa.api;

import br.com.blecaute.sigaa.api.model.Bulletin;
import br.com.blecaute.sigaa.api.model.Disciplines;
import br.com.blecaute.sigaa.api.model.User;
import br.com.blecaute.sigaa.api.parser.ParserMap;
import br.com.blecaute.sigaa.api.response.BulletinResponse;
import br.com.blecaute.sigaa.api.response.DisciplinesResponse;
import br.com.blecaute.sigaa.api.response.HistoricResponse;
import br.com.blecaute.sigaa.api.response.ResponseType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import okhttp3.OkHttpClient;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

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

    public CompletableFuture<byte[]> getHistoric() {
        return CompletableFuture.supplyAsync(() -> {
            HistoricResponse response = ResponseType.HISTORIC.getResponse();
            return response.getHistoric(httpClient, cookie);
        });
    }

    public CompletableFuture<Path> getHistoric(@NonNull Path target, CopyOption... options) {
        return getHistoric().handle((bytes, throwable) -> {
            if (throwable != null) {
                throw new CompletionException(throwable);
            }

            try {
                Files.copy(new BufferedInputStream(new ByteArrayInputStream(bytes)), target, options);
            } catch (IOException exception) {
                throw new CompletionException(exception);
            }

            return target;
        });
    }

    public CompletableFuture<User> refreshUser() {
        return null;
    }

}