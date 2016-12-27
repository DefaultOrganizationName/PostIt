package post.it.project.social_networks;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import post.it.project.postit.MainActivity;
import post.it.project.postit.R;

/**
 * Created by Kirill Antonov on 27.12.2016.
 */

public abstract class AbstractSocialNetworks extends Activity implements View.OnClickListener {

    protected TextView stateText;
    protected LinearLayout textContainer;

    public void setComponents() {
        Button b = (Button) findViewById(R.id.btn);
        b.setOnClickListener(this);
        stateText = (TextView) findViewById(R.id.status_text);
        textContainer = (LinearLayout) findViewById(R.id.linerContainer);
    }

    public void setText(int k) {
        stateText.setText(k);
    }

    public void setText(String s) {
        stateText.setText(s);
    }

    public TextView makeTextView(int name) {
        TextView tv = new TextView(getApplicationContext());
        tv.setText(name);
        //String s = tv.getText().toString();
        return tv;
    }

    @Override
    public void onClick(View v) {
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
