package no.responseweb.imagearchive.imageduplicatedetection;

import lombok.extern.slf4j.Slf4j;
import nu.pattern.OpenCV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Slf4j
@SpringBootApplication
@EnableFeignClients
public class ImageDuplicateDetectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageDuplicateDetectionApplication.class, args);
		OpenCV.loadShared();
	}

}


