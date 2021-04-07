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

import cs309.sr2.chirrupfrontend.login.CreateNewProfile;
import cs309.sr2.chirrupfrontend.messaging.MessageThread;
import cs309.sr2.chirrupfrontend.messaging.SingleThread;
import cs309.sr2.chirrupfrontend.messaging.GroupThread;
import cs309.sr2.chirrupfrontend.messaging.MessageFragment;
import cs309.sr2.chirrupfrontend.settings.CurrentUserData;
import cs309.sr2.chirrupfrontend.settings.NewProfileData;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

@RunWith(MockitoJUnitRunner.class)
public class profileTests {
    @Mock
    private NewProfileData NewUser;

    @Test
    public void testCreationOfUser() {
        NewUser = mock(NewProfileData.class);

    }
}
