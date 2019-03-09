package safecar.cursoandroid.com.safecar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import java.util.Random;
import safecar.cursoandroid.com.safecar.helper.Permissao;
import safecar.cursoandroid.com.safecar.helper.Preferencias;
import safecar.cursoandroid.com.safecar.model.Usuario;


public class TelefoneActivity extends AppCompatActivity {

    private Toolbar toolbarTelefone;
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
        setContentView(R.layout.activity_telefone);

        getSupportActionBar().setTitle("Cadastrando Usuário");

        Permissao.validaPermissoes(1,this, permissoesNecessarias);

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

                //Salvar os dados para validaÃƒÂ§ÃƒÂ£o
                Preferencias preferencias = new Preferencias(TelefoneActivity.this);
                preferencias.salvarUsuarioPreferencias(telefoneNoFormatacao, token);

                //Envio SMS
                telefoneNoFormatacao = "5554";
                boolean enviadoSMS = enviaSMS("+" + telefoneNoFormatacao, mensagemEnvio);
                if (enviadoSMS){
                    Intent intent = new Intent(TelefoneActivity.this, ValidacaoTelefoneActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(TelefoneActivity.this, "Problema ao enviar Sms, tente novamente!", Toast.LENGTH_LONG).show();
                }

                /*if(telefoneNoFormatacao.equals("") ||
                        telefoneNoFormatacao.equals("0000000000000") ||
                        telefoneNoFormatacao.equals("1111111111111") ||
                        telefoneNoFormatacao.equals("2222222222222") || telefoneNoFormatacao.equals("3333333333333") ||
                        telefoneNoFormatacao.equals("4444444444444") || telefoneNoFormatacao.equals("5555555555555") ||
                        telefoneNoFormatacao.equals("6666666666666") || telefoneNoFormatacao.equals("7777777777777") ||
                        telefoneNoFormatacao.equals("8888888888888") || telefoneNoFormatacao.equals("9999999999999")){
                    Toast.makeText(TelefoneActivity.this, "Informe um telefone valido!", Toast.LENGTH_LONG).show();
                }

                */


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
        builder.setTitle("PermissÃƒÂµes negadas");
        builder.setMessage("Para utilizar esse app, NÃƒÂ£o ÃƒÂ© necessario aceitar as permissÃƒÂµes");

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
        Intent intent = new Intent(TelefoneActivity.this, ValidacaoTelefoneActivity.class);
        startActivity(intent);
    }

}
