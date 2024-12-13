# Feedback6 Eventos - Documentación del Proyecto

## Enlace al Repositorio

[Repositorio en GitHub](https://github.com/jmartter/Feedback6_eventos.git)

## Funcionalidades Implementadas

### 1. **Corrección en la Carga de Favoritos**
- Se han corregido errores en la carga de la información desde Firebase.
- Ahora, la imagen de "favorito" se muestra correctamente en la lista de novelas.

### 2. **Ubicación Simulada del Usuario**
- **Método de Generación de Ubicación Aleatoria:**
    - Se utiliza una clase personalizada (`RandomLocationUpdater`) para generar valores aleatorios de latitud y longitud cada 20 segundos.
    - La ubicación generada se pasa al buscador de Google Maps para su visualización.

### 3. **Clase Librería y Ubicación Aleatoria de Novelas**
- **Clase `Libreria`:**
    - Contiene una lista predefinida de librerías con sus direcciones en Madrid.
    - Cada vez que se añade una nueva novela, se asigna aleatoriamente una librería de esta lista.
    - La dirección de la librería seleccionada se guarda en el atributo `ubicacion` de la novela.
- **Actualización en Firebase:**
    - La ubicación de la librería seleccionada se guarda también en Firebase junto con los datos de la novela.

### 4. **Botón "Ubicación Novelas"**
- **Ubicaciones Asociadas a Novelas:**
    - Este botón permite al usuario visualizar una lista con las ubicaciones de todas las novelas asociadas a su cuenta.
    - Cada elemento de la lista representa una dirección de librería.
    - Al seleccionar una ubicación de la lista, se abre automáticamente en Google Maps para visualizar la dirección específica de esa novela.

### 5. **Visualización de Ubicaciones de Novelas en Google Maps**
- **Interacción con Google Maps:**
    - Al hacer clic en una ubicación de novela en el diálogo de ubicaciones, se abre Google Maps con la dirección de la novela seleccionada.
    - Esto permite al usuario ver la ubicación en el mapa de manera interactiva.