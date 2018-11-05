package safecar.cursoandroid.com.safecar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import safecar.cursoandroid.com.safecar.config.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {

    //Atributos
    private Toolbar toolbarTelaPrincipal;
    private FirebaseAuth autenticacao;
    private TextView buscarOfertasCaronas, ofertasCaronas, caronasPendentes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buscando os Ids
        buscarOfertasCaronas = findViewById(R.id.buscarOfertasCaronasId);
        ofertasCaronas = findViewById(R.id.oferecerCaronasId);
        caronasPendentes = findViewById(R.id.caronasPendentesId);

       /* buscarOfertasCaronas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirBuscarCaronaActivity();
            }
        });

        ofertasCaronas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirOfertasCaronasActivity();
            }
        });*/

    }

    //Adicionando Menu ao Layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater infrater = getMenuInflater();
        infrater.inflate(R.menu.menu, menu);
        return true;
    }

    //AcÃ£o do Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this, "Calma! ainda estou programando.", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.item2:
                Toast.makeText(this, "Atualizando...", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.item3:
                Toast.makeText(this, "deslogando...", Toast.LENGTH_SHORT).show();
                autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                autenticacao.signOut();
                finish();
                voltarParaLogin();

                return true;

            case R.id.item_seguranca:
                abrirSegurancaCadastro();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void voltarParaLogin(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void abrirSegurancaCadastro(){
        Intent intent = new Intent(this, SegurancaCadastroActivity.class);
        startActivity(intent);
    }

    /*private void abrirBuscarCaronaActivity(){
        Intent intent = new Intent(this, BuscarCaronaActivity.class);
        startActivity(intent);
    }

    private void abrirOfertasCaronasActivity(){
        Intent intent = new Intent(this, OferecerCaronaActivity.class);
        startActivity(intent);
    }*/
}
