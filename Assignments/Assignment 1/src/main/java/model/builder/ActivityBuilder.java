package model.builder;

import model.Activity;

import java.time.LocalDate;
import java.util.Date;

public class ActivityBuilder {
    private final Activity activity;

    public ActivityBuilder() {
        this.activity = new Activity ();
    }

    public ActivityBuilder setId(Long id) {
        activity.setId ( id );
        return this;
    }
    public ActivityBuilder setIdUser(Long idUser) {
        activity.setIdUser ( idUser );
        return this;
    }
    public ActivityBuilder setAction(String action){
        activity.setAction ( action );
        return this;
    }

    public ActivityBuilder setDate(Date date) {
        activity.setDate ( date );
        return this;
    }
    public Activity build(){
        return activity;
    }

}
