package cs309.sr2.chirrupfrontend;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import cs309.sr2.chirrupfrontend.post.PostFragment;
import cs309.sr2.chirrupfrontend.post.PostPresenter;

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
     * <p>
     * note: current class structure prevents me from completing this properly
     *
     * @throws JSONException
     */
    @Test
    public void addLikeTest() {
        final int[] likeCount = {0};
        final boolean[] liked = {false};

        when(postPresenter.getLikes()).thenReturn(likeCount[0]);
        when(postPresenter.isLiked()).thenReturn(liked[0]);
        doAnswer(invocation -> {

            if (liked[0]) {
                liked[0] = false;
                likeCount[0]--;
                when(postPresenter.getLikes()).thenReturn(likeCount[0]);
                when(postPresenter.isLiked()).thenReturn(liked[0]);
            } else {
                liked[0] = true;
                likeCount[0]++;
                when(postPresenter.getLikes()).thenReturn(likeCount[0]);
                when(postPresenter.isLiked()).thenReturn(liked[0]);
            }

            return null;
        }).when(postPresenter).likePost(anyString());

        assertFalse(postPresenter.isLiked());
        assertEquals(0, postPresenter.getLikes());

        postPresenter.likePost(anyString());

        assertTrue(postPresenter.isLiked());
        assertEquals(1, postPresenter.getLikes());
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
