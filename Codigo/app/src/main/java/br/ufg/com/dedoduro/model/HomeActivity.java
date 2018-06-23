package br.ufg.com.dedoduro.model;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import br.ufg.com.dedoduro.R;
import br.ufg.com.dedoduro.auth.LoginActivity;
import br.ufg.com.dedoduro.connection.Connection;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Obras Paralizadas");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(myToolbar);

        registerNew();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.home_menu, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_out:
                Connection.logOut();
                Intent openLogin = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(openLogin);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void registerNew() {
        FloatingActionButton floatingActionButton =
                (FloatingActionButton) findViewById(R.id.fab_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegisterNew = new Intent(getApplicationContext(), NewRegisterActivity.class);
                startActivity(intentRegisterNew);
            }
        });
    }

}
