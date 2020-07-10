package no.responseweb.imagearchive.imageduplicatedetection.services;

import no.responseweb.imagearchive.imageduplicatedetection.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(value = "file-store-fetcher-service", url = "${feign.client.url.FileStoreFetcherServiceUrl}", configuration = FeignClientConfig.class)
public interface FileStoreFetcherServiceFeignClient {

    @GetMapping(value = FileStoreFetcherServiceFeignImpl.FETCH_PATH)
    ResponseEntity<byte[]> fetchFile(@PathVariable UUID fileItemId);

    @GetMapping(value = FileStoreFetcherServiceFeignImpl.MEDIA_TYPE_PATH)
    ResponseEntity<String> getMimeType(@PathVariable UUID fileItemId);
}