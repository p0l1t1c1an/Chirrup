package settings;

import org.springframework.data.repository.CrudRepository;

public interface SettingsRepository<T extends Settings> extends CrudRepository<T, Integer>  {  } 

