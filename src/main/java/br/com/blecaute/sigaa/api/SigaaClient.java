package br.com.blecaute.sigaa.api;

import br.com.blecaute.sigaa.api.model.Bulletin;
import br.com.blecaute.sigaa.api.model.Disciplines;
import br.com.blecaute.sigaa.api.model.User;
import br.com.blecaute.sigaa.api.parser.ParserMap;
import br.com.blecaute.sigaa.api.response.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

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
        return getHistoric().thenApply(bytes -> {
            try {
                return copyFile(target, bytes, options);
            } catch (IOException exception) {
                throw new CompletionException(exception);
            }
        });
    }

    public CompletableFuture<byte[]> getEnrollmentStatement() {
        return CompletableFuture.supplyAsync(() -> {
            EnrollmentStatementResponse response = ResponseType.ENROLLMENT_STATEMENT.getResponse();
            return response.getEnrollmentStatement(httpClient, cookie);
        });
    }

    public CompletableFuture<Path> getEnrollmentStatement(@NonNull Path target, CopyOption... options) {
        return getEnrollmentStatement().thenApply(bytes -> {
            try {
                return copyFile(target, bytes, options);
            } catch (IOException exception) {
                throw new CompletionException(exception);
            }
        });
    }

    private Path copyFile(@NotNull Path target, byte[] bytes, CopyOption... options) throws IOException {
        try (ByteArrayInputStream byteArray = new ByteArrayInputStream(bytes);
             BufferedInputStream buffered = new BufferedInputStream(byteArray)) {

            Files.copy(buffered, target, options);
        }

        return target;
    }
}