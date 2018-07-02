package br.ufg.com.dedoduro.auth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import br.ufg.com.dedoduro.R;
import br.ufg.com.dedoduro.connection.Connection;
import br.ufg.com.dedoduro.model.HomeActivity;

public class ForgotPassActivity extends AppCompatActivity {

    MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        //habilita toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.forgotPass_toolbar);
        setSupportActionBar(myToolbar);

        //habilita botão de voltar na toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        sutupRedefinePass();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sutupRedefinePass() {
        Button buttonRedefine = (Button) findViewById(R.id.button_redefine);

        buttonRedefine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryRedefine();
            }
        });


    }

    private void tryRedefine() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        final EditText editTextEmail = (EditText) findViewById(R.id.input_forgot_email);

        if (!"".equals(editTextEmail.getText().toString())) {
            showLoading();

            auth.sendPasswordResetEmail(editTextEmail.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            hideLoading();
                            alert("Verifique sua caixa de entrada");
                            Intent intentLogin = new Intent(ForgotPassActivity.this, LoginActivity.class);
                            startActivity(intentLogin);
                            finish();
                        }
                    });

        } else
            editTextEmail.setError("Preencha o campo de email");
    }

    private void alert(String msg) {
        Toast.makeText(ForgotPassActivity.this, msg, Toast.LENGTH_SHORT)
                .show();
    }

    private void showLoading() {
        dialog = new MaterialDialog.Builder(this)
                .content(R.string.textoAguarde)
                .progress(true, 0)
                .cancelable(false)
                .show();
    }

    private void hideLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.hide();
            dialog = null;
        }
    }

}
