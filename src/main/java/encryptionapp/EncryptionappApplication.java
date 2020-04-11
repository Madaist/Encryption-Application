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

		/*
		PolybiusSquareService ob = new PolybiusSquareService("CUTE DISNEY PRINCESS", "EXEMPLIFICARE");
		ob.encrypt();

		PolybiusSquareService ob1 = new PolybiusSquareService("235145113221444111541425214123114444", "EXEMPLIFICARE");
		ob1.decrypt();

		BifidCipherService ob3 = new BifidCipherService("DIFUZIE", "CRIPTOGRAFIE");
		ob3.encrypt();

		BifidCipherService ob4 = new BifidCipherService("BFVHKZL", "CRIPTOGRAFIE");
		ob4.decrypt();

		 */
	}

}
