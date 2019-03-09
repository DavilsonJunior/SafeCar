package safecar.cursoandroid.com.safecar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AvaliacaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao);

        getSupportActionBar().setTitle("Avalie sua Carona");
    }
}
