package hamletleon.empleado_androidjava.infrastructure.adapters;

import java.util.List;

import hamletleon.empleado_androidjava.infrastructure.dtos.JobCriteria;

/**
 * Created by hsantana on 26/9/2017.
 * Adaptador de listas generico con diseño de Google Materials
 */

// C extends of a PaginationParameter Entity ...
public abstract class PaginatedAdapter<E, C extends JobCriteria> extends GenericAdapter<E, C> {
    public boolean IS_FILTERED;
    private List<E> mItemsFiltered;
    private C mCriteriaFiltered;

    public PaginatedAdapter(List<E> list, C criteria) {
        super(list, criteria);
        mItemsFiltered = list;
        mCriteriaFiltered = criteria;
    }

    public void onSearchPagination(List<E> itemsFound, C criteria) {
        mItemsFiltered.addAll(itemsFound);
        mCriteriaFiltered = criteria;
        notifyDataSetChanged();
    }

    public void onSearchFound(List<E> found, C criteria) {
        mItemsFiltered = found;
        mCriteriaFiltered = criteria;
        IS_FILTERED = true;
        notifyDataSetChanged();
    }

    public void onFilteredReset() {
        mItemsFiltered = mItems;
        mCriteriaFiltered = mCriteria;
        IS_FILTERED = false;
        notifyDataSetChanged();
    }

    public void reset() {
        mItemsFiltered.clear();
        notifyDataSetChanged();
    }

    public C getCriteriaFiltered() {
        return mCriteriaFiltered;
    }

    public List<E> getItemsFiltered() {
        return mItemsFiltered;
    }

    public E getItemFiltered(int position) {
        return mItemsFiltered.get(position);
    }
}