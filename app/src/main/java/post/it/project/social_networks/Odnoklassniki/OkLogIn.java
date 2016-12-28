package post.it.project.social_networks.Odnoklassniki;

import android.app.Activity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import post.it.project.postit.R;
import post.it.project.social_networks.AbstractSocialNetworks;
import ru.ok.android.sdk.Odnoklassniki;
import ru.ok.android.sdk.OkListener;
import ru.ok.android.sdk.util.OkAuthType;
import ru.ok.android.sdk.util.OkScope;


public class OkLogIn extends AbstractSocialNetworks {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);
        super.setComponents();
        super.setText(R.string.status_performing);
        Odnoklassniki ok = Odnoklassniki.createInstance(this, OkConstants.APP_ID, OkConstants.APP_KEY);
        final boolean needToLogin = false;
        ok.checkValidTokens(new OkListener() {
            @Override
            public void onSuccess(JSONObject json) {
                setText(R.string.status_logged_in);
                try {
                    OkConstants.access_token = json.getString("access_token");
                } catch (JSONException e) {
                    setText(R.string.status_wrong);
                }
            }
            @Override
            public void onError(String error) {
                setText(R.string.status_wrong);
            }
        });
        ok.requestAuthorization(this, OkConstants.REDIRECT_URI, OkAuthType.ANY, OkScope.VALUABLE_ACCESS);
        super.setText(R.string.status_logged_in);
    }
}
