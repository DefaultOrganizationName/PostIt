package post.it.project.VK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKScopes;
import com.vk.sdk.api.model.VKWallPostResult;

import post.it.project.postit.MainActivity;
import post.it.project.postit.R;

/**
 * Created by Kirill Antonov on 18.12.2016.
 */

public class VkActivity extends Activity  {

    public static final String[] scopes = {VKScopes.WALL, VKScopes.PHOTOS};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!VKSdk.wakeUpSession(this)) {
            Log.d(TAG, "need to go to the settings");
        }
        else {
            Log.d(TAG, "I am logged in");
            //makePost(null, "first post with sdk!!!", 0);
            makeUsersRequest();
        }
    }

    @NonNull
    private void makeUsersRequest() {

        makePost(null, Constants.editText);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Log.d(TAG, "onLogIn: ura!");
                //makePost(null, "first post with sdk!!!", 0);
                makeUsersRequest();
            }
            @Override
            public void onError(VKError error) {
                Log.d(TAG, "onError: fuck you!");
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    void makePost(VKAttachments att, String msg) {
        VKParameters parameters = new VKParameters();
        //parameters.put(VKApiConst.OWNER_ID, String.valueOf(ownerId));
        if (att != null) parameters.put(VKApiConst.ATTACHMENTS, att);
        parameters.put(VKApiConst.MESSAGE, msg);
        VKRequest post = VKApi.wall().post(parameters);
        post.setModelClass(VKWallPostResult.class);
        post.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                Log.d(TAG, "post is posted");

            }
            @Override
            public void onError(VKError error) {
                Log.d(TAG, "error!");
            }
        });
    }

    private final String TAG = "VkActivity";
}
