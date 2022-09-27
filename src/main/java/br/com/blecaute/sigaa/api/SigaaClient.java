package br.com.blecaute.sigaa.api;

import br.com.blecaute.sigaa.api.model.Bulletin;
import br.com.blecaute.sigaa.api.model.Disciplines;
import br.com.blecaute.sigaa.api.model.classroom.Classroom;
import br.com.blecaute.sigaa.api.mapper.Mappers;
import br.com.blecaute.sigaa.api.response.*;
import lombok.Data;
import lombok.NonNull;
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
import java.util.concurrent.atomic.AtomicReference;

@Data
public class SigaaClient {

    private final OkHttpClient httpClient;
    private final String cookie;

    private final AtomicReference<String> viewState = new AtomicReference<>("j_id2");
    private final AtomicReference<ResponseType> lastResponse = new AtomicReference<>(ResponseType.STUDENT);

    public String getViewState() {
        return viewState.get();
    }

    public void setViewState(@NonNull String viewState) {
        this.viewState.set(viewState);
    }

    public ResponseType getLastResponse() {
        return lastResponse.get();
    }

    public void setLastResponse(@NonNull ResponseType lastResponse) {
        this.lastResponse.set(lastResponse);
    }

    public CompletableFuture<Bulletin> getBulletin() {
        return CompletableFuture.supplyAsync(() -> {
            final var response = ResponseType.BULLETIN.getResponse(BulletinResponse.class);
            return Mappers.map(Bulletin.class, response.getBulletin(this));
        });
    }

    public CompletableFuture<Disciplines> getDisciplines() {
        return CompletableFuture.supplyAsync(() -> {
            final var response = ResponseType.DISCIPLINES.getResponse(DisciplinesResponse.class);
            return Mappers.map(Disciplines.class, response.getDisciplines(this));
        });
    }

    public CompletableFuture<byte[]> getHistoric() {
        return CompletableFuture.supplyAsync(() -> {
            final var response = ResponseType.HISTORIC.getResponse(HistoricResponse.class);
            return response.getHistoric(this);
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
            final var response = ResponseType.ENROLLMENT_STATEMENT.getResponse(EnrollmentStatementResponse.class);
            return response.getEnrollmentStatement(this);
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

    public CompletableFuture<Classroom> getClassroom(@NonNull Disciplines.Discipline discipline) {
        return getClassroom(discipline.getId());
    }

    public CompletableFuture<Classroom> getClassroom(@NonNull String id) {
        return CompletableFuture.supplyAsync(() -> {
            final var response = ResponseType.CLASSROOM.getResponse(ClassroomResponse.class);
            return Mappers.map(Classroom.class, response.getClassroom(this, id));
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