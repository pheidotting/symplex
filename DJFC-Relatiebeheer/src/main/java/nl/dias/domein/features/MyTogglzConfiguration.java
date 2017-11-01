package nl.dias.domein.features;

import org.springframework.stereotype.Component;
import org.togglz.core.Feature;
import org.togglz.core.manager.TogglzConfig;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.file.FileBasedStateRepository;
import org.togglz.core.user.FeatureUser;
import org.togglz.core.user.SimpleFeatureUser;
import org.togglz.core.user.UserProvider;
import org.togglz.servlet.util.HttpServletRequestHolder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Component
public class MyTogglzConfiguration implements TogglzConfig {

    public Class<? extends Feature> getFeatureClass() {
        return MyFeatures.class;
    }

    public StateRepository getStateRepository() {
        return new FileBasedStateRepository(new File("/tmp/features.properties"));
    }

    @Override
    public UserProvider getUserProvider() {
        return new UserProvider() {

            @Override
            public FeatureUser getCurrentUser() {
                HttpServletRequest request = HttpServletRequestHolder.get();

                String username = request.getHeader("username");
                boolean isAdmin = "admin".equals(username);

                return new SimpleFeatureUser(username, isAdmin);

            }
        };
    }
}
