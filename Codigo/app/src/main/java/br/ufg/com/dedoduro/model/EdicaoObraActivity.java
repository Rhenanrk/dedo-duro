package br.ufg.com.dedoduro.model;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.xw.repo.BubbleSeekBar;

import java.util.Calendar;

import br.ufg.com.dedoduro.R;

public class EdicaoObraActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private StorageReference mStorageReference;
    private String chave;
    private TextView mDisplayDateConclusao;
    private DatePickerDialog.OnDateSetListener mDateSetListenerFim;
    ObraFullDTO obraFullDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicao_obra);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();

        //habilita toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarEdit_obra);
        setSupportActionBar(myToolbar);

        //habilita botão de voltar na toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //recupera chave da obra clicada na tela anterior
        Intent intentEditar = getIntent();
        chave = intentEditar.getStringExtra("chave");

        //preenche
        final TextView textViewNome = (TextView) findViewById(R.id.textViewEdicaoNome_Obra);
        final TextView textViewLocal = (TextView) findViewById(R.id.textViewEdicaoLocal_obra);

        //referencia no firebase
        mDatabase = FirebaseDatabase.getInstance().getReference().child("obrasFull").child(chave);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                obraFullDTO = dataSnapshot.getValue(ObraFullDTO.class);

                String nome = obraFullDTO.getNome();
                String local = obraFullDTO.getLocal();

                textViewNome.setText(nome);
                textViewLocal.setText(local);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
                setupEdicao();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupEdicao() {
        //Captura data de conclusão da obra
        TextView textViewDataConclusao = (TextView) findViewById(R.id.textViewEdicaoData_conclusao);

        //Captura progresso da obra
        TextView textViewProgresso = (TextView) findViewById(R.id.textViewEdicaoProgresso);

        persistData(textViewDataConclusao.getText().toString(), textViewProgresso.getText().toString());

    }

    private void persistData(String previsaoDeConclusao, String porcentagemDeConclusao) {

        //Persiste no Firebase
        mDatabase.child("previsaoDeConclusao").setValue(previsaoDeConclusao);
        mDatabase.child("porcentagemDeConclusao").setValue(porcentagemDeConclusao);

        alert("Edição realizada com sucesso!");
        Intent intentRetorna = new Intent(EdicaoObraActivity.this, DescricaoObraActivity.class);
        intentRetorna.putExtra("chave", chave);
        startActivity(intentRetorna);
        finish();
    }

    private void alert(String msg) {
        Toast.makeText(EdicaoObraActivity.this, msg, Toast.LENGTH_SHORT)
                .show();
    }

    private void setupDataFinalObra() {
        mDisplayDateConclusao = (TextView) findViewById(R.id.textViewEdicaoData_conclusao);
        mDisplayDateConclusao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EdicaoObraActivity.this,
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

    private void setupProgressoObra() {
        BubbleSeekBar bubbleSeekBar = (BubbleSeekBar) findViewById(R.id.seekBarEdicao);
        final TextView textView = (TextView) findViewById(R.id.textViewEdicaoProgresso);

        bubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void onProgressChanged(int progress, float progressFloat) {
                textView.setText(progress + getString(R.string.dialogLoadImage));
            }

            @Override
            public void getProgressOnActionUp(int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(int progress, float progressFloat) {

            }
        });
    }
}
