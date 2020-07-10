package no.responseweb.imagearchive.imageduplicatedetection;

import lombok.extern.slf4j.Slf4j;
import no.responseweb.imagearchive.filestoredbservice.config.DBModuleConfig;
import nu.pattern.OpenCV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootApplication
@EnableFeignClients
@Import(DBModuleConfig.class)
public class ImageDuplicateDetectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageDuplicateDetectionApplication.class, args);
		OpenCV.loadShared();
	}

}


