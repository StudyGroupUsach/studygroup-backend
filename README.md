# studygroup-backend

Servicio REST de StudyGroup
=================

Servicio REST implementado en JavaEE para el grupo 4 de Taller de Base de Datos.

Requerimientos
--------------

* GlassFish
* Java EE 7
* Gradle

Instalación
-----------

Por agregar...

Listado de Servicios
--------------

ID? Indica si tiene un ID como entrada por parametro a traves de la URL

1. Servicio de Gestión de carreras
  * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/gestion_carreras
  * Servicios relacionados a las carreras y sus ramos.
  1. Encontrar todas las carreras
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/gestion_carreras
    * Tipo: GET
    * ID?: No tiene
    * Entrada: No tiene
    * Salida: JSON Listado de Carreras
  2. Datos de una carrera por ID
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/gestion_carreras/{ID}
    * Tipo: GET
    * ID?: ID de la carrera a buscar
    * Entrada: No tiene
    * Salida: Json de Carrera
  3. Agregar Carrera Nueva
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/gestion_carreras
    * Tipo: POST
    * ID?: No tiene
    * Entrada: JSON con los siguientes datos:
      * "nombreCarrera" = "STRING"
    * Salida: No tiene
  4. Editar una Carrera
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/gestion_carreras/{ID}
    * Tipo: PUT
    * ID?: ID de la carrera
    * Entrada: JSON de carrera:
      * "nombreCarrera" : "STRING"
    * Salida: No tiene
  5. Eliminar una carrera
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/gestion_carreras/{ID}
    * Tipo: DELETE
    * ID?: ID de la carrera
    * Entrada: No tiene
    * Salida: No tiene
  6. Listar todos los ramos existentes
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/gestion_carreras/ramos
    * Tipo: GET
    * ID?: No tiene
    * Entrada: No tiene
    * Salida: JSON Listado de ramos
  7. Obtener informacion de un ramos
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/gestion_carreras/ramos/{ID}
    * Tipo: GET
    * ID?: ID del ramo a consultar
    * Entrada: No tiene
    * Salida: JSON de Ramo
  8. Agregar un ramo a una carrera
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/gestion_carreras/carreras/{ID}
    * Tipo: POST
    * ID?: ID de la carrera a la que se agrega el ramo
    * Entrada: Json de Ramo:
      * "nombreRamo" : "STRING"
    * Salida: No tiene
  9. Cambiar nombre de Ramo
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/gestion_carreras/ramo/{ID}
    * Tipo: PUT
    * ID?: ID del ramo a modificar
    * Entrada: JSon de Ramo:
      * "nombreRamo" : "STRING"
    * Salida: No tiene
  10. Eliminar ramo
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/gestion_carreras/ramo/{ID}
    * Tipo: DELETE
    * ID?: ID de ramo a eliminar
    * Entrada: No tiene
    * Salida: No tiene
2. Servicios de Gestion de Relacion entre Usuarios
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/gestion_relacion_usuarios
    * Servicios relacionados a los usuarios, con quien puede hacer grupo, ramos que tiene seleccionados, agregar ramos seleccionados
    1. Obtener usuarios con los que hacer grupo (Realiza un match por TODOS los ramos en la lista de preferidos) CAMBIAR
      * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/gestion_relacion_usuarios/grupo_estudio/{ID}
      * Tipo: GET
      * ID?: ID del usuario que quiere encontrar a otros usuarios
      * Entrada: No tiene
      * Salida: JSON:
        * "usuarioId": ID
        * "usuario": [JSON Lista de usuarios con listas de preferencias]
    2. Obtener lista de preferencias de un usuario por su ID
      * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/gestion_relacion_usuarios/{ID}
      * Tipo: GET
      * ID?: ID de usuario
      * Entrada: No tiene
      * Salida: JSON de lista de preferencia del usuario:
        * "usuarioId": "ID"
        * "ramo":[JSON Lista de preferencia]
          * La lista de preferencia tiene forma:
            * "nombraRamo":"STRING",
            * "ramoID": "ramoID",
            * "carreraId": "carreraID",
            * "nombreCarrera": "nombreCarrera"
    3. Agregar una lista de encuentros previos.
      * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/gestion_relacion_usuarios/encuentros_previos/{ID}
      * Tipo: POST
      * ID?: ID de usuario
      * Entrada: JSON List{Usuario} cada usuario de la forma:
        * "nombre": "STRING",
        * "usuarioId": "ID",
        * "apellidos": "STRING",
        * "mail": "STRING"
      * Salida: JSON
    4. Agregar ramos a la lista de preferencias de un usuario
      * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/gestion_relacion_usuarios/{ID}
      * Tipo: POST
      * ID?: Id de usuario
      * Entrada: JSON List{ramo} cada ramo de la forma:
      * Salida:
    5. Eliminar ramos seleccionados
      * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/gestion_relacion_usuarios/{ID}
      * Tipo: DELETE
      * ID?: ID del usuario que tiene ya un listado de preferencias.
      * Entrada: No tiene
      * Salida: No tiene
    6.  Encontrar usuarios con los que estudiar segun una id de ramo
      * URL:http://mongostudygroup-app4tbd.rhcloud.com/servicios/gestion_relacion_usuarios/grupo_estudio_por_usuarios/{ID}/{ramoId}
      * Tipo: GET
      * ID?: ID de usuario y ID de ramo
      * Entrada: No tiene
      * Salida: JSON Lista de preferencia, ver punto 2
    7. Encontrar grupos con los que estudiar segun id de ramo
      * URL:http://mongostudygroup-app4tbd.rhcloud.com/servicios/gestion_relacion_usuarios/grupo_estudio_por_grupos/{ID}/{ramoId}
      * Tipo: GET
      * ID?: ID de usuario y ID de ramo
      * Entrada: No Tiene
      * Salida: JSON Lista de Grupos Temporales
