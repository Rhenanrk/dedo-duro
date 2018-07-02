package br.ufg.com.dedoduro.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.ufg.com.dedoduro.R;

public class DescricaoObraActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    DatabaseReference mDelete;
    private String chave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descricao_obra);

        //habilita toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(myToolbar);

        //habilita botão de voltar na toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //recupera chave da obra clicada na tela anterior
        Intent intentDescricao = getIntent();
        chave = intentDescricao.getStringExtra("chave");

        //inicializa campos
        final TextView textViewNome = (TextView) findViewById(R.id.textViewEdicaoNome_Obra);
        final TextView textViewLocal = (TextView) findViewById(R.id.textViewEdicaoLocal_obra);
        final TextView textViewDescricao = (TextView) findViewById(R.id.textViewDescricaoDescricao_obra);
        final TextView textViewInicio = (TextView) findViewById(R.id.textViewDescricaoData_inicio);
        final TextView textViewFim = (TextView) findViewById(R.id.textViewDescricaoData_fim);
        final TextView textViewPorcentagem = (TextView) findViewById(R.id.textViewDescricaoPorcentagem);


        //preenche
        setupDescricao(textViewNome, textViewLocal, textViewDescricao, textViewInicio, textViewFim, textViewPorcentagem);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.descricao_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Editar:
                //Chama tela de edição
                Intent intentEditar = new Intent(DescricaoObraActivity.this, EdicaoObraActivity.class);
                intentEditar.putExtra("chave", chave);
                startActivity(intentEditar);
                return true;
            case R.id.Deletar:
                //Deletar obra
                finish();
                mDelete = FirebaseDatabase.getInstance().getReference().child("obrasFull").child(chave);
                mDelete.removeValue();
                mDelete = FirebaseDatabase.getInstance().getReference().child("obrasLite").child(chave);
                mDelete.removeValue();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupDescricao(final TextView textViewNome, final TextView textViewLocal, final TextView textViewDescricao, final TextView textViewInicio, final TextView textViewFim, final TextView textViewPorcentagem) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("obrasFull").child(chave);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ObraFullDTO obraFullDTO = dataSnapshot.getValue(ObraFullDTO.class);

                String nome = obraFullDTO != null ? obraFullDTO.getNome() : null;
                String local = obraFullDTO != null ? obraFullDTO.getLocal() : null;
                String descricao = obraFullDTO != null ? obraFullDTO.getDescricao() : null;
                String inicio = obraFullDTO != null ? obraFullDTO.getInicioObra() : null;
                String fim = obraFullDTO != null ? obraFullDTO.getPrevisaoDeConclusao() : null;
                String porcentagem = obraFullDTO != null ? obraFullDTO.getPorcentagemDeConclusao() : null;

                textViewNome.setText(nome);
                textViewLocal.setText(local);
                textViewDescricao.setText(descricao);
                textViewInicio.setText(inicio);
                textViewFim.setText(fim);
                textViewPorcentagem.setText(porcentagem);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
