package hamletleon.empleado_androidjava.infrastructure.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static hamletleon.empleado_androidjava.infrastructure.utils.tools.getDate;

/**
 * Created by hsantana on 11/11/2017.
 */

public class Job implements Parcelable {
    public static final Creator<Job> CREATOR = new Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };
    private static transient SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM 'de' yyyy", Locale.ENGLISH);
    public String jobTitle;
    public String JobLink;
    public String JobURI;
    public String jobCompany;
    public String jobLocation;
    public String jobDate;
    public String jobCategory;
    public String jobType;

    protected Job(Parcel in) {
        jobTitle = in.readString();
        JobLink = in.readString();
        JobURI = in.readString();
        jobCompany = in.readString();
        jobLocation = in.readString();
        jobDate = in.readString();
        jobCategory = in.readString();
        jobType = in.readString();
    }

    public Date getJobDate() {
        return getDate(dateFormat, jobDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(jobTitle);
        dest.writeString(JobLink);
        dest.writeString(JobURI);
        dest.writeString(jobCompany);
        dest.writeString(jobLocation);
        dest.writeString(jobDate);
        dest.writeString(jobCategory);
        dest.writeString(jobType);
    }
}
