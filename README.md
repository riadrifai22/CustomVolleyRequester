# CustomVolleyRequester
A library, based on Google's Volley library, that makes android HTTP requests easier and requires no boiler plate code 
that volley usually needs.

CustomVolleyRequester is used to make developer's lives easier. Making HTTP calls is a common requirement for most apps and we 
believe that using it does not require the developer to repeat the initialization process each time. With CustomVolleyRequester, all you need to do
is specify the request fields needed for your request, give it the desired model class to be returned, and the library will deliver your model.  

## Downlaod
**Through gradle:**

```
repositories {
    jcenter()
}

dependencies {
    compile 'com.custom.requester:customvolleyrequester:1.0.0'
}
```
## Common terms and classes:
- Request: An API request to your endpoint
- `NetworkMethodTypes.java`: Contains the type of a request. Ex: NetworkMethodTypes.GET
- `NetworkPriorityTypes.java`: The priority of a request, this determines the importance of the request in the queue.
- `SimpleRequestFields.java`: Fields contain the common details of a simple request (ex: url). Each request will have a request field. You can set the priority of the request using the request fields.
- `NetworkConnectionResponseError.java`: Contains the details of a connection error in case volley failed to deliver the request.

## How to use

1. Add the internet permission in your `AndroidManifest.xml`:
```
    <uses-permission android:name="android.permission.INTERNET"/>
```

2. Create your custom Application class and initialize `VolleyInitiator` in its `onCreate()` method:
```
public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VolleyInitiator.init(this);
    }
}
```

3. Finally, add the following wherever you'd like to create a request:
    1. Create the request fields for your request:
    ```
    String url = "YOUR_ENDPOINT";
    SimpleRequestFields<Model> simpleRequestFields = new SimpleRequestFields<>(NetworkMethodTypes.GET, url,
                null, null, Model.class, "DEBUG"); //Model.class here is the expected object to be returned from your server.
    ```
    
    2. Pass the request fields to the request initiator:
    ```
    RequestGenerator.getInstance().getRequesterInitiator().createRequest(simpleRequestFields,
                new NetworkResponseHandler<Model>() {
            @Override
            public void onSuccess(Model result) {
                //Your desired returned result from server as an object
            }

            @Override
            public void onRequestAddedToQueue(int requestId) {
                super.onRequestAddedToQueue(requestId);

            }

            @Override
            public void onConnectionFailed(NetworkConnectionResponseError connectionError) {
                super.onConnectionFailed(connectionError);
                //Connection error
            }
        });
    ```
    `onRequestAddedToQueue(int requestId)`: Optional to override; this method will notify the calling thread of when the request queue with its
    unique request id. You can use the request id to cancel a request.
    `onConnectionFailed(NetworkConnectionResponseError connectionError)`: Optional to override; in case volley failed to complete the request. The parameter will contain the error details.
    
    **And that's it!**

###### Playing with Request fields:

* Change the priority of a request:
```
simpleRequestFields.setPriority(NetworkPriorityTypes.IMMEDIATE);
```

* Add new request headers and send json body with the request:
```
        String jsonObjectToSend = "{email:test@test.com}";
        Map<String, String> optional = new HashMap<>();
        optional.put("Authorization", "value");
        
        Map<String, String> headers = new RequestHeadersGenerator.Builder()
                .addAcceptJson()
                .addHeaders(optional)
                .build().getHeaders();

        SimpleRequestFields<JsonObject> heavyRequest = new SimpleRequestFields<>(
                NetworkMethodTypes.GET, url, jsonObjectToSend, headers, JsonObject.class, "DEBUG");
```

* Changing request timeout, maximum retries and the back-off multiplier:
```
   VolleyInitiator.setBackoffMult(2f);
   VolleyInitiator.setConnectionTimeOut(15000);
   VolleyInitiator.setMaxRetries(2);
```

* Canceling a request:
```
  RequestGenerator.getInstance().getRequesterInitiator().cancelRequest(requestId); //requestId is saved from onRequestAddedToQueue method
```


## Suggestions:

We're currently working on adding features that each app might need. So suggesting your own is more than welcomed! Feel free to open issues and suggest new features you might see
important.

Thank you for using CustomVolleyRequester.

## License
 Copyright 2017 Riad Rifai

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
   
   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.


