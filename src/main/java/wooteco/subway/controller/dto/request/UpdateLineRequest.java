package wooteco.subway.controller.dto.request;

import javax.validation.constraints.NotNull;

public class UpdateLineRequest {

    @NotNull
    private String name;
    @NotNull
    private String color;

    public UpdateLineRequest(){
    }

    public UpdateLineRequest(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
