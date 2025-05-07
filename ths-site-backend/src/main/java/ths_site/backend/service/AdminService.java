package ths_site.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ths_site.backend.model.database.Admin;
import ths_site.backend.model.dto.UserData;
import ths_site.backend.repository.AdminRepository;

@Service
public class AdminService {

  private final AdminRepository adminRepository;
   private final PasswordEncoder passwordEncoder;

  public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
    this.adminRepository = adminRepository;
    this.passwordEncoder = passwordEncoder;
  }

  // - Hämta alla admins.
  public List<Admin> getAll() {
    return this.adminRepository.findAll();
  }

  // - Hämta en admin efter id.
  public Optional<Admin> getById(UUID id) {
    return this.adminRepository.findById(id);
  }

  // - Hämta en admin efter email.
  public Optional<Admin> getByEmail(String email) {
    return this.adminRepository.findByEmail(email);
  }

  // - Spara en admin. (Bara admin kan skapa nya admins).
  public Admin save(Admin admin) {
    return this.adminRepository.save(admin);
  }

  // - Spara en ny admin med data from UserData.
  public Admin saveNew(UserData adminData) {
    Admin admin = new Admin(adminData);
    admin.setPassword(passwordEncoder.encode(adminData.getPassword()));
    return this.adminRepository.save(admin);
  }

  // - Ändra data för en admin.

  // - Radera admin efter id (Kan inte radera om det bara finns en admin kvar).
  public void deleteById(UUID id) {
    this.adminRepository.deleteById(id);
  }

  // - Kolla om email redan existerar i databasen.
  public boolean ifEmailExists(String email) {
    return this.adminRepository.existsByEmail(email);
  }

  // - Bytar ut gammal data hos Admin med ny data från AdminData.
  public Admin updateAdmin(Admin admin, UserData userData) {
    admin.setFirstName(admin.getFirstName().toLowerCase().equals(userData.getFirstName().toLowerCase())
        ? admin.getFirstName()
        : userData.getFirstName());
    admin.setLastName(admin.getLastName().toLowerCase().equals(userData.getLastName().toLowerCase())
        ? admin.getLastName()
        : userData.getLastName());
    admin.setEmail(admin.getEmail().toLowerCase().equals(userData.getEmail().toLowerCase())
        ? admin.getEmail()
        : userData.getEmail());
    admin.setPassword(admin.getPassword().toLowerCase().equals(userData.getPassword().toLowerCase())
        ? admin.getPassword()
        : userData.getPassword());
    return admin;
  }
}
