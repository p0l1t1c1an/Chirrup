package coms309.settings;

import javax.transaction.Transactional;

@Transactional
public interface StandardRepository extends SettingsRepository<StandardSettings>  {  } 

