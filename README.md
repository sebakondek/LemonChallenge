# Lemon Challenge

## API para [Ejercicio FuckOff](https://thorn-paperback-665.notion.site/L2-Coding-Challenge-f55f26875e1c4871b528f07e109c0e52)

La API disponibiliza un único endpoint que consume el servicio de [Fuck Off as a Service](https://www.foaas.com/)


## Para ejecutar localmente:
```bash
./gradlew run
```
## Para probar:
### GET
```bash
curl --location --request GET 'localhost:8080/message' \
--header 'x-caller-id: 1234'
```
Es obligatorio el envío del header x-caller-id y actualmente el único usuario permitido para operar es el `1234`


### Posibles Respuestas

```bash
200 - El flujo operó correctamente y se respondió el mensaje del servicio.

400 - validation_exception, el header x-caller-id no puede ser nulo.

401 - not_authorized, el caller-id no está autorizado. Actualmente solamente puede operar el usuario 1234.

420 - enhance_your_calm, se excedió el límite de veces que se puede solicitar un mensaje cada 10 segundos.

500 - NumberFormatException, el header x-caller-id tiene que ser solo numérico.
    - RepositoryException, ocurrió algún error inesperado con los repositorios. Más info en la respuesta.
    - JSONException, ocurrió algún error al intentar deserealizar la respuesta del servicio FuckOff. Mas info en la respuesta.
```

## Arquitectura
En cuanto a la arquitectura decidí ir por Clean Architecture. Entre los principales beneficios de esta arquitectura:
* Código modularizado, permite tener casos de uso con responsabilidades unitarias sin acople de código.
* Mayor facilidad y cobertura a la hora de testear.
* Código flexible y reutilizable. Al cumplir funciones específicas y ser agnósticos al resto de implementaciones, se puede extraer y reutilizar fácilmente.
* Facilidad y transparencia casi completa a la hora de querer de cambiar de algún proveedor o servicio.

## Framework
Para el uso de framework elegí [spark-java](https://sparkjava.com/) ya que es ligero, sencillo de implementar y me permite tener el control de todo el flujo.

## BD
Se utiliza una BD H2 en memoria, por lo que si se reincia la aplicación la información no se persiste.


### TODO:
* Pulir manejo de errores.
* Subir test coverage.