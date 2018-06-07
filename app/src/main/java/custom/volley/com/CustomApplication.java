package custom.volley.com;

import android.app.Application;

import com.ezvolley.EzVolley;

/**
 * Created by Riad on 28-07-17.
 */

public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EzVolley.init(this, "DEBUG");
    }
}