3. Servicio de Grupos Temporales
  * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/grupos_temporales
  * Servicios para crear, editar y eliminar grupos temporales.
  1. Encontrar todos los grupos Temporales
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/grupos_temporales
    * Tipo: GET
    * ID?: No tiene
    * Entrada: No tiene
    * Salida: JSON Lista de Grupo Temporal de la forma:
      * "grupoTemporalId":"ID",
      * "descripcionTemporal":"STRING",
      * "duracionTemporal":"22:00:00", (TIME)
      * "inicioTemporal":"Sat Jun 11 02:23:21 CLT 2016", (DATE)
      * "idLugar":"ID",
      * "ramoId":"ID",
      * "nombreRamo":"STRING",
      * "usuarioId":"ID",
      * "nombre":"STRING"
  2. Obtener un grupo Temporal por su ID
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/grupos_temporales/{ID}
    * Tipo: GET
    * ID?: ID de Grupo Temporal
    * Entrada: No tiene
    * Salida: JSON Grupo Temporal
  3. Iniciar nuevo grupo temporal
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/grupos_temporales/{ID}
    * Tipo: POST
    * ID?: ID del usuario que lo crea
    * Entrada: JSON con:
      * "descripcionTemporal":"Descripcion del grupo y lugar",
      * "idLugar":"ID"
    * Salida: No tiene
  4.  Cambiar el lugar
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/grupos_temporales/cambiar_locacion/{ID}
    * Tipo: PUT
    * ID?: ID del grupo temporal
    * Entrada: JSON Lugar:
      * "idLugar":"ID"
    * Salida: No tiene
  5.  Modificar descripcion del grupo Temporal
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/grupos_temporales/{ID}
    * Tipo: PUT
    * ID?: ID del grupo temporal
    * Entrada: JSON Grupo Tempora:
      * "ramo":{"ramoId":"ID"},
      * "usuario":{"usuarioId":"ID"},
      * "descripcionTemporal":"STRING"
    * Salida:No tiene
  6.  Agregar integrantes al grupo
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/grupos_temporales/agregar_integrantes/{ID}
    * Tipo: PUT
    * ID?: ID del grupo temporal
    * Entrada: JSON List{Usuario} (minimo deben tener ID)
    * Salida: No tiene
  7.  Eliminar grupo
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/grupos_temporales/{ID}
    * Tipo: DELETE
    * ID?: ID Grupo Temporal
    * Entrada: No tiene
    * Salida: No tiene
  8.  Encontrar usuarios con los que estudiar segun una id de ramo
    * URL:http://mongostudygroup-app4tbd.rhcloud.com/servicios/grupos_temporales/eliminar_usuario/{ID}
    * Tipo: GET
    * ID?: ID de usuario
    * Entrada: JSON con grupoTemporaId
      * "grupoTemporaId":ID
    * Salida: No tiene
4. Servicio de login
  * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/login
  * Servicios para el inicio de sesion de un usuarioId
  1. Desloguear
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/login
    * Tipo: GET
    * ID?: No tiene
    * Entrada: No tiene
    * Salida: True o False
  2. Inicio de Sesion
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/login
    * Tipo: POST
    * ID?: No tiene
    * Entrada: JSON Usuario:
      * "mail":"CORREO@usach.cl",
      * "pass":"STRING"
    * Salida: No tiene
5. Servicios de Lugar
  * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/lugares
  * Creacion, edicion y eliminacion de lugares.
  1. Obtener listado de lugares
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/lugares
    * Tipo: GET
    * ID?: No tiene
    * Entrada: No tiene
    * Salida: JSON List{Lugar}
  2. Informacion de un lugar segun id
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/lugares/{ID}
    * Tipo: GET
    * ID?: ID de lugar
    * Entrada: No tiene
    * Salida: JSON Lugar:
      * "idLugar": "ID",
      * "latitudLugar": "STRING" (como entrada es long),
      * "longitudLugar":"STRING" (como entrada es long),
      * "grupoTemporals": [{}], Listado de grupos temporales
      * "grupoHorarios": [{}] Listado de grupos Horario
  3. Crear un lugar
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/lugares
    * Tipo: POST
    * ID?: No tiene
    * Entrada: JSON Lugar:
      * "longitudLugar": LONG,
      * "latitudLugar": LONG
    * Salida: No tiene
  4. Editar direcciones de lugar
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/lugares/{ID}
    * Tipo: PUT
    * ID?: ID de lugar
    * Entrada: JSON Lugar:
      * "longitudLugar": LONG,
      * "latitudLugar": LONG
    * Salida: No tiene
  5. Eliminar lugar
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/lugares/{ID}
    * Tipo: DELETE
    * ID?: ID de lugar
    * Entrada: No tiene
    * Salida: No tiene
