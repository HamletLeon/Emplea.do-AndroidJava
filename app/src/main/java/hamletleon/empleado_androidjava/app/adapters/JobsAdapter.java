package hamletleon.empleado_androidjava.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import hamletleon.empleado_androidjava.R;
import hamletleon.empleado_androidjava.app.viewHolders.JobViewHolder;
import hamletleon.empleado_androidjava.infrastructure.adapters.PaginatedAdapter;
import hamletleon.empleado_androidjava.infrastructure.dtos.JobCriteria;
import hamletleon.empleado_androidjava.infrastructure.entities.Job;
import hamletleon.empleado_androidjava.infrastructure.viewHolders.GenericViewHolder;

/**
 * Created by hsantana on 11/11/2017.
 */

public class JobsAdapter extends PaginatedAdapter<Job, JobCriteria> {
    public JobsAdapter(List<Job> list, JobCriteria criteria) {
        super(list, criteria);
    }

    @Override
    public GenericViewHolder<Job> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dynamic_listitem, parent, false);
        return new JobViewHolder(view);
    }
}
