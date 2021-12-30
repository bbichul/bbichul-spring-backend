package site.bbichul.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.bbichul.dto.TeamRequestDto;
import site.bbichul.dto.TeamTaskRequestDto;
import site.bbichul.dto.TeamProgressbarResponseDto;
import site.bbichul.models.Team;
import site.bbichul.models.TeamTask;
import site.bbichul.models.User;
import site.bbichul.repository.TeamRepository;
import site.bbichul.repository.TeamTaskRepository;
import site.bbichul.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamTaskRepository teamTaskRepository;

    public Object checkTeam(User user) {
        if (user.getTeam() == null) {
            return false;
        }

        return user.getTeam();
    }

    @Transactional
    public Team createTeam(TeamRequestDto teamRequestDto, User user) {
        String teamname = teamRequestDto.getTeamname();

        Optional<Team> found = teamRepository.findByTeamname(teamname);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 팀명이 존재합니다.");
        }

        Team team = new Team(teamname);
        teamRepository.save(team);

        user.setTeam(team);
        userRepository.save(user);
        return team;
    }

    public TeamTask addTask(TeamTaskRequestDto teamTaskRequestDto) {
        TeamTask teamTask = new TeamTask(teamTaskRequestDto);
        Optional<Team> team = teamRepository.findById(teamTaskRequestDto.getTeamId());
        team.orElseThrow(() -> new IllegalArgumentException("소속팀을 찾을 수 없습니다."));

        teamTask.setDone(false);
        teamTask.setTeam(team.get());
        teamTaskRepository.save(teamTask);

        return teamTask;
    }

    public List<TeamTask> showTask(Long teamId) {
        List<TeamTask> teamTask = teamTaskRepository.findAllByTeamId(teamId);
        return teamTask;
    }

    public void deleteTask(TeamTaskRequestDto teamTaskRequestDto) {
        Long id = teamTaskRequestDto.getId();
        teamTaskRepository.deleteById(id);
    }

    @Transactional
    public void changeTask(TeamTaskRequestDto teamTaskRequestDto) {
        Long id = teamTaskRequestDto.getId();
        Optional<TeamTask> teamTask = teamTaskRepository.findById(id);
        if (teamTask.get().getDone() == false) {
            teamTask.get().setDone(true);
        }
        else {
            teamTask.get().setDone(false);
        }
    }

    public List<User> checkStatus(Long teamId) {
        List<User> users = userRepository.findAllByTeamId(teamId);
        return users;
    }

    public Team signupTeam(TeamRequestDto teamRequestDto, User user) {
        String teamname = teamRequestDto.getTeamname();

        Optional<Team> found = teamRepository.findByTeamname(teamname);
        if (found.isPresent()) {
            user.setTeam(found.get());
            userRepository.save(user);
        }
        else {
            throw new IllegalArgumentException("존재하지 않는 팀입니다. 팀 이름을 확인해주세요.");
        }
        return user.getTeam();
    }

    public Boolean checkName(TeamRequestDto teamRequestDto) {
        String teamname = teamRequestDto.getTeamname();

        Optional<Team> result = teamRepository.findByTeamname(teamname);
        if (result.isPresent()) {
            throw new IllegalArgumentException("중복되는 팀 이름입니다. 다시 입력해주세요.");
        }

        return true;
    }

    public TeamProgressbarResponseDto getTeamProgressbar(Long teamId, TeamProgressbarResponseDto teamProgressbarResponseDto) {
        Long doneCount = teamTaskRepository.countByTeamIdAndDone(teamId, true);
        Long notDoneCount = teamTaskRepository.countByTeamIdAndDone(teamId, false);
        Integer total = (int) (doneCount + notDoneCount);
        Integer percent = (int) Math.round(((double) doneCount / total) * 100);

        if (total == 0) {
            teamProgressbarResponseDto.setDoneCount(0L);
            teamProgressbarResponseDto.setPercent(0);
        } else {
            teamProgressbarResponseDto.setDoneCount(doneCount);
            teamProgressbarResponseDto.setPercent(percent);
        }

        return teamProgressbarResponseDto;
    }

    @Transactional
    public void updateTask(TeamTaskRequestDto teamTaskRequestDto) {
        Long id = teamTaskRequestDto.getId();

        Optional<TeamTask> teamtask = teamTaskRepository.findById(id);
        teamtask.orElseThrow(() -> new IllegalArgumentException("해당 task를 찾을 수 없습니다."));

        teamtask.get().taskUpdate(teamTaskRequestDto);
    }
}
