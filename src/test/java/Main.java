import br.com.blecaute.sigaa.api.SigaaClient;
import br.com.blecaute.sigaa.api.response.BulletinResponse;
import br.com.blecaute.sigaa.api.response.impl.BulletinResponseImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class Main {

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder()
            .readTimeout(25, TimeUnit.SECONDS)
            .connectTimeout(25, TimeUnit.SECONDS)
            .build();

    public static void main(String[] args) {
        final var client = new SigaaClient(HTTP_CLIENT, "");
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        client.getBulletin().whenComplete((a, b) -> {
            if (b != null) {
                b.printStackTrace();
                return;
            }

            System.out.println(a);

            System.out.println(gson.toJson(a));
        });
    }

}
