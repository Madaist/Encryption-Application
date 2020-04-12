package encryptionapp;

import encryptionapp.config.StorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import encryptionapp.services.storage.IStorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class EncryptionappApplication {

	@Bean
	CommandLineRunner init(IStorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(EncryptionappApplication.class, args);
	}

}
