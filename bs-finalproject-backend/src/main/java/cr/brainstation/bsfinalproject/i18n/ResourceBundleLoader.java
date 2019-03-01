package cr.brainstation.bsfinalproject.i18n;

import java.util.ResourceBundle;

/**
 * Class with static methods used for retrieving a ResourceBundle of the default locale.
 */
public class ResourceBundleLoader {

    /**
     * Gets the default resource bundle of the application.
     * @return the resource bundle of the default locale.
     */
    public static ResourceBundle getDefaultBundle() {
        return ResourceBundle.getBundle("cr.brainstation.bsfinalproject.i18n.Messages");
    }

}
