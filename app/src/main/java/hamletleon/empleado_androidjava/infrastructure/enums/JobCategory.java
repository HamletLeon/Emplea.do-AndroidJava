package hamletleon.empleado_androidjava.infrastructure.enums;

import android.support.annotation.StringDef;

import java.util.Arrays;
import java.util.List;

import static hamletleon.empleado_androidjava.infrastructure.enums.JobCategory.MOBILE_DEVELOPMENT;
import static hamletleon.empleado_androidjava.infrastructure.enums.JobCategory.NETWORKING;
import static hamletleon.empleado_androidjava.infrastructure.enums.JobCategory.NONE;
import static hamletleon.empleado_androidjava.infrastructure.enums.JobCategory.SOFTWARE_DEVELOPMENT;
import static hamletleon.empleado_androidjava.infrastructure.enums.JobCategory.SYSTEM_ADMINISTRATOR;
import static hamletleon.empleado_androidjava.infrastructure.enums.JobCategory.WEB_DEVELOPMENT;

/**
 * Created by hsantana on 11/11/2017.
 */

@StringDef({NONE, SOFTWARE_DEVELOPMENT, WEB_DEVELOPMENT, MOBILE_DEVELOPMENT, NETWORKING, SYSTEM_ADMINISTRATOR})
public @interface JobCategory {
    String NONE = "None";
    String SOFTWARE_DEVELOPMENT = "SoftwareDevelopment";
    String WEB_DEVELOPMENT = "WebDevelopment";
    String MOBILE_DEVELOPMENT = "MobileDevelopment";
    String NETWORKING = "Networking";
    String SYSTEM_ADMINISTRATOR = "SystemAdministrator";

    class Actions {
        public static String getDescription(@JobCategory String category) {
            switch (category) {
                case SOFTWARE_DEVELOPMENT:
                    return "Desarrollo de Software";
                case WEB_DEVELOPMENT:
                    return "Desarrollo Web";
                case MOBILE_DEVELOPMENT:
                    return "Desarrollo Movil";
                case NETWORKING:
                    return "Redes y Telecomunicaciones";
                case SYSTEM_ADMINISTRATOR:
                    return "Administrador de Sistemas";
            }
            return NONE;
        }

        public static List<String> getList() {
            return Arrays.asList(NONE, SOFTWARE_DEVELOPMENT, WEB_DEVELOPMENT, MOBILE_DEVELOPMENT, NETWORKING, SYSTEM_ADMINISTRATOR);
        }

        public static List<String> getListDescriptions() {
            return Arrays.asList(getDescription(NONE), getDescription(SOFTWARE_DEVELOPMENT), getDescription(WEB_DEVELOPMENT), getDescription(MOBILE_DEVELOPMENT),
                    getDescription(NETWORKING), getDescription(SYSTEM_ADMINISTRATOR));
        }
    }
}
