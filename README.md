List of Endpoints

| # | URI                                | Method | Details                                                                              				| Comentarios |
| - | ---------------------------------- | ------ | --------------------------------------------------------------------------------------------------- | ----------- |
| 1 | /empresa/register     		     | POST   | Registra una empresa                                                                                | |
| 2 | /postulantes/register 			 | POST   | Registra un postulante                                                                              | |
| 3 | /public/users/login 				 | POST   | Logea con usuario y contraseña y obtiene un token.                                                  | Recibe el bearer token, el cual identifica a la empresa o al postulante. Informa en la respuesta si el token pertenece a una empresa o no. |
| 4 | /empresa/{id}		 				 | GET	  | Obtiene los detalles de una empresa                                                                 ||
| 5 | /postulante/{id}		 			 | GET	  | Obtiene los detalles de un postulante                                                               ||
| 6 | /postulante/interes/add 			 | POST   | Agrega interes al postulante logeado.       														||
| 7 | /publicacion/add 					 | POST   | Publica una oferta laboral para una empresa. Solo funciona para usuarios logeados como empresa      | Se debe enviar el PublicacionVO en el body y el token bearer para identificar a la empresa. |
| 8 | /publicacion/{id}					 | GET    | Obtiene una publicacion a través del {id} de la publicacion                                         ||
| 9 | /publicacion/all					 | GET    | Obtiene todas las publicaciones							                                            ||
| 10 | /publicacion/all/{categoria}		 | GET    | Obtiene todas las publicaciones que sean de la {categoria}.							                ||
| 11 | /publicacion/empresa/{idEmpresa}  | GET    | Obtiene todas las publicaciones que correspondan a una empresa a través de su {idEmpresa}           ||
| 12 | /publicacion/open/{id}	 		 | POST   | Abre una publicación cerrada.                                       								| Solo puede abrirse si esta cerrada (y no abierta ni finalizada). Solo la empresa dueña de la publicación puede abrirla. |
| 13 | /postulacion/add	 				 | POST   | Registra una postulacion de un postulante a una publicación.                                        | La publicacion debe existir. El postulante debe existir. No debe el postulante ya estar postulado a la publicación. Sólo el mismo postulante loggeado puede postularse a una publicación (El token bearer debe coincidir con el id_postulante enviado en la request body.) |

Reportes

| # | URI                                | Method | Details                                                                              				| Comentarios |
| - | ---------------------------------- | ------ | --------------------------------------------------------------------------------------------------- | ----------- |
| 1 | /reporte/01/{mmYYYY}				 | GET    | Obtiene la oferta laboral que más postulantes recibió en un mes y/o año determinado.                | '112021' formato de mmYYYY para Noviembre del 2021. Incluye postulaciones entre el día inicial del mes y el día final del mes. Ejemplo: postulaciones que se hayan realizado entre el 1 de Febrero al 28 de Febrero del 2021 para '022021'. |
| 2 | /reporte/02/{N} 					 | GET    | Obtiene las primeras {N} categorías más seleccionadas en las ofertas laborales.                     ||
| 3 | /reporte/03 						 | GET    | Obtiene la publicación “más accesible”, considerando que “el más accesible” es aquel trabajo part-time, remoto, con menor cantidad de tareas asignadas y menor cantidad de requisitos. ||
| 4 | /reporte/04						 | GET    | Obtiene la publicación “más exigente”, considerando que es “la más exigente” aquella oferta que tiene mayor cantidad de requisitos. ||