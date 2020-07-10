package no.responseweb.imagearchive.imageduplicatedetection.services;

import no.responseweb.imagearchive.model.ImageDuplicateComparisonDto;

import java.util.UUID;

public interface ImageDuplicateComparisonService {
    ImageDuplicateComparisonDto compareFiles(UUID compareFileItemId, UUID compareWithFileItemId);
}
