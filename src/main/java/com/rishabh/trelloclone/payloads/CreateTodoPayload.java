package com.rishabh.trelloclone.payloads;

import java.sql.Date;

public class CreateTodoPayload {

    private String mustBeCompletedBy;
    private String heading;
    private String description;



    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getMustBeCompletedBy() {
        return mustBeCompletedBy;
    }

    public void setMustBeCompletedBy(String mustBeCompletedBy) {
        this.mustBeCompletedBy = mustBeCompletedBy;
    }

    @Override
    public String toString() {
        return "CreateTodoPayload{" +
                "mustBeCompleted=" + mustBeCompletedBy +
                ", heading='" + heading + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
