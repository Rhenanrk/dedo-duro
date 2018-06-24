package br.ufg.com.dedoduro.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.ufg.com.dedoduro.R;
import br.ufg.com.dedoduro.auth.LoginActivity;
import br.ufg.com.dedoduro.connection.Connection;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewObras;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(myToolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("obrasLite");
        mDatabase.keepSynced(true);

        recyclerViewObras = (RecyclerView) findViewById(R.id.reciclerViewObras);
        recyclerViewObras.setHasFixedSize(true);
        recyclerViewObras.setLayoutManager(new LinearLayoutManager(this));

        registerNew();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<ObraLiteDTO, ObraLiteViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ObraLiteDTO, ObraLiteViewHolder>(ObraLiteDTO.class,
                        R.layout.row_obra, ObraLiteViewHolder.class, mDatabase) {
                    @Override
                    protected void populateViewHolder(ObraLiteViewHolder viewHolder, ObraLiteDTO model, int position) {
                        viewHolder.setNomeObra(model.getNome());
                        viewHolder.setLocalObra(model.getLocal());
                    }
                };
        recyclerViewObras.setAdapter(firebaseRecyclerAdapter);

    }

    public static class ObraLiteViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public ObraLiteViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setNomeObra(String nome) {
            TextView textViewNomeObra = (TextView) mView.findViewById(R.id.textViewRowNome_obra);
            textViewNomeObra.setText(nome);
        }

        public void setLocalObra(String local) {
            TextView textViewLocalObra = (TextView) mView.findViewById(R.id.textViewRowLocal_obra);
            textViewLocalObra.setText(local);
        }
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
