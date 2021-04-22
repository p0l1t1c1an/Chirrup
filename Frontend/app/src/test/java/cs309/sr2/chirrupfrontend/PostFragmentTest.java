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

import cs309.sr2.chirrupfrontend.listui.post.PostFragment;
import cs309.sr2.chirrupfrontend.listui.post.PostPresenter;
import cs309.sr2.chirrupfrontend.utils.AppController;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * class to test methods of post fragment
 *
 * @author Jeremy Noesen
 */
@RunWith(MockitoJUnitRunner.class)
public class PostFragmentTest {

    /**
     * mocked post presenter for post fragment
     */
    @Mock
    PostPresenter postPresenter;

    /**
     * post fragment being tested
     */
    PostFragment postFragment;

    /**
     * initialize variables and mocks
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        postFragment = new PostFragment(0);
        postFragment.setPostPresenter(postPresenter);
    }

    /**
     * test adding a like to a post with no likes initially
     *
     * note: current class structure prevents me from completing this properly
     *
     * @throws JSONException
     */
    @Test
    public void addLikeTest() throws JSONException {
        JSONObject postData = new JSONObject();
        JSONArray likes = new JSONArray();
        likes.put(12);
        likes.put(4);
        likes.put(7);
        postData.put("likes", likes);

//        doCallRealMethod().when(postPresenter).loadLikeData(postData);
        doNothing().when(postPresenter).likePostRemote(anyString());
        doCallRealMethod().when(postPresenter).likePost(anyString());
        when(postPresenter.getLikes()).thenCallRealMethod();
        when(postPresenter.isLiked()).thenCallRealMethod();

//        postPresenter.loadLikeData(postData);

        assertFalse(postPresenter.isLiked());
        assertEquals(0, postPresenter.getLikes());

//        postFragment.likePost();

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

    /**
     * test that updating the post presenter works
     */
    @Test
    public void postPresenterUpdateTest() {
        PostPresenter newPostPresenter = mock(PostPresenter.class);
        assertEquals(postPresenter, postFragment.getPostPresenter());
        postFragment.setPostPresenter(newPostPresenter);
        assertEquals(newPostPresenter, postFragment.getPostPresenter());
    }

    /**
     * test that the post ID id being set properly
     */
    @Test
    public void postIDTest() {
        assertEquals(0, postFragment.getPostID());
        assertEquals(34, new PostFragment(34).getPostID());
        assertEquals(456, new PostFragment(456).getPostID());
        assertEquals(5, new PostFragment(5).getPostID());
    }



}
