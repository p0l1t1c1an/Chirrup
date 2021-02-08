package edu.iastate.jnoesen.experiment2.ui.page3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Page3ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Page3ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the third page, hit the button 👀");
    }

    public LiveData<String> getText() {
        return mText;
    }
}