package br.ufg.com.dedoduro.auth;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import br.ufg.com.dedoduro.R;
import br.ufg.com.dedoduro.web.WebError;
import br.ufg.com.dedoduro.web.WebTaskLogin;

public class LoginActivity extends AppCompatActivity {

    MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupLogin();
        callRegister();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void setupLogin() {
        Button buttonLogin =
                (Button) findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryLogin();
            }
        });
    }

    private void tryLogin() {
        EditText editTextEmail = (EditText) findViewById(R.id.input_email);
        EditText editTextPassword = (EditText) findViewById(R.id.input_password);

        if (!"".equals(editTextEmail.getText().toString())) {
            if (!"".equals(editTextPassword.getText().toString())) {
                showLoading();
                sendCredentials(editTextEmail.getText().toString(),
                        editTextPassword.getText().toString());
            } else {
                editTextPassword.setError("Preencha o campo senha");
            }
        } else {
            editTextEmail.setError("Preencha o campo email");
        }
    }

    private void sendCredentials(String email, String pass) {
        WebTaskLogin taskLogin = new WebTaskLogin(this,
                email, pass);
        taskLogin.execute();
    }

    private void showLoading() {
        dialog = new MaterialDialog.Builder(this)
                .content(R.string.label_wait)
                .progress(true, 0)
                .cancelable(false)
                .show();
    }

    private void callRegister() {
        Button buttonRegister =
                (Button) findViewById(R.id.button_register_new);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
            }
        });
    }

    @Subscribe
    public void onEvent(String response) {
        hideLoading();
        Intent openUrlIntent = new Intent(Intent.ACTION_VIEW);
        openUrlIntent.setData(
                Uri.parse("http://www.freescreencleaner.com/"));
        startActivity(openUrlIntent);
    }

    @Subscribe
    public void onEvent(WebError error) {
        hideLoading();
        Snackbar.make(findViewById(R.id.container),
                error.getMessage(),
                Snackbar.LENGTH_LONG).show();
    }

    private void hideLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.hide();
            dialog = null;
        }
    }
}