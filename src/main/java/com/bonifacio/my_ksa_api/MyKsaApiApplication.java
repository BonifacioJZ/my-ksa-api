package com.bonifacio.my_ksa_api;

import com.bonifacio.my_ksa_api.persistence.entities.ERole;
import com.bonifacio.my_ksa_api.persistence.entities.PermissionEntity;
import com.bonifacio.my_ksa_api.persistence.entities.RoleEntity;
import com.bonifacio.my_ksa_api.persistence.entities.UserEntity;
import com.bonifacio.my_ksa_api.persistence.reporsitory.IRoleRepository;
import com.bonifacio.my_ksa_api.persistence.reporsitory.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class MyKsaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyKsaApiApplication.class, args);
	}

	@Bean
	CommandLineRunner init(IRoleRepository roleRepository, IUserRepository userRepository){
		return args -> {
			PermissionEntity createPermission = PermissionEntity.builder()
					.permission("CREATE")
					.build();
			PermissionEntity readPermission = PermissionEntity.builder()
					.permission("READ")
					.build();
			PermissionEntity updatePermission = PermissionEntity.builder()
					.permission("UPDATE")
					.build();
			PermissionEntity deletePermission = PermissionEntity.builder()
					.permission("DELETE")
					.build();
			PermissionEntity refactorPermission = PermissionEntity.builder()
					.permission("REFACTOR")
					.build();
			PermissionEntity createUser = PermissionEntity.builder()
					.permission("CREATE_USER")
					.build();
			RoleEntity admin = RoleEntity.builder()
					.role(ERole.ADMIN)
					.build();
			admin.setPermissions(Set.of(createPermission,readPermission,deletePermission,updatePermission));
			RoleEntity user = RoleEntity.builder()
					.role(ERole.USER)
					.build();
			user.setPermissions(Set.of(createPermission,readPermission));
			RoleEntity root = RoleEntity.builder()
					.role(ERole.ROOT)
					.build();
			root.setPermissions(Set.of(createPermission,readPermission,deletePermission,updatePermission,createUser));
			roleRepository.saveAll(List.of(admin,root,user));
			UserEntity uRoot = UserEntity.builder()
					.username("root")
					.email("root@root.com")
					.password(new BCryptPasswordEncoder().encode("admin"))
					.isEnabled(true)
					.accountNoLocked(true)
					.accountNoExpired(true)
					.credentialNoExpired(true)
					.build();
			uRoot.setRoles(Set.of(root));
			userRepository.saveAll(List.of(uRoot));
		};
	}

}
