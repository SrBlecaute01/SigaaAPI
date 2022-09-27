package br.com.blecaute.sigaa.api.response;

import br.com.blecaute.sigaa.api.SigaaClient;
import lombok.NonNull;
import org.jsoup.nodes.Document;

public interface ClassroomResponse extends ClientResponse{

    Document getClassroom(@NonNull SigaaClient client, @NonNull String id);

}