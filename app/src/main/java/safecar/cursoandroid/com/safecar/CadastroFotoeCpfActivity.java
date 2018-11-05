package safecar.cursoandroid.com.safecar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import safecar.cursoandroid.com.safecar.config.ValidaCPF;
import safecar.cursoandroid.com.safecar.model.Usuario;


public class CadastroFotoeCpfActivity extends AppCompatActivity {

    private Toolbar toolbarFotoeCpf;
    private Spinner habilitacao;
    private EditText cpf;
    private Button cpfContinuar;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_fotoe_cpf);

        habilitacao = findViewById(R.id.inserirHabilitacaoId);
        String[] tipo = {"Tipo: A/B", "Tipo: A", "Tipo: C", "Tipo: D", "Tipo: E"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                tipo);
        habilitacao.setAdapter(adapter);

        cpf = findViewById(R.id.validarCpfId);
        cpfContinuar = findViewById(R.id.btn_Cpf_continuarId);

        //Definir Mascaras
        SimpleMaskFormatter simpleMaskCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");

        MaskTextWatcher maskCPf = new MaskTextWatcher(cpf, simpleMaskCpf);

        cpf.addTextChangedListener( maskCPf );

        cpfContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setCpf(cpf.getText().toString());
                String tefefoneSemFormatao = usuario.getCpf().replace(".", "");
                tefefoneSemFormatao = tefefoneSemFormatao.replace("-", "");

                if(ValidaCPF.isCPF(tefefoneSemFormatao) == false || usuario.getCpf().equals("")){

                    Toast.makeText(CadastroFotoeCpfActivity.this, "CPF invalido ou falta preencher", Toast.LENGTH_LONG).show();

                }
                else{
                    abrirTelaTelefone();
                }
            }
        });

    }

    private void abrirTelaTelefone(){

        Intent i = new Intent(CadastroFotoeCpfActivity.this, TelefoneActivity.class);
        startActivity(i);
    }
}
