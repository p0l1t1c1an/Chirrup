package coms309.settings;

import javax.transaction.Transactional;

@Transactional
public interface UserSettingsRepository extends SettingsRepository<UserSettings>  {  } 

