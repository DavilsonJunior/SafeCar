package safecar.cursoandroid.com.safecar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class TrajetoActivity extends AppCompatActivity {

    private ImageView segurancaMotorista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trajeto);

        getSupportActionBar().setTitle("Mapa");

        inicializarComponentes();
    }

    private void inicializarComponentes(){

        segurancaMotorista = findViewById(R.id.segurancaMotoristaId);

        segurancaMotorista.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(TrajetoActivity.this, SegurancaActivity.class));
                return false;
            }
        });

    }
}
