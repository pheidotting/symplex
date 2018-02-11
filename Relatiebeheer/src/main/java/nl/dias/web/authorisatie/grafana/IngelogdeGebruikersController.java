package nl.dias.web.authorisatie.grafana;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.core.MediaType;

import static com.google.common.collect.Lists.newArrayList;

@RequestMapping("/ingelogdeGebruiker")
@Controller
public class IngelogdeGebruikersController {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public String okido() {
        return "ok";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public String search() {
        return new Gson().toJson(newArrayList(1, 2, 3, 4));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/query", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public String query() {
        return new Gson().toJson(newArrayList(2, 3, 4, 5));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/annotations", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public String annotations() {
        return new Gson().toJson(newArrayList(3, 4, 5, 6));
    }
}
