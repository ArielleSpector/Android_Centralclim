package com.example.android_centralclim; // Certifique-se de que este é o seu pacote correto

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button; // Alterado de MaterialButton para Button se você não estiver usando especificamente
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout; // Importado se você quiser interagir com o Layout também

public class RecuperarSenhaActivity extends AppCompatActivity {

    private TextInputEditText emailEditText; // Corresponde ao ID @+id/emailEditText
    private Button sendButton; // Corresponde ao ID @+id/sendButton (MaterialButton é um tipo de Button)
    // Se você precisar interagir com o TextInputLayout para mostrar erros, declare-o também:
    // private TextInputLayout emailInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this); // Você pode manter ou remover se não estiver usando especificamente
        setContentView(R.layout.activity_recuperar_senha); // Garanta que este é o nome correto do seu layout

        // Inicializa as views com os IDs do seu layout
        emailEditText = findViewById(R.id.emailEditText);
        sendButton = findViewById(R.id.sendButton);
        // Se você declarou emailInputLayout:
        // emailInputLayout = findViewById(R.id.emailInputLayout);




        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = emailEditText.getText().toString().trim();

                if (emailAddress.isEmpty()) {
                    // Você pode mostrar o erro diretamente no TextInputEditText
                    // ou usar o TextInputLayout se o tiver referenciado:
                    // emailInputLayout.setError("O e-mail é obrigatório.");
                    emailEditText.setError("O e-mail é obrigatório.");
                    emailEditText.requestFocus();
                    return;
                } else {
                    // Limpa o erro se o e-mail for preenchido
                    // if (emailInputLayout != null) emailInputLayout.setError(null);
                    emailEditText.setError(null);
                }


                String assunto = "Recuperação de Senha - CentralClim";
                // IMPORTANTE: O link abaixo é um placeholder.
                String linkDeRedefinicaoPlaceholder = "https://centralclim.example.com/reset?token=TOKEN_EXEMPLO_" + System.currentTimeMillis();
                String corpoDoEmail = "Olá,\n\nVocê solicitou a recuperação de senha para sua conta no CentralClim.\n\n" +
                        "Para redefinir sua senha, por favor, clique no link abaixo:\n" +
                        linkDeRedefinicaoPlaceholder + "\n\n" +
                        "Se você não fez esta solicitação, por favor, ignore este e-mail.\n\n" +
                        "Atenciosamente,\nEquipe CentralClim";
                // NUNCA ENVIE A SENHA DIRETAMENTE.

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:")); // Apenas apps de e-mail devem lidar com isso
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress}); // Destinatário
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, assunto);
                emailIntent.putExtra(Intent.EXTRA_TEXT, corpoDoEmail);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Escolha um aplicativo de e-mail:"));
                    // Toast modificado conforme solicitado
                    Toast.makeText(RecuperarSenhaActivity.this, "Preparando e-mail para reenvio de senha...", Toast.LENGTH_LONG).show();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(RecuperarSenhaActivity.this, "Nenhum aplicativo de e-mail encontrado.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Se você quiser adicionar funcionalidade ao TextView "Tentar novamente"
        TextView tryAgainTextView = findViewById(R.id.tryAgainTextView);
        tryAgainTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ação para "Tentar novamente", por exemplo, limpar o campo de e-mail
                emailEditText.setText("");
                emailEditText.setError(null);
                // if (emailInputLayout != null) emailInputLayout.setError(null);
                Toast.makeText(RecuperarSenhaActivity.this, "Campo de e-mail limpo.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}