# E-Commer-Backend
Project Description

This project is a backend-based E-commerce application developed during my training period in my first company with the objective of understanding real-world backend development using Java. The application is designed to handle multiple user roles and business workflows commonly found in an E-commerce system.

The system is built around four core modules: Admin, Seller, Customer (User), and Order Management. Each user type has its own separate database table, which helps in maintaining clear role separation, better data security, and scalability. The Admin module is responsible for managing sellers, monitoring system activities, and maintaining overall control of the platform. The Seller module allows sellers to manage their products, view orders, and handle order-related operations. The Customer module enables users to register, log in, browse products, and place orders. The Order module manages the complete order lifecycle from creation to tracking.

The backend is developed using Spring Boot and follows a layered architecture consisting of controller, service, and repository layers. The application exposes RESTful APIs to handle all business operations efficiently. For security, JWT (JSON Web Token) based authentication and authorization is implemented. This ensures that only authenticated users can access protected APIs and that role-based access control is enforced across the system.

Additional features include user authentication, role-based authorization, product and order management, and secure API communication. Proper exception handling and validation mechanisms are also implemented to improve application reliability.

This project helped me gain practical experience in Java backend development, Spring Boot, REST API design, database modeling, and implementing security using JWT. It strengthened my understanding of building secure, scalable, and maintainable backend systems in a real-world training environment.
