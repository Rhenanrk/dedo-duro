package br.ufg.com.dedoduro.model;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;




import br.ufg.com.dedoduro.R;
import br.ufg.com.dedoduro.web.Connection;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        clickLogout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.home_menu, menu);
       return true;
    }

    private void clickLogout() {
        ImageView buttonLogout = (ImageView) findViewById(R.id.button_logout);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connection.logOut();
                finish();

            }
        });
    }
}
