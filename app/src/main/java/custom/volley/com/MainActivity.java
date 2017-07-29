package custom.volley.com;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.custom.volley.requester.RequestGenerator;
import com.custom.volley.requester.error.NetworkConnectionResponseError;
import com.custom.volley.requester.fields.SimpleRequestFields;
import com.custom.volley.requester.headers.RequestHeadersGenerator;
import com.custom.volley.requester.response.NetworkResponseHandler;
import com.custom.volley.requester.types.NetworkMethodTypes;
import com.google.gson.JsonObject;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "YOUR_URL";

        /**
         * Simplest way to make a request.
         */
        SimpleRequestFields<Model> simpleRequestFields = new SimpleRequestFields<>(NetworkMethodTypes.GET, url,
                null, null, Model.class, "DEBUG");

        RequestGenerator.getInstance().getRequesterInitiator().createRequest(simpleRequestFields,
                new NetworkResponseHandler<Model>() {
            @Override
            public void onSuccess(Model result) {
                Toast.makeText(MainActivity.this, result.getResult(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRequestAddedToQueue(int requestId) {
                super.onRequestAddedToQueue(requestId);

            }

            @Override
            public void onConnectionFailed(NetworkConnectionResponseError connectionError) {
                super.onConnectionFailed(connectionError);
            }
        });



        /**
         * In case you'd like to add headers or send a body with the request
         */

        String jsonObjectToSend = "{email:test@test.com}";
        Map<String, String> headers = new RequestHeadersGenerator.Builder()
                .addAcceptJson()
                .addHeaders(null)
                .build().getHeaders();

        SimpleRequestFields<JsonObject> heavyRequest = new SimpleRequestFields<>(
                NetworkMethodTypes.GET, url, jsonObjectToSend, headers, JsonObject.class, "DEBUG");

        RequestGenerator.getInstance().getRequesterInitiator().createRequest(heavyRequest,
                new NetworkResponseHandler<JsonObject>() {
            @Override
            public void onSuccess(JsonObject result) {
                //JsonObject returned from server
            }
        });
    }

    public class Model {
        String origin;

        public String getResult() {
            return origin;
        }
    }
}
