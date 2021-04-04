package coms309.settings;

import javax.transaction.Transactional;

@Transactional
public interface ParentRepository extends SettingsRepository<ParentSettings>  {  } 

