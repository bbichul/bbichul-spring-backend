package site.bbichul.utills;

import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import site.bbichul.models.Wise;
import site.bbichul.repository.WiseRepository;
import java.io.IOException;


@Component
@AllArgsConstructor
public class NaverScraping {


    private final WiseRepository wiseRepository;

    // 네이버 명언 스크래핑
    @Scheduled(cron = "0 24 21 * * *")
    public String goRegister() throws IOException {
        Document doc = Jsoup.connect("https://search.naver.com/search.naver?where=nexearch&sm=tab_etc&mra=blMy&qvt=0&query=%EA%B3%B5%EB%B6%80%20%EB%AA%85%EC%96%B8").get();
        Elements viewlst = doc.select("div.viewlst");
        wiseRepository.deleteAll();
        for (Element element : viewlst) {
            String name = element.select("h4.blind").text();
            String wise = element.select("p.lngkr").text();

            Wise wise1 =  new Wise();
            wise1.setName(name);
            wise1.setWise(wise);

            wiseRepository.save(wise1);

        }
        return "okay";
    }

}