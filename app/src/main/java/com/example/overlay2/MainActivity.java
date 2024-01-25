package com.example.overlay2;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.overlay2.OverlayService;

public class MainActivity extends AppCompatActivity {

    private static final int OVERLAY_PERMISSION_REQUEST_CODE = 123;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Vérifiez si la permission de superposition est accordée, sinon demandez-la
        if (!Settings.canDrawOverlays(this)) {
            requestOverlayPermission();
        } else {
            // L'overlay est déjà autorisé, pas besoin de démarrer une nouvelle activité
            startOverlayService();

        }
        finish(); // Fermer l'activité actuelle
    }

    private void requestOverlayPermission() {
        // Affiche un message demandant la permission
        Toast.makeText(this, "Please grant overlay permission", Toast.LENGTH_SHORT).show();

        // Ouvre les paramètres pour accorder la permission de superposition
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
            // Vérifiez à nouveau si la permission a été accordée après le retour des paramètres
            if (Settings.canDrawOverlays(this)) {
                startOverlayService();
            } else {
                // L'utilisateur n'a pas accordé la permission, vous pouvez prendre une action appropriée ici
                Toast.makeText(this, "Overlay permission not granted", Toast.LENGTH_SHORT).show();
            }
            finish(); // Fermer l'activité actuelle même si la permission n'est pas accordée
        }
    }

    private void startOverlayService() {
        Intent overlayIntent = new Intent(this, OverlayService.class);
        startService(overlayIntent);
    }
}
