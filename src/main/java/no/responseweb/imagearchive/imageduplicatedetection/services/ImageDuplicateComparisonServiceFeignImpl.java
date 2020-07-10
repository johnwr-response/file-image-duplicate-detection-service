package no.responseweb.imagearchive.imageduplicatedetection.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.responseweb.imagearchive.model.ImageDuplicateComparisonDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageDuplicateComparisonServiceFeignImpl implements ImageDuplicateComparisonService {

    public static final String FETCH_PATH = "api/v1/compare/{compareFileItemId}/{compareWithFileItemId}";

    private final ImageDuplicateComparisonFeignClient imageDuplicateComparisonFeignClient;

    @Override
    public ImageDuplicateComparisonDto compareFiles(UUID compareFileItemId, UUID compareWithFileItemId) {
        ResponseEntity<ImageDuplicateComparisonDto> responseEntity = imageDuplicateComparisonFeignClient.compareFiles(compareFileItemId, compareWithFileItemId);
        return responseEntity.getBody();
    }
}
