package post.it.project.social_networks;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

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
        Bitmap photo = null;
        if (Constants.HAVE_PHOTO) photo = intent.getParcelableExtra(Constants.PHOTO_KEY);
        String message = null;
        if (Constants.HAVE_TEXT) message = intent.getStringExtra(Constants.MESSAGE_KEY);
        VkPost request = new VkPost(photo, message);
        new Thread(request).start();
        return super.onStartCommand(intent, flags, startId);
    }

    private final String TAG = "PostService";
}
