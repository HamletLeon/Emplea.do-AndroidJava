package hamletleon.empleado_androidjava.infrastructure.api;

import hamletleon.empleado_androidjava.app.shared.IApiService;
import hamletleon.empleado_androidjava.infrastructure.utils.requestUtils.RetrofitClient;

/**
 * Created by hsantana on 5/25/2017.
 * Requests manager
 */

public class ApiUtils {
    private static final String API_BASE_URL = "https://api.digidev.do/empleado/";

    public static IApiService getApiService() {
        return RetrofitClient.getClient(API_BASE_URL).create(IApiService.class);
    }
}