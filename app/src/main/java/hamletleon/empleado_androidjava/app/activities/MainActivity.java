package hamletleon.empleado_androidjava.app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hamletleon.empleado_androidjava.R;
import hamletleon.empleado_androidjava.app.adapters.JobsAdapter;
import hamletleon.empleado_androidjava.app.shared.IApiService;
import hamletleon.empleado_androidjava.infrastructure.api.ApiUtils;
import hamletleon.empleado_androidjava.infrastructure.dtos.JobCriteria;
import hamletleon.empleado_androidjava.infrastructure.entities.Job;
import hamletleon.empleado_androidjava.infrastructure.utils.EndlessRecyclerViewScrollListener;
import hamletleon.empleado_androidjava.infrastructure.utils.requestUtils.CallUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    private JobsAdapter mAdapter;
    private IApiService mApiService;
    private JobCriteria mCriteria = new JobCriteria();
    private boolean endPageReached;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mApiService = ApiUtils.getApiService();
        requestJobs();
    }

    private void requestJobs() {
        if (endPageReached) return;
        CallUtil.enqueueWithRetry(mApiService.getJobsByCriteria(mCriteria.getMap()), new Callback<List<Job>>() {
            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    setJobsList(response.body());
                } else {
                    Toast.makeText(MainActivity.this, "Error - " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Job>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error Fatal - " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setJobsList(List<Job> list) {
        if (list != null && list.size() != 0) {
            if (mAdapter == null) {
                mAdapter = new JobsAdapter(list, mCriteria);
                LinearLayoutManager manager = new LinearLayoutManager(this);
                mRecyclerView.setLayoutManager(manager);
                mRecyclerView.setAdapter(mAdapter);
                setScrollListener(manager);
            } else {
                mAdapter.onSearchPagination(list, mCriteria);
            }
            if (list.size() == mCriteria.PageSize) mCriteria.page++;
            else endPageReached = true;
        }
    }

    private void setScrollListener(final LinearLayoutManager manager) {
        EndlessRecyclerViewScrollListener mScrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (!endPageReached) {
                    mCriteria.page = page;
                    requestJobs();
                }
            }
        };
        assert mRecyclerView != null;
        mRecyclerView.addOnScrollListener(mScrollListener);
    }
}