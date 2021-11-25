package site.bbichul.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.bbichul.dto.CalendarMemoDto;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
public class CalendarMemo extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String dateData;

    @Column
    private String contents;



    public CalendarMemo(CalendarMemoDto calendarMemoDto) {
        this.dateData = calendarMemoDto.getDateData();
        this.contents = calendarMemoDto.getContents();
    }

    public void updateMemo(CalendarMemoDto calendarMemoDto){
        this.dateData = calendarMemoDto.getDateData();
        this.contents = calendarMemoDto.getContents();
    }

}
