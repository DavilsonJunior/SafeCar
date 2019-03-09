package safecar.cursoandroid.com.safecar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FotosActivity extends AppCompatActivity {

    private ImageView voltarCadastroFotoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);

        getSupportActionBar().setTitle("Galeria");

        voltarCadastroFotoPerfil = findViewById(R.id.voltarCadastroFotoPerfilId);

        voltarCadastroFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FotosActivity.this, CadastroFotoeCpfActivity.class));
            }
        });
    }
}
