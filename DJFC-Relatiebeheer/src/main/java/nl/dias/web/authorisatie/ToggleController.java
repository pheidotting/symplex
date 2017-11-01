package nl.dias.web.authorisatie;

import nl.dias.domein.features.MyFeatures;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.togglz.core.Feature;
import org.togglz.core.context.FeatureContext;
import org.togglz.core.repository.FeatureState;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("togglz")
@Controller
public class ToggleController {
    @RequestMapping(method = RequestMethod.GET, value = "/toggle", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void toggle(@QueryParam("feature") String feature, @QueryParam("toggle") boolean toggle) {
        checkAdmin();

        FeatureContext.getFeatureManager().setFeatureState(new FeatureState(MyFeatures.valueOf(feature), toggle));
        FeatureContext.clearCache();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/toggles", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Map<String, Boolean> toggles() {
        return getToggles(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/toggles/{toggle}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public boolean toggles(@PathVariable("toggle") String toggle) {
        return getToggles(toggle).get(toggle);
    }

    private Map<String, Boolean> getToggles(String toggle) {
        Map<String, Boolean> result = new HashMap<>();

        for (Feature feature : MyFeatures.values()) {
            if (toggle == null || toggle.equals(feature.name())) {
                result.put(feature.name(), MyFeatures.valueOf(feature.name()).isActive());
            }
        }

        return result;
    }

    private void checkAdmin() {
        if (!FeatureContext.getFeatureManager().getCurrentFeatureUser().isFeatureAdmin()) {
            throw new UnauthorizesdAccessException();
        }
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public class UnauthorizesdAccessException extends RuntimeException {

    }
}
