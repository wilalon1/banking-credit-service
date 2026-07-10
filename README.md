# Credit Service

## Descripción

Microservicio encargado de la gestión de créditos dentro del sistema bancario.

Permite administrar créditos personales, empresariales y tarjetas de crédito, además de gestionar pagos realizados y validaciones relacionadas con el estado crediticio de los clientes.

Este microservicio forma parte de una arquitectura basada en microservicios utilizando Spring Cloud.

---

## Funcionalidades

- Crear créditos.
- Consultar todos los créditos registrados.
- Buscar créditos por ID.
- Buscar créditos asociados a un cliente.
- Actualizar información de créditos.
- Eliminar créditos.
- Validar si un cliente posee tarjeta de crédito.
- Validar si un cliente tiene deudas vencidas.
- Registrar pagos de créditos.
- Consultar historial de pagos.

---

## Tecnologías utilizadas

- Java 17
- Spring Boot 3
- Spring WebFlux
- Spring Data Reactive MongoDB
- MongoDB
- RxJava 3
- Spring Cloud Config Client
- Eureka Client
- Resilience4j Circuit Breaker
- Maven
- JUnit 5
- Mockito
- JaCoCo
- Checkstyle

---

## Arquitectura

El microservicio sigue una arquitectura por capas:
credit-service
│
├── controller
│ └── Exposición de endpoints REST
│
├── service
│ └── Lógica de negocio
│
├── repository
│ └── Acceso a datos MongoDB
│
├── model
│ └── Entidades del dominio
│
├── dto
│ └── Objetos de transferencia de datos
│
└── config
└── Configuraciones del servicio

## Endpoints

Base URL:/api/credits

/api/credits
### Crear crédito

**POST**
/api/credits
{
  "customerId": "C001",
  "type": "PERSONAL",
  "amount": 10000,
  "balance": 10000,
  "maxCreditsAllowed": 3,
  "status": "VIGENTE"
}

##Obtener todos los créditos

GET
/api/credits

/api/credits

##Obtener créditos por cliente

GET

/api/credits/customer/{customerId}
/api/credits/customer/6a3b4de37af6c52917ab0648

##Actualizar crédito

/api/credits/{id}

##Eliminar crédito

/api/credits/{id}

DELETE

##Validar tarjeta de crédito

GET  /api/credits/cards/customer/{customerId}

Respuesta
true

##Validar deuda vencida

/api/credits/overdue/{customerId}

Respuesta
{
  "overdue": true
}

##Registrar pago


/api/credits/{creditId}/payments

Request
{
  "payerId": "P001",
  "amount": 500
}

Respuesta
Payment made successfully.

Se registrará en credits
credit_payment_log

#Configuración del servicio

Puerto: 8083

#Registro de servicio:
Eureka Client

#Configuración externa:
Spring Cloud Config Server

#Pruebas

Las pruebas unitarias se encuentran en:

src/test/java

Incluyen:

Tests de Controller.
Tests de Service.
Tests de Model.
Tests de DTO.
Tests con Mockito.
Validación de comportamiento reactivo.

##Documentación adicional

La carpeta docs contiene:

docs
│
├── pruebas
│   ├── Evidencias de ejecución
│   └── Reportes de pruebas
│
├── jacoco
│   └── Reporte de cobertura
│
├── checkstyle
│   └── Reporte de calidad de código
│
└── postman
    └── Colección de pruebas API  
	
##Documentacion

swagger  -> documentacion 