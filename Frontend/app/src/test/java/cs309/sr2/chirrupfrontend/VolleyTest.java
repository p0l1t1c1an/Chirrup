package cs309.sr2.chirrupfrontend;

import com.android.volley.toolbox.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

        VolleyListener volleyListener = new VolleyListener() {
            @Override
            public void onArrayResponse(JSONArray response) {
                assertEquals(response, array);
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
                assertNotEquals(response, new JSONArray());
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

        VolleyListener volleyListener = new VolleyListener() {
            @Override
            public void onArrayResponse(JSONArray response) {
                assertEquals(response, array);
            }
        };

        VolleyRequester volleyRequester = mock(VolleyRequester.class);

        doAnswer((invocation) -> {
            volleyListener.onArrayResponse(array);
            return null;
        }).when(volleyRequester).setArray(anyString(), any(), anyInt());

        volleyRequester.setArray(anyString(), any(), anyInt());
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
                assertNotEquals(response.toString(), new JSONArray());
            }
        };

        VolleyRequester volleyRequester = mock(VolleyRequester.class);

        doAnswer((invocation) -> {
            volleyListener.onArrayResponse(array);
            return null;
        }).when(volleyRequester).setArray(anyString(), any(), anyInt());

        volleyRequester.setArray(anyString(), any(), anyInt());
    }

    /**
     * test that get object returns the right object
     */
    @Test
    public void getObjectTest1() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("field1", 1);
        object.put("field2", "test");
        object.put("field3", 12.45);

        VolleyListener volleyListener = new VolleyListener() {
            @Override
            public void onObjectResponse(JSONObject response) {
                assertEquals(response, object);
            }
        };

        VolleyRequester volleyRequester = mock(VolleyRequester.class);

        doAnswer((invocation) -> {
            volleyListener.onObjectResponse(object);
            return null;
        }).when(volleyRequester).getObject(anyString());

        volleyRequester.getObject(anyString());
    }

    /**
     * test that get object gets a different object
     */
    @Test
    public void getObjectTest2() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("field1", 1);
        object.put("field2", "test");
        object.put("field3", 12.45);

        VolleyListener volleyListener = new VolleyListener() {
            @Override
            public void onObjectResponse(JSONObject response) {
                assertNotEquals(response, new JSONObject());
            }
        };

        VolleyRequester volleyRequester = mock(VolleyRequester.class);

        doAnswer((invocation) -> {
            volleyListener.onObjectResponse(object);
            return null;
        }).when(volleyRequester).getObject(anyString());

        volleyRequester.getObject(anyString());
    }

    /**
     * test that set object returns the right object
     */
    @Test
    public void setObjectTest1() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("field1", 1);
        object.put("field2", "test");
        object.put("field3", 12.45);

        VolleyListener volleyListener = new VolleyListener() {
            @Override
            public void onObjectResponse(JSONObject response) {
                assertEquals(response, object);
            }
        };

        VolleyRequester volleyRequester = mock(VolleyRequester.class);

        doAnswer((invocation) -> {
            volleyListener.onObjectResponse(object);
            return null;
        }).when(volleyRequester).setObject(anyString(), any(), anyInt());

        volleyRequester.setObject(anyString(), any(), anyInt());
    }

    /**
     * test that set object gets a different object
     */
    @Test
    public void setObjectTest2() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("field1", 1);
        object.put("field2", "test");
        object.put("field3", 12.45);

        VolleyListener volleyListener = new VolleyListener() {
            @Override
            public void onObjectResponse(JSONObject response) {
                assertNotEquals(response, new JSONObject());
            }
        };

        VolleyRequester volleyRequester = mock(VolleyRequester.class);

        doAnswer((invocation) -> {
            volleyListener.onObjectResponse(object);
            return null;
        }).when(volleyRequester).setObject(anyString(), any(), anyInt());

        volleyRequester.setObject(anyString(), any(), anyInt());
    }

    /**
     * test that get image returns the right image
     */
    @Test
    public void getImageTest1() {
        ImageLoader.ImageContainer imageContainer = mock(ImageLoader.ImageContainer.class);

        VolleyListener volleyListener = new VolleyListener() {
            @Override
            public void onImageResponse(ImageLoader.ImageContainer response) {
                assertEquals(response, imageContainer);
            }
        };

        VolleyRequester volleyRequester = mock(VolleyRequester.class);

        doAnswer((invocation) -> {
            volleyListener.onImageResponse(imageContainer);
            return null;
        }).when(volleyRequester).getImage(anyString());

        volleyRequester.getImage(anyString());
    }

    /**
     * test that get image returns a different image
     */
    @Test
    public void getImageTest2() {
        ImageLoader.ImageContainer imageContainer = mock(ImageLoader.ImageContainer.class);

        VolleyListener volleyListener = new VolleyListener() {
            @Override
            public void onImageResponse(ImageLoader.ImageContainer response) {
                assertNotEquals(response, null);
            }
        };

        VolleyRequester volleyRequester = mock(VolleyRequester.class);

        doAnswer((invocation) -> {
            volleyListener.onImageResponse(imageContainer);
            return null;
        }).when(volleyRequester).getImage(anyString());

        volleyRequester.getImage(anyString());
    }

}
