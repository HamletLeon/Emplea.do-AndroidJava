package hamletleon.empleado_androidjava.infrastructure.viewHolders;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hamletleon.empleado_androidjava.R;

/**
 * Created by hsantana on 25/9/2017.
 * ViewHolder generico de listas con diseño de Google Materials
 */

public abstract class GenericViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.itemCardView)
    protected CardView mItemCardView;
    @BindView(R.id.item)
    protected RelativeLayout mItemLayout;
    @BindView(R.id.image)
    protected AppCompatImageView mImage;
    @BindView(R.id.mainData)
    protected LinearLayout mMainDataLayout;
    @BindView(R.id.title)
    protected TextView mTitleTextView;
    @BindView(R.id.subtitle)
    protected TextView mSubtitleTextView;
    @BindView(R.id.optionalData)
    protected LinearLayout mOptionalDataLayout;
    @BindView(R.id.optionalTitle)
    protected TextView mOptionalTitleTextView;
    @BindView(R.id.optionalSubtitle)
    protected TextView mOptionalSubtitleTextView;

    protected T mEntity;

    public GenericViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void bindData(T entity) {
        mEntity = entity;
    }

    @Override
    public abstract void onClick(View view);
}
