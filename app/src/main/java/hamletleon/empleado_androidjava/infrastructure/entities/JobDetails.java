package hamletleon.empleado_androidjava.infrastructure.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hsantana on 28/11/2017.
 */

public class JobDetails implements Parcelable {
    public static final Creator<JobDetails> CREATOR = new Creator<JobDetails>() {
        @Override
        public JobDetails createFromParcel(Parcel in) {
            return new JobDetails(in);
        }

        @Override
        public JobDetails[] newArray(int size) {
            return new JobDetails[size];
        }
    };
    public String jobTitle;
    public String jobLocation;
    public String jobCategory;
    public String jobDetails;
    public String jobContacEmail;
    public String companyName;
    public String companyWebsite;
    public String companyLogo;

    public JobDetails() {
    }

    private JobDetails(Parcel in) {
        jobTitle = in.readString();
        jobLocation = in.readString();
        jobCategory = in.readString();
        jobDetails = in.readString();
        jobContacEmail = in.readString();
        companyName = in.readString();
        companyWebsite = in.readString();
        companyLogo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(jobTitle);
        dest.writeString(jobLocation);
        dest.writeString(jobCategory);
        dest.writeString(jobDetails);
        dest.writeString(jobContacEmail);
        dest.writeString(companyName);
        dest.writeString(companyWebsite);
        dest.writeString(companyLogo);
    }
}
