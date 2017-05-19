package socialDist.util;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.compute.Compute;
import com.google.api.services.compute.model.Instance;
import com.google.api.services.compute.model.InstanceList;
import com.google.api.services.compute.model.NetworkInterface;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by izoomko on 5/17/17.
 */
public class GoogleAPI {
    private static GoogleAPI api;

    @Nonnull
    public static GoogleAPI getInstance() {
        if (api != null) {
            return api;
        }
        return new GoogleAPI();
    }

    public List<String> getIP() throws IOException, GeneralSecurityException {
        // Project ID for this request.
        String project = "coursework-166420";

        // The name of the zone for this request.
        String zone = "europe-west1-d";

        Compute computeService = createComputeService();
        Compute.Instances.List request = computeService.instances().list(project, zone);

        List<String> list = new ArrayList<>(0);
        InstanceList response;
        do {
            response = request.execute();
            if (response.getItems() == null) {
                continue;
            }
            for (Instance instance : response.getItems()) {
                List<NetworkInterface> interfaces = instance.getNetworkInterfaces();
                if (interfaces.size() > 0) {
                    for (NetworkInterface i : interfaces) {
                        if (i.getAccessConfigs().size() > 0) {
                            list.add(i.getAccessConfigs().get(0).getNatIP());
                        }
                    }
                }
            }
            request.setPageToken(response.getNextPageToken());
        } while (response.getNextPageToken() != null);

        return list;
    }

    public Compute createComputeService() throws IOException, GeneralSecurityException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        GoogleCredential credential = GoogleCredential.getApplicationDefault();
        if (credential.createScopedRequired()) {
            credential =
                    credential.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
        }

        return new Compute.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName("Google-ComputeSample/0.1")
                .build();
    }
}