package post.it.project.social_networks;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import post.it.project.postit.MainActivity;
import post.it.project.postit.R;

/**
 * Created by Kirill Antonov on 27.12.2016.
 */

public abstract class AbstractSocialNetworks extends Activity implements View.OnClickListener {

    private TextView v;

    public void setComponents() {
        Button b = (Button) findViewById(R.id.btn);
        b.setOnClickListener(this);
        v = (TextView) findViewById(R.id.status_text);
    }

    public void setText(int k) {
        v.setText(k);
    }

    @Override
    public void onClick(View v) {
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
