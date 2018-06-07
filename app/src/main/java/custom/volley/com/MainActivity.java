package custom.volley.com;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.ezvolley.RequestGenerator;
import com.ezvolley.fields.EzGetFields;
import com.ezvolley.fields.EzPostFields;
import com.ezvolley.headers.RequestHeadersGenerator;
import com.ezvolley.response.NetworkResponseHandler;
import com.google.gson.Gson;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //GET REQUEST EXAMPLE
        String url = "https://jsonplaceholder.typicode.com/posts/1";

        EzGetFields<Model> ezGetFields = new EzGetFields<>(url, Model.class);
        RequestGenerator.getInstance().getRequesterInitiator().createRequest(ezGetFields, new NetworkResponseHandler<Model>() {
            @Override
            public void onSuccess(Model result) {
//                Log.d("MainActivity", "onSuccess: " + result.body);
                Toast.makeText(MainActivity.this, result.body, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponseHeadersAvailable(Map<String, String> headers) {
                super.onResponseHeadersAvailable(headers);
                //optional to override, these headers are returned by the response
            }
        });


        Gson gson = new Gson();

        //POST REQUEST EXAMPLE
        Model model = new Model();
        model.body = "This is body";
        model.userId = 1996;
        model.title = "EzVolley";

        Map<String, String> headers = new RequestHeadersGenerator.Builder()
                .addSendJson()
                .build()
                .getHeaders();

        String postUrl = "https://jsonplaceholder.typicode.com/posts";

        EzPostFields <Model> ezPostFields = new EzPostFields<>(postUrl, gson.toJson(model), headers, Model.class);
        RequestGenerator.getInstance().getRequesterInitiator().createRequest(ezPostFields, new NetworkResponseHandler<Model>() {
            @Override
            public void onSuccess(Model result) {
                //posted the model successfully.
                Toast.makeText(MainActivity.this, result.body, Toast.LENGTH_LONG).show();
//                Log.d("MainActivity", "onSuccess: " + result.body);
            }
        });
    }

    public class Model {
        int userId;
        int id;
        String title;
        String body;
    }
}