package coms309.settings;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SettingsRepository<T extends Settings> extends CrudRepository<T, Integer>  {  } 

