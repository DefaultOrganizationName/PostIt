package post.it.project.social_networks.VK;

import android.app.Application;
import android.content.Intent;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

import post.it.project.social_networks.SocialNetworksActivity;

/**
 * Created by Kirill Antonov on 18.12.2016.
 */

public class VkApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
    }

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Intent i = new Intent(VkApplication.this, SocialNetworksActivity.class);
                startActivity(i);
            }
        }
    };
}
