package site.bbichul.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTaskTest {
    @Nested
    @DisplayName("TeamTask 객체 만들기")
    class CreateTeamTask {
        Long id;
        String teamname;

        @BeforeEach
        void setup() {
            id = 1L;
            teamname = "버스타조";
        }

        @Test
        @DisplayName("normal")
        void createNormal() {
            Team team = new Team();
            team.setId(id);
            team.setTeamname(teamname);

            assertNotNull(team.getId());
            assertEquals(teamname, team.getTeamname());

        }
    }
}