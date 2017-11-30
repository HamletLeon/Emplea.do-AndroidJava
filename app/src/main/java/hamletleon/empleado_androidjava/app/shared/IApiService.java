package hamletleon.empleado_androidjava.app.shared;

import java.util.List;
import java.util.Map;

import hamletleon.empleado_androidjava.infrastructure.entities.Job;
import hamletleon.empleado_androidjava.infrastructure.entities.JobDetails;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by hsantana on 5/25/2017.
 * Api interface for requests - Empty Examples
 */

public interface IApiService {
    @GET("empleos.json")
    Call<List<Job>> getJobsByCriteria(@QueryMap Map<String, Object> criteria);

    @GET("{jobUri}.json")
    Call<JobDetails> getJobsDetails(@Path(value = "jobUri", encoded = true) String jobUri);
}
