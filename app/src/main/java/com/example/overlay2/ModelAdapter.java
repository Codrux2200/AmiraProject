package com.example.overlay2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.overlay2.R;

import java.util.List;

// OverlayAdapter.java
public class ModelAdapter {

    private Context context;
    private View overlayView;
    private ViewGroup overlayContainer;

    public ModelAdapter(Context context) {
        this.context = context;
        initOverlayView();
    }

    private void initOverlayView() {
        // Crée une vue personnalisée pour afficher les éléments de l'overlay
        overlayView = LayoutInflater.from(context).inflate(R.layout.overlay_layout, null);
        overlayView.setBackgroundColor(Color.WHITE);
        overlayContainer = overlayView.findViewById(R.id.overlayContainer);

    }

    // Méthode pour mettre à jour les éléments de l'overlay
    public void setOverlayItems(List<Model> overlayItems) {
        // Efface les anciens éléments
        overlayContainer.removeAllViews();

        // Ajoute les nouveaux éléments de l'overlay à la vue
        for (Model overlayItem : overlayItems) {
            View itemView = new View(context);

            // Créez un Drawable à partir de la ressource d'image
            Drawable drawable = ContextCompat.getDrawable(context, overlayItem.getid());

            // Définissez le Drawable comme fond de la View
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                itemView.setBackground(drawable);
            } else {
                // Utilisez la méthode dépréciée pour les versions plus anciennes d'Android
                itemView.setBackgroundDrawable(drawable);
            }

            // Redimensionnez la vue selon vos besoins
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    /* width */ 100,  // Remplacez par la largeur souhaitée en pixels
                    /* height */ 100  // Remplacez par la hauteur souhaitée en pixels
            );

            itemView.setLayoutParams(layoutParams);

            // Définissez la position
            itemView.setX(overlayItem.getPositionX());
            itemView.setY(overlayItem.getPositionY());

            // Ajoutez la vue à l'overlayContainer
            overlayContainer.addView(itemView);
        }

    }

    // Méthode pour afficher l'overlay
    public void showOverlay() {
        // Ajoute la vue de l'overlay à la fenêtre système
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.addView(overlayView, params);
    }

    // Méthode pour masquer l'overlay
    public void hideOverlay() {
        // Retire la vue de l'overlay de la fenêtre système
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.removeView(overlayView);
    }
}
