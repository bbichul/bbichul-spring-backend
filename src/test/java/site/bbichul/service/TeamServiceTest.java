package site.bbichul.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.bbichul.dto.TeamRequestDto;
import site.bbichul.models.Team;
import site.bbichul.models.User;
import site.bbichul.repository.TeamRepository;
import site.bbichul.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {
    @Mock
    TeamRepository teamRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    TeamService teamService;

    @Test
    @DisplayName("어떤 팀에 소속되어 있는 경우")
    void checkTeam_Normal() {
        //given
        Long teamId = 1L;
        String teamname = "checkTeamTest";

        TeamRequestDto teamRequestDto = new TeamRequestDto();
        teamRequestDto.setId(teamId);
        teamRequestDto.setTeamname(teamname);
        Team team = new Team(teamRequestDto);

        User user = new User();
        user.setTeam(team);

        //when
        String resultCheckTeam = teamService.checkTeam(user);

        //then
        assertEquals(resultCheckTeam, teamname);
    }

    @Test
    @DisplayName("팀 만들기 성공한 경우")
    void createTeam_Normal() {
        //given
        String teamname = "testname";

        TeamRequestDto teamRequestDto = new TeamRequestDto();
        teamRequestDto.setTeamname(teamname);

        User user = new User();
        Team team = new Team();
        user.setTeam(team);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        given(teamRepository.findByTeamname(teamRequestDto.getTeamname())).willReturn(Optional.ofNullable(null));

        //when
        String resultCreateTeam = teamService.createTeam(teamRequestDto, user);


        //then
        verify(userRepository, times(1))
                .save(captor.capture());
        User result = captor.getValue();

        assertEquals(resultCreateTeam, "testname");
        assertNotNull(result.getTeam());
    }

    @Test
    @DisplayName("팀명 중복확인 통과못한 경우")
    void checkName_Normal() {

        //given
        String teamname = "test";
        TeamRequestDto teamRequestDto = new TeamRequestDto();
        teamRequestDto.setTeamname(teamname);

        Team team = new Team();

        given(teamRepository.findByTeamname(teamRequestDto.getTeamname())).willReturn(Optional.of(team));

        //when
        String resultCheckName = teamService.checkName(teamRequestDto);

        //then
        assertEquals(resultCheckName, "중복되는 팀 이름입니다. 다시 입력해주세요.");
    }
}