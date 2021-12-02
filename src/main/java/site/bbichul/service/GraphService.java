package site.bbichul.service;

import com.sun.tools.corba.se.idl.constExpr.Times;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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
import java.util.*;

@RequiredArgsConstructor
@Service
public class GraphService {

    private final TimeRepository timeRepository;

    @Transactional
    public Map<String, Object> drawLineGraph(GraphRequestDto graphRequestDto, User user) {
        int year = graphRequestDto.getYear();
        int month = graphRequestDto.getMonth();

        List<Time> time = timeRepository.findAllByUserIdAndYearAndMonthOrderByDayDesc(user.getId(),year, month);


        Calendar cal = Calendar.getInstance();

        cal.set(year, month - 1, 1);

        int lastDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        ArrayList day_list = new ArrayList();
        ArrayList day_time_list = new ArrayList();

        for (int i = 0; i <= lastDayOfMonth; i++) {
            day_list.add(i);
            day_time_list.add(0);
        }


        for (Time day : time) {
            day_time_list.set(day.getDay(), day.getStudy_time());
        }


        Map<String, Object> map = new HashMap();	//<키 자료형, 값 자료형>
        map.put("day_list", day_list);
        map.put("day_time_list", day_time_list);

        return map;
    }

}
