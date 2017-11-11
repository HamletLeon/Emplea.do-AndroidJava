package hamletleon.empleado_androidjava.infrastructure.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import hamletleon.empleado_androidjava.infrastructure.dtos.Criteria;
import hamletleon.empleado_androidjava.infrastructure.viewHolders.GenericViewHolder;

/**
 * Created by hsantana on 26/9/2017.
 * Adaptador de listas generico con diseño de Google Materials
 */

public abstract class GenericAdapter<E, C extends Criteria> extends RecyclerView.Adapter<GenericViewHolder<E>> {
    List<E> mItems;
    C mCriteria;

    public GenericAdapter(List<E> list, C criteria) {
        mItems = list;
        mCriteria = criteria;
    }

    @Override
    public abstract GenericViewHolder<E> onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(GenericViewHolder<E> holder, int position) {
        holder.bindData(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public C getCriteria() {
        return mCriteria;
    }

    public List<E> getList() {
        return mItems;
    }
}
