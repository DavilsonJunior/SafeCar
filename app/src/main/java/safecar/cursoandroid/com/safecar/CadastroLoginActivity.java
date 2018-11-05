package safecar.cursoandroid.com.safecar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
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

public class CadastroLoginActivity extends AppCompatActivity {

    private Button botaoCadastroContinuar;
    private EditText cadastroNome;
    private EditText cadastroData;
    private EditText cadastroEmail;
    private EditText cadastroSenha;
    private Usuario usuario;
    private FirebaseAuth autenticacao;
    private Switch switchTipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_login);

        botaoCadastroContinuar = findViewById(R.id.btn_cadastro_continuarId);
        cadastroNome = findViewById(R.id.cadastroNomeUsuarioId);
        cadastroData = findViewById(R.id.cadastroDataDeNascimentoUsuarioId);
        cadastroEmail = findViewById(R.id.cadastroEmailUsuarioId);
        cadastroSenha = findViewById(R.id.cadastroSenhaUsuarioId);
        switchTipoUsuario = findViewById(R.id.switchTipoUsuario);

        //Definir Mascaras
        SimpleMaskFormatter simpleMaskData = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(cadastroData, simpleMaskData);
        cadastroData.addTextChangedListener(maskTelefone);

        botaoCadastroContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Recuperar textos dos campos
                String textoNome = cadastroNome.getText().toString();
                String textoData = cadastroData.getText().toString();
                String textoEmail = cadastroEmail.getText().toString();
                String textoSenha = cadastroSenha.getText().toString();

                if (!textoNome.isEmpty()) {//verifica nome
                    if (!textoEmail.isEmpty()) {//verifica e-mail
                        if (!textoSenha.isEmpty()) {//verifica senha
                            if (!textoData.isEmpty()) {//verifica Data

                                Usuario usuario = new Usuario();
                                usuario.setNome(textoNome);
                                usuario.setData(textoData);
                                usuario.setEmail(textoEmail);
                                usuario.setSenha(textoSenha);
                                usuario.setTipo(verificaTipoUsuario());

                                cadastrarUsuario(usuario);

                            } else {
                                Toast.makeText(CadastroLoginActivity.this,
                                        "Preencha a Data!",
                                        Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(CadastroLoginActivity.this,
                                    "Preencha a senha!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CadastroLoginActivity.this,
                                "Preencha o email!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CadastroLoginActivity.this,
                            "Preencha o nome!",
                            Toast.LENGTH_SHORT).show();
                }

            }

                });

            }

    public void cadastrarUsuario(final Usuario usuario ){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    try {

                        String idUsuario = task.getResult().getUser().getUid();
                        usuario.setId(idUsuario);
                        usuario.salvar();

                        //Atualizar nome no UserProfile
                        UsuarioFirebase.atualizarNomeUsuario(usuario.getNome());

                        // Redireciona o usuÃƒÂ¡rio com base no seu tipo
                        // Se o usuÃƒÂ¡rio for passageiro chama a activity maps
                        // senÃƒÂ£o chama a activity requisicoes
                        if (verificaTipoUsuario() == "P") {
                            startActivity(new Intent(CadastroLoginActivity.this, PassageiroActivity.class));
                            finish();

                            Toast.makeText(CadastroLoginActivity.this,
                                    "Sucesso ao cadastrar Passageiro!",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            startActivity(new Intent(CadastroLoginActivity.this, RequisicoesActivity.class));
                            finish();

                            Toast.makeText(CadastroLoginActivity.this,
                                    "Sucesso ao cadastrar Motorista!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        excecao = "Digite uma senha mais forte!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Por favor, digite um e-mail vÃƒÂ¡lido";
                    } catch (FirebaseAuthUserCollisionException e) {
                        excecao = "Este conta jÃƒÂ¡ foi cadastrada";
                    } catch (Exception e) {
                        excecao = "Erro ao cadastrar usuÃƒÂ¡rio: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroLoginActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public String verificaTipoUsuario(){
        return switchTipoUsuario.isChecked() ? "M" : "P" ;
    }


            private void abrirProximaTela() {
                Intent intent = new Intent(CadastroLoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
