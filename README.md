1. Para compilar el proyecto usa siguiente comando:
   `mvn compile`
2. Para ejecutar el proyecto usa el siguiente comando:
   `mvn spring-boot:run`
   
* Para crear usuarios en el sistema debes hacer un llamado POST al endpoint
  http://localhost:8080/user
  
Ejemplo de body para el request
```javascript
{
   "name": "Juan Rodriguez",
   "email": "juan@rodriguez2.org",
   "password": "hunter2",
   "phones": [
      {
         "number": "1234567",
         "citycode": "1",
         "contrycode": "57"
      }
   ]
}
```
En el archivo application.properties hay dos regex que se pueden configurar.
El primero es para el formato de email y el segundo para la contraseña.

Para los valores iniciales se tomó un regex de ejemplo para el correo y para la contraseña el regex está validando 
mínimo 7 caracteres y que contenga al menos un número.