package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    @JsonProperty("homeworks")
    private List<HomeWork> homeworks;

    @JsonProperty("current_page")
    private int current_page;

    @JsonProperty("total_pages")
    private int total_pages;
}
