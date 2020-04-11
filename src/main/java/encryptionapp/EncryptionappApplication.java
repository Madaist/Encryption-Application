package encryptionapp;

import encryptionapp.config.StorageProperties;
import encryptionapp.encrypt.BifidCipherEncryption;
import encryptionapp.encrypt.PolybiusSquareEncryption;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import encryptionapp.services.IStorageService;

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

		PolybiusSquareEncryption ob = new PolybiusSquareEncryption("CUTE DISNEY PRINCESS", "EXEMPLIFICARE");
		ob.encrypt();

		PolybiusSquareEncryption ob1 = new PolybiusSquareEncryption("235145113221444111541425214123114444", "EXEMPLIFICARE");
		ob1.decrypt();

		BifidCipherEncryption ob3 = new BifidCipherEncryption("DIFUZIE", "CRIPTOGRAFIE");
		ob3.encrypt();

		BifidCipherEncryption ob4 = new BifidCipherEncryption("BFVHKZL", "CRIPTOGRAFIE");
		ob4.decrypt();
	}

}
