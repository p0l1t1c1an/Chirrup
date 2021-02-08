package edu.iastate.jnoesen.experiment2.ui.page1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Page1ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Page1ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the first page. Click the top left button to view more pages.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}