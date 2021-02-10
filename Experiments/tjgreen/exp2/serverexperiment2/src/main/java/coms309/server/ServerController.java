package coms309.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import coms309.people.PeopleRepository;
import coms309.serverpeople.ServerPeople;

import java.util.List;
import java.util.Optional;

/**
 * Controller used to create and read from te server table
 *
 * @author Tyler Green
 */

@RestController
public class ServerController {

    @Autowired
    ServerRepository serverRepository;

    // gets a JSON request parses it into a person object and enters it into the database
    @RequestMapping(method = RequestMethod.POST, path = "/server/new")
    public String saveperson(Server server) {
        serverRepository.save(server);
        return "New server "+ server.getName() + " Saved";
    }

    // gets all the people in the database and returns it in the form of a JSON
    @RequestMapping(method = RequestMethod.GET, path = "/servers")
    public List<Server> getAllPerson() {
        
        List<Server> results = serverRepository.findAll();
        return results;
    }

    // gets the server by the id specified in the request URL and returns it in JSON format
    @RequestMapping(method = RequestMethod.GET, path = "/server/{serverID}")
    public Optional<Server> findpersonById(@PathVariable("serverID") int id) {
        
        Optional<Server> results = serverRepository.findById(id);
        return results;
    }
}
