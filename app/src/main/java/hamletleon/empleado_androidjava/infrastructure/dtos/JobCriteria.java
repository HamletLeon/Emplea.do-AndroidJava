package hamletleon.empleado_androidjava.infrastructure.dtos;

import android.os.Parcel;
import android.os.Parcelable;

import hamletleon.empleado_androidjava.infrastructure.enums.JobCategory;

import static hamletleon.empleado_androidjava.infrastructure.enums.JobCategory.NONE;

/**
 * Created by hsantana on 11/11/2017.
 */

public class JobCriteria extends Criteria implements Parcelable {
    public static final Creator<JobCriteria> CREATOR = new Creator<JobCriteria>() {
        @Override
        public JobCriteria createFromParcel(Parcel in) {
            return new JobCriteria(in);
        }

        @Override
        public JobCriteria[] newArray(int size) {
            return new JobCriteria[size];
        }
    };
    public int page = 1;
    public int PageSize = 10;
    public @JobCategory
    String JobCategory = NONE;

    public JobCriteria() {
    }

    protected JobCriteria(Parcel in) {
        page = in.readInt();
        PageSize = in.readInt();
        JobCategory = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeInt(PageSize);
        dest.writeString(JobCategory);
    }
}
