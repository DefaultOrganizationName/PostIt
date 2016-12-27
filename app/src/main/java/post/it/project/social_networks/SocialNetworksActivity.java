package post.it.project.social_networks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
        Post post = aboveIntent.getParcelableExtra(Constants.CURRENT_POST_KEY);

        //TODO add more networks

        if (!VKSdk.wakeUpSession(this)) {
            Log.d(TAG, "need to go to the settings");
            super.setText(R.string.status_not_logged_in);
        }
        else {
            Log.d(TAG, "I am logged in");
            Intent intent = new Intent(this, PostToNetworksService.class);
            try {
                intent.putExtra(Constants.PHOTO_KEY, post.image_bitmap);
                Constants.HAVE_PHOTO = true;
            } catch (NullPointerException e) {
                Log.d(TAG, "Don't have image");
                Constants.HAVE_PHOTO = false;
            }
            try {
                intent.putExtra(Constants.MESSAGE_KEY, post.post_text);
                Constants.HAVE_TEXT = true;
            } catch (NullPointerException e) {
                Log.d(TAG, "Don't have text");
                Constants.HAVE_TEXT = false;
            }
            startService(intent);
        }
    }


    private final String TAG = "NetworksActivity";
}
