List of Endpoints

| # | URI                                | Method | Details                                                                              				| Comentarios |
| - | ---------------------------------- | ------ | --------------------------------------------------------------------------------------------------- | ----------- |
| 1 | /public/users/empresa/register     | POST   | Registra una empresa                                                                                |
| 2 | /public/users/postulantes/register | POST   | Registra un postulante                                                                              |
| 3 | /public/users/login 				 | POST   | Logea con usuario y contraseña y obtiene un token.                                                  | Recibe el bearer token, el cual identifica a la empresa o al postulante. Informa en la respuesta si el token pertenece a una empresa o no. |
| 4 | /publicacion/add 					 | POST   | Publica una oferta laboral para una empresa. Solo funciona para usuarios logeados como empresa      | Se debe enviar el PublicacionVO en el body y el token bearer para identificar a la empresa. |
| 5 | /publicacion/get 					 | GET    | En proceso…                                                                                         | En este endpoint se deben poder recibir filtros de categorías (revisar Word). Además de que si nos se reciben filtros, devolver todas las publicaciones. |
| 6 | /postulante/postular 				 | POST   | En proceso…                                                                                         |
| 7 | ... 								 |        |                                                                                                     |
| 8 | ... 								 |        |                                                                                                     |