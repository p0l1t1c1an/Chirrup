package cs309.sr2.chirrupfrontend;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import cs309.sr2.chirrupfrontend.messaging.MessageThread;
import cs309.sr2.chirrupfrontend.messaging.SingleThread;
import cs309.sr2.chirrupfrontend.messaging.GroupThread;
import cs309.sr2.chirrupfrontend.messaging.MessageFragment;
import cs309.sr2.chirrupfrontend.settings.CurrentUserData;
import cs309.sr2.chirrupfrontend.settings.NewProfileData;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

@RunWith(MockitoJUnitRunner.class)
public class messageTest {

    @Mock
    private SingleThread conversationTest;

    @Mock
    private NewProfileData currentTestData;

    private String[] conversation = new String[] {"Hello", "Hi", "How Are You?", "Good!"};

    private SingleThread conversationDefinedTest =
            new SingleThread(22, conversation);

    @Test
    public void testSingleThreadInitialization() {
        currentTestData = mock(NewProfileData.class);
        when(currentTestData.getID()).thenReturn(22);

        conversationDefinedTest.setUserID(currentTestData.getID());
        assertEquals(conversationDefinedTest.getUserID(), 22);
    }

    @Test
    public void testMessageReturn() {
        conversationTest = mock(SingleThread.class);
        when(conversationTest.getMessages()).thenReturn(conversation);

        //code that runs during onStringResponse when getting messages
        String display = "";
        for (int i = 0; i < conversation.length; ++i) {
            display += conversation[i];
            display += "\n";
        }

        assertFalse(display.equals(conversationTest.getMessages()));
    }

    @Test
    public void messageNotChanged() {
        conversationTest = mock(SingleThread.class);

        conversationTest.getMessages();
        verify(conversationTest, times(0)).setMessages(conversation);
    }

    @Test
    public void noMatchingIDs() {
        conversationTest = mock(SingleThread.class);
        currentTestData = mock(NewProfileData.class);
        when(conversationTest.getToID()).thenReturn(25);
        when(currentTestData.getID()).thenReturn(22);

        assertNotEquals(conversationTest.getToID(), currentTestData.getID());
    }
}
