# AndroidTest

## 🚀 Cómo compilar el proyecto

1. Clona este repositorio:
```bash
git clone https://github.com/Silverath/AndroidTest
.git
```

2. Abre el proyecto en Android Studio.

3. Sincroniza dependencias y ejecuta en un emulador o dispositivo real.

## 📝 Notas adicionales

Para empezar, dado el corto alcance del proyecto, he decidido desarrollar todo en un solo módulo, con arquitectura MVVM y para las vistas he usado también XML como se requería para la prueba. Cabe destacar también el uso de inyección de dependencias.

En cuanto a los requisitos de las tareas, he decidido añadir la eliminación o el cambio de estado de la tarea al formulario de edición de la tarea, ya que me resultaba más convincente que entrara dentro de lo que es la edición de la misma. Por otra parte, para la creación y edición de las mismas he creado dos vistas para cada caso de uso, ya que cada una tiene lógicas distintas:

- A la hora de la creación de las tareas por ejemplo, he puesto como norma que no se pueden crear tareas para una fecha que ya ha pasado, y cuando se crean, automáticamente se crean con el estado de "pendiente".

- Al editarlas, es cuando puedes cambiarlas a "completada" e incluso eliminarlas, y en este caso se puede modificar la fecha a una anterior.

Otra cosa importante a destacar es que se puede apreciar que en cada vista hay un manejo de estados de la iu distintas. Concretamente, para las vistas de editar tarea y carga de los personajes por medio de la api, hay tres tipos de estado: success, error y loading. Esto lo he hecho en estas vistas dado que hay acciones que pueden tardar algo, para que en ese pequeño instante el usuario no pueda hacer nada mientras se estén cargando datos (como setear los valores de la tarea en el formulario para editarlos o la carga de datos de la api), o para informar al usuario de algún error que esté fuera del alcance de la app (como el fallo al cargar los datos de la api por no tener conexión a Internet). También, lo he hecho de esta forma para mostrar diferentes formas de manejar los estados de las vistas, ya que este proyecto es para demostrar conocimientos.

Metiéndonos en los extras (los cuales se han realizado todos), para las notificaciones he decidido utilizar WorkManager, para poder mostrar las notificaciones en el background cada 15 minutos (tiempo mínimo para los intervalos según la documentación) si quedan 15 minutos o menos para que expire dicha tarea.

Para los unit tests, no me he molestado mucho y he hecho solo algunos para testear las funcionalidades del view model para añadir las tareas.
