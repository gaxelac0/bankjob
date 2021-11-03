List of Endpoints

| # | URI                                | Method | Details                                                                              				| Comentarios |
| - | ---------------------------------- | ------ | --------------------------------------------------------------------------------------------------- | ----------- |
| 1 | /public/users/empresa/register     | POST   | Registra una empresa                                                                                | |
| 2 | /public/users/postulantes/register | POST   | Registra un postulante                                                                              | |
| 3 | /public/users/login 				 | POST   | Logea con usuario y contraseña y obtiene un token.                                                  | Recibe el bearer token, el cual identifica a la empresa o al postulante. Informa en la respuesta si el token pertenece a una empresa o no. |
| 4 | /publicacion/add 					 | POST   | Publica una oferta laboral para una empresa. Solo funciona para usuarios logeados como empresa      | Se debe enviar el PublicacionVO en el body y el token bearer para identificar a la empresa. |
| 5 | /publicacion/{id}					 | GET    | Obtiene una publicacion a través del {id} de la publicacion                                         ||
| 6 | /publicacion/all					 | GET    | Obtiene todas las publicaciones							                                            ||
| 7 | /publicacion/all/{categoria}		 | GET    | Obtiene todas las publicaciones que sean de la {categoria}.							                ||
| 8 | /publicacion/empresa/{idEmpresa}   | GET    | Obtiene todas las publicaciones que correspondan a una empresa a través de su {idEmpresa}           ||
| 9 | /postulacion/add	 				 | POST   | Registra una postulacion de un postulante a una publicación.                                        | La publicacion debe existir. El postulante debe existir. No debe el postulante ya estar postulado a la publicación. Sólo el mismo postulante loggeado puede postularse a una publicación (El token bearer debe coincidir con el id_postulante enviado en la request body.) |
| 10 | ... 								 |        |                                                                                                     ||
| 11 | ... 								 |        |                                                                                                     ||