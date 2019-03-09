package safecar.cursoandroid.com.safecar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class VoltandoParaCadastroFotoActivity extends AppCompatActivity {

    private Button botao_Cpf_continuar;
    private ImageView abrirGaleria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voltando_para_cadastro_foto);

        getSupportActionBar().setTitle("Cadastrando Usuário");

        botao_Cpf_continuar = findViewById(R.id.btn_Cpf_continuarId);
        abrirGaleria = findViewById(R.id.abrirGaleriaId);

        botao_Cpf_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VoltandoParaCadastroFotoActivity.this, "Insira uma foto de perfil", Toast.LENGTH_SHORT).show();
            }
        });

        abrirGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VoltandoParaCadastroFotoActivity.this, FotosActivity.class));
            }
        });

    }
}
