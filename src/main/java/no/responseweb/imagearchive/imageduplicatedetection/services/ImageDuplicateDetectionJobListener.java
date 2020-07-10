package no.responseweb.imagearchive.imageduplicatedetection.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.responseweb.imagearchive.filestoredbservice.services.FileEntityService;
import no.responseweb.imagearchive.filestoredbservice.services.FileItemService;
import no.responseweb.imagearchive.imageduplicatedetection.config.JmsConfig;
import no.responseweb.imagearchive.model.FileEntityDto;
import no.responseweb.imagearchive.model.FileItemDto;
import no.responseweb.imagearchive.model.ImageDuplicateComparisonDto;
import no.responseweb.imagearchive.model.ImageDuplicateDetectionJobDto;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Component
@Transactional
public class ImageDuplicateDetectionJobListener {

    private final ImageDuplicateComparisonService imageDuplicateComparisonService;

    private final FileEntityService fileEntityService;
    private final FileItemService fileItemService;

    @JmsListener(destination = JmsConfig.IMAGE_DUPLICATE_DETECTION_JOB_QUEUE)
    public void listen(ImageDuplicateDetectionJobDto imageDuplicateDetectionJobDto) {
        log.info("Called with: {}", imageDuplicateDetectionJobDto);
        FileItemDto fileItemDto = imageDuplicateDetectionJobDto.getFileItemDto();
        if (fileItemDto!=null && fileItemDto.getId()!=null) {
            FileEntityDto fetchedFileEntity = fileEntityService.findFirstById(fileItemDto.getId());
            AtomicInteger iCount = new AtomicInteger();
            AtomicBoolean bFound = new AtomicBoolean(false);
            fileEntityService.findDistinctByIdNotLike(fileItemDto.getId()).forEach(compareWithFileEntityDto -> {
                iCount.getAndIncrement();
                if (compareWithFileEntityDto.getId()!=null) {
                    log.info("Check if {} is duplicate of {}", fileItemDto.getId(), compareWithFileEntityDto.getId());
                    ImageDuplicateComparisonDto comparisonDto = imageDuplicateComparisonService.compareFiles(
                            (fetchedFileEntity!=null) ? fetchedFileEntity.getId() : fileItemDto.getId(),
                            compareWithFileEntityDto.getId()
                    );
                    log.info("{}", comparisonDto);
                    if (comparisonDto.score() > ((fileItemDto.getFileEntityScore() != null) ? fileItemDto.getFileEntityScore() : 0)) {
                        bFound.set(true);
                        fileItemDto.setFileEntityScore(comparisonDto.score());
                        if (comparisonDto.identical()) {
                            log.info("Image {} is identical. Score: {}", compareWithFileEntityDto, comparisonDto.score());
                            fileItemDto.setFileEntityId(compareWithFileEntityDto.getId());
                            markFileAsDuplicate(fileItemDto);
                        } else if (comparisonDto.probable()){
                            log.info("Image {} is probable for duplication. Score: {}", compareWithFileEntityDto, comparisonDto.score());
                            fileItemDto.setFileEntityId(compareWithFileEntityDto.getId());
                            markFileAsDuplicateCandidate(fileItemDto);
                        } else if (comparisonDto.candidate()){
                            log.info("Image {} is candidate for duplication. Score: {}", compareWithFileEntityDto, comparisonDto.score());
                            markFileAsDuplicateCandidate(fileItemDto);
                        } else {
                            log.info("Image {} is NOT candidate for duplication. Score: {}", compareWithFileEntityDto, comparisonDto.score());
                        }
                    }
                }
            });
            if (!bFound.get()) {
                markFileAsNewEntity(fileItemDto);
            }
        }
    }
    private void markFileAsDuplicate(FileItemDto fileItemDto) {
        if (fileItemDto!=null) {
            fileEntityService.deleteById(fileItemDto.getId());
            fileItemService.save(fileItemDto);
            log.info("File marked as duplicate!");
        }
    }
    private void markFileAsDuplicateCandidate(FileItemDto fileItemDto) {
        if (fileItemDto!=null) {
            // TODO: Implement this. New mapping table?
            // For now just mark it as duplicate
            markFileAsDuplicate(fileItemDto);
        }
    }
    private void markFileAsNewEntity(FileItemDto fileItemDto) {
        if (fileItemDto!=null) {
            FileEntityDto fileEntityDto = fileEntityService.findFirstById(fileItemDto.getId());
            if (fileItemDto.getFileEntityId()==null && fileEntityDto==null) {
                fileEntityDto = fileEntityService.save(FileEntityDto.builder().id(fileItemDto.getId()).build());
                fileItemDto.setFileEntityId(fileEntityDto.getId());
                fileItemService.save(fileItemDto);
            }

            log.info("File marked as new entity!");
        }
    }
}
