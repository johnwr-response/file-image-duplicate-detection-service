package no.responseweb.imagearchive.imageduplicatedetection.services;

import no.responseweb.imagearchive.imageduplicatedetection.config.FeignClientConfig;
import no.responseweb.imagearchive.model.ImageDuplicateComparisonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(value = "image-processing-duplicate-comparison-service", url = "${feign.client.url.ImageDuplicateComparisonServiceUrl}", configuration = FeignClientConfig.class)
public interface ImageDuplicateComparisonFeignClient {

    @GetMapping(value = ImageDuplicateComparisonServiceFeignImpl.FETCH_PATH)
    ResponseEntity<ImageDuplicateComparisonDto> compareFiles(@PathVariable UUID compareFileItemId, @PathVariable UUID compareWithFileItemId);

}
