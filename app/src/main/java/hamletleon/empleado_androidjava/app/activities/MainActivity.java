package hamletleon.empleado_androidjava.app.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hamletleon.empleado_androidjava.R;
import hamletleon.empleado_androidjava.app.adapters.JobsAdapter;
import hamletleon.empleado_androidjava.app.shared.IApiService;
import hamletleon.empleado_androidjava.infrastructure.api.ApiUtils;
import hamletleon.empleado_androidjava.infrastructure.dtos.JobCriteria;
import hamletleon.empleado_androidjava.infrastructure.entities.Job;
import hamletleon.empleado_androidjava.infrastructure.enums.JobCategory;
import hamletleon.empleado_androidjava.infrastructure.utils.EndlessRecyclerViewScrollListener;
import hamletleon.empleado_androidjava.infrastructure.utils.requestUtils.CallUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress)
    View mProgress;
    @BindView(R.id.spinner)
    Spinner mJobCategorySpinner;

    private JobsAdapter mAdapter;
    private IApiService mApiService;
    private JobCriteria mCriteria = new JobCriteria();

    private List<String> mJobCategoryList = JobCategory.Actions.getList();
    private List<String> mJobCategoryDescriptionList = JobCategory.Actions.getListDescriptions();

    private boolean endPageReached;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mApiService = ApiUtils.getApiService();
        if (savedInstanceState == null) requestJobs();
        else restorePreviousState(savedInstanceState);
        setFilter();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        outState.putInt("SAVED_SPINNER_STATE", mJobCategorySpinner.getVisibility());
        outState.putParcelable("SAVED_CRITERIA", mCriteria);
        outState.putParcelable("SAVED_RECYCLER", listState);
        outState.putParcelableArrayList("SAVED_LIST", new ArrayList<Parcelable>(mAdapter.getList()));
        super.onSaveInstanceState(outState);
    }

    private void restorePreviousState(Bundle savedInstanceState) {
        Parcelable listState = savedInstanceState.getParcelable("SAVED_RECYCLER");
        List<Job> list = savedInstanceState.getParcelableArrayList("SAVED_LIST");
        mCriteria = savedInstanceState.getParcelable("SAVED_CRITERIA");
        mJobCategorySpinner.setVisibility(savedInstanceState.getInt("SAVED_SPINNER_STATE", View.GONE));
        setJobsList(list);
        mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        if (mProgress.getVisibility() == View.VISIBLE) {
            mProgress.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void requestJobs() {
        if (endPageReached) return;
        CallUtil.enqueueWithRetry(mApiService.getJobsByCriteria(mCriteria.getMap()), new Callback<List<Job>>() {
            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    setJobsList(response.body());
                    if (mProgress.getVisibility() == View.VISIBLE) {
                        mProgress.setVisibility(View.INVISIBLE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
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

    private void setFilter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mJobCategoryDescriptionList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mJobCategorySpinner.setAdapter(adapter);
        mJobCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < mJobCategoryList.size()) {
                    String jobCategory = mJobCategoryList.get(position);
                    if (!mCriteria.JobCategory.equals(jobCategory)) {
                        mRecyclerView.setVisibility(View.INVISIBLE);
                        mProgress.setVisibility(View.VISIBLE);
                        mCriteria.JobCategory = jobCategory;
                        mCriteria.page = 1;
                        mAdapter.reset();
                        mAdapter = null;
                        requestJobs();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.filter:
                if (mJobCategorySpinner.getVisibility() == View.VISIBLE) {
                    mJobCategorySpinner.setVisibility(View.GONE);
                } else {
                    mJobCategorySpinner.setVisibility(View.VISIBLE);
                }
                break;
        }
        return true;
    }
}