package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeWork {
    @JsonProperty("id")
    int id;

    @JsonProperty("status")
    String status;

    @JsonProperty("score")
    String score;

    @JsonProperty("updated_at")
    String updated_at;

    @JsonProperty("last_payout_date")
    String last_payout_date;

    @JsonProperty("last_solution_submit")
    String last_solution_submit;

    @JsonProperty("last_reviewed_at")
    String last_reviewed_at;

    @JsonProperty("task")
    JsonNode task;

    @JsonProperty("lesson_task")
    JsonNode lesson_task;

    @JsonProperty("user")
    JsonNode user;

    @JsonProperty("group_users")
    JsonNode group_users;

    @JsonProperty("program")
    JsonNode program;

    @JsonProperty("solutions")
    JsonNode solutions;

    @JsonProperty("reviewer")
    JsonNode reviewer;

    @JsonProperty("lesson_task_experts")
    JsonNode lesson_task_experts;

    @Override
    public String toString() {
        return "HomeWork{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", score='" + score + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", last_payout_date='" + last_payout_date + '\'' +
                ", last_solution_submit='" + last_solution_submit + '\'' +
                ", last_reviewed_at='" + last_reviewed_at + '\'' +
                ", task=" + task +
                ", lesson_task=" + lesson_task +
                ", user=" + user +
                ", group_users=" + group_users +
                ", program=" + program +
                ", solutions=" + solutions +
                ", reviewer=" + reviewer +
                ", lesson_task_experts=" + lesson_task_experts +
                '}';
    }
}
