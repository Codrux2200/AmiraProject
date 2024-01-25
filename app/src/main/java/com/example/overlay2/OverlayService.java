package com.example.overlay2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.example.overlay2.ModelAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class OverlayService extends Service implements LifecycleOwner{

    private ModelAdapter overlayAdapter;
    private OverlayViewModel overlayViewModel;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("started", "overlay started");

        overlayAdapter = new ModelAdapter(this);
        overlayViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(OverlayViewModel.class);
        overlayViewModel.loadOverlayItems();
        Log.d("items", "loaded");

        overlayViewModel.getOverlayItems().observeForever( overlayItems -> {
            Log.d("items", "caca");
            if (overlayItems != null) {
                Log.d("items", overlayItems.toString());
                overlayAdapter.setOverlayItems(overlayItems);
            }
        });

        startOverlayService();
    }

    private void startOverlayService() {
        // Notification en premier plan pour maintenir le service actif
        // Afficher l'overlay
        overlayAdapter.showOverlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        overlayViewModel.getOverlayItems().removeObservers(this);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return new LifecycleRegistry(this);
    }
}
