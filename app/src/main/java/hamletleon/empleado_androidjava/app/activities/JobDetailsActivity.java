package hamletleon.empleado_androidjava.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.xml.sax.XMLReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hamletleon.empleado_androidjava.R;
import hamletleon.empleado_androidjava.app.shared.IApiService;
import hamletleon.empleado_androidjava.infrastructure.api.ApiUtils;
import hamletleon.empleado_androidjava.infrastructure.entities.Job;
import hamletleon.empleado_androidjava.infrastructure.entities.JobDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static hamletleon.empleado_androidjava.infrastructure.utils.tools.SendEmailIntent;

public class JobDetailsActivity extends AppCompatActivity {
    @BindView(R.id.scrollView)
    View mScrollView;
    @BindView(R.id.progress)
    View mProgress;

    // Obsolete Design
    @BindView(R.id.oldView)
    View mOldView;
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

    // New Design
    @BindView(R.id.newView)
    View mNewView;
    @BindView(R.id.companyLogo)
    AppCompatImageView mCompanyLogo;
    @BindView(R.id.companyName)
    TextView mCompanyName;
    @BindView(R.id.companyLocation)
    TextView mCompanyLocation;
    @BindView(R.id.companyWebsite)
    TextView mCompanyWebsite;
    @BindView(R.id.jobTitleNew)
    TextView mJobTitleNew;
    @BindView(R.id.jobCategoryNew)
    TextView mJobCategoryNew;
    @BindView(R.id.jobDetails)
    TextView mJobDetailsTextView;
    @BindView(R.id.jobContactEmail)
    TextView mJobContactEmail;

    private IApiService mApiService;
    private Job mJob;
    private JobDetails mJobDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        if (intent != null && intent.getExtras() != null && intent.getExtras().containsKey(Job.class.getSimpleName())) {
            mJob = intent.getExtras().getParcelable(Job.class.getSimpleName());
            if (mJob == null) return;
        } else {
            Toast.makeText(this, "FATAL ERROR!", Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService = ApiUtils.getApiService();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setTitle("Detalles de puesto");

        if (mJob.JobURI != null) {
            requestJobDetails(mJob.JobURI);
        } else {
            setOldView();
        }
    }

    private void setOldView() {
        mJobTitle.setText(mJob.jobTitle);
        mJobType.setText(mJob.jobType);
        mJobLocation.setText(mJob.jobLocation);
        mJobCompany.setText(mJob.jobCompany);
        mJobCategory.setText(mJob.jobCategory);
        mJobDate.setText(mJob.jobDate);
        mProgress.setVisibility(View.GONE);
        mOldView.setVisibility(View.VISIBLE);
    }

    private void requestJobDetails(String jobUri) {
        mApiService.getJobsDetails(jobUri.replaceFirst("/", "")).enqueue(new Callback<JobDetails>() {
            @Override
            public void onResponse(Call<JobDetails> call, Response<JobDetails> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mJobDetails = response.body();
                    setNewView();
                } else {
                    setOldView();
                    Toast.makeText(JobDetailsActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JobDetails> call, Throwable t) {
                setOldView();
                Toast.makeText(JobDetailsActivity.this, "Error -> " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setNewView() {
        mScrollView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        if (mJobDetails.companyLogo != null) {
            String imgUrl;
            if (mJobDetails.companyLogo.contains("http") || mJobDetails.companyLogo.contains("www."))
                imgUrl = mJobDetails.companyLogo.trim();
            else imgUrl = "http://www.emplea.do" + mJobDetails.companyLogo.trim();
            Picasso.with(JobDetailsActivity.this).load(imgUrl).into(mCompanyLogo);
        }

        mCompanyName.setText(mJobDetails.companyName.trim());
        mCompanyLocation.setText(mJobDetails.jobLocation.trim());

        if (mJobDetails.companyWebsite != null)
            mCompanyWebsite.setText(mJobDetails.companyWebsite.trim());
        else mCompanyWebsite.setVisibility(View.GONE);
        if (mJobDetails.jobTitle != null)
            mJobTitleNew.setText(mJobDetails.jobTitle.trim());
        else mJobTitleNew.setText(R.string.notSpecified);
        if (mJobDetails.jobCategory != null)
            mJobCategoryNew.setText(mJobDetails.jobCategory.trim());
        else mJobCategoryNew.setText(R.string.notSpecified);
        if (mJobDetails.jobDetails != null)
            mJobDetailsTextView.setText(Html.fromHtml(mJobDetails.jobDetails.trim(), null, new UlTagHandler()));
        else mJobDetailsTextView.setText(R.string.notSpecified);
        if (mJobDetails.jobContacEmail != null)
            mJobContactEmail.setText(String.format(getString(R.string.contactEmail), mJobDetails.jobContacEmail.trim()));
        else mJobContactEmail.setText(R.string.notContactEmail);

        mProgress.setVisibility(View.GONE);
        mNewView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @OnClick(R.id.jobContactLayout)
    public void OnContactLayoutClicked() {
        SendEmailIntent(JobDetailsActivity.this, mJobDetails.jobContacEmail, "CV Puesto - " + mJobDetails.jobTitle,
                "Saludos,\nEstoy interesado en el puesto de trabajo (" + mJobDetails.jobTitle + ")." +
                        "\n\nLe adjunto mi CV para que evalue si soy un candidato pertinente para el puesto." +
                        "\n\nGracias anteladas por su atención.\nQue tenga buen resto del día.");
    }

    public class UlTagHandler implements Html.TagHandler {
        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
            if (tag.equals("ul") && !opening) output.append("\n");
            if (tag.equals("li") && opening) output.append("\n•\t");
        }
    }
}