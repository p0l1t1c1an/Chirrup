package coms309.serverpeople;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

/**
 * Controller used for linking a person to a server
 *
 * @author Tyler Green
 */

@RestController
public class ServerPeopleController {

    @Autowired
    ServerPeopleRepository serverPeopleRepository;

    // gets the server by the id specified in the request URL and returns the people within that server
    @RequestMapping(method = RequestMethod.GET, path = "/servers/{serverID}/people")
    public List<ServerPeople> findServerMembers(@PathVariable("serverID") int serverId) {

        List<ServerPeople> found = serverPeopleRepository.findByServerId(serverId);
        return found;
    }

    // gets all the serverpeople in the database and returns it in the form of a JSON
    @RequestMapping(method = RequestMethod.GET, path = "/serverpeople")
    public List<ServerPeople> getAllPerson() {
        
        List<ServerPeople> results = serverPeopleRepository.findAll();
        return results;
    }

    // gets a JSON request parses it into a serverperson object and enters it into the database
    @RequestMapping(method = RequestMethod.POST, path = "/servers/add")
    public String saveserverperson(ServerPeople serverPeople) {
        serverPeopleRepository.save(serverPeople);
        return "New person added. ID = " + serverPeople.getPersonId();
    }
}
