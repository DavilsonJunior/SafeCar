package safecar.cursoandroid.com.safecar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

import safecar.cursoandroid.com.safecar.helper.Preferencias;

public class ValidacaoTelefoneActivity extends AppCompatActivity {

    private EditText codigoValidar;
    private Button botaoValidar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validacao_telefone);

        getSupportActionBar().setTitle("Cadastrando Usuário");

        codigoValidar = findViewById(R.id.telefoneValidarId);
        botaoValidar = findViewById(R.id.botao_validar_continuvaarId);

        //definir mascaras
        SimpleMaskFormatter simpleMaskCodigoValidacao = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher mascaraCodigoValidacao = new MaskTextWatcher(codigoValidar, simpleMaskCodigoValidacao);
        codigoValidar.addTextChangedListener(mascaraCodigoValidacao);


        botaoValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Recuperar dados das preferencias do usuario
                Preferencias preferencias = new Preferencias(ValidacaoTelefoneActivity.this);

                HashMap<String, String> usuario = preferencias.getDadosUsuario();

                String tokenGerado = usuario.get("token");
                String tokenDigitado = codigoValidar.getText().toString();

                if(tokenDigitado.equals(tokenGerado)){
                    Toast.makeText(ValidacaoTelefoneActivity.this, "Token Validado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ValidacaoTelefoneActivity.this, CadastroActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(ValidacaoTelefoneActivity.this, "Token não Validado", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}
