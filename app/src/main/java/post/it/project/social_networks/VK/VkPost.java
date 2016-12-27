package post.it.project.social_networks.VK;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.nfc.Tag;
import android.util.Log;

import com.vk.sdk.VKAccessToken;
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
import post.it.project.social_networks.Constants;
import post.it.project.social_networks.PostToNetworksService;
import post.it.project.social_networks.SocialNetworksActivity;

public class VkPost extends PostToNetworksService implements Runnable{

    private Bitmap photo = null;
    private String message = null;
    private Context context;


    public VkPost(Bitmap photo, String message) {
        this.photo = photo;
        this.message = message;
    }

    private void loadPhotoToMyWall(final Bitmap photo, final String message) {
        Log.d(TAG, "loading photo to my wall");
        VKRequest request = VKApi.uploadWallPhotoRequest(new VKUploadImage(photo, VKImageParameters.jpgImage(0.9f)), getMyId(), 0);
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                // recycle bitmap
                Log.d(TAG, "loading photo is finished!");
                VKApiPhoto photoModel = ((VKPhotoArray) response.parsedModel).get(0);
                makePost(new VKAttachments(photoModel), message, getMyId());
                Intent intent = new Intent(SocialNetworksActivity.BROADCAST_ACTION);
                context.sendBroadcast(intent);
            }
            @Override
            public void onError(VKError error) {
                Log.d(TAG, "can not load photo :-(");
                Log.d(TAG, error.toString());
            }

        });
    }

    private int getMyId() {
        final VKAccessToken vkAccessToken = VKAccessToken.currentToken();
        return vkAccessToken != null ? Integer.parseInt(vkAccessToken.userId) : 0;
    }

    private void makePost(VKAttachments att, String msg, int id) {
        Log.d(TAG, "making post and final request");
        VKParameters parameters = new VKParameters();
        parameters.put(VKApiConst.OWNER_ID, String.valueOf(id));
        if (att != null) parameters.put(VKApiConst.ATTACHMENTS, att);
        parameters.put(VKApiConst.MESSAGE, msg);
        VKRequest post = VKApi.wall().post(parameters);
        post.setModelClass(VKWallPostResult.class);
        post.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                Log.d(TAG, "post is posted");
                Intent intent = new Intent(SocialNetworksActivity.BROADCAST_ACTION);
                context.sendBroadcast(intent);
            }
            @Override
            public void onError(VKError error) {
                Log.d(TAG, "error!");
                Intent intent = new Intent(SocialNetworksActivity.BROADCAST_ACTION);
                context.sendBroadcast(intent);
            }
        });
    }

    private final String TAG = "VkPost";

    @Override
    public void run() {
        Log.d(TAG, "ready to post: ".concat(message));
        if (photo != null) loadPhotoToMyWall(photo, message);
        else makePost(null, message, getMyId());
    }


}
