package site.bbichul.service;

import com.sun.tools.corba.se.idl.constExpr.Times;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.bbichul.dto.GoalRequestDto;
import site.bbichul.dto.GraphRequestDto;
import site.bbichul.dto.TimeRequestDto;
import site.bbichul.models.Time;
import site.bbichul.models.User;
import site.bbichul.models.UserInfo;
import site.bbichul.repository.TimeRepository;

import javax.transaction.Transactional;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GraphService {

    private final TimeRepository timeRepository;

    @Transactional
    public Map<String, Object> drawLineGraph(GraphRequestDto graphRequestDto, User user) {
        int year = graphRequestDto.getYear();
        int month = graphRequestDto.getMonth();

        List<Time> time = timeRepository.findAllByUserIdAndYearAndMonth(user.getId(),year,month);

        int lastDayOfMonth =
        for (int i = 0; i <= lastDayOfMonth; i++) {

        }

        Map<String, Object> map = new HashMap();	//<키 자료형, 값 자료형>
        map.put("msg", time);

        return map;
    }

}
