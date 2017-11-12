package hamletleon.empleado_androidjava.app.viewHolders;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;

import com.squareup.picasso.Picasso;

import hamletleon.empleado_androidjava.R;
import hamletleon.empleado_androidjava.app.activities.JobDetailsActivity;
import hamletleon.empleado_androidjava.infrastructure.entities.Job;
import hamletleon.empleado_androidjava.infrastructure.viewHolders.GenericViewHolder;

import static hamletleon.empleado_androidjava.infrastructure.utils.tools.intentActivityActionWithMultipleExtras;

/**
 * Created by hsantana on 11/11/2017.
 */

public class JobViewHolder extends GenericViewHolder<Job> {
    public JobViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(Job entity) {
        super.bindData(entity);
        Picasso.with(mImage.getContext()).load(R.drawable.new_job).into(mImage);
        mTitleTextView.setText(entity.jobCompany);
        mSubtitleTextView.setText(entity.jobCategory);
        mOptionalSubtitleTextView.setText(entity.jobDate);
        mOptionalTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        mOptionalTitleTextView.setText(entity.jobType);
    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();
        intentActivityActionWithMultipleExtras(context, JobDetailsActivity.class, new Object[]{mEntity},
                new String[]{Job.class.getSimpleName()});
    }
}
