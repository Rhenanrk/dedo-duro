package br.ufg.com.dedoduro.model;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.xw.repo.BubbleSeekBar;

import br.ufg.com.dedoduro.R;

import static java.lang.String.format;

public class NewRegisterActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_register);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarNew_register);
        setSupportActionBar(myToolbar);

        setupProgressoObra();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_register_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_confirm:
                readData();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void readData() {
        String nomeObra, localObra, dataInicioObra, dataFimObra, porcentagemObra;

        TextInputEditText textInputEditTextNomeObra = (TextInputEditText) findViewById(R.id.textInput_nome_obra);
        TextInputEditText textInputEditTextLocalObra = (TextInputEditText) findViewById(R.id.textInputLocal_obra);
        TextView textViewProgresso = (TextView) findViewById(R.id.textViewProgresso);
        TextInputEditText textInputEditTextDescricao = (TextInputEditText) findViewById(R.id.textInputDescricao_obra);

        persistData("teste2", "teste", "teste",
                textInputEditTextNomeObra.getText().toString(), textInputEditTextLocalObra.getText().toString(),
                "teste", "teste", textInputEditTextDescricao.getText().toString(),
                textViewProgresso.getText().toString());

    }

    private void persistData(String idObra, String idUser, String imageURL, String nome, String local,
                             String inicioObra, String previsaoDeConclusao,
                             String descricao, String porcentagemDeConclusao) {
        ObraDTO obraDTO = new ObraDTO(idObra, idUser, imageURL, nome, local, inicioObra, previsaoDeConclusao,
                descricao, porcentagemDeConclusao);

        mDatabase.child("obrasFull").child(nome).setValue(obraDTO);
            alert("Nova obra cadastrada");
            Intent intentHome = new Intent(NewRegisterActivity.this, HomeActivity.class);
            startActivity(intentHome);
            finish();



    }


    private void setupProgressoObra() {
        BubbleSeekBar bubbleSeekBar = (BubbleSeekBar) findViewById(R.id.seekBar);
        final TextView textView = (TextView) findViewById(R.id.textViewProgresso);

        bubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onProgressChanged(int progress, float progressFloat) {
                textView.setText(String.format(progress + "%% concluído"));
            }

            @Override
            public void getProgressOnActionUp(int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(int progress, float progressFloat) {

            }
        });
    }

    private void alert(String msg) {
        Toast.makeText(NewRegisterActivity.this, msg, Toast.LENGTH_SHORT)
                .show();
    }
}
