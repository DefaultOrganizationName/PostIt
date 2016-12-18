package post.it.project.VK;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.vk.sdk.VKSdk;

/**
 * Created by Kirill Antonov on 18.12.2016.
 */

public class LogIn extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!VKSdk.wakeUpSession(this)) VKSdk.login(this, VkActivity.scopes);
        else {
            Log.d("LogIn", "I am logged in");
            //makePost(null, "first post with sdk!!!", 0);
        }
    }
}
