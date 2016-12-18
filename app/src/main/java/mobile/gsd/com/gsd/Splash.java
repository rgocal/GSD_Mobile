package mobile.gsd.com.gsd;

/**
 * Created by ry on 12/15/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainInterface.class);
        startActivity(intent);
        finish();
    }
}
