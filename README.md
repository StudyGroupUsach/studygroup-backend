# studygroup-backend
Backend de Proyecto Studygroup para TBD 2016-1 USACH

URL Service:
-http://mongostudygroup-app4tbd.rhcloud.com/service

Para acceso a Carreras:
http://mongostudygroup-app4tbd.rhcloud.com/studygroup-backend/gestion_carreras/

GET: Consultar todas las carreras
http://mongostudygroup-app4tbd.rhcloud.com/studygroup-backend/gestion_carreras/

GET: Consultar una carrera por ID
http://mongostudygroup-app4tbd.rhcloud.com/studygroup-backend/gestion_carreras/ID

GET: Consultar todos los ramos
http://mongostudygroup-app4tbd.rhcloud.com/studygroup-backend/gestion_carreras/ramos

GET: Consultar un ramo por ID
http://mongostudygroup-app4tbd.rhcloud.com/studygroup-backend/gestion_carreras/ramos/ID

POST: Agregar Carrera //Administración
http://mongostudygroup-app4tbd.rhcloud.com/studygroup-backend/gestion_carreras/

Requiere un JSON del modo:

{
"nombreCarrera":"Nombre.de.la.Carrera"
}


POST: Agregar un Ramo de una Carrera.
http://mongostudygroup-app4tbd.rhcloud.com/studygroup-backend/gestion_carreras/carreras/ID

El ID es el de la carrera a la que pertenece el ramo a utilizar.

Requiere un JSON del modo:

{
"nombreRamo":"Nombre.del.Ramo"
}

El post solo funcionará si es que el id de carrera existe.

PUT: Modificación de un ramo, a partir de la ID del ramo
http://mongostudygroup-app4tbd.rhcloud.com/studygroup-backend/gestion_carreras/ramos/ID

{
"nombreRamo":"Nombre.del.Ramo"
}

Verificar no enviar un put con un null.


Desde este punto son solo para testing y no estarán en la versión final.

PUT: Modificación de una carrera
http://mongostudygroup-app4tbd.rhcloud.com/studygroup-backend/gestion_carreras/ID

DELETE: Eliminación de una carrera
http://mongostudygroup-app4tbd.rhcloud.com/studygroup-backend/gestion_carreras/ID


DELETE: Eliminación de un ramo
http://mongostudygroup-app4tbd.rhcloud.com/studygroup-backend/gestion_carreras/ramo/ID




Url Base
-http://mongostudygroup-app4tbd.rhcloud.com/service/usuarios

Notas adicionales, los pass se hashean a MD5 , si consultan los usuarios no se 
pueden leer los password.
################################################################################
CONSULTAR USUARIOS(GET)

No se necesitan argumentos para el get.

Get -->Url Base, devuelve un array de JSON, con los usuarios

NOTA:No sé preocupen de "grupoTemporals".

"{"apellidos":"PENELOPbbvnmE","descripcion":"GUINESS",
"grupoTemporals":[],"mail":"caca@usach.cl","nombre":"jjjhgfj","numeroMovil":"jjjj",
"pass":"50a7cd36f7ea1e0fb6d2ffd1befb04ad","usuarioId":6}"

################################################################################
CREAR USUARIOS(POST)

post -->Url Base
Argumentos:"{"apellidos":"Banana","descripcion":"Esta descripción es opcional",
"mail":"xxxx@usach.cl","nombre":"Pepe","numeroMovil":"797191919",
"pass":"pepebananaselacome"}]"

Nota , no sé puede ingresar el mismo mail una vez que se ingresa un usuario, ya 
que el sistema lo rechaza, el nombre los apellidos, mail y pass son obligatorios
de lo contrario no se crea el Usuario

################################################################################
Eliminar Usuarios del sistema(DELETE)

DELETE --> (Url Base) + usuarioId

No necesita argumentos, un ejemplo de Url:
http://mongostudygroup-app4tbd.rhcloud.com/service/usuarios/5

Estaríamos eliminado el usuario , con Id 5

################################################################################

Cambiar datos de Usuario(PUT)

PUT --> (Url Base) + usuarioId

Nota: no todos los valores se pueden cambiar , solo se puede cambiar el pass, 
el numero de telefono y la descripción.

A diferencia de los otros , en este caso los argumentos pueden ir completos o 
incompletos.

Por ejemplo si quiero cambiar el Pass entonces:

Put --> http://mongostudygroup-app4tbd.rhcloud.com/service/usuarios/5

argumento:

{"pass":"pepebananaselacome"}

y solo cambia el pass, tambien se puede poner mas de uno... eso

################################################################################
Magic

Happy Work , espero hacer el login pronto ya que tuve problemas , pero.. estoy 
muriendo ... así que denme tiempo por que ya queda poco, en el sentido de que 
parece que cada vez son menos los problemas con java EE, aunque no deja de 
sorprenderme que tan bugeado puede ser este framework....
