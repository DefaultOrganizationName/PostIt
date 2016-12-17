package post.it.project.VK;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.model.VKScopes;
import com.vk.sdk.api.model.VKUsersArray;

/**
 * Created by Kirill Antonov on 17.12.2016.
 */

public class VkActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<LoadResult<VKUsersArray, VKError>> {

    static final String KEY_TOKEN = "vk_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initContentView();

        VKAccessToken token = VKAccessToken.tokenFromSharedPreferences(this, KEY_TOKEN);
        if (token == null && savedInstanceState == null) {
            VKSdk.login(this, VKScopes.WALL);
        }
    }

    public void startCurrentUserRequest() {
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<LoadResult<VKUsersArray, VKError>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<LoadResult<VKUsersArray, VKError>> loader, LoadResult<VKUsersArray, VKError> data) {

    }

    @Override
    public void onLoaderReset(Loader<LoadResult<VKUsersArray, VKError>> loader) {

    }
}
