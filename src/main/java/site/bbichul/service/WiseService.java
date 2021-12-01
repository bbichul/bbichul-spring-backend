package site.bbichul.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.bbichul.models.Wise;
import site.bbichul.repository.WiseRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WiseService {

    private final WiseRepository wiseRepository;

    public List<Wise> getWises(){
        return wiseRepository.findAll();
    }
}

