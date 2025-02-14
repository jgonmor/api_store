# 🌍 Language / Idioma

- [🇬🇧 English](#-api-store-en)
- [🇪🇸 Español](#-api-store-es)

---

# 🛒 API Store EN

## 📚 Table of Contents

1. [Description](#-description)
2. [Installation](#-installation)
3. [Configuration](#-configuration)
4. [Usage](#-usage)
5. [Endpoints](#-endpoints)
   - [Products](#-products)
   - [Orders](#-orders)
   - [Customers](#-customers)
6. [Entity-Relationship Diagram](#-entity-relationship-diagram)
7. [Contributing](#-contributing)
8. [License](#-license)

---

## 📝 Description

This API allows managing products, orders, and customers. It is developed using **Spring Boot** and follows RESTful principles.

---

## 🛠 Installation

### 1️⃣ Clone the repository

```sh
git clone https://github.com/jgonmor/api_store.git
cd api_store
```

### 2️⃣ Set up the environment

Make sure you have **Java 17+** and **Maven** installed.

---

## ⚙️ Configuration

Create a file `application.properties` in `src/main/resources/` with the following settings:

```properties
spring.application.name=api_store

# DataSource settings
spring.datasource.url=jdbc:mysql://${DB_IP}:${DB_PORT}/${DB_NAME}?useSSL=false&serverTimezone=UTC
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
server.port=8080

# Hibernate settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true
hibernate.transaction.jta.platform=enabled

spring.jackson.date-format=yyyy-MM-dd HH:mm:ss.SS

# Logging settings
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

---

## 🚀 Usage

You can test the API using tools like **Postman** or **cURL**.

Example to fetch all products:

```sh
curl -X GET http://localhost:8080/api/products
```

---

## 🔗 Endpoints

### 📦 Products

- `GET /api/products` → Retrieves all products.

  **Example response:**

  ```json
  [
    {
      "id": 1,
      "name": "Laptop Pro 15",
      "brand": "TechMaster",
      "price": 1499.99,
      "stock": 71
    },
    {
      "id": 2,
      "name": "Wireless Headphones",
      "brand": "SoundWave",
      "price": 199.99,
      "stock": 170
    },
    {
      "id": 3,
      "name": "Smartphone X200",
      "brand": "NextGen",
      "price": 999.99,
      "stock": 30
    },
    {
      "id": 4,
      "name": "Gaming Mouse",
      "brand": "PlayGear",
      "price": 59.99,
      "stock": 100
    }
  ]
  ```

- `POST /api/products` → Creates a new product.
  **Example body request:**

  ```json
  {
    "name": "4K Monitor",
    "brand": "ViewPoint",
    "price": 399.99,
    "stock": 15
  }
  ```

  **Example response:**

  ```json
  {
    "id": 5,
    "name": "4K Monitor",
    "brand": "ViewPoint",
    "price": 399.99,
    "stock": 15
  }
  ```

- `GET /api/products/{id}` → Retrieves a product by ID.
  **Example response:**
  ```json
  {
    "id": 5,
    "name": "4K Monitor",
    "brand": "ViewPoint",
    "price": 399.99,
    "stock": 15
  }
  ```
- `PUT /api/products/update/{id}` → Updates a product.

**Example body request:**

```json
{
  "id": 5,
  "name": "4K Monitor",
  "brand": "ViewPoint",
  "price": 399.99,
  "stock": 20
}
```

**Example before update:**

```json
{
  "id": 5,
  "name": "4K Monitor",
  "brand": "ViewPoint",
  "price": 399.99,
  "stock": 15
}
```

**Example response:**

```json
{
  "id": 5,
  "name": "4K Monitor",
  "brand": "ViewPoint",
  "price": 399.99,
  "stock": 20
}
```

- `DELETE /api/products/delete/{id}` → Deletes a product.
  
  **Example response:**
  ```json
  Product removed successfully
  ```

### 🛍️ Orders

- `GET /api/sells` → Retrieves all sells.
  
  **Example response:**
  ```json
  [
    {
      "id": 20,
      "date": "2025-01-07T19:11:35.000",
      "total": 799.96,
      "client": {
        "id": 1,
        "name": "Juan",
        "lastName": "Pérez",
        "citizenId": "123456789"
      },
      "sellDetails": [
        {
          "id": 19,
          "unitPrice": 1499.99,
          "quantity": 4,
          "total": 5999.96,
          "product": {
            "id": 1,
            "name": "Laptop Pro 15",
            "brand": "TechMaster",
            "price": 1499.99,
            "stock": 80
          }
        }
      ]
    },
    {
      "id": 21,
      "date": "2025-01-07T19:11:35.000",
      "total": 5699.91,
      "client": {
        "id": 1,
        "name": "Juan",
        "lastName": "Pérez",
        "citizenId": "123456789"
      },
      "sellDetails": [
        {
          "id": 21,
          "unitPrice": 1499.99,
          "quantity": 3,
          "total": 4499.97,
          "product": {
            "id": 1,
            "name": "Laptop Pro 15",
            "brand": "TechMaster",
            "price": 1499.99,
            "stock": 80
          }
        },
        {
          "id": 22,
          "unitPrice": 199.99,
          "quantity": 6,
          "total": 1199.94,
          "product": {
            "id": 2,
            "name": "Wireless Headphones",
            "brand": "SoundWave",
            "price": 199.99,
            "stock": 170
          }
        }
      ]
    }
  ]
  ```
- `GET /api/sells/{id}` → Retrieves a sell by ID.

  **Example response:**

  ```json
  {
    "id": 24,
    "date": "2025-01-07T19:11:35.000",
    "total": 5699.91,
    "client": {
      "id": 1,
      "name": "Juan",
      "lastName": "Pérez",
      "citizenId": "123456789"
    },
    "sellDetails": [
      {
        "id": 31,
        "unitPrice": 1499.99,
        "quantity": 3,
        "total": 4499.97,
        "product": {
          "id": 1,
          "name": "Laptop Pro 15",
          "brand": "TechMaster",
          "price": 1499.99,
          "stock": 77
        }
      },
      {
        "id": 32,
        "unitPrice": 199.99,
        "quantity": 6,
        "total": 1199.94,
        "product": {
          "id": 2,
          "name": "Wireless Headphones",
          "brand": "SoundWave",
          "price": 199.99,
          "stock": 164
        }
      }
    ]
  }
  ```

- `POST /api/sells/new` → Creates a new sell.

**Body Reqquest Example:**

```json
{
  "date": "2025-01-07T19:11:35.000",
  "client": {
    "id": 1
  },
  "sellDetails": [
    {
      "product": { "id": "1" },
      "quantity": 3,
      "unitPrice": "1499.99"
    },
    {
      "product": { "id": "2", "price": "199.99" },
      "quantity": 6
    }
  ]
}
```

**Example response:**

```json
{
  "id": 24,
  "date": "2025-01-07T19:11:35.000",
  "total": 5699.91,
  "client": {
    "id": 1,
    "name": "Juan",
    "lastName": "Pérez",
    "citizenId": "123456789"
  },
  "sellDetails": [
    {
      "id": 31,
      "unitPrice": 1499.99,
      "quantity": 3,
      "total": 4499.97,
      "product": {
        "id": 1,
        "name": "Laptop Pro 15",
        "brand": "TechMaster",
        "price": 1499.99,
        "stock": 77
      }
    },
    {
      "id": 32,
      "unitPrice": 199.99,
      "quantity": 6,
      "total": 1199.94,
      "product": {
        "id": 2,
        "name": "Wireless Headphones",
        "brand": "SoundWave",
        "price": 199.99,
        "stock": 164
      }
    }
  ]
}
```

- `PUT /api/sells/update/{id}` → Updates an order.

**Example body request:**

```json
{
  "id": 24,
  "date": "2025-01-07T19:11:35.000",
  "client": {
    "id": 1
  },
  "sellDetails": [
    {
      "product": { "id": "2", "price": "199.99" },
      "quantity": 4
    }
  ]
}
```

**Example before update:**

```json
{
  "id": 24,
  "date": "2025-01-07T19:11:35.000",
  "total": 5699.91,
  "client": {
    "id": 1,
    "name": "Juan",
    "lastName": "Pérez",
    "citizenId": "123456789"
  },
  "sellDetails": [
    {
      "id": 31,
      "unitPrice": 1499.99,
      "quantity": 3,
      "total": 4499.97,
      "product": {
        "id": 1,
        "name": "Laptop Pro 15",
        "brand": "TechMaster",
        "price": 1499.99,
        "stock": 77
      }
    },
    {
      "id": 32,
      "unitPrice": 199.99,
      "quantity": 6,
      "total": 1199.94,
      "product": {
        "id": 2,
        "name": "Wireless Headphones",
        "brand": "SoundWave",
        "price": 199.99,
        "stock": 164
      }
    }
  ]
}
```

**Example of response:**

```json
{
  "id": 24,
  "date": "2025-01-07T19:11:35.000",
  "total": 799.96,
  "client": {
    "id": 1,
    "name": "Juan",
    "lastName": "Pérez",
    "citizenId": "123456789"
  },
  "sellDetails": [
    {
      "id": 31,
      "unitPrice": 1499.99,
      "quantity": 4,
      "total": 5999.96,
      "product": {
        "id": 1,
        "name": "Laptop Pro 15",
        "brand": "TechMaster",
        "price": 1499.99,
        "stock": 76
      }
    },
    {
      "id": 32,
      "unitPrice": 199.99,
      "quantity": 4,
      "total": 799.96,
      "product": {
        "id": 2,
        "name": "Wireless Headphones",
        "brand": "SoundWave",
        "price": 199.99,
        "stock": 168
      }
    }
  ]
}
```

- `DELETE /api/sells/delete/{id}` → Deletes an order.

**Example response:**

```json
Sell removed successfully
```

### 👤 Customers

- `GET /api/clients` → Retrieves all clients.

  **Example response:**

  ```json
  [
    {
      "id": 1,
      "name": "Juan",
      "lastName": "Pérez",
      "citizenId": "123456789"
    },
    {
      "id": 2,
      "name": "María",
      "lastName": "González",
      "citizenId": "987654321"
    },
    {
      "id": 3,
      "name": "Carlos",
      "lastName": "Ramírez",
      "citizenId": "456789123"
    },
    {
      "id": 4,
      "name": "Laura",
      "lastName": "Fernández",
      "citizenId": "789123456"
    },
    {
      "id": 5,
      "name": "Antonio",
      "lastName": "Pérez",
      "citizenId": "123456782"
    },
    {
      "id": 6,
      "name": "Nombre 1",
      "lastName": "Apellido 1",
      "citizenId": "12345678X"
    },
    {
      "id": 7,
      "name": "Johm",
      "lastName": "Doe",
      "citizenId": "12345678X"
    }
  ]
  ```

- `POST /api/clients/new` → Creates a new client.

  **Example of body request:**

  ```json
  {
    "name": "Johm",
    "lastName": "Doe",
    "citizenId": "12345678X"
  }
  ```

  **Example response:**

  ```json
  {
    "id": 7,
    "name": "Johm",
    "lastName": "Doe",
    "citizenId": "12345678X"
  }
  ```

- `GET /api/clients/{id}` → Retrieves a client by ID.

  **Example response:**

  ```json
  {
    "id": 1,
    "name": "Juan",
    "lastName": "Pérez",
    "citizenId": "123456789"
  }
  ```

- `PUT /api/clients/update/{id}` → Updates a client.

  **Example request body:**

  ```json
  {
    "id": 1,
    "name": "Juan",
    "lastName": "Zambrota",
    "citizenId": "123456789"
  }
  ```

  **Example before update:**

  ```json
  {
    "id": 1,
    "name": "Juan",
    "lastName": "Pérez",
    "citizenId": "123456789"
  }
  ```

  **Example of response**

  ```json
  {
    "id": 1,
    "name": "Juan",
    "lastName": "Zambrota",
    "citizenId": "123456789"
  }
  ```

- `DELETE /api/clients/delete/{id}` → Deletes a client.

**Example of response**

```json
Sell removed successfully
```

---

## 📊 Entity-Relationship Diagram

Below is the **Entity-Relationship Diagram (ERD)** for the database structure:

![Entity-Relationship Diagram](https://github.com/jgonmor/api_store/blob/master/docs/resources/ER%20Store.png)

This diagram represents the relationships between Clients, Sales, Products, and SellDetails.

---

## 🤝 Contributing

1. Fork the repository.
2. Create a new branch: `git checkout -b my-feature`
3. Make your changes and commit: `git commit -m "Description of change"`
4. Push to your branch: `git push origin my-feature`
5. Open a **Pull Request**.

---

## 📜 License

This project is licensed under the **MIT** License.

---

# 🛒 API Store ES

API REST para la gestión de productos, órdenes y clientes en una tienda en línea.

## 📚 Índice

1. [Descripción](#-descripción)
2. [Instalación](#-instalación)
3. [Configuración](#-configuración)
4. [Uso](#-uso)
5. [Endpoints](#-endpoints)
   - [Productos](#-productos)
   - [Órdenes](#-órdenes)
   - [Clientes](#-clientes)
6. [Entity-Relationship Diagram](#-diagrama-entidad-relacion)
7. [Contribuir](#-contribuir)
8. [Licencia](#-licencia)

---

## 📝 Descripción

Esta API permite gestionar productos, órdenes y clientes. Está desarrollada en **Spring Boot** y sigue principios RESTful.

---

## 🛠 Instalación

### 1️⃣ Clonar el repositorio

```sh
git clone https://github.com/jgonmor/api_store.git
cd api_store
```

### 2️⃣ Configurar el entorno

Asegúrate de tener **Java 17+** y **Maven** instalados.

---

## ⚙️ Configuración

Crea un archivo `application.properties` en `src/main/resources/` con la siguiente configuración:

```properties
spring.application.name=api_store

# DataSource settings
spring.datasource.url=jdbc:mysql://${DB_IP}:${DB_PORT}/${DB_NAME}?useSSL=false&serverTimezone=UTC
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
server.port=8080

# Hibernate settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true
hibernate.transaction.jta.platform=enabled

spring.jackson.date-format=yyyy-MM-dd HH:mm:ss.SS

# Logging settings
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

---

## 🚀 Uso

Puedes probar la API con herramientas como **Postman** o **cURL**.

Ejemplo para obtener todos los productos:

```sh
curl -X GET http://localhost:8080/api/products
```

---

## 🔗 Endpoints

### 📦 Productos

- `GET /api/products` → Lista todos los productos.

  **Ejemplo de respuesta:**

  ```json
  [
    {
      "id": 1,
      "name": "Laptop Pro 15",
      "brand": "TechMaster",
      "price": 1499.99,
      "stock": 71
    },
    {
      "id": 2,
      "name": "Wireless Headphones",
      "brand": "SoundWave",
      "price": 199.99,
      "stock": 170
    },
    {
      "id": 3,
      "name": "Smartphone X200",
      "brand": "NextGen",
      "price": 999.99,
      "stock": 30
    },
    {
      "id": 4,
      "name": "Gaming Mouse",
      "brand": "PlayGear",
      "price": 59.99,
      "stock": 100
    }
  ]
  ```

- `POST /api/products` → Crea un nuevo producto.
  **Example body request:**

  ```json
  {
    "name": "4K Monitor",
    "brand": "ViewPoint",
    "price": 399.99,
    "stock": 15
  }
  ```

  **Example response:**

  ```json
  {
    "id": 5,
    "name": "4K Monitor",
    "brand": "ViewPoint",
    "price": 399.99,
    "stock": 15
  }
  ```

- `GET /api/products/{id}` → Obtiene un producto por ID.

**Example response:**

```json
{
  "id": 5,
  "name": "4K Monitor",
  "brand": "ViewPoint",
  "price": 399.99,
  "stock": 15
}
```

- `PUT /api/products/update/{id}` → Actualiza un producto.

**Example body request:**

```json
{
  "id": 5,
  "name": "4K Monitor",
  "brand": "ViewPoint",
  "price": 399.99,
  "stock": 20
}
```

**Example before update:**

```json
{
  "id": 5,
  "name": "4K Monitor",
  "brand": "ViewPoint",
  "price": 399.99,
  "stock": 15
}
```

**Example response:**

```json
{
  "id": 5,
  "name": "4K Monitor",
  "brand": "ViewPoint",
  "price": 399.99,
  "stock": 20
}
```

- `DELETE /api/products/delete/{id}` → Elimina un producto.

**Ejemplo de respuesta**

```json
 Product removed successfully
```

### 🛍️ Órdenes

- `GET /api/sells` → Lista todas las ventas.

  **Ejemplo de respuesta:**

  ```json
  [
    {
      "id": 20,
      "date": "2025-01-07T19:11:35.000",
      "total": 799.96,
      "client": {
        "id": 1,
        "name": "Juan",
        "lastName": "Pérez",
        "citizenId": "123456789"
      },
      "sellDetails": [
        {
          "id": 19,
          "unitPrice": 1499.99,
          "quantity": 4,
          "total": 5999.96,
          "product": {
            "id": 1,
            "name": "Laptop Pro 15",
            "brand": "TechMaster",
            "price": 1499.99,
            "stock": 80
          }
        }
      ]
    },
    {
      "id": 21,
      "date": "2025-01-07T19:11:35.000",
      "total": 5699.91,
      "client": {
        "id": 1,
        "name": "Juan",
        "lastName": "Pérez",
        "citizenId": "123456789"
      },
      "sellDetails": [
        {
          "id": 21,
          "unitPrice": 1499.99,
          "quantity": 3,
          "total": 4499.97,
          "product": {
            "id": 1,
            "name": "Laptop Pro 15",
            "brand": "TechMaster",
            "price": 1499.99,
            "stock": 80
          }
        },
        {
          "id": 22,
          "unitPrice": 199.99,
          "quantity": 6,
          "total": 1199.94,
          "product": {
            "id": 2,
            "name": "Wireless Headphones",
            "brand": "SoundWave",
            "price": 199.99,
            "stock": 170
          }
        }
      ]
    }
  ]
  ```

- `POST /api/sells/new` → Crea una nueva venta.

**Ejemplo del body de la petición:**

```json
{
  "date": "2025-01-07T19:11:35.000",
  "client": {
    "id": 1
  },
  "sellDetails": [
    {
      "product": { "id": "1" },
      "quantity": 3,
      "unitPrice": "1499.99"
    },
    {
      "product": { "id": "2", "price": "199.99" },
      "quantity": 6
    }
  ]
}
```

**Ejemplo de respuesta:**

```json
{
  "id": 24,
  "date": "2025-01-07T19:11:35.000",
  "total": 5699.91,
  "client": {
    "id": 1,
    "name": "Juan",
    "lastName": "Pérez",
    "citizenId": "123456789"
  },
  "sellDetails": [
    {
      "id": 31,
      "unitPrice": 1499.99,
      "quantity": 3,
      "total": 4499.97,
      "product": {
        "id": 1,
        "name": "Laptop Pro 15",
        "brand": "TechMaster",
        "price": 1499.99,
        "stock": 77
      }
    },
    {
      "id": 32,
      "unitPrice": 199.99,
      "quantity": 6,
      "total": 1199.94,
      "product": {
        "id": 2,
        "name": "Wireless Headphones",
        "brand": "SoundWave",
        "price": 199.99,
        "stock": 164
      }
    }
  ]
}
```

- `GET /api/sells/{id}` → Obtiene una venta por ID.

  **Ejemplo de respuesta:**

  ```json
  {
    "id": 24,
    "date": "2025-01-07T19:11:35.000",
    "total": 5699.91,
    "client": {
      "id": 1,
      "name": "Juan",
      "lastName": "Pérez",
      "citizenId": "123456789"
    },
    "sellDetails": [
      {
        "id": 31,
        "unitPrice": 1499.99,
        "quantity": 3,
        "total": 4499.97,
        "product": {
          "id": 1,
          "name": "Laptop Pro 15",
          "brand": "TechMaster",
          "price": 1499.99,
          "stock": 77
        }
      },
      {
        "id": 32,
        "unitPrice": 199.99,
        "quantity": 6,
        "total": 1199.94,
        "product": {
          "id": 2,
          "name": "Wireless Headphones",
          "brand": "SoundWave",
          "price": 199.99,
          "stock": 164
        }
      }
    ]
  }
  ```

- `PUT /api/sells/update/{id}` → Actualiza una venta.

**Ejemplo del body de la petición:**

```json
{
  "id": 24,
  "date": "2025-01-07T19:11:35.000",
  "client": {
    "id": 1
  },
  "sellDetails": [
    {
      "product": { "id": "2", "price": "199.99" },
      "quantity": 4
    }
  ]
}
```

**Ejemplo antes de actualizar:**

```json
{
  "id": 24,
  "date": "2025-01-07T19:11:35.000",
  "total": 5699.91,
  "client": {
    "id": 1,
    "name": "Juan",
    "lastName": "Pérez",
    "citizenId": "123456789"
  },
  "sellDetails": [
    {
      "id": 31,
      "unitPrice": 1499.99,
      "quantity": 3,
      "total": 4499.97,
      "product": {
        "id": 1,
        "name": "Laptop Pro 15",
        "brand": "TechMaster",
        "price": 1499.99,
        "stock": 77
      }
    },
    {
      "id": 32,
      "unitPrice": 199.99,
      "quantity": 6,
      "total": 1199.94,
      "product": {
        "id": 2,
        "name": "Wireless Headphones",
        "brand": "SoundWave",
        "price": 199.99,
        "stock": 164
      }
    }
  ]
}
```

**Ejemplo de la respuesta:**

```json
{
  "id": 24,
  "date": "2025-01-07T19:11:35.000",
  "total": 799.96,
  "client": {
    "id": 1,
    "name": "Juan",
    "lastName": "Pérez",
    "citizenId": "123456789"
  },
  "sellDetails": [
    {
      "id": 31,
      "unitPrice": 1499.99,
      "quantity": 4,
      "total": 5999.96,
      "product": {
        "id": 1,
        "name": "Laptop Pro 15",
        "brand": "TechMaster",
        "price": 1499.99,
        "stock": 76
      }
    },
    {
      "id": 32,
      "unitPrice": 199.99,
      "quantity": 4,
      "total": 799.96,
      "product": {
        "id": 2,
        "name": "Wireless Headphones",
        "brand": "SoundWave",
        "price": 199.99,
        "stock": 168
      }
    }
  ]
}
```

- `DELETE /api/sells/delete/{id}` → Elimina una venta.

**Ejemplo de respuesta**

```json
Product removed successfully
```

### 👤 Clientes

- `GET /api/clients` → Lista todos los clientes.

  **Ejemplo de respuesta:**

  ```json
  [
    {
      "id": 1,
      "name": "Juan",
      "lastName": "Pérez",
      "citizenId": "123456789"
    },
    {
      "id": 2,
      "name": "María",
      "lastName": "González",
      "citizenId": "987654321"
    },
    {
      "id": 3,
      "name": "Carlos",
      "lastName": "Ramírez",
      "citizenId": "456789123"
    },
    {
      "id": 4,
      "name": "Laura",
      "lastName": "Fernández",
      "citizenId": "789123456"
    },
    {
      "id": 5,
      "name": "Antonio",
      "lastName": "Pérez",
      "citizenId": "123456782"
    },
    {
      "id": 6,
      "name": "Nombre 1",
      "lastName": "Apellido 1",
      "citizenId": "12345678X"
    },
    {
      "id": 7,
      "name": "Johm",
      "lastName": "Doe",
      "citizenId": "12345678X"
    }
  ]
  ```

- `POST /api/clients/new` → Crea un nuevo cliente.

  **Ejemplo del body de la petición:**

  ```json
  {
    "name": "Johm",
    "lastName": "Doe",
    "citizenId": "12345678X"
  }
  ```

  **Ejemplo de respuesta**

  ```json
  {
    "id": 7,
    "name": "Johm",
    "lastName": "Doe",
    "citizenId": "12345678X"
  }
  ```

- `GET /api/clients/{id}` → Obtiene un cliente por ID.

  **Ejemplo de Respuesta:**

  ```json
  {
    "id": 1,
    "name": "Juan",
    "lastName": "Pérez",
    "citizenId": "123456789"
  }
  ```

- `PUT /api/clients/update/{id}` → Actualiza un cliente.

  **Example request body:**

  ```json
  {
    "id": 1,
    "name": "Juan",
    "lastName": "Zambrota",
    "citizenId": "123456789"
  }
  ```

  **Example before update:**

  ```json
  {
    "id": 1,
    "name": "Juan",
    "lastName": "Pérez",
    "citizenId": "123456789"
  }
  ```

  **Example of response**

  ```json
  {
    "id": 1,
    "name": "Juan",
    "lastName": "Zambrota",
    "citizenId": "123456789"
  }
  ```

- `DELETE /api/clients/delete/{id}` → Elimina un cliente.
  **Ejemplo de respuesta**

```json
Client removed successfully
```

---

## 📊 Diagrama Entidad-Relación

Abajo está el **Diagrama Entidad-Relación (ERD)** para la estructura de la base de datos:

![Entity-Relationship Diagram](https://github.com/jgonmor/api_store/blob/master/docs/resources/ER%20Store.png)

Este diagrama representa las relaciones entre Clients, Sales, Products, y SellDetails.

---

## 🤝 Contribuir

1. Haz un **fork** del repositorio.
2. Crea una nueva rama: `git checkout -b mi-mejora`
3. Realiza tus cambios y haz commit: `git commit -m "Descripción del cambio"`
4. Haz push a tu rama: `git push origin mi-mejora`
5. Abre un **Pull Request**.

---

## 📜 Licencia

Este proyecto está bajo la licencia **MIT**.
