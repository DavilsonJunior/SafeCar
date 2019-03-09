package safecar.cursoandroid.com.safecar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import java.util.Random;

import safecar.cursoandroid.com.safecar.helper.Permissoes;
import safecar.cursoandroid.com.safecar.helper.Preferencias;
import safecar.cursoandroid.com.safecar.model.Usuario;


public class Telefone2Activity extends AppCompatActivity {

    private Button btnContinuarTelefone;
    private EditText telefone;
    private Usuario usuario;
    private String[] permissoesNecessarias = new String[]{
            android.Manifest.permission.SEND_SMS,
            android.Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefone2);

        Permissoes.validarPermissoes(permissoesNecessarias,this, 1);

        btnContinuarTelefone = findViewById(R.id.botao_telefone_continuarId);
        telefone = findViewById(R.id.telefoneId);

        //Definir Mascaras
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("+NNNNNNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone, simpleMaskTelefone);
        telefone.addTextChangedListener(maskTelefone);

        btnContinuarTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                //usuario.setTelefone(telefone.getText().toString());
                String telefoneCompleto = telefone.getText().toString();

                String telefoneNoFormatacao = telefoneCompleto.replace("-", "");
                telefoneNoFormatacao = telefoneNoFormatacao.replace("+", "");
                //telefoneNoFormatacao = telefoneNoFormatacao.replace("(", "");
                //telefoneNoFormatacao = telefoneNoFormatacao.replace(")", "");

                //gerar token
                Random randomico = new Random();
                int numeroRandomico = randomico.nextInt(9999 - 1000) + 1000;
                String token = String.valueOf(numeroRandomico);
                String mensagemEnvio = "SafeCar codigo de confirmacao: " + token;

                //Salvar os dados para validaÃ§Ã£o
                Preferencias preferencias = new Preferencias(Telefone2Activity.this);
                preferencias.salvarUsuarioPreferencias(telefoneNoFormatacao, token);

                //Envio SMS
                //telefoneNoFormatacao = "5554";
                boolean enviadoSMS = enviaSMS("+" + telefoneNoFormatacao, mensagemEnvio);
                if (enviadoSMS){
                    Intent intent = new Intent(Telefone2Activity.this, ValidacaoTelefoneActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(Telefone2Activity.this, "Problema ao enviar Sms, tente novamente!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    //envia SMS
    private boolean enviaSMS(String telefone, String mensagem){

        try{

            SmsManager smsManager = SmsManager.getDefault();
            //smsManager.sendTextMessage(telefone, null, mensagem, null, null);
            smsManager.sendTextMessage(telefone, null, mensagem, null, null );
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for( int resultado : grantResults ){

            if( resultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }

        }

    }

    private void alertaValidacaoPermissao(){

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle("PermissÃµes negadas");
        builder.setMessage("Para utilizar esse app, Não é necessario aceitar as permissóes");

        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }




    private void abrirValidacaoTelefone(){
        Intent intent = new Intent(Telefone2Activity.this, ValidacaoTelefoneActivity.class);
        startActivity(intent);
    }

}
