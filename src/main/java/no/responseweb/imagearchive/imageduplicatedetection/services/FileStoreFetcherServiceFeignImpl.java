package no.responseweb.imagearchive.imageduplicatedetection.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileStoreFetcherServiceFeignImpl implements FileStoreFetcherService {

    public static final String FETCH_PATH = "api/v1/fetchFile/{fileItemId}";
    public static final String MEDIA_TYPE_PATH = "api/v1/fetchFile/{fileItemId}/mimeType";

    private final FileStoreFetcherServiceFeignClient fileStoreFetcherServiceFeignClient;

    @Override
    public byte[] fetchFile(UUID fileItemId) {
        ResponseEntity<byte[]> responseEntity = fileStoreFetcherServiceFeignClient.fetchFile(fileItemId);
        return responseEntity.getBody();
    }

    @Override
    public String getMimeType(UUID fileItemId) {
        ResponseEntity<String> responseEntity = fileStoreFetcherServiceFeignClient.getMimeType(fileItemId);
        return responseEntity.getBody();
    }
}
