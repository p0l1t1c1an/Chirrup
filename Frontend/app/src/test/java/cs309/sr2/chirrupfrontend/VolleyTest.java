package cs309.sr2.chirrupfrontend;

import org.json.JSONArray;
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
    public void getStringTest1() {
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
    public void getStringTest2() {
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

    /**
     * test that set string returns the right string
     */
    @Test
    public void setStringTest1() {
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
        }).when(volleyRequester).setString(anyString(), anyString(), anyInt());

        volleyRequester.setString(anyString(), anyString(), anyInt());
    }

    /**
     * test that set string gets a different string
     */
    @Test
    public void setStringTest2() {
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
        }).when(volleyRequester).setString(anyString(), anyString(), anyInt());

        volleyRequester.setString(anyString(), anyString(), anyInt());
    }

    /**
     * test that get array returns the right array
     */
    @Test
    public void getArrayTest1() {
        JSONArray array = new JSONArray();
        array.put("1");
        array.put("5");
        array.put("3252");

        String toString = array.toString();

        VolleyListener volleyListener = new VolleyListener() {
            @Override
            public void onArrayResponse(JSONArray response) {
                assertEquals(response.toString(), toString);
            }
        };

        VolleyRequester volleyRequester = mock(VolleyRequester.class);

        doAnswer((invocation) -> {
            volleyListener.onArrayResponse(array);
            return null;
        }).when(volleyRequester).getArray(anyString());

        volleyRequester.getArray(anyString());
    }

    /**
     * test that get array gets a different array
     */
    @Test
    public void getArrayTest2() {
        JSONArray array = new JSONArray();
        array.put("1");
        array.put("5");
        array.put("3252");

        VolleyListener volleyListener = new VolleyListener() {
            @Override
            public void onArrayResponse(JSONArray response) {
                assertNotEquals(response.toString(), "other string");
            }
        };

        VolleyRequester volleyRequester = mock(VolleyRequester.class);

        doAnswer((invocation) -> {
            volleyListener.onArrayResponse(array);
            return null;
        }).when(volleyRequester).getArray(anyString());

        volleyRequester.getArray(anyString());
    }

    /**
     * test that set array returns the right array
     */
    @Test
    public void setArrayTest1() {
        JSONArray array = new JSONArray();
        array.put("1");
        array.put("5");
        array.put("3252");

        String toString = array.toString();

        VolleyListener volleyListener = new VolleyListener() {
            @Override
            public void onArrayResponse(JSONArray response) {
                assertEquals(response.toString(), toString);
            }
        };

        VolleyRequester volleyRequester = mock(VolleyRequester.class);

        doAnswer((invocation) -> {
            volleyListener.onArrayResponse(array);
            return null;
        }).when(volleyRequester).setArray(anyString(), any(JSONArray.class), anyInt());

        volleyRequester.setArray(anyString(), any(JSONArray.class), anyInt());
    }

    /**
     * test that set array gets a different array
     */
    @Test
    public void setArrayTest2() {
        JSONArray array = new JSONArray();
        array.put("1");
        array.put("5");
        array.put("3252");

        VolleyListener volleyListener = new VolleyListener() {
            @Override
            public void onArrayResponse(JSONArray response) {
                assertNotEquals(response.toString(), "other string");
            }
        };

        VolleyRequester volleyRequester = mock(VolleyRequester.class);

        doAnswer((invocation) -> {
            volleyListener.onArrayResponse(array);
            return null;
        }).when(volleyRequester).setArray(anyString(), any(JSONArray.class), anyInt());

        volleyRequester.setArray(anyString(), any(JSONArray.class), anyInt());
    }

}
