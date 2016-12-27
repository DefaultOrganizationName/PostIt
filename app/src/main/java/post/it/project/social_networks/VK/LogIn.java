package post.it.project.social_networks.VK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.vk.sdk.VKSdk;
import com.vk.sdk.api.model.VKScopes;

import post.it.project.postit.MainActivity;
import post.it.project.postit.R;
import post.it.project.social_networks.AbstractSocialNetworks;

/**
 * Created by Kirill Antonov on 18.12.2016.
 */

public class LogIn extends AbstractSocialNetworks {

    public static final String[] scopes = {VKScopes.WALL, VKScopes.PHOTOS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);
        super.setComponents();
        super.setText(R.string.status_performing);
        if (!VKSdk.wakeUpSession(this)) {
            super.setText(R.string.status_not_logged_in);
            VKSdk.login(this, scopes);
        }
        else {
            Log.d("LogIn", "I am logged in");
            super.setText(R.string.status_logged_in);
        }
    }

    @Override
    public void onClick(View v) {
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
