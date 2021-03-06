package ru.nsu.fit.endpoint.service.manager;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import ru.nsu.fit.endpoint.service.database.DBService;
import ru.nsu.fit.endpoint.service.database.data.Customer;

import java.util.List;
import java.util.UUID;

public class CustomerManager extends ParentManager {
    public CustomerManager(DBService dbService, Logger flowLog) {
        super(dbService, flowLog);
    }

    /**
     * Метод создает новый объект типа Customer. Ограничения:
     * Аргумент 'customerData' - не null;
     * firstName - нет пробелов, длина от 2 до 12 символов включительно, начинается с заглавной буквы, остальные символы строчные, нет цифр и других символов;
     * lastName - нет пробелов, длина от 2 до 12 символов включительно, начинается с заглавной буквы, остальные символы строчные, нет цифр и других символов;
     * login - указывается в виде email, проверить email на корректность, проверить что нет customer с таким же email;
     * pass - длина от 6 до 12 символов включительно, не должен быть простым (123qwe или 1q2w3e), не должен содержать части login, firstName, lastName
     * money - должно быть равно 0.
     */
    public Customer createCustomer(Customer customer) {
        Validate.notNull(customer, "Argument 'customerData' is null.");

        Validate.notNull(customer.pass);
        Validate.isTrue(customer.pass.length() >= 6 && customer.pass.length() < 13, "Password's length should be more or equal 6 symbols and less or equal 12 symbols.");
        Validate.isTrue(!customer.pass.equalsIgnoreCase("123qwe"), "Password is easy.");

        Validate.notNull(customer.login);
        Validate.isTrue(customer.login.matches("^\\S+@\\S+\\.[a-zA-Z]{1,10}$"), "login is incorrect");

        dbService.getCustomers().forEach(x -> {
            if (x.login.equals(customer.login))
                throw new IllegalArgumentException("Customer with login " + customer.login + " already exists");
        });

        // TODO: необходимо дописать дополнительные проверки

        return dbService.createCustomer(customer);
    }

    /**
     * Метод возвращает список объектов типа customer.
     */
    public List<Customer> getCustomers() {
        return dbService.getCustomers();
    }


    /**
     * Метод обновляет объект типа Customer.
     * Можно обновить только firstName и lastName.
     */
    public Customer updateCustomer(Customer customer) {
        throw new NotImplementedException("Please implement the method.");
    }

    public void removeCustomer(String login) {
        Validate.notNull(login);
        Validate.isTrue(login.matches("^\\S+@\\S+\\.[a-zA-Z]{1,10}$"), "login is incorrect");

        UUID id = dbService.getCustomerIdByLogin(login);

        dbService.removeCustomerById(id);
    }

    public Customer topUpBalance(UUID customerId, int amount) {
        throw new NotImplementedException("Please implement the method.");
    }
}
