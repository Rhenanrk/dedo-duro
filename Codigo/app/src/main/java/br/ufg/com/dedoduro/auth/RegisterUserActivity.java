package br.ufg.com.dedoduro.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.ufg.com.dedoduro.R;
import br.ufg.com.dedoduro.connection.Connection;
import br.ufg.com.dedoduro.model.HomeActivity;
import br.ufg.com.dedoduro.model.UserDTO;

public class RegisterUserActivity extends AppCompatActivity {

    MaterialDialog dialog;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle saveInstanveState) {
        super.onCreate(saveInstanveState);
        setContentView(R.layout.activity_register_user);

        //habilita toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(myToolbar);

        //habilita botão de voltar na toolbar
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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
            if (VerifyDataInput.validarEmail(editTextEmail.getText().toString())) {
                if (!"".equals(editTextPassword.getText().toString())) {
                    if (VerifyDataInput.validarSenha(editTextPassword.getText().toString())) {
                        showLoading();
                        performRegister(editTextEmail.getText().toString(),
                                editTextPassword.getText().toString());
                    } else {
                        editTextPassword.setError("A senha deve conter pelo menos seis caracteres");
                    }
                } else {
                    editTextPassword.setError("Preencha o campo senha");
                }
            } else {
                editTextEmail.setError("Email inválido");
            }
        } else {
            editTextEmail.setError("Preencha o campo email");
        }
    }

    private void performRegister(String email, String pass) {
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(RegisterUserActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            performRegisterInFirebase();
                            alert("Sucesso ao registrar");
                            hideLoading();
                            finish();
                        } else {
                            alert("Erro ao cadastrar");
                            hideLoading();
                        }
                    }

                    private void performRegisterInFirebase() {
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String userEmail = user.getEmail();
                        String userUid = user.getUid();
                        UserDTO userDTO = new UserDTO(userEmail, userUid);
                        mDatabase.child("users").child(userUid).setValue(userDTO);
                    }
                });
    }

    private void alert(String msg) {
        Toast.makeText(RegisterUserActivity.this, msg, Toast.LENGTH_SHORT)
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
