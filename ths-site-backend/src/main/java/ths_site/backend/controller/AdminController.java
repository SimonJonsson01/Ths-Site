package ths_site.backend.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ths_site.backend.model.database.Admin;
import ths_site.backend.model.dto.UserData;
import ths_site.backend.service.AdminService;
import ths_site.backend.service.CustomerService;

@EnableMethodSecurity(prePostEnabled = true)
@RestController
@RequestMapping("/admin")
public class AdminController {
  private final AdminService adminService;
  private final CustomerService customerService;

  public AdminController(AdminService adminService, CustomerService customerService) {
    this.adminService = adminService;
    this.customerService = customerService;
  }

  /*
   * This functions finds an Admin based on id.
   * - AS OF NOW, OK!
   */
  @PreAuthorize("hasAnyRole('ADMIN')")
  @GetMapping(value = "/searchById", produces = "application/json")
  public ResponseEntity<UserData> getById(@RequestParam UUID id) {
    Optional<Admin> opAdmin = this.adminService.getById(id);
    if (opAdmin.isPresent()) {
      Admin admin = opAdmin.get();
      return ResponseEntity.ok(admin.toData());
    }
    return ResponseEntity.notFound().build();
  }

  /*
   * This functions finds an Admin based on email.
   * - AS OF NOW, OK!
   */
  @PreAuthorize("hasAnyRole('ADMIN')")
  @GetMapping(value = "/searchByEmail", produces = "application/json")
  public ResponseEntity<UserData> getByEmail(@RequestParam String email) {
    Optional<Admin> opAdmin = this.adminService.getByEmail(email);
    if (opAdmin.isPresent()) {
      Admin admin = opAdmin.get();
      return ResponseEntity.ok(admin.toData());
    }
    return ResponseEntity.notFound().build();
  }

  /*
   * This function saves a New Admin to the database.
   * TODO: Add password encoder in this function later.
   * - AS OF NOW, OK!
   */
  @PreAuthorize("hasAnyRole('ADMIN')")
  @PostMapping(value = "/create", produces = "application/json")
  public ResponseEntity<UserData> post(@RequestBody UserData admin) {
    boolean emailAdminPresent = this.adminService.ifEmailExists(admin.getEmail());
    boolean emailCustomerPresent = this.customerService.ifEmailExists(admin.getEmail());

    if (emailAdminPresent && emailCustomerPresent) {
      return ResponseEntity.badRequest().build();
    }

    this.adminService.saveNew(admin);

    return ResponseEntity.ok(admin);
  }

  /*
   * This function updates the Admin data with new data from UserData.
   * TODO: Add password encoder in this function later, IF the password is
   * changed.
   * - AS OF NOW, OK!
   */
  @PreAuthorize("hasAnyRole('ADMIN')")
  @PutMapping(value = "/update", produces = "application/json")
  public ResponseEntity<UserData> put(@RequestParam UUID id, @RequestBody UserData adminData) {
    Optional<Admin> opAdmin = this.adminService.getById(id);
    if (opAdmin.isPresent()) {
      Admin admin = opAdmin.get();
      admin = this.adminService.updateAdmin(admin, adminData);
      this.adminService.save(admin);
      return ResponseEntity.ok(admin.toData());
    }
    return ResponseEntity.notFound().build();
  }

  /*
   * This function deletes a Admin from the database, based on id.
   * TODO: Add if-statement to avoid deleting the last admin in the database.
   * - AS OF NOW, OK!
   */
  @PreAuthorize("hasAnyRole('ADMIN')")
  @DeleteMapping(value = "/delete", produces = "application/json")
  public ResponseEntity<UUID> delete(@RequestParam UUID id) {
    Optional<Admin> opAdmin = this.adminService.getById(id);

    if (opAdmin.isPresent()) {
      this.adminService.deleteById(id);
      return ResponseEntity.ok(id);
    }
    return ResponseEntity.notFound().build();
  }

}
