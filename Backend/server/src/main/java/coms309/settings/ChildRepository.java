package coms309.settings;

import javax.transaction.Transactional;

@Transactional
public interface ChildRepository extends SettingsRepository<ChildSettings>  {  } 

