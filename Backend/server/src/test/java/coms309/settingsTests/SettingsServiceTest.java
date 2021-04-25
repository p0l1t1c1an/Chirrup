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
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

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

import coms309.ServerApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class, 
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
		
        when(settingsRepo.findById(1)).thenReturn(Optional.of(new UserSettings(user1, true, 30, 11)));
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
                return s;
            });

        service.saveSettings(settings1);
        List<UserSettings> settingsList = service.getAllSettings();

        assertEquals(1, settingsList.size());
		verify(settingsRepo, times(1)).save(settings1);
	}

    // More Complex Mockito Test for the creation of a child account

    @Test
    public void createChildTest() {    
        User parent = new User(1, "email", "pass", "parent", "Anakin", "Skywalker", 1, "911", null, null, "bio");
        User child = new User(2, "email", "pass", "child", "Luke", "Skywalker", 1, "911", null, null, "bio");
        
        UserSettings parentSettings = new UserSettings(parent);
        parent.setSettings(parentSettings);

        when(settingsRepo.findById(1)).thenReturn(Optional.of(parentSettings)); 
        when(userRepo.getUserById(1)).thenReturn(parent);

        when(userRepo.saveAndFlush((User)any(User.class))).thenAnswer(x -> { return (User) x.getArgument(0); });
        when(settingsRepo.save((UserSettings)any(UserSettings.class)))
            .thenAnswer(x -> {return (UserSettings) x.getArgument(0); });

        service.createChildOfParent(1, child);

        assertEquals(User.PARENT, parent.getRole());
        assertEquals(User.CHILD, child.getRole());

        assertNotEquals(null, child.getSettings());
        
        assertEquals(1, parentSettings.getChildren().size());
        
        UserSettings childSettings = (UserSettings) child.getSettings();

        assertEquals(1, childSettings.getParents().size());
    }
}
