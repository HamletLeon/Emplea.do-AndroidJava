package hamletleon.empleado_androidjava.app.viewHolders;

import android.view.View;

import com.squareup.picasso.Picasso;

import hamletleon.empleado_androidjava.R;
import hamletleon.empleado_androidjava.infrastructure.entities.Job;
import hamletleon.empleado_androidjava.infrastructure.viewHolders.GenericViewHolder;

/**
 * Created by hsantana on 11/11/2017.
 */

public class JobViewHolder extends GenericViewHolder<Job> {
    public JobViewHolder(View itemView) {
        super(itemView);
        mOptionalTitleTextView.setVisibility(View.GONE);
    }

    @Override
    public void bindData(Job entity) {
        super.bindData(entity);
        Picasso.with(mImage.getContext()).load(R.drawable.new_job).into(mImage);
        mTitleTextView.setText(entity.jobCompany);
        mSubtitleTextView.setText(entity.jobCategory);
        mOptionalSubtitleTextView.setText(entity.jobDate);
    }
}
