package coms309.settingsTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;

import coms309.user.User;
import coms309.user.UserRepository;

import coms309.settings.Settings;
import coms309.settings.UserSettings;

import coms309.settings.SettingsService;
import coms309.settings.UserSettingsRepository;

@RunWith(SpringRunner.class)
public class SettingsServiceTest { 

    @InjectMocks
	SettingsService service;

	@Mock
	UserSettingsRepository settingsRepo;

    @Mock
    UserRepository userRepo;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

    // Generic Testing of Service (Escentially Copied What Tyler Did)

	@Test
	public void getSettingsByIdTest() {
        //User to get id for the settings
        User user1 = new User(1, "email", "pass", "username", "first", "last", 1, "911", null, null, "bio");
		
        when(settingsRepo.findById(1).get()).thenReturn(new UserSettings(user1, true, 30, 11));
		UserSettings s = service.getSettingsById(1);

		assertEquals(1, s.getId().intValue());
        assertEquals(true, s.getDarkMode());
        assertEquals(30, s.getUpdateTime().intValue());
        assertEquals(11, s.getTextSize().intValue());
	}

    @Test
	public void getAllSettingsTest() {
        List<UserSettings> settings = new ArrayList<UserSettings>();

        UserSettings settings1 = new UserSettings();
        UserSettings settings2 = new UserSettings();
        UserSettings settings3 = new UserSettings();

        settings.add(settings1);
        settings.add(settings2);
        settings.add(settings3);

		when(settingsRepo.findAll()).thenReturn(settings);
        List<UserSettings> settingsList = service.getAllSettings();

        assertEquals(3, settingsList.size());
		verify(settingsRepo, times(1)).findAll();
	}

	@Test
	public void saveSettingsTest() {
        List<UserSettings> settings = new ArrayList<UserSettings>();
        UserSettings settings1 = new UserSettings();
        
        when(settingsRepo.findAll()).thenReturn(settings);
		when(settingsRepo.save((UserSettings)any(UserSettings.class)))
            .thenAnswer(x -> {
                UserSettings s = x.getArgument(0);
                settings.add(s);
                return null;
            });

        service.saveSettings(settings1);
        List<UserSettings> settingsList = service.getAllSettings();

        assertEquals(1, settingsList.size());
		verify(settingsRepo, times(1)).save(settings1);
	}

    @Test
	public void deleteSettingsTest() {
        List<UserSettings> settings = new ArrayList<UserSettings>();
        UserSettings settings1 = new UserSettings();
        
        settings.add(settings1);

		doNothing().when(settingsRepo).deleteById(1);
        doNothing().when(settingsRepo).save(settings1);
        when(settingsRepo.findAll()).thenReturn(settings);

        service.deleteSettingsById(1);
        List<UserSettings> settingsList = service.getAllSettings();

		verify(settingsRepo, times(1)).deleteById(1);
        assertEquals(0, settingsList.size());
	}

    // More Complex Mockito Test for the creation of a child account

    @Test
    public void createChildTest() {    
        User parent = new User(1, "email", "pass", "parent", "Anakin", "Skywalker", 1, "911", null, null, "bio");
        User child = new User(2, "email", "pass", "child", "Luke", "Skywalker", 1, "911", null, null, "bio");
        
        UserSettings parentSettings = new UserSettings(parent);
        parent.setSettings(parentSettings);

        when(settingsRepo.findById(1).get()).thenReturn(parentSettings); 
        when(userRepo.findById(1).get()).thenReturn(parent);
        
        doNothing().when(userRepo).save((User)any(User.class));
        doNothing().when(settingsRepo).save((UserSettings)any(UserSettings.class));

        service.createChildOfParent(1, child);

        assertEquals(User.PARENT, parent.getRole());
        assertEquals(User.CHILD, child.getRole());

        assertNotEquals(null, child.getSettings());
        
        assertEquals(1, parentSettings.getChildren().size());
        
        UserSettings childSettings = (UserSettings) child.getSettings();

        assertEquals(1, childSettings.getParents().size());
    }
}
