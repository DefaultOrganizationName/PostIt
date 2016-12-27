package post.it.project.social_networks;

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

import post.it.project.postit.R;
import post.it.project.social_networks.VK.VkPost;

/**
 * Created by Kirill Antonov on 18.12.2016.
 */

public class SocialNetworksActivity extends AbstractSocialNetworks {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);
        super.setComponents();
        if (!VKSdk.wakeUpSession(this)) {
            Log.d(TAG, "need to go to the settings");
            super.setText(R.string.status_not_logged_in);
        }
        else {
            Log.d(TAG, "I am logged in");
            VkPost post = new VkPost(null, Constants.editText);
            new Thread(post).start();
        }
    }


    private final String TAG = "Networks_Activity";
}
