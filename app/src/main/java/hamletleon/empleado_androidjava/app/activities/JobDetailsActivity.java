package hamletleon.empleado_androidjava.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import hamletleon.empleado_androidjava.R;
import hamletleon.empleado_androidjava.infrastructure.entities.Job;

public class JobDetailsActivity extends AppCompatActivity {
    @BindView(R.id.jobTitle)
    TextView mJobTitle;
    @BindView(R.id.jobType)
    TextView mJobType;
    @BindView(R.id.jobLocation)
    TextView mJobLocation;
    @BindView(R.id.jobCompany)
    TextView mJobCompany;
    @BindView(R.id.jobCategory)
    TextView mJobCategory;
    @BindView(R.id.jobDate)
    TextView mJobDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        Job mJob;
        if (intent != null && intent.getExtras() != null && intent.getExtras().containsKey(Job.class.getSimpleName())) {
            mJob = intent.getExtras().getParcelable(Job.class.getSimpleName());
            if (mJob == null) return;
        } else {
            Toast.makeText(this, "FATAL ERROR!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setTitle("Detalles de puesto");

        mJobTitle.setText(mJob.jobTitle);
        mJobType.setText(mJob.jobType);
        mJobLocation.setText(mJob.jobLocation);
        mJobCompany.setText(mJob.jobCompany);
        mJobCategory.setText(mJob.jobCategory);
        mJobDate.setText(mJob.jobDate);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}