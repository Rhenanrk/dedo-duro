package br.ufg.com.dedoduro.model;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.xw.repo.BubbleSeekBar;

import java.util.Calendar;
import java.util.UUID;

import br.ufg.com.dedoduro.R;

import static java.lang.String.format;

public class NewRegisterActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private static final String TAG = "NewRegisterActivity";
    private TextView mDisplayDateInicio;
    private TextView mDisplayDateConclusao;
    private DatePickerDialog.OnDateSetListener mDateSetListenerInicio;
    private DatePickerDialog.OnDateSetListener mDateSetListenerFim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_register);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarNew_register);
        setSupportActionBar(myToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setupDataInicioObra();
        setupDataFinalObra();
        setupProgressoObra();
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
        //Cria id para obra
        UUID uuid = UUID.randomUUID();
        String myRandom = uuid.toString();
        String idObra = myRandom.substring(0, 28);

        //Captura id do usuário logado
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String idUser = user.getUid();

        //Captura nome da obra
        TextInputEditText textInputEditTextNomeObra = (TextInputEditText) findViewById(R.id.textInput_nome_obra);

        //Captura localização da obra
        TextInputEditText textInputEditTextLocalObra = (TextInputEditText) findViewById(R.id.textInputLocal_obra);

        //Captura data inicial da obra
        TextView textViewDataInicio = (TextView) findViewById(R.id.textViewData_inicio);

        //Captura data de conclusão da obra
        TextView textViewDataConclusao = (TextView) findViewById(R.id.textViewData_conclusao);

        //Captura descrição da obra
        TextInputEditText textInputEditTextDescricao = (TextInputEditText) findViewById(R.id.textInputDescricao_obra);

        //Captura progresso da obra
        TextView textViewProgresso = (TextView) findViewById(R.id.textViewProgresso);

        //Chama método que grava dados no banco
        persistData(idObra, idUser, "teste",
                textInputEditTextNomeObra.getText().toString(), textInputEditTextLocalObra.getText().toString(),
                textViewDataInicio.getText().toString(), textViewDataConclusao.getText().toString(),
                textInputEditTextDescricao.getText().toString(), textViewProgresso.getText().toString());

    }

    private void persistData(String idObra, String idUser, String imageURL, String nome, String local,
                             String inicioObra, String previsaoDeConclusao,
                             String descricao, String porcentagemDeConclusao) {
        //Criando objeto ObraFullDTO
        ObraFullDTO obraFullDTO = new ObraFullDTO(idObra, idUser, imageURL, nome, local, inicioObra, previsaoDeConclusao,
                descricao, porcentagemDeConclusao);
        //Criando objeto ObraFullDTO
        ObraLiteDTO obraLiteDTO = new ObraLiteDTO(idObra, nome, local);
        //Persiste no Firebase
        mDatabase.child("obrasFull").child(idObra).setValue(obraFullDTO);
        mDatabase.child("obraLite").child(idObra).setValue(obraLiteDTO);
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

    private void setupDataInicioObra() {
        mDisplayDateInicio = (TextView) findViewById(R.id.textViewData_inicio);
        mDisplayDateInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        NewRegisterActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListenerInicio,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenerInicio = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = day + "/" + month + "/" + year;
                mDisplayDateInicio.setText(date);
            }
        };
    }

    private void setupDataFinalObra() {
        mDisplayDateConclusao = (TextView) findViewById(R.id.textViewData_conclusao);
        mDisplayDateConclusao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        NewRegisterActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListenerFim,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenerFim = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = day + "/" + month + "/" + year;
                mDisplayDateConclusao.setText(date);
            }
        };
    }

    private void alert(String msg) {
        Toast.makeText(NewRegisterActivity.this, msg, Toast.LENGTH_SHORT)
                .show();
    }
}
