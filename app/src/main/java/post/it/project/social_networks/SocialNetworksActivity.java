package post.it.project.social_networks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.util.Log;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.api.model.VKWallPostResult;
import com.vk.sdk.api.photo.VKImageParameters;
import com.vk.sdk.api.photo.VKUploadImage;

import post.it.project.postit.ParcelablePost;
import post.it.project.postit.Post;
import post.it.project.postit.R;
import post.it.project.social_networks.VK.VkPost;

/**
 * Created by Kirill Antonov on 18.12.2016.
 */

public class SocialNetworksActivity extends AbstractSocialNetworks {


    public static String BROADCAST_ACTION = "MY_BROADCAST_FOR_POSTING";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);
        super.setComponents();
        Intent aboveIntent = getIntent();
        final Post post = aboveIntent.getParcelableExtra(Constants.CURRENT_POST_KEY);
        Intent intent = new Intent(this, PostToNetworksService.class);
        //intent.putExtra(Constants.CURRENT_POST_KEY, post);
        Log.d(TAG, post.post_text);
        VkPost request = new VkPost(post.image_bitmap, post.post_text);
        Thread t = new Thread(request);
        t.start();
        //startService(intent);
    }

    private final String TAG = "NetworksActivity";
}
