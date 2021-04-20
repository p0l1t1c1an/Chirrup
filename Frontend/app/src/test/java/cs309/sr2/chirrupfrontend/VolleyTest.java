package cs309.sr2.chirrupfrontend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * class to test the volley classes
 *
 * @author Jeremy Noesen
 */
@RunWith(MockitoJUnitRunner.class)
public class VolleyTest {

    /**
     * test that get string returns the right string
     */
    @Test
    public void getStringTestPass() {
        String correct = "test string";

        VolleyListener volleyListener = new VolleyListener() {
            @Override
            public void onStringResponse(String response) {
                assertEquals(response, correct);
            }
        };

        VolleyRequester volleyRequester = mock(VolleyRequester.class);

        doAnswer((invocation) -> {
            volleyListener.onStringResponse(correct);
            return null;
        }).when(volleyRequester).getString(anyString());

        volleyRequester.getString(anyString());
    }

    /**
     * test that get string gets a different string
     */
    @Test
    public void getStringTestFail() {
        String correct = "test string";
        String wrong = "another string";

        VolleyListener volleyListener = new VolleyListener() {
            @Override
            public void onStringResponse(String response) {
                assertNotEquals(response, correct);
            }
        };

        VolleyRequester volleyRequester = mock(VolleyRequester.class);

        doAnswer((invocation) -> {
            volleyListener.onStringResponse(wrong);
            return null;
        }).when(volleyRequester).getString(anyString());

        volleyRequester.getString(anyString());
    }

}
