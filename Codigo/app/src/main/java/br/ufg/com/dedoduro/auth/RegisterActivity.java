package br.ufg.com.dedoduro.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.ufg.com.dedoduro.R;
import br.ufg.com.dedoduro.model.HomeActivity;
import br.ufg.com.dedoduro.web.Connection;

public class RegisterActivity extends AppCompatActivity {

    MaterialDialog dialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle saveInstanveState) {
        super.onCreate(saveInstanveState);
        setContentView(R.layout.activity_register);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(myToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setupRegister();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Connection.getFirebaseAuth();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void setupRegister() {
        Button buttonRegister = (Button) findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryRegister();
            }
        });
    }

    private void tryRegister() {
        EditText editTextEmail = (EditText) findViewById(R.id.input_new_email);
        EditText editTextPassword = (EditText) findViewById(R.id.input_new_password);

        if (!"".equals(editTextEmail.getText().toString())) {
            if (!"".equals(editTextPassword.getText().toString())) {
                showLoading();
                performRegister(editTextEmail.getText().toString(),
                        editTextPassword.getText().toString());
            } else {
                editTextPassword.setError("Preencha o campo senha");
            }
        } else {
            editTextEmail.setError("Preencha o campo email");
        }
    }

    private void performRegister(String email, String pass) {
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            alert("Sucesso ao registrar");
                            Intent intentHome = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(intentHome);
                            finish();
                        } else {
                            alert("Erro ao cadastrar");
                            finish();
                        }

                    }
                });
    }

    private void alert(String msg) {
        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT)
                .show();
    }

    private void showLoading() {
        dialog = new MaterialDialog.Builder(this)
                .content(R.string.label_wait)
                .progress(true, 0)
                .cancelable(false)
                .show();
    }
}
