package com.jonnyle.checklist;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

// uncomment to initialize school
// recomment after loading database for the first time or change application.properties ddl-auto to "none"
@Component
public class DataLoader implements ApplicationRunner {

	private SchoolRepo schoolRepo;
	private CheckInRepo checkInRepo;
	private AdminRepo adminRepo;

	@Autowired
	private WebSecurityConfig config;

	@Autowired
	public DataLoader(SchoolRepo schoolRepo, CheckInRepo checkInRepo, AdminRepo adminRepo) {
		this.schoolRepo = schoolRepo;
		this.checkInRepo = checkInRepo;
		this.adminRepo = adminRepo;
	}

	public Set<Integer> getRandomSet(int seed, int size, int bound) {
		if (bound < size) {
			throw new IllegalArgumentException("Can't ask for more numbers than are available");
		}
		Random rng = new Random(seed); // Ideally just create one instance globally
		// Note: use LinkedHashSet to maintain insertion order
		Set<Integer> generated = new LinkedHashSet<Integer>();
		while (generated.size() < size) {
			Integer next = rng.nextInt(bound) + 1;
			// As we're adding to a set, this will automatically do a containment check
			generated.add(next);
		}
		return generated;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		adminRepo.deleteAll();
		checkInRepo.deleteAll();
		schoolRepo.deleteAll();

		int seed = 1;
		Set<Integer> randomTea = getRandomSet(seed, 5, 400);
		Iterator<Integer> itr = randomTea.iterator();
		schoolRepo.save(new School(itr.next(), "Lakeview", "A01"));
		schoolRepo.save(new School(itr.next(), "Rowlett", "B02"));
		schoolRepo.save(new School(itr.next(), "Paschal", "C03"));
		schoolRepo.save(new School(itr.next(), "South Garland", "D04"));
		schoolRepo.save(new School(itr.next(), "Mesquite", "E05"));

		adminRepo.save(new Admin("admin", config.passwordEncoder().encode("admin"), new SimpleGrantedAuthority("ROLE_ADMIN")));
	}

}
