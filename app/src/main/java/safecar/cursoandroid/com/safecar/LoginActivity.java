package safecar.cursoandroid.com.safecar;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import safecar.cursoandroid.com.safecar.config.ConfiguracaoFirebase;
import safecar.cursoandroid.com.safecar.helper.UsuarioFirebase;
import safecar.cursoandroid.com.safecar.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private TextView irParaCadastroLogin;
    private EditText emailLogar;
    private EditText senhaLogar;
    private FirebaseAuth autenticacao;
    private Button botaoLogar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        irParaCadastroLogin = findViewById(R.id.irParaCadastroLoginId);
        emailLogar = findViewById(R.id.emailLogarId);
        senhaLogar = findViewById(R.id.senhaLogarId);
        botaoLogar = findViewById(R.id.btnLogarId);

        verificarUsuarioLogado();

        irParaCadastroLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroFotoeCpfActivity.class);
                startActivity(intent);
            }
        });

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoEmail = emailLogar.getText().toString();
                String textoSenha = senhaLogar.getText().toString();

                if(!textoEmail.isEmpty()){//verifica e-mail
                    if(!textoSenha.isEmpty()){//verifica senha

                        Usuario usuario = new Usuario();
                        usuario.setEmail( textoEmail );
                        usuario.setSenha( textoSenha );

                        logarUsuario( usuario );

                    }else{
                        Toast.makeText(LoginActivity.this, "Preencha a senha", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Preencha o e-mail", Toast.LENGTH_SHORT).show();
                }
           }

        });
    }

    public void logarUsuario(Usuario usuario){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if( task.isSuccessful() ){

                    //Verificar o tipo de Usuario Logado
                    //"Motorista" / "Passageiro"
                    UsuarioFirebase.redirecionaUsuarioLogado(LoginActivity.this);

                }else{
                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte!";
                    }catch ( FirebaseAuthInvalidCredentialsException e){
                        excecao= "Por favor, digite um e-mail válido";
                    }catch ( FirebaseAuthUserCollisionException e){
                        excecao = "Este conta já foi cadastrada";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: "  + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(LoginActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }



    /*private void verificarUsuarioLogado(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }

    }*/

        private void verificarUsuarioLogado(){

            autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

            if(UsuarioFirebase.getUsuarioAtual() != null){
                UsuarioFirebase.redirecionaUsuarioLogado(LoginActivity.this);
            }

        }

    private void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
