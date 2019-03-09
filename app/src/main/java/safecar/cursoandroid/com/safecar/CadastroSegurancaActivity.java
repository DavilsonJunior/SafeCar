package safecar.cursoandroid.com.safecar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CadastroSegurancaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_seguranca);

        getSupportActionBar().setTitle("Cadastrando Amigos");
    }
}
