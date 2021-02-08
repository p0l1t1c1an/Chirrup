package edu.iastate.jnoesen.experiment2.ui.page2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Page2ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Page2ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is another page 😎");
    }

    public LiveData<String> getText() {
        return mText;
    }
}