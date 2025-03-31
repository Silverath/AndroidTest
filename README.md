# AndroidTest

## 🚀 Cómo compilar el proyecto

1. Clona este repositorio:
```bash
git clone https://github.com/tuusuario/nombre-del-repo.git
```

2. Abre el proyecto en Android Studio.

3. Sincroniza dependencias y ejecuta en un emulador o dispositivo real.

## 📝 Notas adicionales

Para empezar, dado el corto alcance del proyecto, he decidido desarrollar todo en un solo módulo, con arquitectura MVVM y para las vistas he usado también XML como se requería para la prueba. Cabe destacar también el uso de inyección de dependencias.

En cuanto a los requisitos de las tareas, he decidido añadir la eliminación o el cambio de estado de la tarea al formulario de edición de la tarea, ya que me resultaba más convincente que entrara dentro de lo que es la edición de la misma. Por otra parte, para la creación y edición de las mismas he creado dos vistas para cada caso de uso, ya que cada una tiene lógicas distintas:

    - A la hora de la creación de las tareas por ejemplo, he puesto como norma que no se pueden crear tareas para una fecha que ya ha pasado, y cuando se crean, automáticamente se crean con el estado de "pendiente".

    - Al editarlas, es cuando puedes cambiarlas a "completada" e incluso eliminarlas, y en este caso se puede modificar la fecha a una anterior.

Con respecto al consumo de la api de Rick y Morty, para la acción de añadir a favoritos, he puesto en cada entrada de los personajes un botón para ello y nada más, ya que en los requisitos solo dice dsfs
