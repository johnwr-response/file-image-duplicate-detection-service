package no.responseweb.imagearchive.imageduplicatedetection.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.responseweb.imagearchive.imageduplicatedetection.config.JmsConfig;
import no.responseweb.imagearchive.model.ImageDuplicateDetectionJobDto;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ImageDuplicateDetectionJobListener {
    @JmsListener(destination = JmsConfig.IMAGE_DUPLICATE_DETECTION_JOB_QUEUE)
    public void listen(ImageDuplicateDetectionJobDto imageDuplicateDetectionJobDto) {
        log.info("Called with: {}", imageDuplicateDetectionJobDto);
    }
}
