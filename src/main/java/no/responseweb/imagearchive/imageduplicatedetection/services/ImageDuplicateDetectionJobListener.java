package no.responseweb.imagearchive.imageduplicatedetection.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.responseweb.imagearchive.imageduplicatedetection.config.JmsConfig;
import no.responseweb.imagearchive.model.ImageDuplicateDetectionJobDto;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class ImageDuplicateDetectionJobListener {

    private final FileStoreFetcherService fileStoreFetcherService;

    @JmsListener(destination = JmsConfig.IMAGE_DUPLICATE_DETECTION_JOB_QUEUE)
    public void listen(ImageDuplicateDetectionJobDto imageDuplicateDetectionJobDto) throws IOException {
        log.info("Called with: {}", imageDuplicateDetectionJobDto);
        if (imageDuplicateDetectionJobDto.getFileItemIdQuery()!=null) {
            byte[] downloadedBytesQuery = fileStoreFetcherService.fetchFile(imageDuplicateDetectionJobDto.getFileItemIdQuery());
            BufferedImage imageQuery = ImageIO.read(new ByteArrayInputStream(downloadedBytesQuery));
            if (imageQuery!=null) {
                // get dHash of all images in dataset

/*
Consider using Lire:
https://github.com/dermotte/lire

openimaj approach:
                MBFImage query = ImageUtilities.readMBF(new ByteArrayInputStream(downloadedBytesQuery));
                MBFImage target = ImageUtilities.readMBF(new ByteArrayInputStream(downloadedBytesTarget));

                DoGSIFTEngine engine = new DoGSIFTEngine();
                LocalFeatureList<Keypoint> queryKeypoints = engine.findFeatures(query.flatten());
                LocalFeatureList<Keypoint> targetKeypoints = engine.findFeatures(target.flatten());

                LocalFeatureMatcher<Keypoint> matcher = new BasicMatcher<Keypoint>(80);
                matcher.setModelFeatures(queryKeypoints);
                matcher.findMatches(targetKeypoints);

                MBFImage basicMatches = MatchingUtilities.drawMatches(query, target, matcher.getMatches(), RGBColour.RED);
                DisplayUtilities.display(basicMatches);

                RobustAffineTransformEstimator modelFitter = new RobustAffineTransformEstimator(50.0, 1500,
                        new RANSAC.PercentageInliersStoppingCondition(0.5));
                matcher = new ConsistentLocalFeatureMatcher2d<Keypoint>(
                        new FastBasicKeypointMatcher<Keypoint>(8), modelFitter);

                matcher.setModelFeatures(queryKeypoints);
                matcher.findMatches(targetKeypoints);

                MBFImage consistentMatches = MatchingUtilities.drawMatches(query, target, matcher.getMatches(),
                        RGBColour.RED);
*/
            }

        }
    }
}
