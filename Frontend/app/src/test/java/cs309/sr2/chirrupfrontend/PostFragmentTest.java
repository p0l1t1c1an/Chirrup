package cs309.sr2.chirrupfrontend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import cs309.sr2.chirrupfrontend.listui.post.PostFragment;
import cs309.sr2.chirrupfrontend.listui.post.PostPresenter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PostFragmentTest {

    @Mock
    PostPresenter postPresenter;

//    @Mock
    PostFragment postFragment;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
//        postPresenter = new PostPresenter(postFragment.getView());
        postFragment = new PostFragment(0);
        postFragment.setPostPresenter(postPresenter);
    }

    @Test
    public void addLikeTest() throws JSONException {
        JSONObject postData = new JSONObject();
        JSONArray likes = new JSONArray();
        likes.put(12);
        likes.put(4);
        likes.put(7);
        postData.put("likes", likes);

        doCallRealMethod().when(postPresenter).loadLikeData(postData);
        doNothing().when(postPresenter).likePostRemote(anyString());
        doCallRealMethod().when(postPresenter).likePost(anyString());
        doCallRealMethod().when(postPresenter).getLikes();
        doCallRealMethod().when(postPresenter).isLiked();



//        postPresenter.loadLikeData(postData);

        assertFalse(postPresenter.isLiked());
        assertEquals(0, postPresenter.getLikes());

//        postFragment.likePost();
//
//        assertTrue(postPresenter.isLiked());
//        assertEquals(1, postPresenter.getLikes());
    }


    /**
     * test show profile method. normally shows another fragment, so nothing to really assert
     */
    @Test
    public void showProfileTest() {
        doNothing().when(postPresenter).showProfile();
        postFragment.showProfile();
    }

}
