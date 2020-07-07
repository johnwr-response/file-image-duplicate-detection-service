package no.responseweb.imagearchive.imageduplicatedetection.services;

import java.util.UUID;

public interface FileStoreFetcherService {
    byte[] fetchFile(UUID fileItemId);
}
