package post.it.project.social_networks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import post.it.project.postit.ParcelablePost;
import post.it.project.postit.R;
import post.it.project.social_networks.Odnoklassniki.PostToOkService;
import post.it.project.social_networks.VK.PostToVkService;

/**
 * Created by Kirill Antonov on 18.12.2016.
 */

public class SocialNetworksActivity extends AbstractSocialNetworks {


    public static String BROADCAST_ACTION = "MY_BROADCAST_FOR_POSTING";

    private BroadcastReceiver answerCatcher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);
        super.setComponents();
        Intent aboveIntent = getIntent();
        final ParcelablePost post = aboveIntent.getParcelableExtra(Constants.CURRENT_POST_KEY);



        //making broadcast
        answerCatcher = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                stateText.setVisibility(View.INVISIBLE);
                Response answer = intent.getParcelableExtra(Constants.ANSWER_OF_POST_SERVICE);
                TextView tv = makeTextView(answer.nameOfNetwork);
                tv.append(" ");
                if (answer.resultType == ResultType.OK) {
                    tv.setTextColor(Color.MAGENTA);
                } else {
                    tv.setTextColor(Color.RED);
                }
                tv.append(answer.message);
                textContainer.addView(tv);
            }
        };
        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(answerCatcher, intentFilter);

        //starting service
        if (post.networks[0] == 1) {
            Intent intent = new Intent(this, PostToVkService.class);
            intent.putExtra(Constants.CURRENT_POST_KEY, post);
            startService(intent);
        }
        if (post.networks[1] == 1) {
            Intent intent  = new Intent(this, PostToOkService.class);
            intent.putExtra(Constants.CURRENT_POST_KEY, post);
            startService(intent);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(answerCatcher);
    }

    private final String TAG = "NetworksActivity";
}
