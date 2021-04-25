package cs309.sr2.chirrupfrontend;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

import cs309.sr2.chirrupfrontend.post.PostPresenter;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * class to test methods of post presenter
 *
 * @author Jeremy Noesen
 */
@RunWith(MockitoJUnitRunner.class)
public class PostPresenterTest {

    /**
     * post presenter being tested
     */
    PostPresenter postPresenter;

    /**
     * mocked view
     */
    @Mock
    View view;

    /**
     * mocked volley requester
     */
    @Mock
    VolleyRequester volleyRequester;

    /**
     * initialize variables and mocks
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        postPresenter = new PostPresenter(view);
        postPresenter.setVolleyRequester(volleyRequester);
    }

    /**
     * test that loading the data works as expected
     */
    @Test
    public void loadDataTest() {
        String postURL = "post url";
        String userURL = "user url";
        String imageURL = "image url";
        int likeUserID = 3;
        postPresenter.loadData(postURL, userURL, imageURL, likeUserID);

        assertEquals(postURL, postPresenter.getPostURL());
        assertEquals(userURL, postPresenter.getUserURL());
        assertEquals(imageURL, postPresenter.getImageURL());
        assertEquals(likeUserID, postPresenter.getLikeUserID());
        assertEquals(0, postPresenter.getLikes());
        assertFalse(postPresenter.isLiked());
        assertNull(postPresenter.getAvatar());
        assertNull(postPresenter.getPostData());
        assertNull(postPresenter.getUserData());
    }

    /**
     * test that liking a post works
     */
    @Test
    public void likePostTest() {
        when(view.findViewById(anyInt())).thenReturn(mock(Button.class));

        assertEquals(0, postPresenter.getLikes());
        assertFalse(postPresenter.isLiked());

        postPresenter.likePost(null);

        assertEquals(1, postPresenter.getLikes());
        assertTrue(postPresenter.isLiked());
    }

    /**
     * test that setting and getting the user data works
     *
     * @throws JSONException
     */
    @Test
    public void userDataTest() throws JSONException {
        when(view.findViewById(anyInt())).thenReturn(mock(TextView.class));

        assertNull(postPresenter.getUserData());

        JSONObject userData = new JSONObject();
        userData.put("username", "user name");
        userData.put("firstname", "first name");
        userData.put("lastname", "last name");

        postPresenter.setUserData(userData);

        assertEquals(userData, postPresenter.getUserData());
    }

    /**
     * test loading like data
     *
     * @throws JSONException
     */
    @Test
    public void likeDataTest() throws JSONException {
        when(view.findViewById(anyInt())).thenReturn(mock(Button.class));

        assertEquals(0, postPresenter.getLikes());
        assertFalse(postPresenter.isLiked());

        ArrayList<Integer> likes = new ArrayList<>();
        likes.add(1);
        likes.add(3);
        likes.add(5);
        JSONArray array = new JSONArray(likes);
        JSONObject postData = new JSONObject();
        postData.put("likes", array);
        String postURL = "post url";
        String userURL = "user url";
        String imageURL = "image url";
        int likeUserID = 3;

        postPresenter.loadData(postURL, userURL, imageURL, likeUserID);
        postPresenter.loadLikeData(postData);

        assertEquals(3, postPresenter.getLikes());
        assertTrue(postPresenter.isLiked());
    }

    /**
     * test setting and getting the post data
     *
     * @throws JSONException
     */
    @Test
    public void postDataTest() throws JSONException {
        when(view.findViewById(anyInt())).thenReturn(mock(TextView.class));

        JSONObject postData = new JSONObject();
        postData.put("creator", 4);
        postData.put("content", "post body");
        postData.put("dateCreated", "date format");

        String postURL = "post url";
        String userURL = "user url";
        String imageURL = "image url";
        int likeUserID = 3;
        postPresenter.loadData(postURL, userURL, imageURL, likeUserID);
        postPresenter.setPostData(postData);

        assertEquals(postData, postPresenter.getPostData());
    }

    /**
     * test loading comments data
     *
     * @throws JSONException
     */
    @Test
    public void commentsDataTest() throws JSONException {
        when(view.findViewById(anyInt())).thenReturn(mock(Button.class));

        JSONObject postData = new JSONObject();
        postData.put("comments", new JSONArray(new int[]{1, 2, 3}));

        postPresenter.loadCommentsData(postData);
    }

    /**
     * test setting avatar
     */
    @Test
    public void setAvatarTest() {
        when(view.findViewById(anyInt())).thenReturn(mock(ImageView.class));

        postPresenter.setAvatar(null);

        assertNull(postPresenter.getAvatar());
    }

}
