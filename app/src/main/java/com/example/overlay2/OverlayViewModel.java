package com.example.overlay2;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class OverlayViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> showOverlay = new MutableLiveData<>();
    private MutableLiveData<List<Model>> overlayItems = new MutableLiveData<>();

    public OverlayViewModel(@NonNull Application application) {
        super(application);
        // Initialiser et récupérer les données nécessaires
    }

    public LiveData<Boolean> getShowOverlay() {
        return showOverlay;
    }

    public LiveData<List<Model>> getOverlayItems() {
        return overlayItems;
    }

    public void loadOverlayItems() {
        // Charger les données depuis une source (API, base de données, etc.)
        List<Model> items =  new ArrayList<>();
        items.add(new Model(R.drawable.google, 100f, 100f));
        items.add(new Model(R.drawable.facebook, 300f, 300f));
        Log.d("items:", "load items worked" + items.toString());
        overlayItems.setValue(items);
    }

    // ... autres méthodes du ViewModel
}