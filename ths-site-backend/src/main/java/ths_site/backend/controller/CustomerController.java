package ths_site.backend.controller;

import java.util.List;
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

import ths_site.backend.model.database.Customer;
import ths_site.backend.model.dto.UserData;
import ths_site.backend.model.dto.UserDto;
import ths_site.backend.service.AdminService;
import ths_site.backend.service.CustomerService;

@EnableMethodSecurity(prePostEnabled = true)
@RestController
@RequestMapping("/user")
public class CustomerController {

    private final AdminService adminService;
    private final CustomerService customerService;

    public CustomerController(AdminService adminService, CustomerService customerService) {
        this.adminService = adminService;
        this.customerService = customerService;
    }

    /*
     * This function get all Customer as CustomerDto in a List.
     * - AS OF NOW, OK!
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> list = this.customerService.findAll().stream().map(Customer::toDto).toList();
        return ResponseEntity.ok(list);
    }

    /*
     * This function finds a Customer based on Id and returns a CustomerDto.
     * - AS OF NOW, OK!
     */
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @GetMapping(value = "/searchId", produces = "application/json")
    public ResponseEntity<UserDto> getById(@RequestParam UUID id) {
        Optional<Customer> opCustomer = this.customerService.getById(id);
        if (opCustomer.isPresent()) {
            UserDto dto = opCustomer.get().toDto();
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    /*
     * This function finds a Customer based on it's email and returns a CustomerDto.
     * - AS OF NOW, OK!
     */
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @GetMapping(value = "/searchEmail", produces = "application/json")
    public ResponseEntity<UserDto> getByEmail(@RequestParam String email) {
        Optional<Customer> opCustomer = this.customerService.getByEmail(email);
        if (opCustomer.isPresent()) {
            UserDto dto = opCustomer.get().toDto();
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    /*
     * This function saves a new Customer to database.
     * The function will check for existing Customer, by checking email.
     * TODO: Add password encoder in this function later.
     * TODO: This function is deprecated. The new function is in AccountController.
     * - AS OF NOW, OK!
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<UserDto> saveNew(@RequestBody UserData customer) {
        boolean emailAdminPresent = this.adminService.ifEmailExists(customer.getEmail());
        boolean emailCustomerPresent = this.customerService.ifEmailExists(customer.getEmail());

        if (emailAdminPresent && emailCustomerPresent) {
            return ResponseEntity.badRequest().build();
        }
        
        Customer savedCustomer = this.customerService.saveNew(customer);

        return ResponseEntity.ok(savedCustomer.toDto());
    }

    /*
     * This function updates the Customer data with new data from CustomerData.
     * TODO: Add password encoder in this function later, IF the password is
     * changed.
     * - AS OF NOW, OK!
     */
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PutMapping(value = "/update", produces = "application/json")
    public ResponseEntity<UserDto> put(@RequestParam String email, @RequestBody UserData customerData) {
        Optional<Customer> opCustomer = this.customerService.getByEmail(email);
        boolean emailInUse = this.customerService.ifEmailExists(customerData.getEmail());

        if (opCustomer.isPresent() && !emailInUse) {
            Customer customer = opCustomer.get();
            customer = this.customerService.updateCustomer(customer, customerData);
            this.customerService.save(customer);
            return ResponseEntity.ok(customer.toDto());
        }

        return ResponseEntity.notFound().build();
    }

    /*
     * This function deletes a Customer by their email, IF the Customer exists in
     * the
     * database.
     * Also removes all Jobs and Review that has connections to the Customer.
     * - AS OF NOW, OK!
     */
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<UUID> delete(@RequestParam String email) {
        Optional<Customer> opCustomer = this.customerService.getByEmail(email);
        if (opCustomer.isPresent()) {
            UUID id = opCustomer.get().getId();
            this.customerService.deleteById(id);
            return ResponseEntity.ok(id);
        }
        return ResponseEntity.notFound().build();
    }

    /*
     * This function deletes a Customer by their id, IF the Customer exists in
     * the
     * database.
     * Also removes all Jobs and Review that has connections to the Customer.
     * - AS OF NOW, OK!
     */
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @DeleteMapping(value = "/deleteById")
    public ResponseEntity<UUID> delete(@RequestParam UUID id) {
        Optional<Customer> opCustomer = this.customerService.getById(id);
        if (opCustomer.isPresent()) {
            this.customerService.deleteById(id);
            return ResponseEntity.ok(id);
        }
        return ResponseEntity.ok().build();
    }
}
