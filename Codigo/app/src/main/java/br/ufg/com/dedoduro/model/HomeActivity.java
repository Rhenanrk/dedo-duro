package br.ufg.com.dedoduro.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.ufg.com.dedoduro.R;
import br.ufg.com.dedoduro.auth.LoginActivity;
import br.ufg.com.dedoduro.connection.Connection;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(myToolbar);

        setupRecyclerView();
        registerNew();
    }

    private void setupRecyclerView() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("obrasLite");

        recycler = (RecyclerView) findViewById(R.id.reciclerViewObras);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerAdapter<ObraLiteDTO, UserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ObraLiteDTO, UserViewHolder>(
                ObraLiteDTO.class,
                R.layout.row_obra,
                UserViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(final UserViewHolder holder, ObraLiteDTO model, int position) {

                final String post_key = getRef(position).getKey();

                holder.txtNome.setText(model.nome);
                holder.txtLocal.setText(model.local);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentDescricao = new Intent(HomeActivity.this, DescricaoObraActivity.class);
                        intentDescricao.putExtra("chave", post_key);
                        startActivity(intentDescricao);
                    }
                });
            }
        };
        recycler.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome;
        TextView txtLocal;

        public UserViewHolder(View itemView) {
            super(itemView);
            txtNome = (TextView) itemView.findViewById(R.id.textViewRowNome_obra);
            txtLocal = (TextView) itemView.findViewById(R.id.textViewRowLocal_obra);
        }
    }
}
