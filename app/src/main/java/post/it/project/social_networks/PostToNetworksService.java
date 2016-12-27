package post.it.project.social_networks;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import post.it.project.postit.Post;
import post.it.project.social_networks.VK.VkPost;

/**
 * Created by Kirill Antonov on 27.12.2016.
 */

public class PostToNetworksService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service is started!");
        Post p = intent.getParcelableExtra(Constants.CURRENT_POST_KEY);
        VkPost request = new VkPost(p.image_bitmap, p.post_text);
        new Thread(request).start();
        return super.onStartCommand(intent, flags, startId);
    }

    private final String TAG = "PostService";
}