6. Servicios de Usuario
  * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/usuarios
  * Servicios referente a la creacion, modificacion, eliminacion de usuarios y su geolocalizacion
  1. Encontrar todos los usuarios
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/usuarios
    * Tipo: GET
    * ID?: No tiene
    * Entrada: No tiene
    * Salida: JSON Lista de Usuario
  2.  Encontrar un usuario por ID
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/usuarios/{ID}
    * Tipo: GET
    * ID?: ID de Usuario
    * Entrada: No Tiene
    * Salida: JsON de Usuario:
      * "usuarioId":"ID",
      * "nombre":"STRING",
      * "apellidos":"STRING",
      * "descripcion":"STRING",
      * "mail":"CORREO@usach.cl",
      * "numeroMovil":"NUMEROS",
      * "pass":"STRING",
      * "carreraId":"ID",
      * "nombreCarrera":"STRING",
      * "grupoTemporals":[]
  3.  Crear nuevo usuario
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/usuarios
    * Tipo: POST
    * ID?: No tiene
    * Entrada: JSON Usuario
      * "nombre":"STRING",
      * "apellidos":"STRING",
      * "descripcion":"STRING",
      * "mail":"CORREO@usach.cl",
      * "numeroMovil":"NUMEROS",
      * "pass":"STRING",
      * "carrera":{"carreraId":"ID","nombreCarrera":"STRING"}
    * Salida: JSON Usuario
  4.  Modificar usuario
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/usuarios/{ID}
    * Tipo: PUT
    * ID?: ID de Usuario
    * Entrada: Json de Usuario:
      * "numeroMovil":"NUMERO",
      * "descripcion":"STRING",
      * "pass":"STRING",
      * "carrera":{"carreraId":"ID","nombreCarrera":"STRING"}
    * Salida: No tiene
  5.  Eliminar un usuario segun ID
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/usuarios/{ID}
    * Tipo: DELETE
    * ID?: ID de Usuario
    * Entrada: No tiene
    * Salida: No tiene
  6.  Listado de Ramos segun la carrera del usuario
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/usuarios/ramos_a_elegir/{ID}
    * Tipo: GET
    * ID?: Id de Usuario
    * Entrada: No tiene
    * Salida: JSON lista de ramos:
      * "nombreRamo":"STRING",
      * "ramoId":"ID",
      * "carreraId":"ID",
      * "nombreCarrera":"STRING"
  7. Obtener listado de ramos de un usuario
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/usuarios/{ID}/ramos
    * Tipo: GET
    * ID?: Id de Usuario
    * Entrada: No tiene
    * Salida: JSON con usuarioId y Ramos
      * "usuarioId": "ID",
      * "ramo": [{}]
  8.  Agregar ramos seleccionados (inicia en caso de que no tenga)
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/usuarios/{ID}/ramos
    * Tipo: POST
    * ID?: Id de Usuario
    * Entrada: JSON List ramo (ver Salida de punto 6)
    * Salida: JSON con usuarioId y Ramos
      * "usuarioId": "ID",
      * "ramo": [{}]
  9.  Modifica un listado de ramos seleccionados
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/usuarios/{ID}/ramos
    * Tipo: PUT
    * ID?: Id de Usuario
    * Entrada: JSON List ramo (ver Salida de punto 6)
    * Salida: JSON con usuarioId y Ramos
      * "usuarioId": "ID",
      * "ramo": [{}]
  10.  Eliminar listado de preferencias
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/usuarios/{ID}/ramos
    * Tipo: DELETE
    * ID?: Id de Usuario
    * Entrada: No tiene
    * Salida: {"historialUsuarioEliminado":"true"} o {"historialUsuarioEliminado":"false"}
  11.  Agrega la locacion de un usuario
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/usuarios/{ID}/geo
    * Tipo: POST
    * ID?: Id de Usuario
    * Entrada: JSON con location
      * "location":{type: "Point", coordinates: [ -73.97, 40.77 ] }
    * Salida: No tiene
  12.  Obtener la locacion de un usuario por su ID
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/usuarios/{ID}/geo
    * Tipo: GET
    * ID?: ID de Usuario
    * Entrada: No tiene
    * Salida: JSON locacion de usuario de la forma:
      * "location": null,
      * "date": "2016/06/11 20:36:50", (DATE)
      * "active": true,
      * "usuarioId": "ID"
  13.  Obtener la locacion de todos los usuario
    * URL: http://mongostudygroup-app4tbd.rhcloud.com/servicios/usuarios/geo
    * Tipo: GET
    * ID?: No tiene
    * Entrada: No tiene
    * Salida: JSON lista de locacion de usuario:
