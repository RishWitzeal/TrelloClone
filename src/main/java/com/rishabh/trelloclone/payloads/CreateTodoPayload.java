package com.rishabh.trelloclone.payloads;

public class CreateTodoPayload {

    private String status;
    private String heading;
    private String description;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    @Override
    public String toString() {
        return "CreateTodoPayload{" +
                "status='" + status + '\'' +
                ", heading='" + heading + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
