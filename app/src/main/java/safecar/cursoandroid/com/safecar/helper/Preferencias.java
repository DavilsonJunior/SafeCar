package safecar.cursoandroid.com.safecar.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private String NOME_ARQUIVO = "safecar.preferencias";
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    private String CHAVE_NOME = "nome";
    private String CHAVE_DATA = "data";
    private String CHAVE_CPF = "cpf";
    private String CHAVE_TELEFONE = "telefone";
    private String CHAVE_TOKEN = "token";

    public Preferencias(Context ContextoParametro){

        contexto = ContextoParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();

    }

    public void salvarUsuarioPreferencias(String telefone, String token){

        //editor.putString(CHAVE_NOME, nome);
        //editor.putString(CHAVE_DATA, data);
        //editor.putString(CHAVE_CPF, cpf);
        editor.putString(CHAVE_TELEFONE, telefone);
        editor.putString(CHAVE_TOKEN, token);
        editor.commit();

    }

    public HashMap<String, String> getDadosUsuario(){

        HashMap<String, String> dadosUsuario = new HashMap<>();

        //dadosUsuario.put(CHAVE_NOME, preferences.getString(CHAVE_NOME, null));
        //dadosUsuario.put(CHAVE_DATA, preferences.getString(CHAVE_DATA, null));
        //dadosUsuario.put(CHAVE_CPF, preferences.getString(CHAVE_CPF, null));
        dadosUsuario.put(CHAVE_TELEFONE, preferences.getString(CHAVE_TELEFONE, null));
        dadosUsuario.put(CHAVE_TOKEN, preferences.getString(CHAVE_TOKEN, null));

        return dadosUsuario;
    }
}
